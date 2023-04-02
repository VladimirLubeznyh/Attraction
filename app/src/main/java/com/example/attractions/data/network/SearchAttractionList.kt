package com.example.attractions.data.network

import com.example.attractions.BuildConfig
import com.example.attractions.data.network.response.ResponsePreviewAttraction
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SearchAttractionList {

    @GET("/0.1/{lang}/places/radius")
    suspend fun getAttractionList(
        @Path("lang") lang: String = LANG_EN,
        @Query("radius") radius: Int = DEFAULT_RADIUS,
        @Query("lon") lon: Double = DEFAULT_LON,
        @Query("lat") lat: Double = DEFAULT_LAT,
        @Query("rate") rate: String = RATE,
        @Query("apikey") apikey: String = API_KEY,
        @Query("format") format: String = FORMAT_JSON
    ): List<ResponsePreviewAttraction>

    private companion object {
        const val LANG_EN = "en"
        const val API_KEY = BuildConfig.API_KEY
        const val FORMAT_JSON = "json"
        const val RATE = "2"
        const val DEFAULT_LAT = 55.755864
        const val DEFAULT_LON = 37.617698
        const val DEFAULT_RADIUS = 150_000
    }
}
