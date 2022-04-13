package com.example.turapp.model

import android.app.Application
import com.example.turapp.model.repo.CabinRoomDatabase

class TripApplication : Application() {
    val database : CabinRoomDatabase by lazy { CabinRoomDatabase.getDatabase(this) }
}