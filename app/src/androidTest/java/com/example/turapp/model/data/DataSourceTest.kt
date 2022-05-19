package com.example.turapp.model.data

import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DataSourceTest {

    private lateinit var dataSource: DataSource

    @Before
    fun setup() {
        dataSource = DataSource()
    }

    @Test
    fun checkNumberOfCabins() = runBlocking {
        val cabins = dataSource.fetchCabins()
        assertEquals(10, cabins.size)
    }

    @Test
    fun checkFirstCabin() = runBlocking {
        val cabins = dataSource.fetchCabins()
        val name = cabins[0].name
        assertEquals("Gamle Storlihytta", name)

    }
}