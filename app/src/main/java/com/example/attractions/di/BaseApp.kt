package com.example.attractions.di

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.google.firebase.crashlytics.FirebaseCrashlytics

class BaseApp() : Application() {
    lateinit var appComponent: AppComponent
    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .context(this)
            .build()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            createNotificationChanel()
        }

        FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(true)

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChanel() {
        val name = "Create photo"
        val descriptionText = "This is description"
        val importance = NotificationManager.IMPORTANCE_HIGH

        val channel = NotificationChannel(NOTIFICATION_CHANNEL_ID,name,importance).apply {
            description = descriptionText
        }

        val notificationManager =
            this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.createNotificationChannel(channel)
    }

    companion object {
        const val NOTIFICATION_CHANNEL_ID = "CreatePhoto"
    }
}

val Context.appComponent: AppComponent
    get() = when (this) {
        is BaseApp -> appComponent
        else -> this.applicationContext.appComponent
    }