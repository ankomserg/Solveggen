package com.example.turapp.model.interfaces

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {
    val url = "https://in2000-apiproxy.ifi.uio.no/"
    val test = "https://my-json-server.typicode.com/"

    fun getInstance(): Retrofit {
        return Retrofit.Builder().baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getCabinInstance(): Retrofit {
        return Retrofit.Builder().baseUrl(test)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}