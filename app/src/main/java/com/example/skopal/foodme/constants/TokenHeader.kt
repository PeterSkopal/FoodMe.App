package com.example.skopal.foodme.constants

class TokenHeader {
    companion object {
        @JvmStatic val TEMP_TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6InRlc3QudXNlckBleGFtcGxlLmNvbSIsImlhdCI6MTUzOTY3MDQyN30.VD21A7by1ZMJq1sns3Jxbo501owfCNEMXUjAIcYnJm8"
        @JvmStatic fun tokenHeader(token: String): Map<String, String> = mapOf("Authorization" to "Bearer $token")
    }
}
