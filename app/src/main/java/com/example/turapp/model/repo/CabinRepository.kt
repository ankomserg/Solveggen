package com.example.turapp.model.repo

import com.example.turapp.model.data.Cabin
import com.example.turapp.model.data.DataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CabinRepository(private val database: CabinRoomDatabase) {
    suspend fun loadCabins(): List<Cabin> {
        val cabins: List<Cabin>

        withContext(Dispatchers.IO) {
            deleteAll()
            val datasource = DataSource()
            cabins = datasource.fetchCabins()
        }

        return cabins
    }

    suspend fun loadWeather(startDate: String, endDate: String) {
        withContext(Dispatchers.IO) {
            val datasource = DataSource()
            val cabins = datasource.fetchWeather(getCabins(), startDate, endDate)

            //update database with all cabins
            database.cabinDao().insertAll(cabins)
        }
    }

    suspend fun insertCabin(cabin: Cabin) {
        withContext(Dispatchers.IO) {
            database.cabinDao().insert(cabin)
        }
    }

    suspend fun deleteCabin(id: String) {
        withContext(Dispatchers.IO) {
            database.cabinDao().delete(id)
        }
    }

    private suspend fun deleteAll() {
        withContext(Dispatchers.IO) {
            database.cabinDao().deleteAll()
        }
    }

    private suspend fun getCabins(): List<Cabin> {
        var cabins: List<Cabin>

        withContext(Dispatchers.IO) {
            cabins = database.cabinDao().getAllUnsorted()
        }

        return cabins
    }

    suspend fun getSortedCabins(preference: String): List<Cabin> {
        var cabins: List<Cabin>

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

