package com.example.turapp.model.interfaces

import com.example.turapp.model.data.Weather
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("/weatherapi/locationforecast/2.0/compact")
    suspend fun getWeather(
        @Query("lat") lat: Number?,
        @Query("lon") lon: Number?)
    : Response<Weather>
}

