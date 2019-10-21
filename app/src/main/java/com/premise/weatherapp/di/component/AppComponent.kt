package com.premise.weatherapp.di.component

import android.app.Application
import com.premise.weatherapp.di.module.AppModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent{
    fun inject(app: Application)
}