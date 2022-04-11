package com.example.turapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import androidx.lifecycle.viewModelScope
import com.example.turapp.model.data.Cabin
import com.example.turapp.model.data.DataSource
import kotlinx.coroutines.launch

class ChooserMapFragmentViewModel: ViewModel() {
    private val dataSource = DataSource()
    private val cabins = MutableLiveData<List<Cabin>>()


    fun loadCabins(){
        viewModelScope.launch(Dispatchers.IO){
            cabins.postValue((dataSource.fetchCabins()))
        }
    }

    fun getCabins(): MutableLiveData<List<Cabin>> {
        return cabins
    }

}