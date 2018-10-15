package com.example.skopal.foodme.layouts.mykitchen

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.skopal.foodme.R
import com.example.skopal.foodme.classes.GroceryItem
import com.example.skopal.foodme.layouts.mykitchen.GroceryFragment.OnListFragmentInteractionListener
import com.example.skopal.foodme.utils.dateToSimpleString
import kotlinx.android.synthetic.main.fragment_grocery.view.*

/**
 * [RecyclerView.Adapter] that can display a [GroceryItem] and makes a call to the
 * specified [OnListFragmentInteractionListener].
 */
class MyGroceryRecyclerViewAdapter(
        private val mValues: List<GroceryItem>,
        private val mListener: OnListFragmentInteractionListener?)
    : RecyclerView.Adapter<MyGroceryRecyclerViewAdapter.ViewHolder>() {

    private val mOnClickListener: View.OnClickListener

    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as GroceryItem
            // Notify the active callbacks interface (the activity, if the fragment is attached to
            // one) that an item has been selected.
            mListener?.onListFragmentInteraction(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_grocery, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mValues[position]
        holder.mContentView.text = item.name
        holder.mTimeView.text = dateToSimpleString(item.inserted)

        with(holder.mView) {
            tag = item
            setOnClickListener(mOnClickListener)
        }
    }

    override fun getItemCount(): Int = mValues.size

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val mContentView: TextView = mView.content
        val mTimeView: TextView = mView.time

        override fun toString(): String {
            return super.toString() + " '" + mContentView.text
        }
    }
}
