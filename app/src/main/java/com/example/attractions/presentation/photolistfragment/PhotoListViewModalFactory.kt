package com.example.attractions.presentation.photolistfragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException
import javax.inject.Inject

class PhotoListViewModalFactory @Inject constructor(private val photoListViewModel: PhotoListViewModel) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(photoListViewModel::class.java)) {
            return photoListViewModel as T
        } else {
            throw IllegalArgumentException("Unknown class name")
        }
    }
}