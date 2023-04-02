package com.example.attractions.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.attractions.data.local.entity.PhotoEntity

@Database(
    entities = [
        PhotoEntity::class
    ], version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun photoDao(): PhotoDao
}
