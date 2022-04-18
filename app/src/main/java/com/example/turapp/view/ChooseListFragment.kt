package com.example.turapp.view

import android.app.Application
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.turapp.databinding.ChooseListFragmentBinding
import com.example.turapp.model.TripApplication
import com.example.turapp.view.adapters.ChooseListAdapter
import com.example.turapp.viewmodel.ChooseListViewModel

class ChooseListFragment : Fragment() {
    private var _binding : ChooseListFragmentBinding? = null

    private val binding get() = _binding!!

    private lateinit var viewModel: ChooseListViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ChooseListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ChooseListViewModel(requireNotNull(this.activity).application)

        viewModel.getCabins().observe(viewLifecycleOwner) {
            binding.recyclerView.apply {
                layoutManager = GridLayoutManager(requireContext(), 1)
                adapter = ChooseListAdapter(it)
            }
        }



    }



}