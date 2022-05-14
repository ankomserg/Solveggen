package com.example.turapp.util

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

//                    var i = 0
//                    var temp = sortedCabins[i]
//                    var next : Cabin
//
//                    while (i < sortedCabins.size - 1) {
//                        next = sortedCabins[i + 1]
//
//                        if (temp.air_temperature == next.air_temperature) {
//                            if (temp.precipitation_amount!! > next.precipitation_amount!!) {
//                                sortedCabins[i] = next
//                                sortedCabins[i + 1] = temp
//                            } else if (temp.precipitation_amount!! == next.precipitation_amount!!) {
//                                if (temp.wind_speed!! > next.wind_speed!!) {
//                                    sortedCabins[i] = next
//                                    sortedCabins[i + 1] = temp
//                                }
//                            }
//                        }
//
//                        temp = sortedCabins[i + 1]
//                        i++
//                    }
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
            }

            return sortedCabins
        }
    }
}