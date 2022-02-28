package com.eldho.sampleproject.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.eldho.sampleproject.R
import com.eldho.sampleproject.databinding.AppbarDefaultBinding
import java.io.IOException
import java.net.URL


fun Fragment.inflateAppBar(
    appBarTitle: String = "",
    appBarTitleColor: Int = R.color.black,
    mBinding: AppbarDefaultBinding?,
    onBackButtonPress: () -> Unit,
) {
    inflateAppBarCommon(
        appBarTitle,
        appBarTitleColor,
        mBinding,
    )
    mBinding?.apply {
        btnBackBtn.setOnClickListener {
            onBackButtonPress.invoke()
        }

    }
}

fun Fragment.popBackStackExt() {
    findNavController().popBackStack()
}


fun ImageView.setImageFormNetwork(
    link: String?,
    fallBack: Int = R.drawable.placeholder_rectangle,
    placeholder: Int = R.drawable.placeholder_rectangle
) {

    val options: RequestOptions = RequestOptions()
        .placeholder(R.drawable.progress_animation)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .priority(Priority.HIGH)
        .dontAnimate()
        .dontTransform()
    Glide.with(this.context)
        .load(link)
        .fallback(fallBack)
        .apply(options)
        .into(this)

}


fun URL.toBitmap(): Bitmap?{
    return try {
        BitmapFactory.decodeStream(openStream())
    }catch (e: IOException){
        null
    }
}