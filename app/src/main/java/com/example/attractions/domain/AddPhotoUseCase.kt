package com.example.attractions.domain

import com.example.attractions.data.Repository
import com.example.attractions.data.local.entity.PhotoEntity
import javax.inject.Inject

class AddPhotoUseCase @Inject constructor(private val repository: Repository) {

    suspend fun execute(photo: PhotoEntity) {
        repository.addPhoto(photo)
    }
}
