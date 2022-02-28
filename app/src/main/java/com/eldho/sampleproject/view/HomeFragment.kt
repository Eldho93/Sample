package com.eldho.sampleproject.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.eldho.sampleproject.R
import com.eldho.sampleproject.databinding.FragmentGenerateBinding
import com.eldho.sampleproject.databinding.FragmentHomeBinding
import com.eldho.sampleproject.utils.inflateAppBar
import com.eldho.sampleproject.utils.popBackStackExt
import com.eldho.sampleproject.viewmodel.GenerateFragmentViewModel


class HomeFragment : Fragment() {

    private lateinit var mBinding: FragmentHomeBinding


    companion object {
        fun newInstance() = HomeFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentHomeBinding.inflate(inflater, container, false)
        return mBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        mBinding.apply {

            btnGenerate.setOnClickListener {
                val action = HomeFragmentDirections.actionHomeFragmentToFragmentGenerate()
                it.findNavController().navigate(action)
            }

            btnListDogs.setOnClickListener{
                val action = HomeFragmentDirections.actionHomeFragmentToFragmentListDogs()
                it.findNavController().navigate(action)
            }
        }
    }


}