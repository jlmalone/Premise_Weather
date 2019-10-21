package com.premise.weatherapp.model

//API Defined Model Object for 5 Day forcast
data class ConsolidatedWeather(var consolidated_weather: ArrayList<WeatherDay>)