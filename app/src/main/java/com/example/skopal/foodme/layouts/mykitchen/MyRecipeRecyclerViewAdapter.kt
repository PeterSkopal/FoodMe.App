package com.example.skopal.foodme.layouts.mykitchen

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.skopal.foodme.R
import com.example.skopal.foodme.classes.RecipeItem
import com.example.skopal.foodme.layouts.mykitchen.RecipeFragment.OnListFragmentInteractionListener
import kotlinx.android.synthetic.main.fragment_recipe.view.*
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.widget.ImageView

/**
 * [RecyclerView.Adapter] that can display a [RecipeItem] and makes a call to the
 * specified [OnListFragmentInteractionListener].
 * TODO: Replace the implementation with code for your data type.
 */
class MyRecipeRecyclerViewAdapter(
        private val mValues: List<RecipeItem>,
        private val mListener: OnListFragmentInteractionListener?)
    : RecyclerView.Adapter<MyRecipeRecyclerViewAdapter.ViewHolder>() {

    private val mOnClickListener: View.OnClickListener

    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as RecipeItem
            // Notify the active callbacks interface (the activity, if the fragment is attached to
            // one) that an item has been selected.
            mListener?.onListFragmentInteraction(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_recipe, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mValues[position]
        holder.mContentView.text = item.title
        DownloadImageTask(holder.mImageView).execute(item.image)

        with(holder.mView) {
            tag = item
            setOnClickListener(mOnClickListener)
        }
    }

    override fun getItemCount(): Int = mValues.size

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val mContentView: TextView = mView.content
        val mImageView: ImageView = mView.card_view_image

        override fun toString(): String {
            return super.toString() + " '" + mContentView.text
        }
    }
}


/**
 * Class found at https://stackoverflow.com/questions/2471935/how-to-load-an-imageview-by-url-in-android
 */
private class DownloadImageTask(var bmImage: ImageView) : AsyncTask<String, Void, Bitmap>() {

    override fun doInBackground(vararg urls: String): Bitmap? {
        val urlDisplay = urls[0]
        var mIcon11: Bitmap? = null
        try {
            val `in` = java.net.URL(urlDisplay).openStream()
            mIcon11 = BitmapFactory.decodeStream(`in`)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return mIcon11
    }

    override fun onPostExecute(result: Bitmap) {
        bmImage.setImageBitmap(result)
    }
}
