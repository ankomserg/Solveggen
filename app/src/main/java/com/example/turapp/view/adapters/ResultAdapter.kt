package com.example.turapp.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.constraintlayout.utils.widget.ImageFilterView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.turapp.R
import com.example.turapp.model.data.Cabin

class ResultAdapter(private val cabins: MutableList<Cabin>
) : RecyclerView.Adapter<ResultAdapter.CabinViewHolder>() {

    class CabinViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.title)
        val firstInfo: TextView = view.findViewById(R.id.info_first)
        val secondInfo: TextView = view.findViewById(R.id.info_second)
        val cabinPhoto: ImageFilterView = view.findViewById(R.id.cabin_picture)
        val weatherPhoto: ImageFilterView = view.findViewById(R.id.weather_picture)
        val temperature: TextView = view.findViewById(R.id.weather_forecast_temperature)
        val rain: TextView = view.findViewById(R.id.weather_forecast_rain)
        val wind: TextView = view.findViewById(R.id.weather_forecast_wind)
        val chooser: CheckBox = view.findViewById(R.id.choose_list_checkbox)
        val moreInfo: Button = view.findViewById(R.id.more_info)
        // val map: View = view.findViewById(R.id.map_holder)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CabinViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.cabin_element, parent, false)
        return CabinViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: CabinViewHolder, position: Int) {
        val showedCabin = cabins[position]
        holder.title.text = showedCabin.name
        holder.firstInfo.visibility = View.GONE
        holder.secondInfo.visibility = View.GONE
        holder.temperature.text = showedCabin.air_temperature.toString()
        holder.rain.text = showedCabin.precipitation_amount.toString()
        holder.wind.text = showedCabin.wind_speed.toString()
        //holder.weatherPhoto.setAltImageResource(R.drawable.ic_baseline_add_a_photo_24)
        holder.chooser.visibility = View.GONE
        // holder.map.visibility = View.GONE

        holder.moreInfo.setOnClickListener {
            it.findNavController().navigate(
                R.id.action_resultFragment_to_infoFragment2)
        }
    }

    override fun getItemCount() = cabins.size
}