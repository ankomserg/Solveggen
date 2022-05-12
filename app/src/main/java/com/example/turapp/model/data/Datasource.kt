package com.example.turapp.model.data

import android.util.Log
import com.example.turapp.model.interfaces.CabinApi
import com.example.turapp.model.interfaces.RetrofitHelper
import com.example.turapp.model.interfaces.WeatherApi
import com.example.turapp.util.Average

data class Weather(val type: String?, val geometry: Geometry?, val properties: Properties?)

data class Geometry(val type: String?, val coordinates: List<Number>?)

data class Properties(val meta: Meta?, val timeseries: List<Timeserie>?)

data class Meta(val updated_at: String?, val units: Units?)

data class Units(
    val air_pressure_at_sea_level: String?, val air_temperature: String?,
    val cloud_area_fraction: String?, val precipitation_amount: String?,
    val relative_humidity: String?, val wind_from_direction: String?,
    val wind_speed: String?
)

data class Timeserie(val time: String?, val data: Data?)

data class Data(
    val instant: Instant?, val next_12_hours: NextTwelveHours?,
    val next_1_hours: NextHour?, val next_6_hours: NextSixHours?
)

data class Instant(val details: Details?)

data class Details(
    val air_pressure_at_sea_level: Number?, val air_temperature: Number?,
    val cloud_area_fraction: Number?, val relative_humidity: Number?,
    val wind_from_direction: Number?, val wind_speed: Number?
)

data class NextTwelveHours(val summary: Summary?)

data class NextHour(val summary: Summary?, val details: RainDetails?)

data class NextSixHours(val summary: Summary?, val details: RainDetails?)

data class Summary(val symbol_code: String?)

data class RainDetails(val precipitation_amount: Number?)

class DataSource {

    suspend fun fetchCabins(): List<Cabin> {
        try {
            val cabins = mutableListOf<Cabin>()

            val cabinApi = RetrofitHelper.getCabinInstance().create(CabinApi::class.java)
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
        } catch (exception: Exception) {
            Log.d("fetchCabins()", "A network request was thrown: ${exception.message}")
            return mutableListOf()
        }
    }

    suspend fun fetchWeather(cabins: List<Cabin>, startDate: String, endDate: String): List<Cabin> {
        return try {
            val dataApi = RetrofitHelper.getInstance().create(WeatherApi::class.java)
            val weatherMap: MutableMap<Int, Weather?> = emptyMap<Int, Weather?>().toMutableMap()

            for (cabin in cabins) {
                val result = dataApi.getWeather(cabin.DDLat, cabin.DDLon).body()
                weatherMap[cabin.id] = result
            }
            return Average.calculateAverageWeather(cabins, weatherMap, startDate, endDate)

        } catch (exception: Exception) {
            Log.d("fetchCabins()", "A network request was thrown: ${exception.message}")
            emptyList()
        }
    }

}


