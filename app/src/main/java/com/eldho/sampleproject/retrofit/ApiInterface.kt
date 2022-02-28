package com.eldho.sampleproject.retrofit

import com.eldho.sampleproject.model.DogDataModel
import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {

    @GET("breeds/image/random")
    fun getDog(): Call<DogDataModel>
}