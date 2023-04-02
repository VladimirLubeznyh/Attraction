package com.example.attractions.di

import android.content.Context
import com.example.attractions.presentation.createphoto.CreatePhotoFragment
import com.example.attractions.presentation.mapfragment.MapFragment
import com.example.attractions.presentation.photolistfragment.PhotoListFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Component(
    modules = [
        DataModule::class,
        CoroutinesModule::class
    ]
)
@Singleton
interface AppComponent {

    fun injectInCreatePhotoFragment(createPhotoFragment: CreatePhotoFragment)
    fun injectInPhotoListFragment(photoListFragment: PhotoListFragment)
    fun injectInMapFragment(mapFragment: MapFragment)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun context(context: Context): Builder

        fun build(): AppComponent
    }
}
