package com.example.attractions.presentation.createphoto

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import java.lang.IllegalArgumentException
import javax.inject.Inject

class CreatePhotoViewModelFactory @Inject constructor(private val fullScreenViewModel: CreatePhotoViewModel) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        if (modelClass.isAssignableFrom(CreatePhotoViewModel::class.java)) {
            return fullScreenViewModel as T
        } else {
            throw IllegalArgumentException("Unknown class name")
        }
    }
}