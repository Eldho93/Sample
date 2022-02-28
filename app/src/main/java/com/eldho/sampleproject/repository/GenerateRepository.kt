package com.eldho.sampleproject.repository

import androidx.lifecycle.MutableLiveData
import com.eldho.sampleproject.model.DogDataModel
import com.eldho.sampleproject.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GenerateRepository {

    val apiResponseDogData: MutableLiveData<DogDataModel> = MutableLiveData()

    suspend fun getDogData() {

        RetrofitClient.apiInterface.getDog().enqueue(object : Callback<DogDataModel> {
            override fun onFailure(call: Call<DogDataModel>, t: Throwable) {

            }

            override fun onResponse(
                call: Call<DogDataModel>,
                response: Response<DogDataModel>
            ) {
                apiResponseDogData.value = response.body()

            }
        })

    }

}