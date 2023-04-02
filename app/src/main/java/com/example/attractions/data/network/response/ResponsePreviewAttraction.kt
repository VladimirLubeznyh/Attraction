package com.example.attractions.data.network.response

import com.google.gson.annotations.SerializedName

data class ResponsePreviewAttraction(
    @SerializedName("xid") val xid: String,
    @SerializedName("point") val point: ResponsePointAttraction,
)

data class ResponsePointAttraction(
    @SerializedName("lon") val lon: Double,
    @SerializedName("lat") val lat: Double,
)
