package com.sdv.brewerieslist.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sdv.brewerieslist.data.breweries.Breweries
import com.sdv.brewerieslist.data.breweries.db.BreweriesDao

/**
 * Created by FrostEagle on 20.03.2020.
 * My Email: denisshakhov21@gmail.com
 * Skype: lifeforlight
 */
@Database(entities = [Breweries::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract val breweriesDao: BreweriesDao
}