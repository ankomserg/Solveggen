package com.example.turapp.model.repo

import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry
import com.example.turapp.model.data.Cabin
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class CabinRepositoryTest {

    private lateinit var cabinRepository: CabinRepository
    private lateinit var context: Context
    private lateinit var cabinRoomDatabase: CabinRoomDatabase


    @Before
    fun setup() {
        context = InstrumentationRegistry.getInstrumentation().targetContext
        cabinRoomDatabase = CabinRoomDatabase.getDatabase(context)
        cabinRepository = CabinRepository(cabinRoomDatabase)
    }

    @Test
    fun insertCabinTest() = runBlocking {

        val cabin = Cabin(
            1, "Skihytta", null, null, null,
            null, null, null, null,
            null, null, null, null, null,
            null, null, null
        )

        cabinRepository.insertCabin(cabin)
        assertEquals("Skihytta", cabinRepository.getCabins()[0].name)
    }
}