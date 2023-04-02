package com.example.attractions.di

import android.content.Context
import androidx.room.Room
import com.example.attractions.BuildConfig

import com.example.attractions.data.local.AppDatabase
import com.example.attractions.data.local.PhotoDao
import com.example.attractions.data.network.RetrofitServices
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
object DataModule {

    @Provides
    @Singleton
    fun providesRetrofitServices(): RetrofitServices {
        val retrofit =
            Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        return RetrofitServices(retrofit)
    }

    @Provides
    @Singleton
    fun providesDatabase(app: Context): AppDatabase {
        return Room.databaseBuilder(
            app,
            AppDatabase::class.java,
            "db"
        ).build()
    }

    @Provides
    @Singleton
    fun providesPhotoDao(db: AppDatabase): PhotoDao {
        return db.photoDao()
    }
}
