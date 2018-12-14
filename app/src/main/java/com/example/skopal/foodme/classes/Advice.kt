package com.example.skopal.foodme.classes

import com.google.gson.annotations.SerializedName

data class Advice(
        @SerializedName("type") val type: String,
        @SerializedName("product") val product: String,
        @SerializedName("description") val description: String,
        @SerializedName("freezing_compatibility") val freezing_compatibility: String,
        @SerializedName("detrimental_issues") val detrimental_issues: String,
        @SerializedName("lifetime") val lifetime: List<Float?>
)

data class AdviceList(
        @SerializedName("advice") val advice: List<Advice>
)
