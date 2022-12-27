package com.example.attractions.entity

import com.google.gson.annotations.SerializedName


data class PreviewAttractions(
    @SerializedName("name") val name: String,
    @SerializedName("osm") val osm: String,
    @SerializedName("xid") val xid: String,
    @SerializedName("wikidata") val wikidata: String,
    @SerializedName("kind") val kind: String,
    @SerializedName("point") val point: PointAttractions,
)

data class PointAttractions(
    @SerializedName("lon") val lon: Double,
    @SerializedName("lat") val lat: Double,
)

data class Attractions(
    @SerializedName("xid") val xid: String,
    @SerializedName("name") val name: String,
    @SerializedName("address") val address: AttractionsAddress,
    @SerializedName("rate") val rate: String,
    @SerializedName("point") val point: PointAttractions,
    @SerializedName("wikipedia") val wikipedia: String,
    @SerializedName("image") val image: String,
    @SerializedName("wikipedia_extracts") val wikipediaExtracts: WikipediaExtracts?,
    @SerializedName("info") val info: AttractionsInfo?,
)

data class AttractionsInfo(
    @SerializedName("descr") val descr: String,
)

data class AttractionsAddress(
    @SerializedName("city") val city: String,
    @SerializedName("road") val road: String,
    @SerializedName("house") val house: String,
    @SerializedName("state") val state: String,
    @SerializedName("suburb") val suburb: String,
    @SerializedName("country") val country: String,
    @SerializedName("postcode") val postcode: String,
    @SerializedName("country_code") val countryCode: String,
    @SerializedName("house_number") val houseNumber: String,
    @SerializedName("neighbourhood") val neighbourhood: String,
    @SerializedName("state_district") val stateDistrict: String,
)

data class WikipediaExtracts(
    @SerializedName("title") val title: String,
    @SerializedName("text") val text: String,
)

