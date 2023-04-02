package com.example.attractions.domain

import com.example.attractions.data.Repository
import com.example.attractions.data.mapToPreviewAttraction
import com.example.attractions.domain.model.PreviewAttraction
import javax.inject.Inject

class GetAttractionsPreviewListUseCase @Inject constructor(private val repository: Repository) {

    suspend fun execute( lon: Double, lat: Double): List<PreviewAttraction> =
        repository.getListAttractions(lon = lon, lat = lat)
}
