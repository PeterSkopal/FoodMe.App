package com.example.skopal.foodme.services

import com.example.skopal.foodme.constants.FoodMeApiConstants
import org.json.JSONObject

class FoodMeApiUser {
    private val baseUrl = "${FoodMeApiConstants.BASE_URL}${FoodMeApiConstants.USER}"

    fun loginUser(email: String, pwd: String, cb: (String) -> Unit) {
        val payload = mapOf("email" to email, "password" to pwd)
        khttp.async.get("$baseUrl/login", data = payload) {
            cb(text)
        }
    }

}
