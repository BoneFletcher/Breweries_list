package com.sdv.brewerieslist.data.breweries.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sdv.brewerieslist.data.breweries.Breweries

/**
 * Created by FrostEagle on 20.03.2020.
 * My Email: denisshakhov21@gmail.com
 * Skype: lifeforlight
 */

@Dao
interface BreweriesDao {
    @Query("SELECT * FROM breweries")
    suspend fun getAllBreweries(): List<Breweries>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(breweries: List<Breweries>)
}