package com.premise.weatherapp.ui

import android.app.Activity
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.databinding.ObservableBoolean
import com.premise.weatherapp.api.WeatherApi
import com.premise.weatherapp.model.ConsolidatedWeather
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import javax.inject.Inject
import kotlin.collections.ArrayList
import com.premise.weatherapp.model.ForcastForm


class ForecastViewModel : BaseViewModel() {

    var isLoading = ObservableBoolean()
    //contains observables that can be bound to the xml layout
    var forcastForm = ForcastForm()

    fun onRefresh() {
        isLoading.set(true)
        fetchForcastData()
    }

    @Inject
    lateinit var weatherApi: WeatherApi

    var recyclerAdapter: ForcastAdapter? = null

    var activity: Activity? = null

    var weatherLocationId: String? = null
        set(s) {
            forcastForm.setUpdatedTimestampString()
            field = s
            if (s != null && s.isNotEmpty()) {
                fetchForcastData()
            }
        }


    fun fetchForcastData() {
        val looper = activity?.mainLooper?:return
        val mainHandler = Handler(looper)

        val weatherId = weatherLocationId
        if (weatherId != null) {

            recyclerAdapter?.items = ArrayList()
            recyclerAdapter?.notifyDataSetChanged()

            weatherApi.getWeather(weatherId)

                // The commented code should have worked but network calls were
                // not firing. There is a bug
                //and I have reverted to the below work around implementation which is not optimal.
                //Refactoring to Kotlin Coroutines would be one way to solve.


                //                .subscribeOn(Schedulers.computation())
                //                .observeOn(AndroidSchedulers.mainThread()).doOnNext {
                //                recyclerAdapter?.items = it
                //                recyclerAdapter?.notifyDataSetChanged()
                //            }.doOnError {
                //
                //                //TODO show error throwable
                //                recyclerAdapter?.items = ArrayList()
                //
                //            }


                .subscribe(object : Observer<ConsolidatedWeather> {
                    override fun onSubscribe(d: Disposable) {}

                    override fun onNext(value: ConsolidatedWeather) {

                        Log.d("RxJAVACall", "onNext: ---------> $value")
                        val myRunnable = Runnable {
                            Log.d("RxJAVACall", "onNext: ---------> $value")

                            recyclerAdapter?.items = value.consolidated_weather
                            recyclerAdapter?.notifyDataSetChanged()
                            forcastForm.setUpdatedTimestampString()
                            when {
                                (value.consolidated_weather.isEmpty()) -> {
                                    Toast.makeText(
                                        activity,
                                        "No location Could be found",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }

                            }
                        }
                        mainHandler.post(myRunnable)
                    }

                    override fun onError(e: Throwable) {

                        val myRunnable = Runnable {

                            recyclerAdapter?.items = ArrayList()
                            recyclerAdapter?.notifyDataSetChanged()
                            Toast.makeText(
                                activity,
                                "Unable to fetch results " + e.localizedMessage,
                                Toast.LENGTH_LONG
                            ).show()
                            Log.v("WtF", "it didnt work " + e.localizedMessage)
                        }

                        mainHandler.post(myRunnable)
                    }

                    override fun onComplete() {
                        isLoading.set(false)
                        Log.d("RxJAVACall", "DONE: --------->")
                    }
                })

        } else {
            isLoading.set(false)
        }
    }

    private lateinit var subscription: Disposable

    override fun onCleared() {
        super.onCleared()
        subscription.dispose()
    }

}