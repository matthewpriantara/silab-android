package com.example.silab_app.utils

import android.content.Context
import android.content.SharedPreferences
import com.example.silab_app.models.User
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class UserManager(context: Context) {

    private val prefs: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = prefs.edit()
    private val gson = Gson()

    companion object {
        private const val PREF_NAME = "LabTediUsers"
        private const val KEY_USERS = "users"
    }

    // Get all users
    private fun getAllUsers(): MutableList<User> {
        val usersJson = prefs.getString(KEY_USERS, null)
        return if (usersJson != null) {
            val type = object : TypeToken<MutableList<User>>() {}.type
            gson.fromJson(usersJson, type)
        } else {
            mutableListOf()
        }
    }

    // Save user
    fun saveUser(user: User) {
        val users = getAllUsers()
        users.add(user)
        val usersJson = gson.toJson(users)
        editor.putString(KEY_USERS, usersJson)
        editor.apply()
    }

    // Get user by email
    fun getUserByEmail(email: String): User? {
        val users = getAllUsers()
        return users.find { it.email == email }
    }

    // Check if email exists
    fun isEmailExists(email: String): Boolean {
        return getUserByEmail(email) != null
    }

    // Get all registered emails (for debugging)
    fun getAllEmails(): List<String> {
        return getAllUsers().map { it.email }
    }
}