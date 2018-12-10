package com.example.skopal.foodme.classes

import com.google.gson.annotations.SerializedName
import java.util.*

data class GroceryItem(
        @SerializedName("name") val name: String,
        @SerializedName("inserted") val inserted: Date,
        @SerializedName("id") val id: String
)
