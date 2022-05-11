package com.example.turapp.view.adapters

import android.app.Application
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.constraintlayout.utils.widget.ImageFilterView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.turapp.R
import com.example.turapp.model.data.Cabin
import com.example.turapp.viewmodel.ChooseListViewModel

class ResultAdapter(private val cabins: MutableList<Cabin>
) : RecyclerView.Adapter<ResultAdapter.CabinViewHolder>() {

    class CabinViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.title)
        val firstInfo: TextView = view.findViewById(R.id.info_first)
        val secondInfo: TextView = view.findViewById(R.id.info_second)
        val cabinPhoto: ImageFilterView = view.findViewById(R.id.cabin_picture)
        val temperature: TextView = view.findViewById(R.id.weather_forecast_temperature_set)
        val rain: TextView = view.findViewById(R.id.weather_forecast_rain_set)
        val wind: TextView = view.findViewById(R.id.weather_forecast_wind_set)
        val moreInfo: Button = view.findViewById(R.id.more_info)
        val pos : TextView = view.findViewById(R.id.score)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CabinViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.cabin_element_result, parent, false)
        return CabinViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: CabinViewHolder, position: Int) {
        val showedCabin = cabins[position]
        holder.title.text = showedCabin.name
        holder.firstInfo.text = showedCabin.fylke
        holder.secondInfo.text = showedCabin.beds.toString() + " sengeplasser"
        holder.temperature.text = showedCabin.air_temperature.toString()
        holder.rain.text = showedCabin.precipitation_amount.toString()
        holder.wind.text = showedCabin.wind_speed.toString()
        Glide.with(holder.cabinPhoto).load(showedCabin.image?.get(0)).into(holder.cabinPhoto)
        //holder.weatherPhoto.setAltImageResource(R.drawable.ic_baseline_add_a_photo_24)
        holder.pos.text = (position+1).toString()

        val viewModel = ChooseListViewModel.getInstance(holder.title.context.applicationContext as Application)

        holder.moreInfo.setOnClickListener {
            viewModel.setCabinId(showedCabin.id)

            it.findNavController().navigate(
                R.id.action_resultFragment_to_infoFragment2)
        }
    }

    override fun getItemCount() = cabins.size
}