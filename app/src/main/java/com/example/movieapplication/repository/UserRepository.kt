package com.example.movieapplication.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.example.movieapplication.App.Companion.dataStore
import com.example.movieapplication.UserPreferences
import com.example.movieapplication.model.User
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserRepository(private val context: Context) {

    private val dataStore: DataStore<Preferences> = context.dataStore
    private val gson = Gson()

    val userFlow: Flow<User?> = dataStore.data.map { preferences ->
            preferences[UserPreferences.USER_KEY]?.let { json ->
                gson.fromJson(json, User::class.java)
            }
        }

    suspend fun saveUser(user: User) {
        val json = gson.toJson(user)
        dataStore.edit { preferences ->
            preferences[UserPreferences.USER_KEY] = json
        }
    }

    suspend fun clearUser() {
        dataStore.edit { preferences ->
            preferences.remove(UserPreferences.USER_KEY)
        }
    }
}