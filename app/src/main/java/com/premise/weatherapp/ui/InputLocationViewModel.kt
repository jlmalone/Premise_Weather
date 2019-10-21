package com.premise.weatherapp.ui

import android.app.Activity
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.navigation.Navigation
import com.premise.weatherapp.R
import com.premise.weatherapp.api.WeatherApi
import com.premise.weatherapp.model.LocationLookupResult
import io.reactivex.Observer

import io.reactivex.disposables.Disposable
import javax.inject.Inject
import android.content.ContextWrapper
import android.content.Context
import android.os.Handler
import android.widget.Toast
import com.premise.weatherapp.model.InputLocationForm


class InputLocationViewModel : BaseViewModel() {

    @Inject
    lateinit var weatherApi: WeatherApi

    var inputLocationForm: InputLocationForm? = InputLocationForm()

    private lateinit var subscription: Disposable

    override fun onCleared() {
        super.onCleared()
        subscription.dispose()
    }

    fun hideKeyboard(activity: Activity) {
        val imm: InputMethodManager = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        //Find the currently focused view, so we can grab the correct window token from it.
        var view: View? = activity.currentFocus
        //If no view currently has focus, create a new one,
        // just so we can grab a window token from it
        if (view == null) {
            view = View(activity)
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0)
    }

    //We want to access the activity of a context (like a view) without having to
    //pass a reference to this class and create additional dependencies
    private fun scanForActivity(cont: Context?): Activity? {
        when (cont) {
            null -> return null
            is Activity -> return cont
            is ContextWrapper -> return scanForActivity(cont.baseContext)
            else -> return null
        }
    }

    fun goButtonClickListener(view: View, inputText: String) {
        //We want to close the keyboard if we can manage whenever the go button is clicked
        //we cannot assume the view context is an activity
        val context = scanForActivity(view.context)
        var looper = context?.mainLooper?:return

        val mainHandler = Handler(looper)
        context.run { hideKeyboard(this) }


        //The bellow method does not seem to be the most common way to invoke rxjava with retrofit.
        //The standard way was taking much more time and for some reason the network calls were
        //not working.
        //I had to just move on with a solution I am not completely satisfied with and needs
        //some refactoring.
        //Since RxJava is actually deprecated, refactoring to Kotlin Coroutines would be optimal
        if (inputText.isNotEmpty()) {
            weatherApi.searchLocationId(inputText)
                .subscribe(object : Observer<List<LocationLookupResult>> {
                    override fun onSubscribe(d: Disposable) {}

                    override fun onNext(value: List<LocationLookupResult>) {
                        Log.d("RxJAVACall", "onNext: --------->$value")
                        val myRunnable = Runnable {
                            Log.d("RxJAVACall", "onNext: --------->$value")
                            if (value.isNotEmpty()) {
                                inputLocationForm?.setLocationReturnedResult(value[0])

                                Navigation.findNavController(view)
                                    .navigate(R.id.action_input_to_forecastFragment)
                            } else {
                                Toast.makeText(
                                    view.context,
                                    "No location Could be found",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                        mainHandler.post(myRunnable)
                    }

                    override fun onError(e: Throwable) {

                        val myRunnable = Runnable {
                            Toast.makeText(
                                view.context,
                                "it didnt work " + e.localizedMessage,
                                Toast.LENGTH_LONG
                            ).show()
                            Log.v("WtF", "it didnt work " + e.localizedMessage)
                        }

                        mainHandler.post(myRunnable)
                    }

                    override fun onComplete() {
                        Log.d("RxJAVACall", "DONE: --------->")
                    }
                })

        }
    }

}