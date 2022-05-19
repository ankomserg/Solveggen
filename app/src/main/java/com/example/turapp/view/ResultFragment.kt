package com.example.turapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.turapp.databinding.FragmentResultBinding
import com.example.turapp.view.adapters.ResultAdapter
import com.example.turapp.viewmodel.ResultFragmentViewModel
import java.text.SimpleDateFormat
import java.util.*


class ResultFragment : Fragment() {

    private var _binding: FragmentResultBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: ResultFragmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ResultFragmentViewModel(requireNotNull(this.activity).application)

        //prepare defualt value for date
        val nowDate = Calendar.getInstance()
        val formatter = SimpleDateFormat("yyyy-MM-dd'T'", Locale.FRENCH)
        val nowString = formatter.format(nowDate.time) + "12:00:00Z"

        //get arguments for weather forecast and load weather
        val startString = arguments?.getString("startString") ?: nowString
        val endString = arguments?.getString("endString") ?: nowString
        val option = arguments?.getString("option") ?: "temperature"
        viewModel.getCabinsFromDatabase(startString, endString, option)

        viewModel.getCabins().observe(viewLifecycleOwner) {
            binding.recyclerView.apply {
                layoutManager = GridLayoutManager(requireContext(), 1)
                adapter = ResultAdapter(it)
            }
        }
    }
}