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
        khttp.async.get(
                "http://www.mocky.io/v2/5c4021f33500004f2fec3b3e"
                //baseUrl, headers = TokenHeader.tokenHeader(this.token)
            ) {
            if (statusCode != 200) {
                cb(mutableListOf())
            } else {
                val arr = gson.fromJson(text, Array<GroceryItem>::class.java).toMutableList()
                cb(arr)
            }
        }
    }

    fun addGroceries(arr: List<Map<String, String>>, cb: (Boolean) -> Unit) {
        khttp.async.post(
                "http://www.mocky.io/v2/5c40263c350000512fec3b54"
                //baseUrl, json = arr, headers = TokenHeader.tokenHeader(this.token)
        ) {
            if (statusCode != 200) cb(false) else cb(true)
        }
    }

    fun deleteGrocery(id: String, cb: ((Boolean) -> Unit)?) {
        khttp.async.delete(
                "http://www.mocky.io/v2/5c402663350000b12dec3b55"
                //"$baseUrl/$id", headers = TokenHeader.tokenHeader(this.token)
        ) {
            if (cb !== null) {
                if (statusCode != 200) cb(false) else cb(true)
            }
        }
    }
}
