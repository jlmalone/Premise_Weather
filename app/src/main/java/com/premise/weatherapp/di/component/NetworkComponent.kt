package com.premise.weatherapp.di.component

import com.premise.weatherapp.ui.activity.MainActivity
import com.premise.weatherapp.di.module.NetworkModule
import dagger.Component
import javax.inject.Singleton

/**
 * Component providing inject() methods for Activities that implement Network.
 */
@Singleton
@Component(modules = [NetworkModule::class])
interface NetworkComponent{
    fun inject(activity: MainActivity)
}