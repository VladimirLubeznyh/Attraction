package com.example.attractions.data.retrofit

import retrofit2.Retrofit


class RetrofitServices(retrofit: Retrofit) {
    val searchListAttractions: SearchAttractionList = retrofit.create(SearchAttractionList::class.java)
    val searchInfoAttractions: SearchAttractionInfo = retrofit.create(SearchAttractionInfo::class.java)
}