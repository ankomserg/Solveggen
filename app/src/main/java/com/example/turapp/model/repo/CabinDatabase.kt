package com.example.turapp.model.repo

import android.content.Context
import androidx.room.*
import com.example.turapp.model.data.Cabin
import com.example.turapp.model.data.Converter
import com.example.turapp.model.interfaces.CabinDao

// Annotates class to be a Room Database with a table (entity) of the Cabin class
@Database(entities = arrayOf(Cabin::class), version = 1,
    exportSchema = false)
@TypeConverters(Converter::class)
abstract class CabinRoomDatabase : RoomDatabase() {

    abstract fun cabinDao(): CabinDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: CabinRoomDatabase? = null

        fun getDatabase(context: Context): CabinRoomDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CabinRoomDatabase::class.java,
                    "cabin_database"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}