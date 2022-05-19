package com.example.turapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.turapp.R
import com.example.turapp.databinding.ChooseListFragmentBinding
import com.example.turapp.util.Internet
import com.example.turapp.view.adapters.ChooseListAdapter
import com.example.turapp.viewmodel.ChooseListViewModel

class ChooseListFragment : Fragment() {
    private var _binding: ChooseListFragmentBinding? = null

    private val binding get() = _binding!!

    private lateinit var viewModel: ChooseListViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ChooseListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ChooseListViewModel.getInstance(requireNotNull(this.activity).application)
        viewModel.isCabinsLoaded = false

        if (!Internet.isOnline(view.context)) {
            view.findNavController().navigate(
                R
                    .id.action_chooseListFragment2_to_noInternetFragment
            )
        }

        // if cabins is not already loaded fetch cabins
        if (viewModel.getCabins().value?.isEmpty() != false) {
            viewModel.loadCabins()
        }


        viewModel.getCabins().observe(viewLifecycleOwner) {
            binding.recyclerView.apply {
                layoutManager = GridLayoutManager(requireContext(), 1)
                adapter = ChooseListAdapter(it)
            }
        }

        binding.nextButtonChooseList.setOnClickListener {
            viewModel.storeCabins()

            if (viewModel.getCabinsNumber() != 0) {
                it.findNavController().navigate(
                    R
                        .id.action_chooseListFragment2_to_preferencesFragment
                )
            } else {
                Toast.makeText(
                    context,
                    "Du må velge minst en hytte",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}