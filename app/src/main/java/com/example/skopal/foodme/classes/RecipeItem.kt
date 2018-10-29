package com.example.skopal.foodme.classes

import com.google.gson.annotations.SerializedName

data class RecipeItem(
        @SerializedName("id") val id: Int,
        @SerializedName("title") val title: String,
        @SerializedName("image") val image: String,
        @SerializedName("imageType") val imageType: String,
        @SerializedName("usedIngredientCount") val usedIngredientCount: Number,
        @SerializedName("missedIngredientCount") val missedIngredientCount: Number,
        @SerializedName("likes") val likes: Number
)
