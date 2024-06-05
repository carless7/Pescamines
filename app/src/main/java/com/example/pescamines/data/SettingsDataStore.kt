package com.example.pescamines.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

private const val SETTING_PREFERENCES = "settings"

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = SETTING_PREFERENCES
)

class SettingsDataStore(preference_datastore: DataStore<Preferences>) {
    private val PLAYER = stringPreferencesKey("username")
    private val GRID_SIZE = intPreferencesKey("gridSize")
    private val BOMB_PERCENTAGE = intPreferencesKey("bombPercentage")
    private val TIMER_ENABLED = booleanPreferencesKey("timerEnable")

    val preferencePlayer: Flow<String> = preference_datastore.data
        .catch {
            if (it is IOException) {
                it.printStackTrace()
                emit(emptyPreferences())
            } else {
                throw it
            }
        }
        .map { preferences ->
            preferences[PLAYER] ?: "default_user"
        }

    suspend fun savePlayerToPreferences(username: String, context: Context) {
        context.dataStore.edit { preferences ->
            preferences[PLAYER] = username
        }
    }

    val preferenceGrid: Flow<Int> = preference_datastore.data
        .catch {
            if (it is IOException) {
                it.printStackTrace()
                emit(emptyPreferences())
            } else {
                throw it
            }
        }
        .map { preferences ->
            preferences[GRID_SIZE] ?: 8
        }

    suspend fun saveGridToPreferences(size: Int, context: Context) {
        context.dataStore.edit { preferences ->
            preferences[GRID_SIZE] = size
        }
    }

    val preferenceBomb: Flow<Int> = preference_datastore.data
        .catch {
            if (it is IOException) {
                it.printStackTrace()
                emit(emptyPreferences())
            } else {
                throw it
            }
        }
        .map { preferences ->
            preferences[BOMB_PERCENTAGE] ?: 10
        }

    suspend fun saveBombToPreferences(percent: Int, context: Context) {
        context.dataStore.edit { preferences ->
            preferences[BOMB_PERCENTAGE] = percent
        }
    }


    val preferenceTimer: Flow<Boolean> = preference_datastore.data
        .catch {
            if (it is IOException) {
                it.printStackTrace()
                emit(emptyPreferences())
            } else {
                throw it
            }
        }
        .map { preferences ->
            preferences[TIMER_ENABLED] ?: true
        }

    suspend fun saveTimerToPreferences(isTimerEnabled: Boolean, context: Context) {
        context.dataStore.edit { preferences ->
            preferences[TIMER_ENABLED] = isTimerEnabled
        }
    }
}