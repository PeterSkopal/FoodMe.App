package com.example.skopal.foodme.layouts.scanner

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.skopal.foodme.R
import com.example.skopal.foodme.classes.LineAmount


import com.example.skopal.foodme.layouts.scanner.ReceiptVerificationFragment.OnListFragmentInteractionListener

import kotlinx.android.synthetic.main.fragment_receiptverification.view.*

/**
 * [RecyclerView.Adapter] that can display a [LineAmount] and makes a call to the
 * specified [OnListFragmentInteractionListener].
 */
class MyReceiptVerificationRecyclerViewAdapter(
        private val mValues: List<LineAmount>,
        private val mListener: OnListFragmentInteractionListener?)
    : RecyclerView.Adapter<MyReceiptVerificationRecyclerViewAdapter.ViewHolder>() {

    private val mOnClickListener: View.OnClickListener

    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as LineAmount
            // Notify the active callbacks interface (the activity, if the fragment is attached to
            // one) that an item has been selected.
            mListener?.onListFragmentInteraction(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_receiptverification, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mValues[position]
        holder.mItemDescription.text = item.description
        holder.mItemData.text = item.data.toString()

        with(holder.mView) {
            tag = item
            setOnClickListener(mOnClickListener)
        }
    }

    override fun getItemCount(): Int = mValues.size

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val mItemDescription: TextView = mView.receipt_verification_item_description
        val mItemData: TextView = mView.receipt_verification_item_data

        override fun toString(): String {
            return super.toString() + " '" + mItemDescription.text + "'"
        }
    }
}
