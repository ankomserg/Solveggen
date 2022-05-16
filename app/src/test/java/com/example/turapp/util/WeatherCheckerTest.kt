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
        assertEquals(cabin2, WeatherChecker.sort(cabins, "temperature")[0])
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
        assertEquals(cabin2, WeatherChecker.sort(cabins, "rain")[0])
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
        assertEquals(cabin2, WeatherChecker.sort(cabins, "wind")[0])
    }

    @Test
    fun sortTestEqualRainZero() {
        val cabin1 = Cabin(
            1, "Skihytta", null, null, 17.0,
            9.0, 0.0, 18.0, 9.0,
            null, null, null, null, null,
            null, null, null, null, null,
            null
        )

        val cabin2 = Cabin(
            1, "Skihytta", null, null, 19.0,
            9.0, 0.0, 19.0, 12.0,
            null, null, null, null, null,
            null, null, null, null, null,
            null
        )

        val cabin3 = Cabin(
            1, "Skihytta", null, null, 18.0,
            9.0, 0.0, 15.0, 11.0,
            null, null, null, null, null,
            null, null, null, null, null,
            null
        )

        val cabins = listOf(cabin1, cabin2, cabin3)
        println(WeatherChecker.sort(cabins, "rain"))
        assertEquals(cabin2, WeatherChecker.sort(cabins, "rain")[0])
    }

    @Test
    fun sortTestEqualWindZero() {
        val cabin1 = Cabin(
            1, "Skihytta", null, null, 17.0,
            0.0, 0.0, 18.0, 9.0,
            null, null, null, null, null,
            null, null, null, null, null,
            null
        )

        val cabin2 = Cabin(
            1, "Skihytta", null, null, 19.0,
            0.0, 0.0, 19.0, 12.0,
            null, null, null, null, null,
            null, null, null, null, null,
            null
        )

        val cabin3 = Cabin(
            1, "Skihytta", null, null, 28.0,
            0.0, 0.0, 15.0, 11.0,
            null, null, null, null, null,
            null, null, null, null, null,
            null
        )

        val cabins = listOf(cabin1, cabin2, cabin3)
        println(WeatherChecker.sort(cabins, "wind"))
        assertEquals(cabin3, WeatherChecker.sort(cabins, "wind")[0])
    }
}