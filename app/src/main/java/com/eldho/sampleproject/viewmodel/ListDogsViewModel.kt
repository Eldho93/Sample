package com.eldho.sampleproject.viewmodel

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import com.eldho.sampleproject.utils.MyCache

class ListDogsViewModel : ViewModel() {


    fun getAllCachedData(): ArrayList<Bitmap>{

        var listOfBmp = ArrayList<Bitmap>()
        if(MyCache.instance.getCacheSize()>0) {
            for (i in 0 until MyCache.instance.getCacheSize()) {
                listOfBmp.add(MyCache.instance.retrieveBitmapFromCache(i.toString())!!)
                listOfBmp.reverse()
            }
        }
        return listOfBmp
    }

    fun clearAllCachedData(){
        MyCache.instance.clearAllBitmaps()
    }

}