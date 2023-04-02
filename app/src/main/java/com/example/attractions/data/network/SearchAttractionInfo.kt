package com.example.attractions.data.network

import com.example.attractions.BuildConfig
import com.example.attractions.data.network.response.ResponseAttraction
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SearchAttractionInfo {

    @GET("/0.1/{lang}/places/xid/{xid}")
    suspend fun getAttractionsInfo(
        @Path("lang") lang: String = LANG_EN,
        @Path("xid") xid: String,
        @Query("apikey") apikey:String = API_KEY

    ): ResponseAttraction

    companion object{
        const val LANG_EN = "en"
        const val API_KEY = BuildConfig.API_KEY
    }
}
