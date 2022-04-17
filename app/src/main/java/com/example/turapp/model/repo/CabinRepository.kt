package com.example.turapp.model.repo

import androidx.lifecycle.LiveData
import com.example.turapp.model.data.Cabin
import com.example.turapp.model.data.DataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CabinRepository(private val database: CabinRoomDatabase) {
    suspend fun loadCabins() : List<Cabin> {
       val cabins : List<Cabin>

       withContext(Dispatchers.IO) {
           deleteAll()
           val datasource = DataSource()
           cabins = datasource.fetchCabins()
           database.cabinDao().insertAll(cabins)
       }

       return cabins
   }

    suspend fun loadWeather() {
        withContext(Dispatchers.IO) {

        }

    }

    private suspend fun deleteAll() {
        withContext(Dispatchers.IO) {
            database.cabinDao().deleteAll()
        }
    }

    suspend fun getCabins() : LiveData<List<Cabin>> {
        var cabins : LiveData<List<Cabin>>

        withContext(Dispatchers.IO) {
            cabins = database.cabinDao().getAllUnsorted()
        }

        return cabins
    }

    suspend fun getSortedCabins(preference: String) : LiveData<List<Cabin>> {
        var cabins : LiveData<List<Cabin>>

        withContext(Dispatchers.IO) {
            cabins = when (preference) {
                "temperature" -> database.cabinDao().getSortedTemp()
                "wind" -> database.cabinDao().getSortedWind()
                else -> database.cabinDao().getSortedPrec()
            }
        }

        return cabins

    }
}