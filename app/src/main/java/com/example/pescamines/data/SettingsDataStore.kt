package com.example.pescamines.data

import android.content.Context
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore("settings")

object UserPreferences {
    val USERNAME = stringPreferencesKey("username")
    val GRID_SIZE = intPreferencesKey("grid_size")
    val TIMER_ENABLED = booleanPreferencesKey("timer_enabled")
    val BOMB_PERCENTAGE = intPreferencesKey("bomb_percentage")
}

open class UserPreferencesRepository(context: Context) {
    private val dataStore = context.dataStore

    open val userNameFlow = dataStore.data.map { preferences ->
        preferences[UserPreferences.USERNAME] ?: "Aitana Bonmatí"
    }

    open val gridSizeFlow = dataStore.data.map { preferences ->
        preferences[UserPreferences.GRID_SIZE] ?: 8
    }

    open val timerEnabledFlow = dataStore.data.map { preferences ->
        preferences[UserPreferences.TIMER_ENABLED] ?: true
    }

    open val bombPercentageFlow = dataStore.data.map { preferences ->
        preferences[UserPreferences.BOMB_PERCENTAGE] ?: 10
    }

    suspend fun updateUserName(userName: String) {
        dataStore.edit { preferences ->
            preferences[UserPreferences.USERNAME] = userName
        }
    }

    suspend fun updateGridSize(gridSize: Int) {
        dataStore.edit { preferences ->
            preferences[UserPreferences.GRID_SIZE] = gridSize
        }
    }

    suspend fun updateTimerEnabled(timerEnabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[UserPreferences.TIMER_ENABLED] = timerEnabled
        }
    }

    suspend fun updateBombPercentage(bombPercentage: Int) {
        dataStore.edit { preferences ->
            preferences[UserPreferences.BOMB_PERCENTAGE] = bombPercentage
        }
    }
}
