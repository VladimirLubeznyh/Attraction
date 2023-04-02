package com.example.attractions.data

import android.content.Context
import com.example.attractions.R
import com.example.attractions.data.network.response.ResponseAttraction
import com.example.attractions.data.network.response.ResponsePreviewAttraction
import com.example.attractions.domain.model.Attraction
import com.example.attractions.domain.model.PreviewAttraction

fun ResponsePreviewAttraction.mapToPreviewAttraction(): PreviewAttraction =
    PreviewAttraction(xid, point.lon, point.lat)

fun ResponseAttraction.mapToAttraction(context: Context): Attraction =
    Attraction(
        name,
        image,
        info?.descr ?: wikipediaExtracts?.text ?: context.getString(R.string.nothing)
    )
