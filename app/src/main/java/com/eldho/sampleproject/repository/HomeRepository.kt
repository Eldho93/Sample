package com.eldho.sampleproject.repository

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.eldho.sampleproject.model.Sellers
import com.eldho.sampleproject.model.Villages
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException

class HomeRepository {

    val villagesData: MutableLiveData<List<Villages>> = MutableLiveData()
    val sellerData: MutableLiveData<List<Sellers>> = MutableLiveData()

    /**
     * Gets the list of sellers from the json file in assets folder
     * @param context Context
     */
    fun getSellersList(context: Context)  {

        lateinit var jsonString: String
        try {
            jsonString = context.assets.open("sampledata/sellers.json")
                .bufferedReader()
                .use { it.readText() }
        } catch (ioException: IOException) {

        }

        val listSellers = object : TypeToken<List<Sellers>>() {}.type
        sellerData.value =  Gson().fromJson(jsonString, listSellers)
    }


    /**
     * Gets the list of villages from the json file in assets folder
     * @param context Context
     */
    fun getVillagesList(context: Context) {

        lateinit var jsonString: String
        try {
            jsonString = context.assets.open("sampledata/villages.json")
                .bufferedReader()
                .use { it.readText() }
        } catch (ioException: IOException) {

        }

        val listVillages = object : TypeToken<List<Villages>>() {}.type
        villagesData.value =  Gson().fromJson(jsonString, listVillages)
    }


}