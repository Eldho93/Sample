package com.eldho.sampleproject

import androidx.lifecycle.MutableLiveData
import com.eldho.sampleproject.model.DataModel
import com.eldho.sampleproject.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainRepository {

    val apiResponseUserData: MutableLiveData<DataModel> = MutableLiveData()

    suspend fun getUserData(id: String) {

        RetrofitClient.apiInterface.getUser(
            id
        ).enqueue(object : Callback<DataModel> {
            override fun onFailure(call: Call<DataModel>, t: Throwable) {

            }

            override fun onResponse(
                call: Call<DataModel>,
                response: Response<DataModel>
            ) {
                apiResponseUserData.value = response.body()

            }
        })

    }

}