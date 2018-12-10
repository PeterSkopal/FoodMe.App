package com.example.skopal.foodme.constants

class TokenHeader {
    companion object {
        @JvmStatic fun tokenHeader(token: String): Map<String, String> = mapOf("Authorization" to "Bearer $token")
    }
}
