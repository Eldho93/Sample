package com.eldho.sampleproject.retrofit


import com.eldho.sampleproject.retrofit.ApiCalls.Companion.MainServer
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

import java.util.concurrent.TimeUnit


object RetrofitClient {


    val retrofitClient: Retrofit.Builder by lazy {

        val logging = HttpLoggingInterceptor()

        Retrofit.Builder()
            .baseUrl(MainServer)
            .client(getRetrofitClient( interceptors = logging))
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
    }

    val apiInterface: ApiInterface by lazy {
        retrofitClient
            .build()
            .create(ApiInterface::class.java)
    }


    private fun getRetrofitClient(
        interceptors: HttpLoggingInterceptor
    ): OkHttpClient {

        val okhttpClient = OkHttpClient().newBuilder()
        okhttpClient.addInterceptor(interceptors)

        okhttpClient.apply {
            connectTimeout(30, TimeUnit.SECONDS)
            writeTimeout(30, TimeUnit.SECONDS)
            readTimeout(30, TimeUnit.SECONDS)
        }

        return okhttpClient.build()
    }
}

