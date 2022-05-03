package com.example.turapp.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.turapp.R
import com.example.turapp.databinding.CabinElementBinding
import com.example.turapp.model.data.Cabin

class ChooseListAdapter(private val cabins : List<Cabin>)
    : RecyclerView.Adapter<ChooseListAdapter.ViewHolder> (){

    class ViewHolder(
        private val binding: CabinElementBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bindCabin(cabin: Cabin) {
            binding.mapHolder.visibility = View.GONE
            binding.weatherForecast.visibility = View.GONE
            binding.weatherPicture.visibility = View.GONE
            binding.weatherForecastTemperature.visibility = View.GONE
            binding.weatherForecastRain.visibility = View.GONE
            binding.weatherForecastWind.visibility = View.GONE

            binding.title.text = cabin.name
            binding.infoFirst.text = cabin.id.toString()
            binding.infoSecond.text = cabin.DDLat.toString()

            binding.chooseListCheckbox.setOnCheckedChangeListener { _, _ ->
                cabin.isChecked = binding.chooseListCheckbox.isChecked
            }
        }
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val from = LayoutInflater.from(parent.context)
        val binding = CabinElementBinding.inflate(from, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        cabins[position].let { holder.bindCabin(it) }
    }

    override fun getItemCount(): Int {
        return cabins.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }
}