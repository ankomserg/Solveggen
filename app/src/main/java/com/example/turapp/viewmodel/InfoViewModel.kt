package com.example.turapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel

class InfoViewModel(application: Application) : AndroidViewModel(application) {
    val sharedViewModel = ChooseListViewModel.getInstance(application)

}