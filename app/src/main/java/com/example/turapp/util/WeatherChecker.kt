package com.example.turapp.util

import android.util.Log
import com.example.turapp.model.data.Cabin

class WeatherChecker {
    companion object {
        fun sort(cabins : List<Cabin>, option : String) : List<Cabin> {

            val sortedCabins = mutableListOf<Cabin>()
            val map = HashMap<Double?, MutableList<Cabin>>()
            val elementList = ArrayList<Double>()

            when (option) {
                "temperature" -> {
                    for (cabin in cabins) {
                        if (!map.containsKey(cabin.air_temperature)) {
                            map.put(cabin.air_temperature, mutableListOf<Cabin>())
                            map[cabin.air_temperature]!!.add(cabin)
                            elementList.add(cabin.air_temperature!!)
                        } else {
                            map[cabin.air_temperature]!!.add(cabin)
                        }
                    }

                    for (element in elementList) {
                        val newSortedCabins = map.get(element)?.let { secondarySort(it, option) }

                        if (newSortedCabins != null) {
                            for (sortedCabin in newSortedCabins) {
                                sortedCabins.add(sortedCabin)
                            }
                        }
                    }
                }

                "rain" -> {
                    for (cabin in cabins) {
                        if (!map.containsKey(cabin.precipitation_amount)) {
                            map.put(cabin.precipitation_amount, mutableListOf<Cabin>())
                            map[cabin.precipitation_amount]!!.add(cabin)
                            elementList.add(cabin.precipitation_amount!!)
                        } else {
                            map[cabin.precipitation_amount]!!.add(cabin)
                        }
                    }

                    for (element in elementList) {
                        val newSortedCabins = map.get(element)?.let { secondarySort(it, option) }

                        if (newSortedCabins != null) {
                            for (sortedCabin in newSortedCabins) {
                                sortedCabins.add(sortedCabin)
                            }
                        }
                    }
                }

                "wind" -> {
                    for (cabin in cabins) {
                        if (!map.containsKey(cabin.wind_speed)) {
                            map.put(cabin.wind_speed, mutableListOf<Cabin>())
                            map[cabin.wind_speed]!!.add(cabin)
                            elementList.add(cabin.wind_speed!!)
                        } else {
                            map[cabin.wind_speed]!!.add(cabin)
                        }
                    }

                    for (element in elementList) {
                        val newSortedCabins = map.get(element)?.let { secondarySort(it, option) }

                        if (newSortedCabins != null) {
                            for (sortedCabin in newSortedCabins) {
                                sortedCabins.add(sortedCabin)
                            }
                        }
                    }
                }
            }

            return sortedCabins
        }

        fun secondarySort(cabins: List<Cabin>, option: String): MutableList<Cabin> {
            var sortedCabins = mutableListOf<Cabin>()
            cabins.forEach { sortedCabins.add(it)}

            val map = HashMap<Double?, MutableList<Cabin>>()
            val elementList = ArrayList<Double>()

            when (option) {
                "temperature" -> {
                    var swap = true
                    while(swap){
                        swap = false
                        for(i in 0 until sortedCabins.indices.last){
                            if(sortedCabins[i].precipitation_amount!! > sortedCabins[i + 1].precipitation_amount!!){
                                val temp = sortedCabins[i]
                                sortedCabins[i] = sortedCabins[i+1]
                                sortedCabins[i + 1] = temp

                                swap = true
                            }
                        }
                    }

                    for (cabin in sortedCabins) {
                        if (!map.containsKey(cabin.precipitation_amount)) {
                            map.put(cabin.precipitation_amount, mutableListOf<Cabin>())
                            map[cabin.precipitation_amount]!!.add(cabin)
                            elementList.add(cabin.precipitation_amount!!)
                        } else {
                            map[cabin.precipitation_amount]!!.add(cabin)
                        }
                    }

                    sortedCabins = mutableListOf()

                    for (element in elementList) {
                        val newSortedCabins = map.get(element)?.let { finalSort(it, option) }
                        if (newSortedCabins != null) {
                            for (sortedCabin in newSortedCabins) {
                                sortedCabins.add(sortedCabin)
                            }
                        }
                    }
                }

                else -> {
                    var swap = true
                    while(swap){
                        swap = false
                        for(i in 0 until sortedCabins.indices.last){
                            if(sortedCabins[i].air_temperature!! < sortedCabins[i + 1].air_temperature!!) {
                                val temp = sortedCabins[i]
                                sortedCabins[i] = sortedCabins[i+1]
                                sortedCabins[i + 1] = temp

                                swap = true
                            }
                        }
                    }
                    Log.d("HER", sortedCabins.toString())
                    for (cabin in sortedCabins) {
                        if (!map.containsKey(cabin.air_temperature)) {
                            map.put(cabin.air_temperature, mutableListOf<Cabin>())
                            map[cabin.air_temperature]!!.add(cabin)
                            elementList.add(cabin.air_temperature!!)
                        } else {
                            map[cabin.air_temperature]!!.add(cabin)
                        }
                    }

                    sortedCabins = mutableListOf()

                    for (element in elementList) {
                        val newSortedCabins = map.get(element)?.let { finalSort(it, option) }
                        if (newSortedCabins != null) {
                            for (sortedCabin in newSortedCabins) {
                                sortedCabins.add(sortedCabin)
                            }
                        }
                    }
                }
            }

            return sortedCabins
        }

        fun finalSort(cabins: List<Cabin>, option: String): MutableList<Cabin> {
            val sortedCabins = mutableListOf<Cabin>()
            cabins.forEach { sortedCabins.add(it)}

            when (option) {
                "temperature" -> {
                    var swap = true
                    while(swap){
                        swap = false
                        for(i in 0 until sortedCabins.indices.last){
                            if(sortedCabins[i].wind_speed!! > sortedCabins[i+1].wind_speed!!){
                                val temp = sortedCabins[i]
                                sortedCabins[i] = sortedCabins[i+1]
                                sortedCabins[i + 1] = temp

                                swap = true
                            }
                        }
                    }
                }

                "rain" -> {
                    var swap = true
                    while(swap){
                        swap = false
                        for(i in 0 until sortedCabins.indices.last){
                            if(sortedCabins[i].wind_speed!! > sortedCabins[i+1].wind_speed!!){
                                val temp = sortedCabins[i]
                                sortedCabins[i] = sortedCabins[i+1]
                                sortedCabins[i + 1] = temp

                                swap = true
                            }
                        }
                    }
                }

                else -> {
                    var swap = true
                    while(swap){
                        swap = false
                        for(i in 0 until sortedCabins.indices.last){
                            if(sortedCabins[i].precipitation_amount!! > sortedCabins[i+1].precipitation_amount!!){
                                val temp = sortedCabins[i]
                                sortedCabins[i] = sortedCabins[i+1]
                                sortedCabins[i + 1] = temp
                                swap = true
                            }
                        }
                    }
                }
            }

            return sortedCabins
        }
    }
}