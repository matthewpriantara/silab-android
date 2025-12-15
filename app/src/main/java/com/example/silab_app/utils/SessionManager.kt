package com.example.silab_app.utils

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {

    private val prefs: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = prefs.edit()

    companion object {
        private const val PREF_NAME = "LabTediSession"
        private const val KEY_IS_LOGGED_IN = "isLoggedIn"
        private const val KEY_NAMA = "nama"
        private const val KEY_NIM = "nim"
        private const val KEY_EMAIL = "email"
        private const val KEY_PRODI = "prodi"

        private const val KEY_TOKEN = "token"
    }

    fun saveLoginSession(nama: String, nim: String, email: String, prodi: String, token: String) {
        editor.putBoolean(KEY_IS_LOGGED_IN, true)
        editor.putString(KEY_NAMA, nama)
        editor.putString(KEY_NIM, nim)
        editor.putString(KEY_EMAIL, email)
        editor.putString(KEY_PRODI, prodi)
        editor.putString(KEY_TOKEN, token)
        editor.apply()
    }

    fun isLoggedIn(): Boolean {
        return prefs.getBoolean(KEY_IS_LOGGED_IN, false)
    }

    fun getNama(): String {
        return prefs.getString(KEY_NAMA, "") ?: ""
    }

    fun getNim(): String {
        return prefs.getString(KEY_NIM, "") ?: ""
    }

    fun getEmail(): String {
        return prefs.getString(KEY_EMAIL, "") ?: ""
    }

    fun getProdi(): String {
        return prefs.getString(KEY_PRODI, "") ?: ""
    }

    fun getToken(): String {
        return prefs.getString(KEY_TOKEN, "") ?: ""
    }
    fun logout() {
        editor.clear()
        editor.apply()
    }
}