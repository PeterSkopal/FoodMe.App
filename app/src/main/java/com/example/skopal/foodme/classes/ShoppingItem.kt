package com.example.skopal.foodme.classes

import com.google.gson.annotations.SerializedName
import java.util.*

data class ShoppingItem(
        @SerializedName("id") val id: String,
        @SerializedName("name") val name: String,
        @SerializedName("purchased") val purchased: Date
)
