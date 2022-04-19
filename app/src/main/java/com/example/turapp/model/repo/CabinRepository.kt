package com.example.turapp.model.repo

import com.example.turapp.model.data.Cabin
import com.example.turapp.model.data.DataSource
import com.example.turapp.model.data.Weather
import com.example.turapp.model.interfaces.RetrofitHelper
import com.example.turapp.model.interfaces.WeatherApi
import kotlinx.coroutines.*

class CabinRepository(private val database: CabinRoomDatabase) {
    suspend fun loadCabins() : List<Cabin> {
       val cabins : List<Cabin>

       withContext(Dispatchers.IO) {
           deleteAll()
           val datasource = DataSource()
           cabins = datasource.fetchCabins()
           database.cabinDao().insertAll(cabins)
       }

       return cabins
   }

    suspend fun loadWeather(): List<Cabin> {
        val dataApi = RetrofitHelper.getInstance().create(WeatherApi::class.java)
        val cabins = getCabins()
        val allWeather = mutableListOf<Deferred<Weather?>>()
        for (cabin in cabins) {
            val result = CoroutineScope(Dispatchers.IO).async {
                dataApi.getWeather(cabin.DDLat, cabin.DDLon).body()
                }
            allWeather.add(result)
        }
        val readyWeather = allWeather.awaitAll()
        for (cabin in cabins) {
            for (weather in readyWeather) {
                if (weather != null) {
                    if (cabin.DDLat == weather.geometry?.coordinates?.get(0)
                        && cabin.DDLon == weather.geometry?.coordinates?.get(1)) {
                        cabin.air_temperature = weather
                            .properties?.timeseries?.get(0)?.
                            data?.instant?.details?.air_temperature?.toDouble()
                        cabin.wind_speed = weather
                            .properties?.timeseries?.get(0)?.
                            data?.instant?.details?.wind_speed?.toDouble()
                        cabin.precipitation_amount = weather
                            .properties?.timeseries?.get(0)?.
                            data?.next_6_hours?.details?.precipitation_amount?.toDouble()
                    }
                }
            }
        }

        return cabins
    }

    private suspend fun deleteAll() {
        withContext(Dispatchers.IO) {
            database.cabinDao().deleteAll()
        }
    }

    private suspend fun getCabins() : List<Cabin> {
        var cabins : List<Cabin>

        withContext(Dispatchers.IO) {
            cabins = database.cabinDao().getAllUnsorted()
        }

        return cabins
    }

    suspend fun getSortedCabins(preference: String) : List<Cabin> {
        var cabins : List<Cabin>

        withContext(Dispatchers.IO) {
            cabins = when (preference) {
                "temperature" -> database.cabinDao().getSortedTemp()
                "wind" -> database.cabinDao().getSortedWind()
                else -> database.cabinDao().getSortedPrec()
            }
        }

        return cabins

    }
}

