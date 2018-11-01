package com.example.skopal.foodme.layouts.mykitchen

import android.graphics.Color
import android.os.Handler
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
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
        private val mValues: MutableList<GroceryItem>,
        private val mListener: OnListFragmentInteractionListener?)
    : RecyclerView.Adapter<MyGroceryRecyclerViewAdapter.ViewHolder>() {

    companion object {
        @JvmStatic
        val PENDING_REMOVAL_TIMEOUT: Long = 2500 // ms
    }

    private var itemsPendingRemoval: MutableList<GroceryItem> = mutableListOf()
    private var undoOn: Boolean = true

    // handling pending removes
    private val handler = Handler()
    private var pendingRunnables: HashMap<GroceryItem, Runnable> = HashMap()

    private val mOnClickListener: View.OnClickListener

    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as GroceryItem
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

        with(holder.mView) {
            tag = item
            setOnClickListener(mOnClickListener)
        }

        if (itemsPendingRemoval.contains(item)) { // draw undo state
            holder.itemView.setBackgroundColor(Color.RED)
            holder.mNameView.visibility = View.GONE
            holder.mTimeView.visibility = View.GONE
            holder.mUndoButton.visibility = View.VISIBLE

            holder.mUndoButton.setOnClickListener {
                val pendingRemovalRunnable = pendingRunnables[item]
                pendingRunnables.remove(item)
                if (pendingRemovalRunnable != null) handler.removeCallbacks(pendingRemovalRunnable)
                itemsPendingRemoval.remove(item)
                notifyItemChanged(mValues.indexOf(item))
            }

        } else { // draw regular state
            holder.itemView.setBackgroundColor(Color.WHITE)
            holder.mNameView.visibility = View.VISIBLE
            holder.mTimeView.visibility = View.VISIBLE
            holder.mUndoButton.visibility = View.GONE

            holder.mNameView.text = item.name
            holder.mTimeView.text = dateToSimpleString(item.inserted)
            holder.mUndoButton.setOnClickListener(null)
        }
    }

    override fun getItemCount(): Int = mValues.size
    fun isUndoOn(): Boolean = undoOn

    fun pendingRemoval(position: Int) {
        val item = mValues[position]
        if (!itemsPendingRemoval.contains(item)) {
            itemsPendingRemoval.add(item)
            // this will redraw row in "undo" state
            notifyItemChanged(position)

            // create, store and post a runnable to remove the item
            val pendingRemovalRunnable = Runnable { remove(mValues.indexOf(item)) }
            handler.postDelayed(pendingRemovalRunnable, PENDING_REMOVAL_TIMEOUT)
            pendingRunnables[item] = pendingRemovalRunnable
        }
    }

    fun remove(position: Int) {
        val item = mValues[position]
        if (itemsPendingRemoval.contains(item)) {
            itemsPendingRemoval.remove(item)
        }
        if (mValues.contains(item)) {
            mValues.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    fun isPendingRemoval(position: Int): Boolean {
        val item = mValues[position]
        return itemsPendingRemoval.contains(item)
    }

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val mTimeView: TextView = mView.time
        var mNameView: TextView = mView.name_view
        var mUndoButton: Button = mView.undo_button

        override fun toString(): String {
            return super.toString() + " '${mNameView.text} ${mTimeView.text}"
        }
    }
}
