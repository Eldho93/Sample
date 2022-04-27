package com.eldho.sampleproject.view.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.eldho.sampleproject.databinding.FragmentHomeBinding
import com.eldho.sampleproject.model.Sellers
import com.eldho.sampleproject.model.Villages
import com.eldho.sampleproject.utils.listenFor
import com.eldho.sampleproject.utils.toEditable
import com.eldho.sampleproject.view.adapter.CustomSpinnerAdapter
import com.eldho.sampleproject.viewmodel.HomeFragmentViewModel
import com.skydoves.powerspinner.OnSpinnerItemSelectedListener
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment(), OnSpinnerItemSelectedListener<Villages> {

    private lateinit var mBinding: FragmentHomeBinding
    private lateinit var viewModel: HomeFragmentViewModel
    private lateinit var villageList: List<Villages>
    private lateinit var sellerList: List<Sellers>

    private var villageSP: Float = 0.0F
    private var grossWeight: Float = 0.0F


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentHomeBinding.inflate(inflater, container, false)
        return mBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[HomeFragmentViewModel::class.java]

        viewModel.apply {
            getSellersData(requireContext())
            getVillagesData(requireContext())
        }

        setObservers()
        initView()
    }

    private fun setObservers() {
        with(viewModel) {
            getSellersObservable().observe(viewLifecycleOwner) {
                sellerList = it
                Log.d(tag, sellerList.toString())
            }

            getVillagesObservable().observe(viewLifecycleOwner) {
                villageList = it

                setSpinnerItems(it)

            }
            getGrossPriceObservable().observe(viewLifecycleOwner) {
                txtGrossPriceValue.text = "$it INR"
                val totalPrice = it.toString().toDouble()
                mBinding.btnSellProduce.isEnabled = totalPrice > 0
            }

            getLoyaltyIndexObservable().observe(viewLifecycleOwner) {
                txtLoyaltyIndexValue.text = it
            }
        }


    }

    private fun setSpinnerItems(villages: List<Villages>) {
        val adapter = CustomSpinnerAdapter(mBinding.spinnerVillage, this)
        mBinding.spinnerVillage.setSpinnerAdapter(adapter)
        mBinding.spinnerVillage.setItems(villages)
    }

    private fun initView() {
        mBinding.apply {

            etSellerName.listenFor {
                val valueTyped = it.toString()
                if (valueTyped.isNotEmpty()) {
                    if (etSellerName.tag == null) {
                        val seller = getSellerBySellerName(valueTyped)
                        if (seller is Sellers) {
                            viewModel.setLoyaltyFlag(true)
                            etLoyalty.tag = "tag"
                            etLoyalty.text = seller.loyaltyCardID.toEditable()
                            etLoyalty.tag = null
                            viewModel.calculateGrossPrice(villageSP, grossWeight)
                        } else {
                            etLoyalty.tag = "tag"
                            etLoyalty.text = "".toEditable()
                            etLoyalty.tag = null
                            viewModel.setLoyaltyFlag(false)
                            viewModel.calculateGrossPrice(villageSP, grossWeight)
                        }
                    }
                }
            }

            etLoyalty.listenFor {
                val valueTyped = it.toString()
                if (valueTyped.isNotEmpty()) {
                    if (etLoyalty.tag == null) {
                        val seller = getSellerNameByLoyaltyCardID(valueTyped)
                        if (seller is Sellers) {
                            viewModel.setLoyaltyFlag(true)
                            etSellerName.tag = "tag1"
                            etSellerName.text = seller.sellerName.toEditable()
                            etSellerName.tag = null
                            viewModel.calculateGrossPrice(villageSP, grossWeight)
                        } else {
                            etSellerName.tag = "tag1"
                            etSellerName.text = "".toEditable()
                            etSellerName.tag = null
                            viewModel.setLoyaltyFlag(false)
                            viewModel.calculateGrossPrice(villageSP, grossWeight)
                        }
                    }
                }
            }

            etGrossWt.listenFor {
                val valueTyped = it.toString()
                if (valueTyped.isNotEmpty()) {
                    grossWeight = it.toString().toFloat()
                    viewModel.calculateGrossPrice(villageSP, grossWeight)
                } else {
                    grossWeight = 0F
                    viewModel.calculateGrossPrice(villageSP, grossWeight)
                }
            }


            btnSellProduce.setOnClickListener {
                val action = HomeFragmentDirections.actionHomeFragmentToFragmentGenerate(
                    mBinding.etSellerName.text.toString(),
                    viewModel.grossPriceData.value.toString(),
                    mBinding.etGrossWt.text.toString()
                )
                it.findNavController().navigate(action)

            }

        }

    }

    /**
     * Gets the Seller object from the list of sellers by the
     * @param sellerName entered by user.
     */
    private fun getSellerBySellerName(sellerName: String): Sellers? {
        return sellerList.find { seller ->
            sellerName.equals(seller.sellerName, ignoreCase = true)
        }
    }

    /**
     * Gets the Seller object from the list of sellers by the
     * @param loyaltyCardID entered by user.
     */
    private fun getSellerNameByLoyaltyCardID(loyaltyCardID: String): Sellers? {
        return sellerList.find { seller ->
            loyaltyCardID.equals(seller.loyaltyCardID, ignoreCase = true)
        }
    }


    override fun onItemSelected(
        oldIndex: Int,
        oldItem: Villages?,
        newIndex: Int,
        newItem: Villages
    ) {
        villageSP = newItem.villageSellingPrice
        viewModel.calculateGrossPrice(villageSP, grossWeight)
    }

}