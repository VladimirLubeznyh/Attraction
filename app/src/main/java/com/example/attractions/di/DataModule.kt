package com.example.attractions.di

import android.content.Context
import androidx.room.Room
import com.example.attractions.data.db.AppDatabase
import com.example.attractions.data.db.PhotoDao
import com.example.attractions.data.retrofit.RetrofitServices
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
object DataModule {
    @Provides
    fun providesRetrofitServices(): RetrofitServices {
        val retrofit =
            Retrofit.Builder()
                .baseUrl("https://api.opentripmap.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        return RetrofitServices(retrofit)
    }

    @Provides
    fun providesDatabase(app: Context): AppDatabase {
        return Room.databaseBuilder(
            app,
            AppDatabase::class.java,
            "db"
        ).build()
    }

    @Provides
    fun providesPhotoDao(db: AppDatabase): PhotoDao {
        return db.photoDao()
    }
}