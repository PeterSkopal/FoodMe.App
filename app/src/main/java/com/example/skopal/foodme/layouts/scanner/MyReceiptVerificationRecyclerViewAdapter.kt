package com.example.skopal.foodme.layouts.scanner

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import com.example.skopal.foodme.R
import com.example.skopal.foodme.classes.LineAmount
import com.example.skopal.foodme.layouts.scanner.ReceiptVerificationFragment.OnListFragmentInteractionListener
import kotlinx.android.synthetic.main.fragment_receiptverification.view.*
import android.widget.Button
import kotlinx.android.synthetic.main.fragment_receiptverification_button.view.*

/**
 * [RecyclerView.Adapter] that can display a [LineAmount] and makes a call to the
 * specified [OnListFragmentInteractionListener].
 */
class MyReceiptVerificationRecyclerViewAdapter(
        private val mValues: List<LineAmount>,
        private val mListener: OnListFragmentInteractionListener?)
    : RecyclerView.Adapter<MyReceiptVerificationRecyclerViewAdapter.ViewHolder>() {

    private val mOnClickListener: View.OnClickListener

    val CONTENT_TYPE = 1
    val BUTTON_TYPE = 2

    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as LineAmount
            // Notify the active callbacks interface (the activity, if the fragment is attached to
            // one) that an item has been selected.
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var layout: Int = R.layout.fragment_receiptverification
        if (viewType == BUTTON_TYPE) {
            layout = R.layout.fragment_receiptverification_button
        }
        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (position == mValues.size) {
            with(holder.mButton!!) {
                setOnClickListener {
                    mListener?.onListFragmentInteraction(null, mValues)
                }
            }
        } else {
            val item = mValues[position]
            holder.mItemDescription!!.text = item.description
            holder.mItemData!!.text = item.data.toString()
            holder.mItemChecked!!.isSelected = item.include

            with(holder.mView) {
                tag = item

                val checkBox = this.findViewById<CheckBox>(R.id.receipt_verification_item_checkbox)
                checkBox.setOnCheckedChangeListener(null)
                checkBox.isChecked = checkBox.isSelected
                checkBox.setOnCheckedChangeListener { _, isChecked ->
                    val index = mValues.indexOf(item)
                    mValues[index].include = isChecked
                }
            }
            with(holder.mItemDescription) {
                setOnClickListener {
                    mListener?.onListFragmentInteraction(item, null)
                }
            }
        }
    }

    override fun getItemCount(): Int = mValues.size + 1

    override fun getItemViewType(position: Int): Int =
        if (position  == mValues.size) BUTTON_TYPE else CONTENT_TYPE

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val mItemDescription: TextView? = mView.receipt_verification_item_description
        val mItemData: TextView? = mView.receipt_verification_item_data
        val mItemChecked: CheckBox? = mView.receipt_verification_item_checkbox
        val mButton: Button? = mView.receipt_verification_button
    }
}
