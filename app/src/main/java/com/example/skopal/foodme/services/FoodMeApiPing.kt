package com.example.skopal.foodme.services

import com.example.skopal.foodme.constants.FoodMeApiConstants

class FoodMeApiPing {
    private val baseUrl = "${FoodMeApiConstants.BASE_URL}${FoodMeApiConstants.PING}"

    fun getPing(cb: (String) -> Unit) {
        /*khttp.async.get(baseUrl) {
            cb(text)
        }*/
        cb("")
    }

}