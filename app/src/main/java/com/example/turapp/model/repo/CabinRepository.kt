package com.example.turapp.model.repo

import com.example.turapp.model.data.Cabin
import com.example.turapp.model.data.DataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/*
This class is an abstraction level over Data Layer
*/
class CabinRepository(private val database: CabinRoomDatabase) {

    private val dataSource = DataSource()

    /*
    fetches cabins from JSON database
    */
    suspend fun loadCabins(): List<Cabin> {
        val cabins: List<Cabin>

        withContext(Dispatchers.IO) {
            deleteAllCabins()
            cabins = dataSource.fetchCabins()
        }

        return cabins
    }

    /*
    fetches weather forecast for cabins
    */
    suspend fun loadWeather(startDate: String, endDate: String) {
        withContext(Dispatchers.IO) {
            val cabins = dataSource.fetchWeather(getCabins(), startDate, endDate)

            //update database with all cabins
            database.cabinDao().insertAll(cabins)
        }
    }

    /*
    insert a cabin into a database
    */
    suspend fun insertCabin(cabin: Cabin) {
        withContext(Dispatchers.IO) {
            database.cabinDao().insert(cabin)
        }
    }

    /*
    removes cabin from database by id
    */
    suspend fun deleteCabin(id: String) {
        withContext(Dispatchers.IO) {
            database.cabinDao().delete(id)
        }
    }

    /*
    fetches all cabins from database in unsorted order
    */
    suspend fun getCabins(): List<Cabin> {
        var cabins: List<Cabin>

        withContext(Dispatchers.IO) {
            cabins = database.cabinDao().getAllUnsorted()
        }

        return cabins
    }

    /*
    fetches cabins from database in sorted order based on provided preference
    */
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

    /*
    clears database
    */
    suspend fun deleteAllCabins() {
        withContext(Dispatchers.IO) {
            database.cabinDao().deleteAll()
        }
    }
}

