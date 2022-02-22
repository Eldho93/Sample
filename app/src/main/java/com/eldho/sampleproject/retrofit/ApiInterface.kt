package com.eldho.sampleproject.retrofit

import com.eldho.sampleproject.model.DataModel
import retrofit2.Call
import retrofit2.http.FieldMap
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface ApiInterface {

    @GET("users/{id}")
    fun getUser(
        @Path("id") id: String?
    ): Call<DataModel>
}