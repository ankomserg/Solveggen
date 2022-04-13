package com.example.turapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.turapp.databinding.ChooseListrFragmentBinding
import com.example.turapp.view.adapters.ChooseListAdapter
import com.example.turapp.viewmodel.ChooseListViewModel

class ChooseListFragment : Fragment() {
    private var _binding : ChooseListrFragmentBinding? = null

    private val binding get() = _binding!!

    private lateinit var viewModel: ChooseListViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ChooseListrFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(requireContext(), 1)
            // adapter = ChooseListAdapter(viewModel.loadCabins())
        }
    }



}