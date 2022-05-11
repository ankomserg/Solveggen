package com.example.turapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.turapp.R
import com.example.turapp.databinding.FragmentPreferencesBinding
import com.example.turapp.viewmodel.PreferencesFragmentViewModel
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import java.util.*

class PreferencesFragment : Fragment() {

    private var _binding: FragmentPreferencesBinding? = null
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

        //standard option is temperature
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
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                binding.checkboxTemperature.isEnabled = true
                binding.checkboxWind.isEnabled = true
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
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

        val constraintsBuilder =
            CalendarConstraints.Builder()
                .setValidator(DateValidatorPointForward.now())

        val dateRangePicker =
            MaterialDatePicker.Builder.dateRangePicker()
                .setTitleText("Velg dato")
                .setCalendarConstraints(constraintsBuilder.build())
                .build()

        binding.calendarButton.setOnClickListener {
            dateRangePicker.show(parentFragmentManager, "tag")
        }



        dateRangePicker.addOnPositiveButtonClickListener {
            binding.nextButton.isEnabled = true
            val startDate = Calendar.getInstance()
            val endDate = Calendar.getInstance()
            val nowDate = Calendar.getInstance()
            startDate.timeInMillis = dateRangePicker.selection?.first!!
            endDate.timeInMillis = dateRangePicker.selection?.second!!

            //we only have weather forecast for max 10 days, check endDate<10 days
            if (endDate.timeInMillis - nowDate.timeInMillis < 864000000) {
                val formatter = SimpleDateFormat("yyyy-MM-dd'T'", Locale.FRENCH)
                val startString = formatter.format(startDate.time) + "12:00:00Z"
                val endString = formatter.format(endDate.time) + "12:00:00Z"
                viewModel.loadWeather(startString, endString)
                binding.datesSelectedSet.text = dateRangePicker.headerText
            } else {
                val text = "No weather forecast to find!"
                val duration = Toast.LENGTH_LONG
                Toast.makeText(context, text, duration).show()
            }
        }

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
}