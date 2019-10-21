package com.premise.weatherapp.model

import android.graphics.drawable.Drawable
import android.os.Build
import com.premise.weatherapp.PremiseWeatherApplication
import com.premise.weatherapp.R
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

data class WeatherDay(
    var id: Long?,
    var weather_state_name: String?,
    var weather_state_abbr: String?,
    var wind_direction_compass: String?,
    var created: String?,
    var applicable_date: String?,
    var min_temp: Float?,
    var max_temp: Float?,
    var the_temp: Float?,
    var wind_speed: Double?,
    var wind_direction: Double?,
    var air_pressure: Double?,
    var humidity: Double?,
    var visibility: Double?,
    var predictability: Double?
) {

    companion object {
        val outputSdf = SimpleDateFormat("EEEE", Locale.US)
        val inputSdf = SimpleDateFormat("yyyy-MM-dd", Locale.US)
    }

    fun displayPressure():String?{
        if(air_pressure==null) return null
        return air_pressure?.toInt().toString()+"mb Pressure"
    }

    fun displayConfidence():String?{
        if(predictability==null) return null

        return predictability?.toInt().toString()+"% Confidence"}


    fun displayHumidity():String?{
        if(humidity==null) return null
        return humidity?.toInt().toString()+"% Humidity"
    }

    fun  displayTemperature():String {return "${the_temp?.roundToInt()}"+Typography.degree+" C"}

    fun dayOfWeek(): String? {
        val applicableDateCopy = applicable_date
        applicableDateCopy?.run {
            try {
                val date = inputSdf.parse(applicableDateCopy)
                if(date!=null) {
                    return outputSdf.format(date).substring(0..2)
                }
            } catch (e: ParseException) {
            }
        }
        return applicable_date
    }

    fun getWeatherImageResource():Drawable?{
        val ret =  when(weather_state_abbr){
            "sn" -> R.drawable.ic_weather_snowy_black_48dp
            "sl" -> R.drawable.ic_weather_snowy_rainy_black_48dp
            "h" -> R.drawable.ic_weather_hail_black_48dp
            "t" -> R.drawable.ic_weather_lightning_rainy_black_48dp
            "hr" -> R.drawable.ic_weather_pouring_black_48dp
            "lr" -> R.drawable.ic_weather_rainy_black_48dp
            "s" -> R.drawable.ic_weather_partly_rainy_black_48dp
            "hc" ->R.drawable.ic_cloud_black_48dp
            "lc" ->R.drawable.ic_cloud_outline_black_48dp
            "c" -> R.drawable.ic_white_balance_sunny_black_48dp
            else -> R.drawable.ic_cloud_black_48dp
        }
        return PremiseWeatherApplication.instance?.getDrawable(ret).also {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                it?.setTint(PremiseWeatherApplication.instance
                    ?.resources?.getColor(R.color.colorPrimary,null)?:0)
            }
        }
    }
}