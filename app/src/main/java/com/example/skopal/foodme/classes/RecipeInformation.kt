package com.example.skopal.foodme.classes

import com.google.gson.annotations.SerializedName

data class RecipeInformation(
        @SerializedName("id") val name: Number,
        @SerializedName("title") val title: String,
        @SerializedName("image") val image: String,
        @SerializedName("readyInMinutes") val readyInMinutes: Number,
        @SerializedName("vegetarian") val vegetarian: Boolean,
        @SerializedName("vegan") val vegan: Boolean,
        @SerializedName("glutenFree") val glutenFree: Boolean,
        @SerializedName("dairyFree") val dairyFree: Boolean,
        @SerializedName("veryHealthy") val veryHealthy: Boolean,
        @SerializedName("sustainable") val sustainable: Boolean,
        @SerializedName("analyzedInstructions") val analyzedInstructions: List<Instructions>,
        @SerializedName("extendedIngredients") val extendedIngredients: List<Ingredient>,
        @SerializedName("likes") val likes: Number
)

/**
 * Instructions
 */
data class Instructions(
        @SerializedName("name") val name: String,
        @SerializedName("steps") val steps: List<InstructionStep>
)

data class InstructionStep(
        @SerializedName("number") val number: Number,
        @SerializedName("step") val step: String
)

/**
 * Ingredients
 */
data class Ingredient(
        @SerializedName("name") val name: String,
        @SerializedName("measures") val measures: Measures
)

data class Measures(
        @SerializedName("us") val us: Measure,
        @SerializedName("metric") val metric: Measure
)

data class Measure(
        @SerializedName("amount") val amount: Float,
        @SerializedName("unitShort") val unitShort: String,
        @SerializedName("unitLong") val unitLong: String
)

