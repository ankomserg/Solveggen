package com.example.turapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.turapp.databinding.InfoFragmentBinding
import com.example.turapp.view.adapters.InfoAdapter
import com.example.turapp.viewmodel.InfoViewModel

class InfoFragment : Fragment() {

    private var _binding : InfoFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: InfoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = InfoFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = InfoViewModel(requireNotNull(this.activity).application)

        val sharedViewModel = viewModel.sharedViewModel

        //set information for each cabin to show
        sharedViewModel.getCabins().observe(viewLifecycleOwner) {
            val cabin = it.filter { it.id == sharedViewModel.getCabinId() }.get(0)

            binding.title.text = cabin.name
            binding.bedsSet.text = cabin.beds.toString()
            binding.descriptionSet.text = cabin.description
            binding.placeSet.text = cabin.region
            binding.heightSet.text = cabin.altitude.toString()
            binding.directionsSet.text = cabin.directions ?: "Ingen veibeskrivelse tilgjengelig"
            binding.serviceSet.text = cabin.service

            var listOfFacilities = ""
            if (cabin.facilities != null) {
                for (facility in cabin.facilities)
                    listOfFacilities += "$facility, "
                listOfFacilities = listOfFacilities.removeSuffix(", ")
                binding.facilitiesSet.text = listOfFacilities
            }

            var listOfStops = ""
            if (cabin.stops != null) {
                for (stop in cabin.stops)
                    listOfStops += stop.stop + ": " + stop.distance + " meter unna\n"
                binding.stopsSet.text = listOfStops
            }

            if (cabin.booking != null) {
                binding.bookingSet.text = cabin.booking
            }

            binding.imageView.apply {
                layoutManager = GridLayoutManager(requireContext(), 1,
                                GridLayoutManager.HORIZONTAL, false)
                adapter = InfoAdapter(cabin)
            }
        }

        binding.button.setOnClickListener {
            it.findNavController().popBackStack()
        }
    }
}