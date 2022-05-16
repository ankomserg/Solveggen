package com.example.turapp.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.turapp.model.data.Cabin
import com.example.turapp.model.repo.CabinRepository
import com.example.turapp.model.repo.CabinRoomDatabase
import com.example.turapp.util.WeatherChecker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ResultFragmentViewModel(application: Application) : ViewModel() {
    private val cabinRepository = CabinRepository(CabinRoomDatabase.getDatabase(application))
    private var cabins = MutableLiveData<MutableList<Cabin>>()
    val sharedViewModel = ChooseListViewModel.getInstance(application)

    /* private fun loadSortedCabins(option: String) {
        viewModelScope.launch(Dispatchers.IO) {
            cabinRepository.getSortedCabins(option).also {
                cabins.postValue(it as MutableList<Cabin>)
                Log.d("KOM HIT", cabins.value?.size.toString() + "loadSortedCabins")
            }
        }
    } */

    /* fun loadWeather(startDate: String, endDate: String, option: String) {
        viewModelScope.launch(Dispatchers.IO) {
            cabinRepository.loadWeather(startDate, endDate)
            loadSortedCabins(option)
            Log.d("KOM HIT", cabins.value?.size.toString() + "loadWeather")
        }
    } */

    fun getCabins(): MutableLiveData<MutableList<Cabin>> {
        return cabins
    }

    fun getCabinsFromDatabase(startDate: String, endDate: String, option: String) {
        viewModelScope.launch(Dispatchers.Main) {
            Log.d("DataBa", sharedViewModel.isCabinsLoaded.toString() + " before")
            if (sharedViewModel.isCabinsLoaded) {
                Log.d("DataBa", sharedViewModel.isCabinsLoaded.toString() + " if")
                cabinRepository.loadWeather(startDate, endDate)

                val cabinsTest = cabinRepository.getSortedCabins(option)
                cabins.postValue(cabinsTest as MutableList<Cabin>)
                /*cabinRepository.getSortedCabins(option).also {
                    cabins.postValue(it as MutableList<Cabin>)
                }*/
            } else {
                Log.d("DataBa", sharedViewModel.isCabinsLoaded.toString() + " else")
                cabinRepository.loadCabins().also { cabins ->
                    cabins.forEach {
                        cabinRepository.insertCabin(it)
                    }
                }

                cabinRepository.loadWeather(startDate, endDate)
                val cabinsTest = cabinRepository.getSortedCabins(option)
                cabins.postValue(cabinsTest as MutableList<Cabin>)
                /*cabinRepository.getSortedCabins(option).also {
                    cabins.postValue(it as MutableList<Cabin>)
                }*/
                cabinRepository.deleteAllCabins()
            }
        }
    }

}


