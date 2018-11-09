package com.example.skopal.foodme.services

import android.content.Context
import android.util.Log
import com.example.skopal.foodme.classes.Receipt
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.android.extension.responseJson
import com.github.kittinunf.fuel.core.*
import com.google.gson.Gson
import java.io.File

private class TaggunConstants {
    companion object {
        @JvmStatic
        val BASE_URL = "https://api.taggun.io/api/receipt/v1/verbose/file"

        fun taggunHeader(key: String) = mutableMapOf(
                "apikey" to key,
                "Accept" to "application/json",
                "Cache-Control" to "no-cache"
        )
    }
}

const val TAG = "ReceiptRecognitionAPI"

class ReceiptRecognitionApi(context: Context) {
    private val baseUrl = TaggunConstants.BASE_URL
    private val gson = Gson()
    private var taggunApiKey: String = ""

    init {
        val apiKey = context.resources.getIdentifier(
                "taggun_api_key", "string", context.packageName
        )
        if (apiKey != 0) {
            taggunApiKey = context.getString(apiKey)
        }
    }

    fun parseReceipt(file: File, cb: (Receipt?) -> Unit) {

        Fuel.upload(path = baseUrl, method = Method.POST)
                .header(TaggunConstants.taggunHeader(taggunApiKey))
                .dataParts { _, _ -> listOf(DataPart(file, "file", "image/jpeg")) }
                .responseJson { _, _, result ->
                    result.fold(
                            success = { data ->
                                Log.i(TAG, data.content)
                                cb(gson.fromJson(data.content, Receipt::class.java))
                            },
                            failure = { error ->
                                Log.e(TAG, "An error of type ${error.exception} happened: ${error.message}")
                                cb(null)
                            }
                    )
                }

    }
}
