package com.premise.weatherapp.ui

import android.Manifest
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
import android.content.pm.PackageManager
import android.location.Location
import android.os.Handler
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.premise.weatherapp.model.InputLocationForm
import java.text.NumberFormat


class InputLocationViewModel : BaseViewModel() {

    companion object {const val PREMISE_PERMISSIONS_REQUEST_LOCATION_ACCESS = 46}

    @Inject
    lateinit var weatherApi: WeatherApi

    @Inject
    var locationProvider: FusedLocationProviderClient? =null

    var inputLocationForm: InputLocationForm? = InputLocationForm()

    private lateinit var subscription: Disposable

    private val latlonregex = "-?[0-9]{1,2}\\.[0-9]{1,8},-?[0-9]{1,3}\\.[0-9]{1,8}".toRegex()

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

    //called by the xml input page location button.
    //we check if we have location permissions and if not request them
    fun currentLocationClickListener(view: View)
    {
        val activity = scanForActivity(view.context)?:return
        if(ContextCompat.checkSelfPermission(activity,Manifest.permission.ACCESS_COARSE_LOCATION)!=PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(activity,
                arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
                PREMISE_PERMISSIONS_REQUEST_LOCATION_ACCESS)
            return
        }

        val nf = NumberFormat.getNumberInstance()
        nf.maximumFractionDigits = 3
        locationProvider?.lastLocation
            ?.addOnSuccessListener { location : Location? ->
                // Got last known location. In some rare situations this can be null.
                inputLocationForm?.setInputText("${nf.format(location?.latitude)},${nf.format(location?.longitude)}")
            }
    }

    fun goButtonClickListener(view: View, inputText: String) {
        //We want to close the keyboard if we can manage whenever the go button is clicked
        //we cannot assume the view context is an activity
        val context = scanForActivity(view.context)
        var looper = context?.mainLooper?:return

        val mainHandler = Handler(looper)
        context.run { hideKeyboard(this) }


        if (inputText.isNotEmpty()) {

            //getting a bit fancy with this variable assignment
            //it is not the most readable code. If the input text derived from
            //user input or auto location detection matches lat lon pattern,
            //then we use diffrent query parameters in the api call to look up
            //the nearest weather station
           var (query, lattlon)= when (latlonregex.matches(inputText)){
                true-> arrayOf(null,inputText)
                else -> arrayOf(inputText,null)
            }

            //The bellow method does not seem to be the most common way to invoke rxjava with retrofit.
            //The standard way was taking much more time and for some reason the network calls were
            //not working.
            //I had to just move on with a solution I am not completely satisfied with and needs
            //some refactoring.
            //Since RxJava is actually deprecated, refactoring to Kotlin Coroutines would be optimal
            weatherApi.searchLocationId(query,lattlon)
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