package com.example.skopal.foodme.services

import android.content.Context
import com.example.skopal.foodme.classes.RecipeInformation
import com.example.skopal.foodme.classes.RecipeItem
import com.example.skopal.foodme.classes.RecipeList
import com.google.gson.Gson
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
        @JvmStatic
        val MINIMIZE_MISSING_INGREDIENTS = 0

        @JvmStatic
        fun spoonacularHeader(key: String, host: String) = mapOf(
                SpoonacularApiConstants.KEY to key,
                SpoonacularApiConstants.HOST to host
        )
    }
}

class SpoonacularApi(context: Context) {
    private val baseUrl = "${SpoonacularApiConstants.BASE_URL}"
    private var spoonacularKey: String = ""
    private var spoonacularHost: String = ""
    private val gson = Gson()

    init {
        val key = context.resources.getIdentifier(
                "spoonacular_mashape_key", "string", context.packageName
        )
        val host = context.resources.getIdentifier(
                "spoonacular_mashape_host", "string", context.packageName
        )
        if (key != 0 && host != 0) {
            this.spoonacularKey = context.getString(key)
            this.spoonacularHost = context.getString(host)
        }
    }

    /**
     * Retrieving a recipe search based on ingredients in payload.
     * Change to http://www.mocky.io/v2/5bd8050f310000f00b474b93 to mock response.
     */
    fun getRecipeSearch(ingredients: String,
                        preference: String?,
                        intolerance: String?,
                        cb: (List<RecipeItem>) -> Unit) {

        var url = "$baseUrl${SpoonacularApiConstants.RECIPES}/searchComplex?" +
                "number=30" +
                "&ranking=${SpoonacularApiConstants.MINIMIZE_MISSING_INGREDIENTS}" +
                "&includeIngredients=${URLEncoder.encode(ingredients, "UTF-8")}" +
                "&type=${URLEncoder.encode("main course", "UTF-8")}" +
                "&instructionsRequired=true"
        if (preference !== null) url += "&diet=${URLEncoder.encode(preference, "UTF-8")}"
        if (intolerance !== null) url += "&intolerances=${URLEncoder.encode(intolerance, "UTF-8")}"

        khttp.async.get(
                url, headers = SpoonacularApiConstants.spoonacularHeader(this.spoonacularKey, this.spoonacularHost)
        ) {
            if (statusCode != 200) {
                cb(listOf())
            } else {
                val obj = gson.fromJson(text, RecipeList::class.java)
                cb(obj.results)
            }
        }
    }

    /**
     * Retrieving recipe information based on id
     * Change to http://www.mocky.io/v2/5bd709d5350000f51cfd7ed9 to mock response.
     */
    fun getRecipe(id: Int, cb: (RecipeInformation?) -> Unit) {
        khttp.async.get(
                "$baseUrl${SpoonacularApiConstants.RECIPES}/$id/information",
                headers = SpoonacularApiConstants.spoonacularHeader(this.spoonacularKey, this.spoonacularHost)
        ) {
            if (statusCode != 200) {
                cb(null)
            } else {
                val obj = gson.fromJson(text, RecipeInformation::class.java)
                cb(obj)
            }
        }
    }
}
