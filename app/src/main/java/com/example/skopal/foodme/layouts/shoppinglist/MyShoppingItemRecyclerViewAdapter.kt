package com.example.skopal.foodme.layouts.shoppinglist

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import com.example.skopal.foodme.R
import com.example.skopal.foodme.classes.ShoppingItem
import com.example.skopal.foodme.layouts.shoppinglist.ShoppingItemFragment.OnListFragmentInteractionListener
import kotlinx.android.synthetic.main.fragment_shoppingitem.view.*

/**
 * [RecyclerView.Adapter] that can display a [ShoppingItem] and makes a call to the
 * specified [OnListFragmentInteractionListener].
 */
class MyShoppingItemRecyclerViewAdapter(
        private val mValues: MutableList<ShoppingItem>,
        private val mListener: OnListFragmentInteractionListener?)
    : RecyclerView.Adapter<MyShoppingItemRecyclerViewAdapter.ViewHolder>() {

    private val mOnClickListener: View.OnClickListener

    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as ShoppingItem
            // Notify the active callbacks interface (the activity, if the fragment is attached to
            // one) that an item has been selected.
            mListener?.onListFragmentInteraction(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_shoppingitem, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mValues[position]
        holder.mContentView.text = item.name

        with(holder.mView) {
            tag = item
            val checkBox = this.findViewById<CheckBox>(R.id.shopping_list_item_checkbox)
            checkBox.setOnCheckedChangeListener(null)
            checkBox.isChecked = checkBox.isSelected
            checkBox.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    val position = mValues.indexOf(item)
                    mValues.removeAt(position)
                    notifyItemRemoved(position)
                }
            }

            setOnClickListener(mOnClickListener)
        }
    }

    override fun getItemCount(): Int = mValues.size

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val mContentView: TextView = mView.shopping_list_item_content

        override fun toString(): String {
            return super.toString() + " '${mContentView.text}"
        }
    }
}
