package com.example.attractions.data.retrofit

import com.example.attractions.entity.PreviewAttractions
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SearchAttractionList {
    @GET("/0.1/{lang}/places/radius")
    suspend fun getAttractionList(
        @Path("lang") lang: String = "en",
        @Query("radius") radius: Int = 150_000,
        @Query("lon") lon: Double =  37.617698,
        @Query("lat") lat: Double = 55.755864,
        @Query("rate") rate: String = "2",
        @Query("apikey") apikey:String ="5ae2e3f221c38a28845f05b615a106998e9353fbfc17ebf2ba1320a4",
        @Query("format")format:String="json"
    ): Response<List<PreviewAttractions>>
}

