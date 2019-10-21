package com.premise.weatherapp.model


import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.premise.weatherapp.BR
import javax.inject.Singleton

@Singleton
class InputLocationForm : BaseObservable() {
    var inputFields: InputFields = InputFields()
    var locationResults: LocationLookupResult = LocationLookupResult()


    @Bindable
    fun getLocationReturnedResult(): LocationLookupResult? {
        return locationResults
    }

    fun setLocationReturnedResult(locResult: LocationLookupResult) {
        if(locationResults!=locResult) {
            locationResults = locResult
            notifyPropertyChanged(BR.inputViewModel)
        }
    }

    @Bindable
    fun getInputText(): String? {
        return inputFields.inputText
    }

    fun setInputText(value: String) {
        if (inputFields.inputText != value) {
            inputFields.inputText = value
            notifyPropertyChanged(BR.inputText)
        }
    }
}