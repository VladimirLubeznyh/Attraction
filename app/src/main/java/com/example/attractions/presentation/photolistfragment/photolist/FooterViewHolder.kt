package com.example.attractions.presentation.photolistfragment.photolist

import androidx.recyclerview.widget.RecyclerView
import com.example.attractions.databinding.LastItemAddPhotoBinding

class FooterViewHolder(private val binding: LastItemAddPhotoBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(onClick: () -> Unit) {
        binding.root.setOnClickListener {
            onClick()
        }
    }
}