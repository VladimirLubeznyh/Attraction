package com.example.attractions.presentation.photolistfragment.photolist

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.attractions.data.local.entity.PhotoEntity
import com.example.attractions.databinding.PhotoItemBinding

class ItemViewHolder(private val binding: PhotoItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(photo: PhotoEntity) {
        binding.dateText.text = photo.photoDate
        Glide
            .with(itemView.context)
            .load(photo.photoUrl)
            .centerCrop()
            .into(binding.photo)
    }
}
