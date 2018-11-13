package com.example.skopal.foodme.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.widget.ImageView
import java.text.SimpleDateFormat
import java.util.*

fun dateToSimpleString(date: Date) : String {
    val format = SimpleDateFormat("dd/MM/yyy", Locale.ENGLISH)
    return format.format(date)
}

/**
 * Class found at https://stackoverflow.com/questions/2471935/how-to-load-an-imageview-by-url-in-android
 */
class DownloadImageTask(var bmImage: ImageView) : AsyncTask<String, Void, Bitmap>() {

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
