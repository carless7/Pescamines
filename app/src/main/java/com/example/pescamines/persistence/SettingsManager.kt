package com.example.pescamines.persistence

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.pescamines.persistence.SettingManager.BOMB_KEY
import com.example.pescamines.persistence.SettingManager.GRID_KEY
import com.example.pescamines.persistence.SettingManager.PLAYER_KEY
import com.example.pescamines.persistence.SettingManager.TIMER_KEY
import kotlinx.coroutines.flow.first

object SettingManager {
    val PLAYER_KEY = stringPreferencesKey("username")
    val GRID_KEY = intPreferencesKey("gridSize")
    val BOMB_KEY = intPreferencesKey("bombPercentage")
    val TIMER_KEY = booleanPreferencesKey("timerEnabled")
}

class SettingsManager(private val context: Context) {
    val dataStore: DataStore<Preferences> = context.preferencesDataStore("settings")

    suspend fun saveUsername(name: String) {
        dataStore.edit { preferences ->
            preferences[PLAYER_KEY] = name
        }
    }

    suspend fun getUsername(): String {
        return dataStore.data.first()[PLAYER_KEY] ?: "UserDefaultName"
    }

    suspend fun saveGrid(size: Int) {
        dataStore.edit { preferences ->
            preferences[GRID_KEY] = size
        }
    }

    suspend fun getGrid(): Int {
        return dataStore.data.first()[GRID_KEY] ?: 8
    }

    suspend fun saveBomb(percentage: Int) {
        dataStore.edit { preferences ->
            preferences[BOMB_KEY] = percentage
        }
    }

    suspend fun getBomb(): Int {
        return dataStore.data.first()[BOMB_KEY] ?: 10
    }

    suspend fun saveTimer(enabled: Boolean) {
        dataStore.edit { prefernces ->
            prefernces[TIMER_KEY] = enabled
        }
    }

    suspend fun getTimer(): Boolean {
        return dataStore.data.first()[TIMER_KEY] ?: true
    }
}