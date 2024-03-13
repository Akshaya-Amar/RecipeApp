package com.example.firstkotlinrecipeproject.util

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map

class MyDataStore(private val context: Context) {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
    private val FIRST_LAUNCH_KEY = booleanPreferencesKey("firstLaunch")
    private val NAME_KEY = stringPreferencesKey("name")

    val isFirstTime = context.dataStore.data.map { preferences ->
        preferences[FIRST_LAUNCH_KEY] ?: true
    }

    val name = context.dataStore.data.map { preferences ->
        preferences[NAME_KEY] ?: "User"
    }

    suspend fun saveFirstTimeValues(isFirstTime: Boolean, name: String) {
        context.dataStore.edit { mutablePreferences ->
            mutablePreferences[FIRST_LAUNCH_KEY] = isFirstTime
            mutablePreferences[NAME_KEY] = name
        }
    }
}