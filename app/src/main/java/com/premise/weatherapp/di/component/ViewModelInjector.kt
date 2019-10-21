package com.premise.weatherapp.di.component

import com.premise.weatherapp.ui.InputLocationViewModel
import com.premise.weatherapp.di.module.NetworkModule
import com.premise.weatherapp.ui.ForecastViewModel
import dagger.Component
import javax.inject.Singleton

/**
 * Component providing inject() methods for presenters.
 */
@Singleton
@Component(modules = [ NetworkModule::class])
interface ViewModelInjector {
    /**
     * Injects required dependencies into the specified ForecastViewModel.
     * @param forecastViewModel ForecastViewModel in which to inject the dependencies
     */
    fun inject(forecastViewModel: ForecastViewModel)

    /**
     * Injects required dependencies into the specified InputLocationViewModel.
     * @param inputLocationViewModel InputLocationViewModel in which to inject the dependencies
     */
    fun inject(inputLocationViewModel: InputLocationViewModel)

    @Component.Builder
    interface Builder {
        fun build(): ViewModelInjector

        fun networkModule(networkModule: NetworkModule): Builder

//        fun application(appModule: AppModule):Builder
    }
}