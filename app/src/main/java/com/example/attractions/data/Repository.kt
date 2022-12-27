package com.example.attractions.data

import android.util.Log
import com.example.attractions.data.db.PhotoDao
import com.example.attractions.data.retrofit.RetrofitServices
import com.example.attractions.entity.Attractions
import com.example.attractions.entity.PhotoEntity
import com.example.attractions.entity.PreviewAttractions
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class Repository @Inject constructor(
    private val photoDao: PhotoDao,
    private val retrofitServices: RetrofitServices
) {
    fun getPhotoList(): Flow<List<PhotoEntity>> {
        return photoDao.getAllPhoto()
    }

    suspend fun addPhoto(photo: PhotoEntity) {
        photoDao.insert(photo)
    }

    suspend fun getListAttractions(
        lang: String,
        lon: Double,
        lat: Double
    ): List<PreviewAttractions>? {
        val response = retrofitServices.searchListAttractions.getAttractionList(
            lang = lang,
            lon = lon,
            lat = lat
        )
        Log.d("REST", response.headers().toString() )
        return response.body()
    }

    suspend fun getInfoAttractions(lang: String, xid: String): Attractions? {
        val response =
            retrofitServices.searchInfoAttractions.getAttractionsInfo(lang = lang, xid = xid)
        Log.d("REST", response.headers().toString() )
        return response.body()
    }
}