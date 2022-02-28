package com.eldho.sampleproject.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.eldho.sampleproject.R
import com.eldho.sampleproject.databinding.AppbarDefaultBinding
import com.eldho.sampleproject.databinding.FragmentGenerateBinding
import com.eldho.sampleproject.databinding.FragmentHomeBinding
import com.eldho.sampleproject.databinding.FragmentListDogsBinding
import com.eldho.sampleproject.utils.MyCache
import com.eldho.sampleproject.utils.inflateAppBar
import com.eldho.sampleproject.utils.popBackStackExt
import com.eldho.sampleproject.viewmodel.GenerateFragmentViewModel
import com.eldho.sampleproject.viewmodel.ListDogsViewModel

class ListDogsFragment : Fragment() {

    companion object {
        fun newInstance() = ListDogsFragment()
    }

    private lateinit var viewModel: ListDogsViewModel
    private lateinit var mBinding: FragmentListDogsBinding
    private lateinit var mBindingAppBar: AppbarDefaultBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentListDogsBinding.inflate(inflater, container, false)
        mBindingAppBar = mBinding.toolBarLayout
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[ListDogsViewModel::class.java]
        initView()
        setObservers()
    }

    private fun initView() {
        mBinding.apply {
            inflateAppBar(
                appBarTitle = getString(R.string.my_recently_generated_dogs),
                mBinding = mBindingAppBar
            ) {
                popBackStackExt()
            }


            btnClearDogs.setOnClickListener {
                viewModel.clearAllCachedData()

                if(recyclerView.adapter is RecyclerViewAdapter){
                    (recyclerView.adapter as RecyclerViewAdapter).refreshList()
                }

            }

            recyclerView.adapter = RecyclerViewAdapter(viewModel.getAllCachedData())

        }
    }

    private fun setObservers() {



    }
}