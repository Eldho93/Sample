package com.eldho.sampleproject

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eldho.sampleproject.model.DataModel
import kotlinx.coroutines.*

class MainViewModel: ViewModel() {

    private var repository: MainRepository= MainRepository()
    val userLiveData: LiveData<DataModel> =
        repository.apiResponseUserData


    fun getUserData() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val multipleIds = listOf("1", "3","10")
                val content : DataModel

                val runningTasks = multipleIds.map { id ->
                    async { // this will allow us to run multiple tasks in parallel
                        val apiResponse = repository.getUserData(id)
                        id to apiResponse // associate id and response for later
                    }
                }

                val responses = runningTasks.awaitAll()

                responses.forEach {
                    println(it.second.toString())


                }

            }
        }
    }

    fun getUserObservable(): MutableLiveData<DataModel> {
        return repository.apiResponseUserData
    }


}