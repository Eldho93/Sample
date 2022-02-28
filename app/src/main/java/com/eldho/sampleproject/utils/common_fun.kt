package com.eldho.sampleproject.utils

import android.content.Context
import android.graphics.Bitmap
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.eldho.sampleproject.R
import com.eldho.sampleproject.databinding.AppbarDefaultBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException


/**
 * Function used to handle appbar inside entrire application
 * Call this function to handle appbar
 */
fun inflateAppBarCommon(
    appBarTitle: String = "",
    appBarTitleColor: Int = R.color.white,
    mBinding: AppbarDefaultBinding?,
) {
    mBinding?.apply {
        txtAppbarTitle.apply {
            text = appBarTitle
            setTextColor(ContextCompat.getColor(this.context, appBarTitleColor))
        }

    }
}




