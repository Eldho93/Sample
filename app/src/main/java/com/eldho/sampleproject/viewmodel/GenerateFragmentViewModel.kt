package com.eldho.sampleproject.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eldho.sampleproject.model.DogDataModel
import com.eldho.sampleproject.repository.GenerateRepository
import kotlinx.coroutines.launch

class GenerateFragmentViewModel : ViewModel() {
    private var repository: GenerateRepository = GenerateRepository()
    val dogLiveData: LiveData<DogDataModel> =
        repository.apiResponseDogData


    fun getDogData() {
        viewModelScope.launch {
            repository.getDogData()

        }
    }

    fun getDogObservable(): MutableLiveData<DogDataModel> {
        return repository.apiResponseDogData
    }
}