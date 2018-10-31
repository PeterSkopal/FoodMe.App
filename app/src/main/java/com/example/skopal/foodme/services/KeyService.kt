package com.example.skopal.foodme.services

import android.content.Context
import com.securepreferences.SecurePreferences

class KeyService(context: Context) {
    private val secureStorageEditor = SecurePreferences(context)

    fun getKey(key: String): String? {
        return secureStorageEditor.getString(key, null)
    }

    fun addKey(key: String, value: String) {
        secureStorageEditor.edit().putString(key, value).apply()
    }

    fun removeKey(key: String) {
        secureStorageEditor.edit().remove(key).apply()
    }
}
