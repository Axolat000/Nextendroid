package com.axolat.nextendroid.data.repository

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences("nextendo_session", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_TOKEN = "jwt_token"
        private const val KEY_USERNAME = "username"
        private const val KEY_FRIEND_CODE = "friend_code"
        private const val KEY_LANGUAGE = "app_language"
        private const val KEY_ACCENT_COLOR = "accent_color"
    }

    fun saveSession(token: String, username: String? = null, friendCode: String? = null) {
        prefs.edit().apply {
            putString(KEY_TOKEN, token)
            username?.let { putString(KEY_USERNAME, it) }
            friendCode?.let { putString(KEY_FRIEND_CODE, it) }
            apply()
        }
    }

    fun getToken(): String? = prefs.getString(KEY_TOKEN, null)

    fun getUsername(): String? = prefs.getString(KEY_USERNAME, null)

    fun getFriendCode(): String? = prefs.getString(KEY_FRIEND_CODE, null)

    fun saveLanguage(langCode: String) {
        prefs.edit().putString(KEY_LANGUAGE, langCode).apply()
    }

    fun getLanguage(): String = prefs.getString(KEY_LANGUAGE, "fr") ?: "fr"

    fun saveAccentColorHex(hex: String) {
        prefs.edit().putString(KEY_ACCENT_COLOR, hex).apply()
    }

    fun getAccentColorHex(): String = prefs.getString(KEY_ACCENT_COLOR, "#FF2D75") ?: "#FF2D75"

    fun clearSession() {
        prefs.edit().clear().apply()
    }

    fun isLoggedIn(): Boolean = !getToken().isNullOrEmpty()
}
