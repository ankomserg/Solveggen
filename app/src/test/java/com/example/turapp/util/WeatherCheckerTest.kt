package com.example.turapp.util

import com.example.turapp.model.data.Cabin
import org.junit.Assert.*
import org.junit.Test

class WeatherCheckerTest {
    @Test
    fun sortTest() {
        val cabin1 = Cabin(
            1, "Skihytta", null, null, 18.0,
            10.0, 100.0, 18.0, 10.0,
            null, null, null, null, null,
            null, null, null, null, null,
            null
        )

        val cabin2 = Cabin(
            1, "Skihytta", null, null, 18.0,
            10.0, 80.0, 18.0, 10.0,
            null, null, null, null, null,
            null, null, null, null, null,
            null
        )

        val cabin3 = Cabin(
            1, "Skihytta", null, null, 18.0,
            10.0, 50.0, 18.0, 10.0,
            null, null, null, null, null,
            null, null, null, null, null,
            null
        )

        val cabins = listOf(cabin1, cabin2, cabin3)

        println(WeatherChecker.sort(cabins, "temperature"))

        assertEquals(cabin3, WeatherChecker.sort(cabins, "temperature").get(0))
    }
}