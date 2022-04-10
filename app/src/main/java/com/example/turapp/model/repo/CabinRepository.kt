package com.example.turapp.model.repo

import com.example.turapp.model.data.Cabin

class CabinRepository(private val database: CabinRoomDatabase) {

    suspend fun insert(cabin: Cabin) {
        database.cabinDao().insert(cabin)
    }

    suspend fun deleteAll() {
        database.cabinDao().deleteAll()
    }

    suspend fun getCabins(preference: String) : List<Cabin> {
        val cabins = when (preference) {
            "temperature" -> database.cabinDao().getSortedTemp()
            "wind" -> database.cabinDao().getSortedWind()
            else -> database.cabinDao().getSortedPrec()
        }

        return cabins
    }
}