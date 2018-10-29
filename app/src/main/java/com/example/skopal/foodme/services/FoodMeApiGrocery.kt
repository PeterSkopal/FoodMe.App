package com.example.skopal.foodme.services

import android.content.Context
import com.example.skopal.foodme.classes.GroceryItem
import com.example.skopal.foodme.constants.FoodMeApiConstants
import com.example.skopal.foodme.constants.SecureKey
import com.example.skopal.foodme.constants.TokenHeader
import com.google.gson.Gson
import com.google.gson.JsonArray

class FoodMeApiGrocery {
    private val baseUrl = "${FoodMeApiConstants.BASE_URL}${FoodMeApiConstants.GROCERY}"
    private val token: String
    private val gson = Gson()

    constructor(context: Context) {
        var keyService = KeyService(context)
        this.token = keyService.getKey(SecureKey.USER_TOKEN)!!
    }

    fun getGroceries(cb: (MutableList<GroceryItem>) -> Unit) {
        khttp.async.get(baseUrl, headers = TokenHeader.tokenHeader(this.token)) {
            if (statusCode !== 200) {
                cb(mutableListOf())
            } else {
                var arr = mutableListOf<GroceryItem>()
                for (item in gson.fromJson(text, JsonArray::class.java)) {
                    arr.add(gson.fromJson(item, GroceryItem::class.java))
                }
                cb(arr)
            }
        }
    }
}
