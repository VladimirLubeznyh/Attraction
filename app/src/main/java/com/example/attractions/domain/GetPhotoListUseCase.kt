package com.example.attractions.domain

import com.example.attractions.data.Repository
import com.example.attractions.data.local.entity.PhotoEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPhotoListUseCase @Inject constructor(private val repository: Repository) {
    fun execute(): Flow<List<PhotoEntity>> {
        return repository.getPhotoList()
    }
}
