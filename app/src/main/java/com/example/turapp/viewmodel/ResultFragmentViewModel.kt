package com.example.turapp.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.turapp.model.data.Cabin
import com.example.turapp.model.repo.CabinRepository
import com.example.turapp.model.repo.CabinRoomDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ResultFragmentViewModel(application: Application): ViewModel() {
    private val cabinRepository = CabinRepository(CabinRoomDatabase.getDatabase(application))
    private var cabins = MutableLiveData<MutableList<Cabin>>()

    fun loadSortedCabins(option: String) {
        viewModelScope.launch(Dispatchers.IO) {
            cabinRepository.getSortedCabins(option).also {
                cabins.postValue(it as MutableList<Cabin>)
            }
        }
    }

    fun getCabins() : MutableLiveData<MutableList<Cabin>> {
        return cabins
    }
}