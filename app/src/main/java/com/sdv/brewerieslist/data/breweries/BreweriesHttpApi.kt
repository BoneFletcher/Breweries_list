package com.sdv.brewerieslist.data.breweries

import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by FrostEagle on 19.03.2020.
 * My Email: denisshakhov21@gmail.com
 * Skype: lifeforlight
 */
val PER_PAGE: Int = 30

interface BreweriesHttpApi {
    @GET("breweries")
    suspend fun getBreweries(@Query("page") page: Int?, @Query("per_page") per_page: Int?): List<Breweries>

    @GET("breweries")
    suspend fun getBreweriesByName(@Query("by_name") per_page: String?): List<Breweries>
}