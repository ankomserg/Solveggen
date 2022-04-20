package com.example.turapp.model.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cabin_table")
data class Cabin(
    @PrimaryKey @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "ddlat") val DDLat: Double?,
    @ColumnInfo(name = "ddlon") val DDLon: Double?,
    @ColumnInfo(name = "air_temperature") var air_temperature: Double?,
    @ColumnInfo(name = "wind_speed") var wind_speed: Double?,
    @ColumnInfo(name = "prec_amount") var precipitation_amount: Double?,
) {
    var isChecked: Boolean = false
}


