package com.premise.weatherapp.di.module

import android.app.Application
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(val application: Application) {
   @Provides
   @Singleton
   fun provideApplication()= application
}