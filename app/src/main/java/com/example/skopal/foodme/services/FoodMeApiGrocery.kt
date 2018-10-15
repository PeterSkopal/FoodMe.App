package com.example.skopal.foodme.services

import com.example.skopal.foodme.constants.FoodMeApiConstants
import com.google.gson.JsonArray

class FoodMeApiGrocery {
    private val baseUrl = "${FoodMeApiConstants.BASE_URL}${FoodMeApiConstants.GROCERY}"

    fun getGroceries(cb: (String) -> Unit) {
        khttp.async.get("$baseUrl/john.doe@example.com") {
            cb(text)
        }
    }

}