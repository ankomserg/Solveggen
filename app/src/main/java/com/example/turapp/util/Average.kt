package com.example.turapp.util

import com.example.turapp.model.data.Cabin
import com.example.turapp.model.data.Weather
import kotlin.math.roundToInt

class Average {
    companion object {
        fun calculateAverage(sum: Double, numOfDays: Int): Double {
            return ((sum / numOfDays) * 10.0).roundToInt() / 10.00
        }

        fun calculateAverageWeather(
            cabins: List<Cabin>,
            weatherMap: MutableMap<Int, Weather?>,
            startDate: String,
            endDate: String
        )
                : List<Cabin> {

            //calculate average weather for each cabin between startDate and endDate
            for (cabin in cabins) {
                var tempSum = 0.0
                var rainSum = 0.0
                var windSum = 0.0
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
                        if (count == timeseries.size) {
                            tempSum =
                                timeseries[0].data?.instant?.details?.air_temperature?.toDouble()!!
                            rainSum =
                                timeseries[0].data?.next_6_hours?.details?.precipitation_amount?.toDouble()!!
                            windSum = timeseries[0].data?.instant?.details?.wind_speed?.toDouble()!!
                            count = 0
                        }

                        //continue summing until we find endDate
                        if (startDate != endDate) {
                            var timeserieTemp = timeseries[count]
                            while (timeserieTemp.time != endDate) {
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
                        cabin.air_temperature = calculateAverage(tempSum, numOfDays)
                        cabin.precipitation_amount = calculateAverage(rainSum, numOfDays)
                        cabin.wind_speed = calculateAverage(windSum, numOfDays)
                    }
                }
            }
            return cabins
        }
    }
}