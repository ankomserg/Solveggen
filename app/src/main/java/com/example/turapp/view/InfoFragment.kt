package com.example.turapp.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.turapp.R
import com.example.turapp.databinding.ChooseListFragmentBinding
import com.example.turapp.databinding.InfoFragmentBinding
import com.example.turapp.model.data.Cabin
import com.example.turapp.viewmodel.ChooseListViewModel
import com.example.turapp.viewmodel.InfoViewModel

class InfoFragment : Fragment() {

    private var _binding : InfoFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: InfoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = InfoFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = InfoViewModel(requireNotNull(this.activity).application)

        Log.d("Vasya", viewModel.sharedViewModel.getCabins().value.toString())
        val sharedViewModel = viewModel.sharedViewModel

        sharedViewModel.getCabins().observe(viewLifecycleOwner) {
            val cabin = it.filter { it.id == sharedViewModel.getCabinId() }.get(0)

            binding.title.text = cabin.name
            binding.bedsSet.text = cabin.beds.toString()
            binding.descriptionSet.text = cabin.description
            binding.placeSet.text = cabin.fylke
            binding.heightSet.text = cabin.altitude.toString()
            binding.directionsSet.text = cabin.directions

            if (cabin.booking != null) {
                binding.bookingSet.text = cabin.booking
            }

            binding.serviceSet.text = cabin.betjening
        }


        binding.button.setOnClickListener {
            it.findNavController().popBackStack()
        }
    }
}