package com.example.movieapplication

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore

class App : Application() {
    companion object {
        private const val USER_PREFERENCES_NAME = "user_preferences"
        val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
            name = USER_PREFERENCES_NAME
        )
    }

}