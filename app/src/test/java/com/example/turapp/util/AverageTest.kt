package com.example.turapp.util

import org.junit.Assert.*
import org.junit.Test

class AverageTest{

    @Test
    fun averageSumTester() {
        assertEquals(5.0, Average.calculateAverage(10.0, 2), 0.002)
    }


}