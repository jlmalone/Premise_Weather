package com.premise.weatherapp.model

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.premise.weatherapp.BR
import java.text.SimpleDateFormat
import java.util.*

class ForcastForm :BaseObservable(){
    private var updatedDateString: String = ""

    @Bindable
    fun getUpdatedTimestampString():String
    {
        return updatedDateString
    }

    fun setUpdatedTimestampString()
    {
        updatedDateString=
            SimpleDateFormat("yyyy MMM dd HH:mm:ss").format( Date(System.currentTimeMillis()))
        notifyPropertyChanged(BR.updatedTimestampString)
    }

}