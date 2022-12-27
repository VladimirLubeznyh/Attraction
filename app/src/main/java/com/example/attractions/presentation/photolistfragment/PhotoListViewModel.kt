package com.example.attractions.presentation.photolistfragment

import androidx.lifecycle.ViewModel
import com.example.attractions.domain.GetPhotoListUseCase

import javax.inject.Inject

class PhotoListViewModel @Inject constructor(getPhotoListUseCase: GetPhotoListUseCase) :
    ViewModel() {
    val listPhotoEntity = getPhotoListUseCase.execute()
}