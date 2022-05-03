package com.example.turapp.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.turapp.R
import com.example.turapp.databinding.FragmentPreferencesBinding
import com.example.turapp.viewmodel.PreferencesFragmentViewModel
import java.util.*

class PreferencesFragment : Fragment() {

    private var _binding : FragmentPreferencesBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: PreferencesFragmentViewModel
    private lateinit var option: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentPreferencesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = PreferencesFragmentViewModel(requireNotNull(this.activity).application)

        option = "temperature"

        //checking preference "wind"
        val checkBoxWind = binding.checkboxWind
        checkBoxWind.setOnClickListener {
            if (checkBoxWind.isChecked) {
                option = "wind"
                binding.checkboxTemperature.isEnabled = false
                binding.checkboxRain.isEnabled = false
            } else {
                binding.checkboxTemperature.isEnabled = true
                binding.checkboxRain.isEnabled = true
            }
        }

        //checking preference "rain"
        val checkBoxRain = binding.checkboxRain
        checkBoxRain.setOnClickListener {
            if (checkBoxRain.isChecked) {
                option = "rain"
                binding.checkboxTemperature.isEnabled = false
                binding.checkboxWind.isEnabled = false
            } else {
                binding.checkboxTemperature.isEnabled = true
                binding.checkboxWind.isEnabled = true
            }
        }

        //checking preference temperature
        val checkBoxTemp = binding.checkboxTemperature
        checkBoxTemp.setOnClickListener {
            if (checkBoxTemp.isChecked) {
                option = "temperature"
                binding.checkboxRain.isEnabled = false
                binding.checkboxWind.isEnabled = false
            } else {
                binding.checkboxRain.isEnabled = true
                binding.checkboxWind.isEnabled = true
            }
        }

        //select date for loadWeather()
        val calendar1 = Calendar.getInstance()
        val calendar2 = Calendar.getInstance()
        binding.calendar.setOnDateChangeListener { _, year, month, dayOfMonth ->
            calendar1.set(year,month,dayOfMonth)
            if (calendar1.timeInMillis - calendar2.timeInMillis < 864000000
                && calendar2.timeInMillis <= calendar1.timeInMillis) {

                var monthString = (month+1).toString()
                if (month+1 < 10)
                    monthString = "0$monthString"
                var dayString = dayOfMonth.toString()
                if (dayOfMonth < 10)
                    dayString = "0$dayString"
                val dateString = "$year-$monthString-$dayString"+"T12:00:00Z"
                Log.d("DATESTRING:", dateString)
                binding.calendar.date = calendar1.timeInMillis
                viewModel.loadWeather(dateString)
            } else {
                val text = "No weather forecast to find!"
                val duration = Toast.LENGTH_SHORT
                Toast.makeText(context, text, duration).show()
            }
        }

        Log.d("CALENDAR: ", calendar1.toString())
        Log.d("DAY:", calendar1.get(Calendar.DAY_OF_MONTH).toString())
        Log.d("MONTH:", calendar1.get(Calendar.MONTH).toString())
        //Log.d("YEAR:" , calendar1.get(Calendar.YEAR).toString())

        //call for weather-api and start result fragment
        binding.nextButton.setOnClickListener {
            val bundle = bundleOf("option" to option)
            it.findNavController().navigate(
                R.id.action_preferencesFragment_to_resultFragment, bundle
            )
        }

        //go back to start fragment
        binding.backButton.setOnClickListener {
            it.findNavController().navigate(
                R.id.action_preferencesFragment_to_chooseListFragment2
            )
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