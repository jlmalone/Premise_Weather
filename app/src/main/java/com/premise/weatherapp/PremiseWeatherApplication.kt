package com.premise.weatherapp

import android.app.Application
import timber.log.Timber

@Suppress("unused")
class PremiseWeatherApplication : Application() {

    companion object{
        var  instance : PremiseWeatherApplication? = null
    }

    override fun onCreate() {
        instance = this
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}