package com.example.turapp.model.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cabin_table")
data class Cabin(
    @PrimaryKey @ColumnInfo(name = "id") val id: Number?,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "ddlat") val DDLat: Double,
    @ColumnInfo(name = "ddlon") val DDLon: Double,
    @ColumnInfo(name = "air_temperature") val air_temperature: Number?,
    @ColumnInfo(name = "wind_speed") val wind_speed: Number?,
    @ColumnInfo(name = "prec_amount") val precipitation_amount: String?,
)


