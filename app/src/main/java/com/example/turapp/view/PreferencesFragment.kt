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
    ): View? {
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
        binding.calendar.setOnDateChangeListener { view, year, month, dayOfMonth ->
            calendar.set(year,month,dayOfMonth)
            binding.calendar.date = calendar.timeInMillis
        }

        //call for weather-api and start result fragment
        binding.nextButton.setOnClickListener {
            viewModel.loadWeather()
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