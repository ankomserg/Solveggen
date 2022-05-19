package com.example.turapp.util

import com.example.turapp.model.data.Cabin

/*
This class is a static support class to sort weather based on the users preferences.
It takes a list of Cabins and a String called option (the users selected preference), and
returns a sorted list based on the preference selected.
If option is "temperature" -> the algorithm sorts first on temperature, then rain and then wind.
If option is "rain" -> the algorithm sorts first on rain, then temperature and then wind.
If option is "wind" -> the algorithm sorts first on wind, then temperature and then rain.
 */
class WeatherChecker {
    companion object {
        fun sort(cabins: List<Cabin>, option: String): List<Cabin> {

            val sortedCabins = mutableListOf<Cabin>()
            val map = HashMap<Double?, MutableList<Cabin>>()
            val elementList = ArrayList<Double>()

            when (option) {
                "temperature" -> {
                    for (cabin in cabins) {
                        if (!map.containsKey(cabin.air_temperature)) {
                            map[cabin.air_temperature] = mutableListOf()
                            map[cabin.air_temperature]!!.add(cabin)
                            elementList.add(cabin.air_temperature!!)
                        } else {
                            map[cabin.air_temperature]!!.add(cabin)
                        }
                    }

                    for (element in elementList) {
                        val newSortedCabins = map[element]?.let { secondarySort(it, option) }

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
                            map[cabin.precipitation_amount] = mutableListOf()
                            map[cabin.precipitation_amount]!!.add(cabin)
                            elementList.add(cabin.precipitation_amount!!)
                        } else {
                            map[cabin.precipitation_amount]!!.add(cabin)
                        }
                    }

                    // sort on second criteria if needed
                    for (element in elementList) {
                        val newSortedCabins = map[element]?.let { secondarySort(it, option) }

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
                            map[cabin.wind_speed] = mutableListOf()
                            map[cabin.wind_speed]!!.add(cabin)
                            elementList.add(cabin.wind_speed!!)
                        } else {
                            map[cabin.wind_speed]!!.add(cabin)
                        }
                    }

                    // sort on second criteria if needed
                    for (element in elementList) {
                        val newSortedCabins = map[element]?.let { secondarySort(it, option) }

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

        private fun secondarySort(cabins: List<Cabin>, option: String): MutableList<Cabin> {
            var sortedCabins = mutableListOf<Cabin>()
            cabins.forEach { sortedCabins.add(it) }

            val map = HashMap<Double?, MutableList<Cabin>>()
            val elementList = ArrayList<Double>()

            when (option) {
                "temperature" -> {
                    var swap = true
                    while (swap) {
                        swap = false
                        for (i in 0 until sortedCabins.indices.last) {
                            if (sortedCabins[i].precipitation_amount!! >
                                    sortedCabins[i + 1].precipitation_amount!!) {
                                val temp = sortedCabins[i]
                                sortedCabins[i] = sortedCabins[i + 1]
                                sortedCabins[i + 1] = temp

                                swap = true
                            }
                        }
                    }

                    for (cabin in sortedCabins) {
                        if (!map.containsKey(cabin.precipitation_amount)) {
                            map[cabin.precipitation_amount] = mutableListOf()
                            map[cabin.precipitation_amount]!!.add(cabin)
                            elementList.add(cabin.precipitation_amount!!)
                        } else {
                            map[cabin.precipitation_amount]!!.add(cabin)
                        }
                    }

                    sortedCabins = mutableListOf()

                    // sort on third criteria if needed
                    for (element in elementList) {
                        val newSortedCabins = map[element]?.let { finalSort(it, option) }
                        if (newSortedCabins != null) {
                            for (sortedCabin in newSortedCabins) {
                                sortedCabins.add(sortedCabin)
                            }
                        }
                    }
                }

                else -> { //option is rain or wind
                    var swap = true
                    while (swap) {
                        swap = false
                        for (i in 0 until sortedCabins.indices.last) {
                            if (sortedCabins[i].air_temperature!! <
                                    sortedCabins[i + 1].air_temperature!!) {
                                val temp = sortedCabins[i]
                                sortedCabins[i] = sortedCabins[i + 1]
                                sortedCabins[i + 1] = temp

                                swap = true
                            }
                        }
                    }

                    for (cabin in sortedCabins) {
                        if (!map.containsKey(cabin.air_temperature)) {
                            map[cabin.air_temperature] = mutableListOf()
                            map[cabin.air_temperature]!!.add(cabin)
                            elementList.add(cabin.air_temperature!!)
                        } else {
                            map[cabin.air_temperature]!!.add(cabin)
                        }
                    }

                    sortedCabins = mutableListOf()

                    // sort on third criteria if needed
                    for (element in elementList) {
                        val newSortedCabins = map[element]?.let { finalSort(it, option) }
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

        private fun finalSort(cabins: List<Cabin>, option: String): MutableList<Cabin> {
            val sortedCabins = mutableListOf<Cabin>()
            cabins.forEach { sortedCabins.add(it) }

            when (option) {
                "rain" -> {
                    var swap = true
                    while (swap) {
                        swap = false
                        for (i in 0 until sortedCabins.indices.last) {
                            if (sortedCabins[i].precipitation_amount!! >
                                    sortedCabins[i + 1].precipitation_amount!!) {
                                val temp = sortedCabins[i]
                                sortedCabins[i] = sortedCabins[i + 1]
                                sortedCabins[i + 1] = temp
                                swap = true
                            }
                        }
                    }
                }

                else -> { //option is wind or temperature
                    var swap = true
                    while (swap) {
                        swap = false
                        for (i in 0 until sortedCabins.indices.last) {
                            if (sortedCabins[i].wind_speed!! > sortedCabins[i + 1].wind_speed!!) {
                                val temp = sortedCabins[i]
                                sortedCabins[i] = sortedCabins[i + 1]
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