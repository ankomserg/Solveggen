package com.example.turapp.model.repo

import com.example.turapp.model.data.Cabin
import com.example.turapp.model.data.DataSource
import com.example.turapp.model.data.Weather
import com.example.turapp.model.interfaces.RetrofitHelper
import com.example.turapp.model.interfaces.WeatherApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.math.roundToInt

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

    suspend fun loadWeather(startDate: String, endDate: String) {
        val cabins : List<Cabin>
        withContext(Dispatchers.IO) {

            val dataApi = RetrofitHelper.getInstance().create(WeatherApi::class.java)
            val weatherMap: MutableMap<Int, Weather?> = emptyMap<Int, Weather?>().toMutableMap()

            //load cabins and get weather forecast for each cabin
            cabins = getCabins()
            for (cabin in cabins) {
                val result = dataApi.getWeather(cabin.DDLat, cabin.DDLon).body()
                weatherMap[cabin.id] = result
            }

            var tempSum= 0.0
            var rainSum= 0.0
            var windSum= 0.0

            //calculate average weather for each cabin between startDate and endDate
            for (cabin in cabins) {
                val weather = weatherMap[cabin.id]
                if (weather != null) {
                    val timeseries = weather.properties?.timeseries
                    if (timeseries != null) {
                        var count = 0
                        var numOfDays = 1

                        //find startDate
                        for (timeserie in timeseries) {
                            if (timeserie.time.equals(startDate)) {
                                tempSum += timeserie.data?.instant?.details?.air_temperature?.toDouble()!!
                                rainSum += timeserie.data.next_6_hours?.details?.precipitation_amount?.toDouble()!!
                                windSum += timeserie.data.instant.details.wind_speed?.toDouble()!!
                                count++
                                break
                            }
                            count++
                        }

                        //if we want today's date, but the time of the call is after 12:00
                        if (count > 12) {
                            tempSum = timeseries[0].data?.instant?.details?.air_temperature?.toDouble()!!
                            rainSum = timeseries[0].data?.next_6_hours?.details?.precipitation_amount?.toDouble()!!
                            windSum = timeseries[0].data?.instant?.details?.wind_speed?.toDouble()!!
                            count=0
                        }

                        //continue summing until we find endDate
                        if (startDate != endDate) {
                            var timeserieTemp = timeseries[count]
                            while (timeserieTemp.time!= endDate) {
                                if (timeserieTemp.time?.endsWith("12:00:00Z") == true) {
                                    tempSum += timeserieTemp.data?.instant?.details?.air_temperature?.toDouble()!!
                                    rainSum += timeserieTemp.data?.next_6_hours?.details?.precipitation_amount?.toDouble()!!
                                    windSum += timeserieTemp.data?.instant?.details?.wind_speed?.toDouble()!!
                                    numOfDays++
                                }
                                count++
                                timeserieTemp = timeseries[count]
                            }

                            //add endDate to sum
                            numOfDays++
                            tempSum += timeserieTemp.data?.instant?.details?.air_temperature?.toDouble()!!
                            rainSum += timeserieTemp.data?.next_6_hours?.details?.precipitation_amount?.toDouble()!!
                            windSum += timeserieTemp.data!!.instant?.details?.wind_speed?.toDouble()!!
                        }

                        //update cabins with weather averages
                        cabin.air_temperature = ((tempSum / numOfDays) * 10.0).roundToInt() / 10.00
                        cabin.precipitation_amount = ((rainSum / numOfDays) * 10.0).roundToInt() / 10.00
                        cabin.wind_speed = ((windSum / numOfDays) * 10.0).roundToInt() / 10.00
                    }
                }
            }

            //update database with all cabins
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

