package com.eldho.sampleproject.view

import android.content.ContentResolver
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.os.ParcelFileDescriptor
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.eldho.sampleproject.R
import com.eldho.sampleproject.databinding.AppbarDefaultBinding
import com.eldho.sampleproject.databinding.FragmentGenerateBinding
import com.eldho.sampleproject.utils.*
import com.eldho.sampleproject.utils.Constants.Companion.SUCCESS
import com.eldho.sampleproject.viewmodel.GenerateFragmentViewModel
import kotlinx.coroutines.*
import java.io.FileDescriptor
import java.io.IOException
import java.net.URL

class GenerateFragment : Fragment() {

    companion object {
        fun newInstance() = GenerateFragment()
    }

    private lateinit var viewModel: GenerateFragmentViewModel
    private lateinit var mBinding: FragmentGenerateBinding
    private lateinit var mBindingAppBar: AppbarDefaultBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentGenerateBinding.inflate(inflater, container, false)
        mBindingAppBar = mBinding.toolBarLayout
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[GenerateFragmentViewModel::class.java]
        initView()
        setObservers()
    }

    private fun setObservers() {
        with(viewModel) {
            getDogObservable().observe(viewLifecycleOwner) {
                if (it.status == SUCCESS) {

                    val url = URL(it.message.toString())
                    val result: Deferred<Bitmap?> = GlobalScope.async {
                        url.toBitmap()
                    }
                    GlobalScope.launch(Dispatchers.Main) {
                        MyCache.instance.saveBitmapToCache("0", result.await())
                    }

                    mBinding.imageView.setImageFormNetwork(it.message)
                }
            }

        }
    }

    private fun initView() {
        mBinding.apply {
            inflateAppBar(
                appBarTitle = getString(R.string.generate_dogs),
                mBinding = mBindingAppBar
            ) {
                popBackStackExt()
            }

            btnGenerate.setOnClickListener {
                viewModel.getDogData()
            }
        }
    }


}