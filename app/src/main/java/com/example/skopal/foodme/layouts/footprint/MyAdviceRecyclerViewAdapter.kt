package com.example.skopal.foodme.layouts.footprint

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.skopal.foodme.R
import com.example.skopal.foodme.classes.Advice
import kotlinx.android.synthetic.main.fragment_advice.view.*

/**
 * [RecyclerView.Adapter] that can display an [Advice]
 */
class MyAdviceRecyclerViewAdapter(private val mValues: List<Advice>)
    : RecyclerView.Adapter<MyAdviceRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_advice, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mValues[position]
        holder.mType.setImageResource(R.drawable.ic_my_kitchen_24dp)
        holder.mTitle.text = item.product
        holder.mDescription.text = item.description

         var lifetime = "No Data"
         if (item.lifetime[0] !== null && item.lifetime[1] !== null) {
             lifetime = "${item.lifetime[0]} - ${item.lifetime[1]}"
         }
         holder.mLifetime.text = lifetime
    }

    override fun getItemCount(): Int = mValues.size

    inner class ViewHolder(private val mView: View) : RecyclerView.ViewHolder(mView) {
        val mType: ImageView = mView.type
        val mTitle: TextView = mView.product_title
        val mDescription: TextView = mView.description
        val mLifetime: TextView = mView.lifetime
    }
}
