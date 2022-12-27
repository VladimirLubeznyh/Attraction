package com.example.attractions.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.attractions.entity.PhotoEntity

@Database(
    entities = [
        PhotoEntity::class
    ], version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun photoDao(): PhotoDao
}