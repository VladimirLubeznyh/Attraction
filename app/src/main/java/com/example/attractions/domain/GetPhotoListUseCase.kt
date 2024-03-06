package com.example.attractions.domain

import com.example.attractions.data.Repository
import com.example.attractions.data.local.entity.PhotoEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.zip
import javax.inject.Inject

class GetPhotoListUseCase @Inject constructor(private val repository: Repository) {
    fun execute(): Flow<List<PhotoEntity>> {
        return repository.getPhotoList().map { it.toMutableList().apply { add(PhotoEntity("","",PhotoEntity.FOOTER)) }.toList() }
    }
}
