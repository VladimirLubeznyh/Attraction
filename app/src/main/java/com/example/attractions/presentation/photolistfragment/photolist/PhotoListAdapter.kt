package com.example.attractions.presentation.photolistfragment.photolist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.*
import com.example.attractions.databinding.LastItemAddPhotoBinding
import com.example.attractions.databinding.PhotoItemBinding
import com.example.attractions.data.local.entity.PhotoEntity

class PhotoListAdapter(private val onClickFooter: () -> Unit) : ListAdapter<PhotoEntity,ViewHolder>(PhotoListDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            FOOTER -> {
                FooterViewHolder(LastItemAddPhotoBinding.inflate(inflater, parent, false))
            }
            ITEM -> {
                ItemViewHolder(PhotoItemBinding.inflate(inflater, parent, false))
            }
            else -> throw IllegalAccessError("Illegal type: $viewType")
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (holder) {
            is ItemViewHolder -> holder.bind(getItem(position))
            is FooterViewHolder -> holder.bind(onClickFooter)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (getItem(position).type == PhotoEntity.FOOTER) {
            FOOTER
        } else {
            ITEM
        }
    }

    private companion object {
        const val ITEM = 101
        const val FOOTER = 102
    }
}
