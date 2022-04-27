package com.eldho.sampleproject.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eldho.sampleproject.model.Sellers
import com.eldho.sampleproject.model.Villages
import com.eldho.sampleproject.repository.HomeRepository
import com.eldho.sampleproject.constants.Constants.Companion.LOYALTY_INDEX_FOR_REGISTERED_USER
import com.eldho.sampleproject.constants.Constants.Companion.LOYALTY_INDEX_FOR_UNREGISTERED_USER
import kotlinx.coroutines.launch
import java.text.DecimalFormat

class HomeFragmentViewModel : ViewModel() {
    private var repository: HomeRepository = HomeRepository()

    val grossPriceData: MutableLiveData<String> = MutableLiveData()
    private val loyaltyFlag: MutableLiveData<Boolean> = MutableLiveData()
    private val loyaltyIndex: MutableLiveData<String> = MutableLiveData()


    init{
        grossPriceData.value = "0"
    }

    fun getSellersData(context: Context) {
        viewModelScope.launch {
            repository.getSellersList(context)

        }
    }

    fun getSellersObservable(): MutableLiveData<List<Sellers>> {
        return repository.sellerData
    }


    fun getVillagesData(context: Context) {
        viewModelScope.launch {
            repository.getVillagesList(context)

        }
    }

    fun getVillagesObservable(): MutableLiveData<List<Villages>> {
        return repository.villagesData
    }

    private fun setGrossPriceLiveData(grossPrice: String){
        grossPriceData.value = grossPrice
    }

    fun getGrossPriceObservable():MutableLiveData<String>{
        return grossPriceData
    }

    private fun setLoyaltyIndex(loyalty: Double){
        loyaltyIndex.value = loyalty.toString()
    }

    fun getLoyaltyIndexObservable():MutableLiveData<String>{
        return loyaltyIndex
    }

    fun setLoyaltyFlag(flag: Boolean){
        loyaltyFlag.value = flag
    }

    /**
     * Calculates gross price of the users products.
     * Calculated by the formula selling price of a village multiplied by gross weight of the produce and Loyalty Index.
     * Loyalty Index for a registered user is 1.12 and for unregistered user is 0.9811 and is defined in Constants class,
     * @param villageSellingPrice Selling price of the selected village as defined in the Villages.json.
     * @param grossWt Gross Weight entered by user.
     */
    fun calculateGrossPrice(villageSellingPrice: Float, grossWt:Float){
        val df = DecimalFormat("#.##")
        if(loyaltyFlag.value == true){

            val grossPrice = df.format(LOYALTY_INDEX_FOR_REGISTERED_USER*villageSellingPrice*grossWt)
            setGrossPriceLiveData(grossPrice)
            setLoyaltyIndex(LOYALTY_INDEX_FOR_REGISTERED_USER)

        }else{
            val grossPrice = df.format(LOYALTY_INDEX_FOR_UNREGISTERED_USER*villageSellingPrice*grossWt)
            setGrossPriceLiveData(grossPrice)
            setLoyaltyIndex(LOYALTY_INDEX_FOR_UNREGISTERED_USER)

        }

    }
}