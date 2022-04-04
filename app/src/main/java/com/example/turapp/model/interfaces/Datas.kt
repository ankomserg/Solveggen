package com.example.turapp.model.interfaces

import com.example.turapp.model.data.Weather
import retrofit2.Response
import retrofit2.http.GET

interface Datas {
    @GET("/weatherapi/locationforecast/2.0/compact?lat=60.10&lon=9.58")
    suspend fun getWeather() : Response<Weather>
}

