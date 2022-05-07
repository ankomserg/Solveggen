package com.example.turapp.model.repo

import android.util.Log
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
       }

       return cabins
   }

    suspend fun loadWeather(date: String, endDate: String) {
        val cabins : List<Cabin>
        withContext(Dispatchers.IO) {

            val dataApi = RetrofitHelper.getInstance().create(WeatherApi::class.java)
            cabins = getCabins()
            //val allWeather = mutableListOf<Deferred<Weather?>>()
            val weatherMap: MutableMap<Int, Weather?> = emptyMap<Int, Weather?>().toMutableMap()
            for (cabin in cabins) {

                val result = dataApi.getWeather(cabin.DDLat, cabin.DDLon).body()

                //allWeather.add(result)
                weatherMap[cabin.id] = result
            }
            //val readyWeather = allWeather.awaitAll()
            //Log.d("READYWEATHER", readyWeather[0].toString())
            var tempSum: Double = 0.0
            var rainSum: Double = 0.0
            var windSum: Double = 0.0
            for (cabin in cabins) {
                val weather = weatherMap[cabin.id]
                if (weather != null) {
                    val timeseries = weather.properties?.timeseries
                    if (timeseries != null) {
                        Log.d("DEtte er lengden på timeseries: ", timeseries.size.toString())
                        var count = 0
                        for (timeserie in timeseries) {
                            if (timeserie.time.equals(date)) {
                                cabin.air_temperature = timeserie.data?.instant?.details?.air_temperature?.toDouble()
                                cabin.wind_speed = timeserie.data?.instant?.details?.wind_speed?.toDouble()
                                cabin.precipitation_amount = timeserie.data?.next_6_hours?.details?.precipitation_amount?.toDouble()
                                tempSum += timeserie.data?.instant?.details?.air_temperature?.toDouble()!!
                                rainSum += timeserie.data?.next_6_hours?.details?.precipitation_amount?.toDouble()!!
                                windSum += timeserie.data.instant.details.wind_speed?.toDouble()!!
                                count++
                                break
                            }
                            count++
                        }
                        if (count > 24) {
                            cabin.air_temperature = timeseries[0].data?.instant?.details?.air_temperature?.toDouble()
                            cabin.wind_speed = timeseries[0].data?.instant?.details?.wind_speed?.toDouble()
                            cabin.precipitation_amount = timeseries[0].data?.next_6_hours?.details?.precipitation_amount?.toDouble()
                            count=0
                        }
                        if (date != endDate) {
                            Log.d("ETTER BREAK", "DET FUNKA")
                            var timeserieTemp = timeseries[count]
                            var countDays = 1
                            while (timeserieTemp.time!= endDate) {
                                if (timeserieTemp.time?.endsWith("12:00:00Z") == true) {
                                    tempSum += timeserieTemp.data?.instant?.details?.air_temperature?.toDouble()!!
                                    rainSum += timeserieTemp.data?.next_6_hours?.details?.precipitation_amount?.toDouble()!!
                                    windSum += timeserieTemp.data!!.instant?.details?.wind_speed?.toDouble()!!
                                    countDays++
                                }
                                count++
                                timeserieTemp = timeseries[count]
                            }

                            countDays++
                            tempSum += timeserieTemp.data?.instant?.details?.air_temperature?.toDouble()!!
                            rainSum += timeserieTemp.data?.next_6_hours?.details?.precipitation_amount?.toDouble()!!
                            windSum += timeserieTemp.data!!.instant?.details?.wind_speed?.toDouble()!!
                            Log.d("SNITTVERDI", tempSum.toString())
                            Log.d("SNITTVERDI", rainSum.toString())
                            Log.d("SNITTVERDI", windSum.toString())
                            cabin.air_temperature = Math.round((tempSum/countDays) * 10.0) / 10.00
                            cabin.precipitation_amount = Math.round((rainSum/countDays) * 10.0) / 10.00
                            cabin.wind_speed = Math.round((windSum/countDays) * 10.0) / 10.00
                        }

                    }
                }
                /*
                for (weather in readyWeather) {
                    if (weather != null) {
                        if (cabin.DDLat == weather.geometry?.coordinates?.get(0)
                            && cabin.DDLon == weather.geometry?.coordinates?.get(1)) {
                                Log.d("TESTER LATLON", "test")
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
                    }*/

            }
            Log.d("TESTER API:", cabins[0].air_temperature.toString())
            database.cabinDao().insertAll(cabins)
        }
    }

    suspend fun insertCabin(cabin: Cabin) {
        withContext(Dispatchers.IO) {
            database.cabinDao().insert(cabin)
        }
    }

    suspend fun deleteCabin(id : String) {
        withContext(Dispatchers.IO) {
            database.cabinDao().delete(id)
        }
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

    suspend fun deleteAllCabins() {
        withContext(Dispatchers.IO) {
            database.cabinDao().deleteAll()
        }
    }
}

