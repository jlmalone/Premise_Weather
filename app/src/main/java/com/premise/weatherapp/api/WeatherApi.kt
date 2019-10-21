package com.premise.weatherapp.api

import com.premise.weatherapp.model.ConsolidatedWeather
import com.premise.weatherapp.model.LocationLookupResult
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * The API interface
 */
interface WeatherApi {

    /**
     *
     * Arguments Either query or lattlong need to be present.
     *
     *  query
     *     Text to search for.
     *  lattlong
     *     Coordinates to search for locations near. Comma separated lattitude and longitude e.g. "36.96,-122.02".
     *
     *  eg https://www.metaweather.com/api/location/44418/
     *
     */
    @GET("/api/location/search/?query=san")
    fun searchLocationId(@Query("query") query: String? =null  , @Query("lattlong") latlon:String? = null): Observable<List<LocationLookupResult>>

    /**
     *
     * Arguments
     * woeid
     *    Where On Earth ID. Docs.
     * Examples
     *    /api/location/44418/ - London
     *    /api/location/2487956/ - San Francisco
     *
     */
    @GET("/api/location/{woeid}/")
    fun getWeather(@Path("woeid") locationId: String): Observable<ConsolidatedWeather>

}