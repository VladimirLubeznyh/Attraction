package com.example.attractions.presentation.photolistfragment

import android.widget.Toast
import com.example.attractions.R
import com.example.attractions.presentation.createphoto.CreatePhotoFragment

fun CreatePhotoFragment.showToastPhotoSave(savedUrl: String) {
    Toast.makeText(
        requireContext(),
        requireContext().getString(R.string.photo_saved_on, savedUrl),
        Toast.LENGTH_LONG
    ).show()
}

fun CreatePhotoFragment.showToastPhotoSaveFailed(errorMassage: String?) {
    Toast.makeText(
        requireContext(),
        this.getString(R.string.photo_failed, errorMassage),
        Toast.LENGTH_LONG
    ).show()
}