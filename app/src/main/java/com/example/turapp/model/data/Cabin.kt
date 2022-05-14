package com.example.turapp.model.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.gson.Gson

@Entity(tableName = "cabin_table")
data class Cabin (
    @PrimaryKey @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "ddlat") val DDLat: Double?,
    @ColumnInfo(name = "ddlon") val DDLon: Double?,
    @ColumnInfo(name = "air_temperature") var air_temperature: Double?,
    @ColumnInfo(name = "wind_speed") var wind_speed: Double?,
    @ColumnInfo(name = "prec_amount") var precipitation_amount: Double?,
    @ColumnInfo(name = "avg_temp") var avg_temperature: Double?,
    @ColumnInfo(name = "avg_wind") var avg_wind: Double?,
    @ColumnInfo(name = "avg_prec") var avg_precipitation: Double?,
    @ColumnInfo(name = "image") val image: List<String>?,
    @ColumnInfo(name = "beds") val beds: Int?,
    @ColumnInfo(name = "fylke") val fylke: String?,
    @ColumnInfo(name = "betjening") val betjening: String?,
    @ColumnInfo(name = "altitude") val altitude: Int?,
    @ColumnInfo(name = "facilities") val facilities: List<String>?,
    @ColumnInfo(name = "description") val description: String?,
    @ColumnInfo(name = "booking") val booking: String?,
    @ColumnInfo(name = "directions") val directions: String?,
    @ColumnInfo(name = "stop") val stops: List<Stop>?
) {
    var isChecked: Boolean = false



}

data class Stop(val stop: String, val distance: Number?)

class Converter{

    @TypeConverter
    fun listToJson(value: List<String>?): String = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String?): List<String> {
        if (Gson().fromJson(value, Array<String>::class.java) == null) return emptyList()
        return Gson().fromJson(value, Array<String>::class.java).toList()
    }

    @TypeConverter
    fun stopListToJson(value: List<Stop>?): String = Gson().toJson(value)

    @TypeConverter
    fun jsonToStopList(value: String?): List<Stop> {
        if (Gson().fromJson(value, Array<Stop>::class.java) == null) return emptyList()
        return Gson().fromJson(value, Array<Stop>::class.java).toList()
    }
}


