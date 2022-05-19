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

    fun getCabins(): MutableLiveData<MutableList<Cabin>> {
        return cabins
    }

    /*if user has already chosen cabins then we fetch weather forecast for them
    otherwise if user went directly from chooselistfragment to resultfragment
    we are fetching cabins from JSON database and after that fetching their weather forecast
     */
    fun getCabinsFromDatabase(startDate: String, endDate: String, option: String) {
        viewModelScope.launch(Dispatchers.Main) {
            Log.d("DataBa", sharedViewModel.isCabinsLoaded.toString() + " before")
            if (sharedViewModel.isCabinsLoaded) {
                Log.d("DataBa", sharedViewModel.isCabinsLoaded.toString() + " if")
                cabinRepository.loadWeather(startDate, endDate)

                val sortedCabins = WeatherChecker.sort(cabinRepository.getSortedCabins(option), option)
                cabins.postValue(sortedCabins as MutableList<Cabin>)

            } else {
                Log.d("DataBa", sharedViewModel.isCabinsLoaded.toString() + " else")
                cabinRepository.loadCabins().also { cabins ->
                    cabins.forEach {
                        cabinRepository.insertCabin(it)
                    }
                }

                cabinRepository.loadWeather(startDate, endDate)
                val sortedCabins = WeatherChecker.sort(cabinRepository.getSortedCabins(option), option)
                cabins.postValue(sortedCabins as MutableList<Cabin>)
                cabinRepository.deleteAllCabins()
            }
        }
    }

}


