package com.example.attractions.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "photo")
data class PhotoEntity(
    @PrimaryKey
    @ColumnInfo(name = "photo_url")
    val photoUrl: String,
    @ColumnInfo(name = "photo_date")
    val photoDate: String
)

