package com.example.attractions.data

import android.content.Context
import com.example.attractions.data.local.PhotoDao
import com.example.attractions.data.network.RetrofitServices
import com.example.attractions.data.network.response.ResponseAttraction
import com.example.attractions.data.local.entity.PhotoEntity
import com.example.attractions.data.network.response.ResponsePreviewAttraction
import com.example.attractions.di.annotation.IoDispatcher
import com.example.attractions.domain.model.Attraction
import com.example.attractions.domain.model.PreviewAttraction
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject

class Repository @Inject constructor(
    private val photoDao: PhotoDao,
    private val retrofitServices: RetrofitServices,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val context: Context
) {
    private val lang by lazy {
        if (Locale.getDefault().toString() == "ru_RU") "ru" else "en"
    }

    fun getPhotoList(): Flow<List<PhotoEntity>> = photoDao.getAllPhoto()

    suspend fun addPhoto(photo: PhotoEntity) = withContext(ioDispatcher) {
        photoDao.insert(photo)
    }

    suspend fun getListAttractions(
        lon: Double,
        lat: Double
    ): List<PreviewAttraction> = withContext(ioDispatcher) {
        retrofitServices.searchListAttractions.getAttractionList(lang = lang, lon = lon, lat = lat)
            .map { it.mapToPreviewAttraction() }
    }

    suspend fun getInfoAttraction(xid: String): Attraction =
        withContext(ioDispatcher) {
            retrofitServices.searchInfoAttractions.getAttractionsInfo(lang = lang, xid = xid)
                .mapToAttraction(context)
        }
}
