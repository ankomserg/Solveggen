package com.example.turapp.view.adapters

import android.app.Application
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.utils.widget.ImageFilterView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.turapp.R
import com.example.turapp.databinding.CabinElementBinding
import com.example.turapp.model.data.Cabin
import com.example.turapp.viewmodel.ChooseListViewModel

class InfoAdapter (val cabin: Cabin) : RecyclerView.Adapter<InfoAdapter.ImageCabinHolder>() {

        class ImageCabinHolder(view: View) : RecyclerView.ViewHolder(view) {
            val image: ImageView = view.findViewById(R.id.cabin_image)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageCabinHolder {
            val adapterLayout = LayoutInflater.from(parent.context)
                .inflate(R.layout.cabin_pictures, parent, false)
            return ImageCabinHolder(adapterLayout)
        }

        override fun onBindViewHolder(holder: ImageCabinHolder, position: Int) {
            val showedImage = cabin.image?.get(position)
            Glide.with(holder.image).load(showedImage).into(holder.image)
        }

        override fun getItemCount() = cabin.image?.size!!

}