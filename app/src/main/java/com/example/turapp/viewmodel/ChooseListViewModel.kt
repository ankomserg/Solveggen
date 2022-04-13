package com.example.turapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.turapp.model.data.Cabin
import com.example.turapp.model.repo.CabinRepository
import com.example.turapp.model.repo.CabinRoomDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ChooseListViewModel(application: Application) : AndroidViewModel(application) {
    private val cabinRepository = CabinRepository(CabinRoomDatabase.getDatabase(application))

    suspend fun loadCabins() : List<Cabin> {
        var cabins : List<Cabin>

        withContext(Dispatchers.IO) {
            cabins = cabinRepository.loadCabins()
        }

        return cabins
    }

}