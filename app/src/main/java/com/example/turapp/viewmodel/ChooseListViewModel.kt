package com.example.turapp.viewmodel

import android.app.Application
import androidx.annotation.MainThread
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.turapp.model.data.Cabin
import com.example.turapp.model.repo.CabinRepository
import com.example.turapp.model.repo.CabinRoomDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ChooseListViewModel(application: Application) : AndroidViewModel(application) {
    private val cabinRepository = CabinRepository(CabinRoomDatabase.getDatabase(application))
    private var cabins = MutableLiveData<MutableList<Cabin>>()

    fun loadCabins() {

        viewModelScope.launch(Dispatchers.IO) {
            cabinRepository.loadCabins().also {
                cabins.postValue(it as MutableList<Cabin>)
            }
        }
    }

    fun storeCabins() {
        viewModelScope.launch(Dispatchers.IO) {
            cabinRepository.deleteAllCabins()
            for (cabin in cabins.value!!) {
                if (cabin.isChecked) {
                    cabinRepository.insertCabin(cabin)
                }
            }
        }
    }

    fun getCabins() : MutableLiveData<MutableList<Cabin>> {
        return cabins
    }

    fun getCabinsNumber() : Int {
        var chosenCabinsNumber = 0

        for (cabin in cabins.value!!) {
            if (cabin.isChecked) {
                chosenCabinsNumber++
            }
        }

        return chosenCabinsNumber
    }

    companion object {
        private lateinit var viewModel: ChooseListViewModel

        @MainThread
        fun getInstance(application: Application): ChooseListViewModel{
            viewModel = if(::viewModel.isInitialized) viewModel else ChooseListViewModel(application)
            return viewModel
        }
    }

}