package com.example.turapp.util

import com.example.turapp.model.data.Cabin
import org.junit.Assert.*
import org.junit.Test

class WeatherCheckerTest {
    @Test
    fun sortTestEqualTemperature() {
        val cabin1 = Cabin(
            1, "Skihytta", null, null, 18.0,
            9.0, 50.0, 18.0, 9.0,
            null, null, null, null, null,
            null, null, null, null, null,
            null
        )

        val cabin2 = Cabin(
            1, "Skihytta", null, null, 18.0,
            5.0, 50.0, 18.0, 12.0,
            null, null, null, null, null,
            null, null, null, null, null,
            null
        )

        val cabin3 = Cabin(
            1, "Skihytta", null, null, 18.0,
            10.0, 50.0, 18.0, 11.0,
            null, null, null, null, null,
            null, null, null, null, null,
            null
        )

        val cabins = listOf(cabin1, cabin2, cabin3)

        println(WeatherChecker.sort(cabins, "temperature"))

        assertEquals(cabin2, WeatherChecker.sort(cabins, "temperature").get(0))
    }

    @Test
    fun sortTestEqualRain() {
        val cabin1 = Cabin(
            1, "Skihytta", null, null, 17.0,
            9.0, 50.0, 18.0, 9.0,
            null, null, null, null, null,
            null, null, null, null, null,
            null
        )

        val cabin2 = Cabin(
            1, "Skihytta", null, null, 18.0,
            5.0, 50.0, 18.0, 12.0,
            null, null, null, null, null,
            null, null, null, null, null,
            null
        )

        val cabin3 = Cabin(
            1, "Skihytta", null, null, 18.0,
            10.0, 50.0, 15.0, 11.0,
            null, null, null, null, null,
            null, null, null, null, null,
            null
        )

        val cabins = listOf(cabin1, cabin2, cabin3)

        println(WeatherChecker.sort(cabins, "rain"))

        assertEquals(cabin2, WeatherChecker.sort(cabins, "rain").get(0))
    }

    @Test
    fun sortTestEqualWind() {
        val cabin1 = Cabin(
            1, "Skihytta", null, null, 17.0,
            9.0, 50.0, 18.0, 9.0,
            null, null, null, null, null,
            null, null, null, null, null,
            null
        )

        val cabin2 = Cabin(
            1, "Skihytta", null, null, 19.0,
            9.0, 40.0, 19.0, 12.0,
            null, null, null, null, null,
            null, null, null, null, null,
            null
        )

        val cabin3 = Cabin(
            1, "Skihytta", null, null, 18.0,
            9.0, 60.0, 15.0, 11.0,
            null, null, null, null, null,
            null, null, null, null, null,
            null
        )

        val cabins = listOf(cabin1, cabin2, cabin3)

        println(WeatherChecker.sort(cabins, "wind"))

        assertEquals(cabin2, WeatherChecker.sort(cabins, "wind").get(0))
    }
}