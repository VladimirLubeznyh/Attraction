package com.example.attractions.domain

import com.example.attractions.data.Repository
import com.example.attractions.entity.PreviewAttractions
import javax.inject.Inject

class GetAttractionsListUseCase @Inject constructor(private val repository: Repository) {
    suspend fun execute(lang: String, lon: Double, lat: Double): List<PreviewAttractions>? {
        val data:List<PreviewAttractions>? = try {
            repository.getListAttractions(lang = lang, lon = lon, lat = lat)
        }catch (e:Exception){
            null
        }
        return data
    }
}