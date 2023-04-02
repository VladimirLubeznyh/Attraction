package com.example.attractions.presentation.createphoto

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.attractions.domain.AddPhotoUseCase
import com.example.attractions.data.local.entity.PhotoEntity
import kotlinx.coroutines.launch
import javax.inject.Inject

class CreatePhotoViewModel @Inject constructor(
    private val addPhotoUseCase: AddPhotoUseCase,
) : ViewModel() {
    fun addPhotoToDataBase(photoUrl: String, photoDate: String) {
        viewModelScope.launch {
            addPhotoUseCase.execute(PhotoEntity(photoUrl, photoDate))
        }
    }
}
