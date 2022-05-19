package com.example.turapp.view.adapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.turapp.R
import com.example.turapp.model.data.Cabin
import com.example.turapp.view.ChooserMapFragment
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker

//adapter for custom info windows
class CustomInfoWindowAdapter(context: ChooserMapFragment) : GoogleMap.InfoWindowAdapter {


    private var mWindow: View = (context as Fragment).layoutInflater.inflate(R.layout.custom_info_window, null)

    private fun rendowWindowText(marker: Marker, view: View){
        val mInfoWindow: Cabin? = marker.tag as Cabin?

        val title = view.findViewById<TextView>(R.id.title)
        val snippet = view.findViewById<TextView>(R.id.snippet)
        val image = view.findViewById<ImageView>(R.id.image)

        title.text = mInfoWindow?.name
        snippet.text = snippet.context.getString(
            R.string.holder_second_info, mInfoWindow?.beds)

        Glide.with(image)
            .load(mInfoWindow?.image?.get(0))
            .override(150)
            .centerCrop()
            .into(image)

    }

    override fun getInfoContents(marker: Marker): View {
        rendowWindowText(marker, mWindow)
        return mWindow
    }

    override fun getInfoWindow(marker: Marker): View? {
        return null
    }
}