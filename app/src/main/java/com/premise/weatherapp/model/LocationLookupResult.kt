package com.premise.weatherapp.model

data class LocationLookupResult(
    var title: String? = null,
    var location_type: String? = null,
    var woeid: String? = null,
    var latt_long: String? = null
)