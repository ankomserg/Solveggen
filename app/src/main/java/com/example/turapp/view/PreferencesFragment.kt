package com.example.turapp.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
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
        val checkBoxWind = binding.root.findViewById<View>(R.id.checkbox_wind)
        checkBoxWind.setOnClickListener {
            if (it is CheckBox)
                if (it.isChecked)
                    option = "wind"
        }

        //checking preference "rain"
        val checkBoxRain = binding.root.findViewById<View>(R.id.checkbox_rain)
        checkBoxRain.setOnClickListener {
            if (it is CheckBox)
                if (it.isChecked)
                    option = "rain"
        }

        //select date for loadWeather()
        val calendar = Calendar.getInstance()
        binding.calendar.setOnDateChangeListener { _, year, month, dayOfMonth ->
            calendar.set(year,month,dayOfMonth)
            var monthString = (month+1).toString()
            if (month+1 < 10)
                monthString = "0$monthString"
            var dayString = dayOfMonth.toString()
            if (dayOfMonth < 10)
                dayString = "0$dayString"
            val dateString = "$year-$monthString-$dayString"+"T12:00:00Z"
            Log.d("DATESTRING:", dateString)
            binding.calendar.date = calendar.timeInMillis
        }

        Log.d("CALENDAR: ", calendar.toString())
        Log.d("DAY:", calendar.get(Calendar.DAY_OF_MONTH).toString())
        Log.d("MONTH:", calendar.get(Calendar.MONTH).toString())
        Log.d("YEAR:" , calendar.get(Calendar.YEAR).toString())

        //call for weather-api and start result fragment
        viewModel.loadWeather()
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