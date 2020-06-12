package com.sdv.brewerieslist.di

import android.app.Application
import androidx.room.Room
import com.sdv.brewerieslist.data.breweries.BreweriesHttpApi
import com.sdv.brewerieslist.data.breweries.db.BreweriesDao
import com.sdv.brewerieslist.data.breweries.repository.BreweriesRepository
import com.sdv.brewerieslist.data.db.AppDatabase
import com.squareup.moshi.Moshi
import okhttp3.Cache
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


val apiModule = module {
    fun provideUserApi(retrofit: Retrofit): BreweriesHttpApi {
        return retrofit.create(BreweriesHttpApi::class.java)
    }

    single { provideUserApi(get()) }
}

val netModule = module {
    fun provideCache(application: Application): Cache {
        val cacheSize = 10 * 1024 * 1024
        return Cache(application.cacheDir, cacheSize.toLong())
    }

    fun provideHttpClient(cache: Cache): OkHttpClient {
        val okHttpClientBuilder = OkHttpClient.Builder().cache(cache)
        return okHttpClientBuilder.build()
    }

    fun provideGson(): Moshi {
        return Moshi.Builder().build()
    }

    fun provideRetrofit(factory: Moshi, client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.openbrewerydb.org/")
            .addConverterFactory(MoshiConverterFactory.create(factory))
            .client(client)
            .build()
    }

    single { provideCache(androidApplication()) }
    single { provideHttpClient(get()) }
    single { provideGson() }
    single { provideRetrofit(get(), get()) }
}

val databaseModule = module {

    fun provideDatabase(application: Application): AppDatabase {
        return Room.databaseBuilder(application, AppDatabase::class.java, "eds.database")
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }

    fun provideDao(database: AppDatabase): BreweriesDao {
        return database.breweriesDao
    }
    single { provideDatabase(androidApplication()) }
    single { provideDao(get()) }
}

val repositoryModule = module {
    fun provideUserRepository(api: BreweriesHttpApi, breweriesDao: BreweriesDao): BreweriesRepository {
        return BreweriesRepository(api, breweriesDao)
    }
    single { provideUserRepository(get(), get()) }
}