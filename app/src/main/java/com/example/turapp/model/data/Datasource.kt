package com.example.turapp.model.data

import android.util.Log
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.coroutines.awaitString
import com.google.gson.Gson
import java.lang.Exception

/*
import android.util.Log
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.coroutines.awaitString
import com.google.gson.Gson

//How API-call can be done with gson and Fuel http library
class DataTest {
    private val gson = Gson()
    val url = "https://in2000-apiproxy.ifi.uio.no/weatherapi/locationforecast/2.0/compact?lat=60.10&lon=9.58"

    suspend fun fetchWeather(): Weather? {
        return try {
            val weather = gson.fromJson(Fuel.get(url).awaitString(), Weather::class.java)
            weather
        } catch(exception: Exception) {
            Log.d("DataSource",
                "A network request exception was thrown: ${exception.message}")
            null
        }
    }
}*/

data class Weather(val type: String?, val geometry: Geometry?, val properties: Properties?)

data class Geometry(val type: String?, val coordinates: List<Number>?)

data class Properties(val meta: Meta?, val timeseries: List<Timeserie>?)

data class Meta(val updated_at: String?, val units: Units?)

data class Units(val air_pressure_at_sea_level: String?, val air_temperature: String?,
                 val cloud_area_fraction: String?, val precipitation_amount: String?,
                 val relative_humidity: String?, val wind_from_direction: String?,
                 val wind_speed: String?)

data class Timeserie(val time: String?, val data: Data?)

data class Data(val instant: Instant?, val next_12_hours: NextTwelveHours?,
                val next_1_hours: NextHour?, val next_6_hours: NextSixHours?)

data class Instant(val details: Details?)

data class Details(val air_pressure_at_sea_level: Number?, val air_temperature: Number?,
                   val cloud_area_fraction: Number?, val relative_humidity: Number?,
                   val wind_from_direction: Number?, val wind_speed: Number?)

data class NextTwelveHours(val summary: Summary?)

data class NextHour(val summary: Summary?, val details: RainDetails?)

data class NextSixHours(val summary: Summary?, val details: RainDetails?)

data class Summary(val symbol_code: String?)

data class RainDetails(val precipitation_amount: Number?)

class DataSource {

    private val pathOne = "https://my-json-server.typicode.com/lenamarsilius/cabin-api-one/cabins"
    private val pathTwo = "https://my-json-server.typicode.com/lenamarsilius/cabin-api-two/cabins"
    private val pathThree = "https://my-json-server.typicode.com/lenamarsilius/cabin-api-three/cabins"

    suspend fun fetchCabins(): List<Cabin> {
        val gson = Gson()
        try{
            val responseOne = gson.fromJson(Fuel.get(pathOne).awaitString(), Array<Cabin>::class.java)
            val cabins = mutableListOf<Cabin>()
            cabins.addAll(responseOne.toList())
            Log.d("First cabin API: ", responseOne.toString())

            val responseTwo = gson.fromJson(Fuel.get(pathTwo).awaitString(), Array<Cabin>::class.java)
            cabins.addAll(responseTwo.toList())
            Log.d("Second cabin API: ", responseTwo.toString())

            val responseThree = gson.fromJson(Fuel.get(pathThree).awaitString(), Array<Cabin>::class.java)
            cabins.addAll(responseThree.toList())
            Log.d("Third cabin API: ", responseThree.toString())

            return cabins
        }
        catch (exception: Exception) {
            Log.d("fetchCabins()", "A network request was thrown: ${exception.message}")
            return mutableListOf()
        }
    }

}


