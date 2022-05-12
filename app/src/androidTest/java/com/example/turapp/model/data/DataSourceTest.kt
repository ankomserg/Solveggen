package com.example.turapp.model.data

import android.content.Context
import android.util.Log
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.turapp.model.interfaces.CabinApi
import com.example.turapp.model.interfaces.RetrofitHelper
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import retrofit2.Retrofit

@RunWith(AndroidJUnit4::class)
class DataSourceTest {

    lateinit var dataSource: DataSource

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
        val name = cabins.get(0).name
        assertEquals("Gamle Storlihytta", name)

    }
}