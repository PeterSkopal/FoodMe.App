package com.example.skopal.foodme.services

import android.content.Context
import com.example.skopal.foodme.classes.GroceryItem
import com.example.skopal.foodme.constants.FoodMeApiConstants
import com.example.skopal.foodme.constants.SecureKey
import com.example.skopal.foodme.constants.TokenHeader
import com.google.gson.Gson

class FoodMeApiGrocery(context: Context) {
    private val baseUrl = "${FoodMeApiConstants.BASE_URL}${FoodMeApiConstants.GROCERY}"
    private val token: String
    private val gson = Gson()

    init {
        val keyService = KeyService(context)
        this.token = keyService.getKey(SecureKey.USER_TOKEN)!!
    }

    fun getGroceries(cb: (MutableList<GroceryItem>) -> Unit) {
        khttp.async.get(baseUrl, headers = TokenHeader.tokenHeader(this.token)) {
            if (statusCode != 200) {
                cb(mutableListOf())
            } else {
                val arr = gson.fromJson(text, Array<GroceryItem>::class.java).toMutableList()
                cb(arr)
            }
        }
    }

    fun addGroceries(arr: List<Map<String, String>>, cb: (Boolean) -> Unit) {
        khttp.async.post(baseUrl, json = arr, headers = TokenHeader.tokenHeader(this.token)) {
            if (statusCode != 200) cb(false) else cb(true)
        }
    }
}
