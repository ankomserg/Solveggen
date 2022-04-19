package com.example.turapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.fragment.app.Fragment
import com.example.turapp.R
import com.example.turapp.databinding.FragmentPreferencesBinding
import com.example.turapp.viewmodel.PreferencesFragmentViewModel

class PreferencesFragment : Fragment() {

    private var _binding : FragmentPreferencesBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: PreferencesFragmentViewModel
    private lateinit var option: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentPreferencesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = PreferencesFragmentViewModel(requireNotNull(this.activity).application)
        viewModel.loadWeather()
    }

    fun onCheckboxClicked(view: View) {
        if (view is CheckBox) {
            val checked: Boolean = view.isChecked

            when (view.id) {
                R.id.checkbox_temperature -> {
                    if (checked)
                        option = "temperature"
                }

                R.id.checkbox_rain -> {
                    if (checked)
                        option = "rain"
                }

                R.id.checkbox_wind -> {
                    if (checked)
                        option = "wind"
                }
            }
        }
    }


    /*
    fun findAllTemperatures(weather: Weather, toTime: String, fromTime: String) {
        val timeseries = weather.properties?.timeseries
        var i = 0
        while (timeseries?.get(i)?.time != toTime) {
            i++
        }
        var count = 0
        var totTemperature = 0
        while (timeseries?.get(i)?.time != fromTime) {
            totTemperature += timeseries?.get(i)?.data?.instant?.details?.air_temperature?.toInt()
                ?: 0
        }
    }*/
}