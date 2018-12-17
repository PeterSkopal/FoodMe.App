package com.example.skopal.foodme.layouts.footprint

import android.support.v7.widget.RecyclerView
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.skopal.foodme.R
import com.example.skopal.foodme.classes.Advice
import com.example.skopal.foodme.constants.AdviceType
import kotlinx.android.synthetic.main.fragment_advice.view.*

/**
 * [RecyclerView.Adapter] that can display an [Advice]
 */
class MyAdviceRecyclerViewAdapter(private val mValues: List<Advice>)
    : RecyclerView.Adapter<MyAdviceRecyclerViewAdapter.ViewHolder>() {

    private fun adviceIcon(item: String): Int {
        return when (item) {
            AdviceType.FREEZE_STORAGE -> R.drawable.ic_snowflake_24dp
            else -> R.drawable.ic_my_kitchen_24dp
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_advice, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mValues[position]

        holder.mType.setImageResource(adviceIcon(item.type))
        holder.mTitle.text = item.product
        holder.mDescription.text = item.description
        var lifetime = "No Data"

        val from = item.lifetime[0]
        val to = item.lifetime[1]
        if (from !== null && to !== null) {
            lifetime = "lifetime\n<b>$from</b> - <b>$to</b>\nmonths"
        }
        holder.mLifetime.text = Html.fromHtml(lifetime)
    }

    override fun getItemCount(): Int = mValues.size

    inner class ViewHolder(private val mView: View) : RecyclerView.ViewHolder(mView) {
        val mType: ImageView = mView.type
        val mTitle: TextView = mView.product_title
        val mDescription: TextView = mView.description
        val mLifetime: TextView = mView.lifetime
    }
}
