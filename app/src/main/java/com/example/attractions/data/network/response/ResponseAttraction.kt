package com.example.attractions.data.network.response

import com.google.gson.annotations.SerializedName

data class ResponseAttraction(
    @SerializedName("xid") val xid: String,
    @SerializedName("name") val name: String,
    @SerializedName("image") val image: String,
    @SerializedName("wikipedia_extracts") val wikipediaExtracts: ResponseWikipediaExtracts?,
    @SerializedName("info") val info: ResponseAttractionInfo?,
)

data class ResponseAttractionInfo(
    @SerializedName("descr") val descr: String,
)

data class ResponseWikipediaExtracts(
    @SerializedName("text") val text: String,
)

