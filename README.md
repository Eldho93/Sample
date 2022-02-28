# Saving Image in LRU Cache and display 
Application to generate sample images of dogs and save it in the LRUCache for viewing later, and clear all images using Kotlin.

Architecture: MVVM

There are 3 fragments HomeFragment, ListDogsFragment, and GenerateFragment, navigation is implemented using **Navigation Component**.

**Logic**

GenerateFragment contains an ImageView and a Button . Generate button generates image using https://dog.ceo/api/breeds/image/random api,displays it in ImageView, converts it to bitmap using BitmapFactory.decodeStream() method inside a coroutine and stores it in the LRUCache. The class MyCache contains 4 methods saveBitmapToCache(), retrieveBitmapFromCache(), clearAllBitmaps(), getCacheSize().
  
**saveBitmapToCache()** - accepts a bitmap as param, checks for the size of LRUCache using size() and if the size is less than 20 it just adds the bitmap to cache,
if size greater than 20 it pops out the first item in the cache and appends new image to the end of cache. The bitmaps are stored as Key Value Pairs, the Key will be the size of the cache at the time of saving image. 

**retrieveBitmapFromCache()** - accepts a key as param and returns the Bitmap associated with the key in cache using get()

**clearAllBitmaps()** - Clears all bitmap in cache using evictAll()

**getCacheSize()** - returns the number of items in cache

The **ListDogsViewModel** contains a function **getAllCachedData()** which iterates through the size of cache memory and every iteration 'i' value if used as key to get items from cache memory. This item is added to a bitmap arraylist and returned.

The **clearCachedData()** in ListDogsViewModel clears the cache.

**ListDogsFragment** contatins a RecyclerView which displays the cached data by calling the viewmodel method **getAllCachedData()** and a button to clear cache. This button after clearing cache,  refreshes the recyclerview. 







