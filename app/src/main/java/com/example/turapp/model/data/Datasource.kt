package com.example.turapp.model.data

import android.util.Log
import com.example.turapp.model.interfaces.CabinApi
import com.example.turapp.model.interfaces.RetrofitHelper
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.coroutines.awaitString
import com.google.gson.Gson
import java.lang.Exception

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
            val cabins = mutableListOf<Cabin>()

            //val responseOne = gson.fromJson(Fuel.get(pathOne).awaitString(), Array<Cabin>::class.java)
            //cabins.addAll(responseOne.toList())

            val cabinApi = RetrofitHelper.getCabinIntance().create(CabinApi::class.java)
            val responseOne = cabinApi.getCabins("cabin-api-one").body()
            Log.d("First cabin API: ", responseOne.toString())
            if (responseOne != null) cabins.addAll(responseOne.toList())


            val responseTwo = cabinApi.getCabins("cabin-api-two").body()
            Log.d("Second cabin API: ", responseTwo.toString())
            if (responseTwo != null) cabins.addAll(responseTwo.toList())

            val responseThree = cabinApi.getCabins("cabin-api-three").body()
            Log.d("Third cabin API: ", responseThree.toString())
            if (responseThree != null) cabins.addAll(responseThree.toList())

            return cabins
        }
        catch (exception: Exception) {
            Log.d("fetchCabins()", "A network request was thrown: ${exception.message}")
            return mutableListOf()
        }
    }

}


