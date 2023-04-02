package com.example.attractions.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.attractions.data.local.entity.PhotoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PhotoDao {

    @Query("SELECT * FROM photo ")
    fun getAllPhoto(): Flow<List<PhotoEntity>>

    @Insert
    suspend fun insert(photo: PhotoEntity)
}
