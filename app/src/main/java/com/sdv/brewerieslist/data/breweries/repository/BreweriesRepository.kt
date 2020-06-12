package com.sdv.brewerieslist.data.breweries.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sdv.brewerieslist.data.breweries.Breweries
import com.sdv.brewerieslist.data.breweries.BreweriesHttpApi
import com.sdv.brewerieslist.data.breweries.db.BreweriesDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Created by FrostEagle on 19.03.2020.
 * My Email: denisshakhov21@gmail.com
 * Skype: lifeforlight
 */
class BreweriesRepository(private val breweriesHttpApi: BreweriesHttpApi, private val breweriesDao: BreweriesDao) {

    suspend fun getAllBreweries(page: Int, perPage: Int): List<Breweries> {
            val breweries = breweriesHttpApi.getBreweries(page, perPage)
            breweriesDao.add(breweries)
        return breweries
    }

    suspend fun getAllLocalBreweries(): List<Breweries> = withContext(Dispatchers.IO) {
         breweriesDao.getAllBreweries()
    }

    suspend fun searchRepo(q: String): LiveData<List<Breweries>> {
        return MutableLiveData(breweriesHttpApi.getBreweriesByName(q))
    }
}