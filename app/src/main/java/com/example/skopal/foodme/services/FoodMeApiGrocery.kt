package com.example.skopal.foodme.services

import android.content.Context
import com.example.skopal.foodme.constants.FoodMeApiConstants
import com.example.skopal.foodme.constants.TokenHeader
import com.securepreferences.SecurePreferences

class FoodMeApiGrocery {
    private val baseUrl = "${FoodMeApiConstants.BASE_URL}${FoodMeApiConstants.GROCERY}"
    private val token: String

    constructor(context: Context) {
        var preferences = SecurePreferences(context)
        this.token = preferences.getString( "usertoken", null )
    }

    fun getGroceries(cb: (String) -> Unit) {
        khttp.async.get(baseUrl, headers=TokenHeader.tokenHeader(this.token)) {
            if (statusCode === 401) cb("[]") else cb(text)
        }
    }

}
