package com.example.turapp.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.turapp.model.repo.CabinRepository
import com.example.turapp.model.repo.CabinRoomDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PreferencesFragmentViewModel(application: Application) : ViewModel() {
    private val cabinRepository = CabinRepository(CabinRoomDatabase.getDatabase(application))

    fun loadWeather(date: String) {
        viewModelScope.launch(Dispatchers.IO) {
            cabinRepository.loadWeather(date)
        }
    }
}