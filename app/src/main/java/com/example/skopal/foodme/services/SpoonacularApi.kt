package com.example.skopal.foodme.services

import android.content.Context
import com.example.skopal.foodme.R
import java.net.URLEncoder

private class SpoonacularApiConstants {
    companion object {
        @JvmStatic
        val BASE_URL = "https://spoonacular-recipe-food-nutrition-v1.p.mashape.com"
        @JvmStatic
        val RECIPES = "/recipes"
        @JvmStatic
        val KEY = "X-Mashape-Key"
        @JvmStatic
        val HOST = "X-Mashape-Host"
    }
}

class SpoonacularApi(context: Context) {
    private val baseUrl = "${SpoonacularApiConstants.BASE_URL}"
    private var spoonacularKey: String = ""
    private var spoonacularHost: String = ""

    init {
        val key = context.resources.getIdentifier(
                "spoonacular_mashape_key", "string", context.packageName
        )
        val host = context.resources.getIdentifier(
                "spoonacular_mashape_host", "string", context.packageName
        )
        if (key !== 0 && host !== 0) {
            this.spoonacularKey = context.getString(key)
            this.spoonacularHost = context.getString(host)
        }
        println("hejhej\t" + this.spoonacularKey + "\t" + this.spoonacularHost)
    }

    fun getRecipeSearch(payload: String, cb: (String) -> Unit) {
        khttp.async.get(
                "$baseUrl${SpoonacularApiConstants.RECIPES}/findByIngredients?number=5&ranking=1&ingredients=${URLEncoder.encode(payload, "UTF-8")}",
                headers = mapOf(
                        SpoonacularApiConstants.KEY to this.spoonacularKey,
                        SpoonacularApiConstants.HOST to this.spoonacularHost
                )
        ) { if (statusCode === 200) cb(text) else cb("[]") }
    }
}
