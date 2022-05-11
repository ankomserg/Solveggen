package com.example.turapp.view

import com.example.turapp.R
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.testmap.CustomInfoWindowAdapter
import com.example.turapp.databinding.FragmentChooserMapBinding
import com.example.turapp.viewmodel.ChooserMapFragmentViewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.Marker


class ChooserMapFragment : Fragment(), GoogleMap.OnInfoWindowClickListener {

    private var _binding : FragmentChooserMapBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        _binding = FragmentChooserMapBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val viewModel: ChooserMapFragmentViewModel by viewModels()
        viewModel.loadCabins()

        val mapFragment = childFragmentManager
            .findFragmentById(R.id.map_view) as SupportMapFragment?
        mapFragment!!.getMapAsync { mMap ->
            mMap.mapType = GoogleMap.MAP_TYPE_NORMAL
            mMap.uiSettings.isZoomControlsEnabled = true
            mMap.clear()
            val cameraPosition = CameraPosition.builder()
                .target(LatLng(59.94, 10.72))
                .zoom(5f)
                .build()

            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))

            mMap.setInfoWindowAdapter(CustomInfoWindowAdapter(this))

            viewModel.getCabins().observe(viewLifecycleOwner, Observer {
                it.forEach { Cabin ->
                    val cPos = LatLng(Cabin.DDLat!!, Cabin.DDLon!!)
                    val marker = mMap.addMarker(MarkerOptions().position(cPos))
                    marker?.tag = Cabin
                    marker?.showInfoWindow()
                }
            })
            mMap.setOnInfoWindowClickListener(this)
        }

    }

    override fun onInfoWindowClick(marker: Marker) {
        findNavController().navigate(R.id.action_chooserMapFragment_to_infoFragment)
    }

}