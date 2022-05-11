package com.example.turapp.model.interfaces

import com.example.turapp.model.data.Cabin
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface CabinApi {
    @GET("lenamarsilius/{number}/cabins")
    suspend fun getCabins(
        @Path("number") number: String?)
            : Response<Array<Cabin>>
}