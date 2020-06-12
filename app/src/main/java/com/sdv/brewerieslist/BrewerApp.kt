package com.sdv.brewerieslist

import android.app.Application
import com.sdv.brewerieslist.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

/**
 * Created by FrostEagle on 19.03.2020.
 * My Email: denisshakhov21@gmail.com
 * Skype: lifeforlight
 */

class BrewerApp: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@BrewerApp)
            modules(listOf(netModule, apiModule, repositoryModule, databaseModule,viewModelModule))
        }
    }
}