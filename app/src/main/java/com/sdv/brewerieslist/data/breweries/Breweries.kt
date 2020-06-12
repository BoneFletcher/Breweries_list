package com.sdv.brewerieslist.data.breweries


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
@Entity
data class Breweries(
    @Json(name = "brewery_type")
    val breweryType: String?,
    @Json(name = "city")
    val city: String?,
    @Json(name = "country")
    val country: String?,
    @PrimaryKey
    @Json(name = "id")
    val id: Int?,
    @Json(name = "latitude")
    val latitude: String?,
    @Json(name = "longitude")
    val longitude: String?,
    @Json(name = "name")
    val name: String?,
    @Json(name = "phone")
    val phone: String?,
    @Json(name = "postal_code")
    val postalCode: String?,
    @Json(name = "state")
    val state: String?,
    @Json(name = "street")
    val street: String?,
    @Json(name = "updated_at")
    val updatedAt: String?,
    @Json(name = "website_url")
    val websiteUrl: String?
)