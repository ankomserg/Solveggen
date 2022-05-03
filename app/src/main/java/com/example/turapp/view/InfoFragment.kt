package com.example.turapp.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.turapp.R
import com.example.turapp.databinding.ChooseListFragmentBinding
import com.example.turapp.databinding.InfoFragmentBinding
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

        binding.button.setOnClickListener {
            it.findNavController().popBackStack()
        }
    }
}