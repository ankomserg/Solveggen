package com.example.turapp.view.adapters

import android.app.Application
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.turapp.R
import com.example.turapp.databinding.CabinElementBinding
import com.example.turapp.model.data.Cabin
import com.example.turapp.viewmodel.ChooseListViewModel

class ChooseListAdapter(private val cabins : List<Cabin>)
    : RecyclerView.Adapter<ChooseListAdapter.ViewHolder> (){

    class ViewHolder(
        private val binding: CabinElementBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bindCabin(cabin: Cabin) {
            Glide.with(binding.cabinPicture).load(cabin.image?.get(0)).into(binding.cabinPicture)
            binding.title.text = cabin.name
            binding.infoFirst.text = cabin.fylke
            binding.infoSecond.text = cabin.beds.toString() + " sengeplasser"

            var viewModel = ChooseListViewModel.getInstance(binding
                .root.context.applicationContext as Application)

            if (cabin.isChecked) {
                binding.chooseListCheckbox.isChecked = true
            }

            binding.chooseListCheckbox.setOnCheckedChangeListener { _, _ ->
                cabin.isChecked = binding.chooseListCheckbox.isChecked
            }

            binding.moreInfo.setOnClickListener {
                viewModel.setCabinId(cabin.id)
                it.findNavController().navigate(
                    R.id.action_chooseListFragment2_to_infoFragment)
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