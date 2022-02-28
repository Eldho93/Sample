package com.eldho.sampleproject.utils

import android.graphics.Bitmap
import androidx.collection.LruCache

class MyCache private constructor() {

    private object HOLDER {
        val INSTANCE = MyCache()
    }

    companion object {
        val instance: MyCache by lazy { HOLDER.INSTANCE }
    }
    private val lru: LruCache<Any, Any> = LruCache(1024)

    fun saveBitmapToCache(key: String, bitmap: Bitmap?) {

        try {

            if(instance.lru.size()>19){
                instance.lru.remove("0")
                instance.lru.put(instance.lru.size().toString(), bitmap!!)
            }else{
                instance.lru.put(instance.lru.size().toString(), bitmap!!)
            }

        } catch (e: Exception) {
        }

    }

    fun retrieveBitmapFromCache(key: String): Bitmap? {

        try {
            val bitmap = instance.lru.get(key) as Bitmap?
            return bitmap
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return null
    }

    fun clearAllBitmaps(){
        try{
            instance.lru.createCount()
            instance.lru.evictAll()
        }catch (e: Exception) {
        }
    }

    fun getCacheSize(): Int{

        return instance.lru.size()

    }
}