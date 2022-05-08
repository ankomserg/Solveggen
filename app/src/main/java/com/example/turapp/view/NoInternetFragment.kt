package com.example.turapp.view

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.turapp.R
import com.example.turapp.databinding.ChooseListFragmentBinding
import com.example.turapp.databinding.FragmentNoInternetBinding
import com.example.turapp.util.Internet


class NoInternetFragment : Fragment() {
    private var _binding: FragmentNoInternetBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNoInternetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.refreshButton.setOnClickListener {
            if (Internet.isOnline(it.context)) {
                it.findNavController().navigate(
                    R
                        .id.action_noInternetFragment_to_chooseListFragment2
                )
            }
        }
    }
}