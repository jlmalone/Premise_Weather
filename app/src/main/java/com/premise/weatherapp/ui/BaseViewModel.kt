package com.premise.weatherapp.ui

import androidx.lifecycle.ViewModel
import com.premise.weatherapp.di.component.DaggerViewModelInjector
import com.premise.weatherapp.di.component.ViewModelInjector
import com.premise.weatherapp.di.module.NetworkModule

abstract class BaseViewModel:ViewModel(){

    private val injector: ViewModelInjector = DaggerViewModelInjector
        .builder()
        .networkModule(NetworkModule)
        .build()

    init {
        inject()
    }
    /**
     * Injects the required dependencies
     */
    private fun inject() {
        when (this) {
            is InputLocationViewModel -> injector.inject(this)
            is ForecastViewModel -> injector.inject(this)
        }
    }
}