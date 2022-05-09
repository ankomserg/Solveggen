package com.example.turapp.model.interfaces

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.turapp.model.data.Cabin

@Dao
interface CabinDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(cabin : Cabin)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(cabins : List<Cabin>)

    @Query("SELECT * FROM cabin_table")
    suspend fun getAllUnsorted() : List<Cabin>

    @Query("SELECT facilities FROM cabin_table")
    suspend fun getAllFac() : List<String>

    @Query("DELETE FROM cabin_table WHERE id = :cabinId")
    suspend fun delete(cabinId : String)

    @Query("DELETE FROM cabin_table")
    suspend fun deleteAll()

    @Query("SELECT * FROM cabin_table ORDER BY air_temperature DESC")
    suspend fun getSortedTemp() : List<Cabin>

    @Query("SELECT * FROM cabin_table ORDER BY wind_speed")
    suspend fun getSortedWind() : List<Cabin>

    @Query("SELECT * FROM cabin_table ORDER BY prec_amount")
    suspend fun getSortedPrec() : List<Cabin>
}