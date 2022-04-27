package com.eldho.sampleproject.view


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.eldho.sampleproject.databinding.FragmentGenerateBinding

class GenerateFragment : Fragment() {

    private lateinit var mBinding: FragmentGenerateBinding
    private lateinit var grossPrice: String
    private lateinit var grossWeight: String
    private lateinit var sellerName: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentGenerateBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        grossPrice = requireArguments().getString("grossPrice").toString()
        grossWeight = requireArguments().getString("grossWeight").toString()
        sellerName = requireArguments().getString("sellerName").toString()

        initView()

    }


    private fun initView() {
        mBinding.apply {

            txtMessage1.text = "Thank you $sellerName for selling your quality produce."
            txtMessage2.text = "Please ensure you received $grossPrice INR for $grossWeight Tonnes of your produce"

            btnSellProduce.setOnClickListener {
                val action = GenerateFragmentDirections.actionFragmentGenerateToHomeFragment()
                it.findNavController().navigate(action)
            }
        }
    }


}