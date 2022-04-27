package com.eldho.sampleproject.view.adapter

import android.util.TypedValue
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eldho.sampleproject.model.Villages
import com.eldho.sampleproject.constants.NO_SELECTED_INDEX
import com.skydoves.powerspinner.OnSpinnerItemSelectedListener
import com.skydoves.powerspinner.PowerSpinnerInterface
import com.skydoves.powerspinner.PowerSpinnerView
import com.skydoves.powerspinner.databinding.ItemDefaultPowerSpinnerLibraryBinding

/** DefaultSpinnerAdapter is a default adapter composed of string items. */
class CustomSpinnerAdapter(
  powerSpinnerView: PowerSpinnerView,listener: OnSpinnerItemSelectedListener<Villages>?
) : RecyclerView.Adapter<CustomSpinnerAdapter.CustomSpinnerViewHolder>(),
  PowerSpinnerInterface<Villages> {

  override var index: Int = powerSpinnerView.selectedIndex
  override val spinnerView: PowerSpinnerView = powerSpinnerView
  override var onSpinnerItemSelectedListener = listener

  private val spinnerItems: MutableList<Villages> = arrayListOf()

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomSpinnerViewHolder {
    val binding =
      ItemDefaultPowerSpinnerLibraryBinding.inflate(
        LayoutInflater.from(parent.context), parent,
        false
      )
    return CustomSpinnerViewHolder(binding)
  }

  override fun onBindViewHolder(holder: CustomSpinnerViewHolder, position: Int) {
    holder.bind(spinnerItems[position].villageName, spinnerView)
    holder.itemView.setOnClickListener {
      notifyItemSelected(position)
    }
  }

  override fun setItems(itemList: List<Villages>) {
    this.spinnerItems.clear()
    this.spinnerItems.addAll(itemList)
    this.index = NO_SELECTED_INDEX
    notifyDataSetChanged()
  }

  override fun notifyItemSelected(index: Int) {
    if (index == NO_SELECTED_INDEX) return
    val oldIndex = this.index
    this.index = index
    this.spinnerView.notifyItemSelected(index, spinnerItems[index].villageName)
    this.onSpinnerItemSelectedListener?.onItemSelected(
      oldIndex = oldIndex,
      oldItem = oldIndex.takeIf { it != NO_SELECTED_INDEX }?.let { spinnerItems[oldIndex] },
      newIndex = index,
      newItem = spinnerItems[index]
    )
  }

  override fun getItemCount() = spinnerItems.size

  class CustomSpinnerViewHolder(private val binding: ItemDefaultPowerSpinnerLibraryBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: CharSequence, spinnerView: PowerSpinnerView) {
      binding.itemDefaultText.apply {
        text = item
        typeface = spinnerView.typeface
        gravity = spinnerView.gravity
        setTextSize(TypedValue.COMPLEX_UNIT_PX, spinnerView.textSize)
        setTextColor(spinnerView.currentTextColor)
      }
      binding.root.setPadding(
        spinnerView.paddingLeft,
        spinnerView.paddingTop,
        spinnerView.paddingRight,
        spinnerView.paddingBottom
      )
    }
  }
}
