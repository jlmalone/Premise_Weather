package com.premise.weatherapp.model


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
)