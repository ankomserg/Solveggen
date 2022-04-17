package com.example.turapp.model.interfaces

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.turapp.model.data.Cabin
import kotlinx.coroutines.flow.Flow

@Dao
interface CabinDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(cabins : List<Cabin>)

    @Query("SELECT * FROM cabin_table")
    suspend fun getAllUnsorted() : LiveData<List<Cabin>>

    @Query("DELETE FROM cabin_table")
    suspend fun deleteAll()

    @Query("SELECT * FROM cabin_table ORDER BY air_temperature DESC")
    suspend fun getSortedTemp() : LiveData<List<Cabin>>

    @Query("SELECT * FROM cabin_table ORDER BY wind_speed")
    suspend fun getSortedWind() : LiveData<List<Cabin>>

    @Query("SELECT * FROM cabin_table ORDER BY prec_amount")
    suspend fun getSortedPrec() : LiveData<List<Cabin>>
}