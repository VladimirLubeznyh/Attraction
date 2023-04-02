package com.example.attractions.di

import com.example.attractions.di.annotation.IoDispatcher
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
object CoroutinesModule {

    @Provides
    @Singleton
    @IoDispatcher
    fun provideDispatcherIO():CoroutineDispatcher = Dispatchers.IO
}
