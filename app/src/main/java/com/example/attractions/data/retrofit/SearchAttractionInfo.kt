package com.example.attractions.data.retrofit

import com.example.attractions.entity.Attractions
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SearchAttractionInfo {
    @GET("/0.1/{lang}/places/xid/{xid}")
    suspend fun getAttractionsInfo(
        @Path("lang") lang: String = "en",
        @Path("xid") xid: String,
        @Query("apikey") apikey:String ="5ae2e3f221c38a28845f05b615a106998e9353fbfc17ebf2ba1320a4"

    ): Response<Attractions>
}