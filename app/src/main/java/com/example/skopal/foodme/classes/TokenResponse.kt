package com.example.skopal.foodme.classes

import com.google.gson.annotations.SerializedName

data class TokenResponse(
        @SerializedName("email") val email: String,
        @SerializedName("token") val token: String
)
