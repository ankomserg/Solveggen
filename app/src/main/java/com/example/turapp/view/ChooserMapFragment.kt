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
import com.example.turapp.viewmodel.ChooserMapFragmentViewModel
import com.google.android.gms.maps.model.CameraPosition


class ChooserMapFragment : Fragment(){

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_chooser_map, container, false)

        val viewModel: ChooserMapFragmentViewModel by viewModels()
        viewModel.loadCabins()

        val mapFragment = childFragmentManager
            .findFragmentById(R.id.map_view) as SupportMapFragment?
        mapFragment!!.getMapAsync{ mMap ->
            mMap.mapType = GoogleMap.MAP_TYPE_NORMAL
            mMap.clear()
            val googlePlex = CameraPosition.builder()
                .target(LatLng(59.94, 10.72))
                .zoom(5f)
                .build()

            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(googlePlex), 10000, null)

            viewModel.getCabins().observe(viewLifecycleOwner, Observer {
                it.forEach { Cabin ->
                    val cPos = LatLng(Cabin.DDLat, Cabin.DDLon)
                    mMap.addMarker(MarkerOptions().position(cPos).title("Cabin").snippet(Cabin.name.toString()))
                }
            })
        }
        return rootView

    }

}