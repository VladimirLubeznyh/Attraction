package com.example.attractions.presentation.photolistfragment.photolist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.*
import com.example.attractions.databinding.LastItemAddPhotoBinding
import com.example.attractions.databinding.PhotoItemBinding
import com.example.attractions.entity.PhotoEntity

//Диффутил
class PhotoListAdapter(private val onClickFooter: () -> Unit) : Adapter<ViewHolder>() {

    private var photoList: List<PhotoEntity> = emptyList()

    fun setData(photoList: List<PhotoEntity>) {
        this.photoList = photoList
        notifyDataSetChanged()
    }

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
            is ItemViewHolder -> holder.bind(photoList[position])
            is FooterViewHolder -> holder.bind(onClickFooter)
        }
    }

    override fun getItemCount(): Int = photoList.size + 1

    override fun getItemViewType(position: Int): Int {
        return if (position == photoList.size) {
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