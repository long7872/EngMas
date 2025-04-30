package com.example.engmas.data.store

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.first

object SessionPreferences {
    private val Context.dataStore by preferencesDataStore(name = "session_prefs")

    private val LAST_TRACKED_DAY = stringPreferencesKey("last_tracked_day")
    private val IS_COMPLETED = booleanPreferencesKey("is_completed")
    private val ELAPSED_MILLIS = longPreferencesKey("elapsed_millis")

    suspend fun saveTrackingCompleted(context: Context, day: String) {
        context.dataStore.edit { prefs ->
            prefs[LAST_TRACKED_DAY] = day
            prefs[IS_COMPLETED] = true
        }
    }

    suspend fun saveElapsedMillis(context: Context, elapsed: Long) {
        context.dataStore.edit { prefs ->
            prefs[ELAPSED_MILLIS] = elapsed
        }
    }

    suspend fun getElapsedMillis(context: Context): Long {
        return context.dataStore.data.first()[ELAPSED_MILLIS] ?: 0L
    }

    suspend fun isTodayCompleted(context: Context): Boolean {
        return context.dataStore.data.first()[IS_COMPLETED] ?: false
    }

    suspend fun saveLastTrackedDay(context: Context, day: String) {
        context.dataStore.edit { prefs ->
            prefs[LAST_TRACKED_DAY] = day
        }
    }

    suspend fun getLastTrackedDay(context: Context): String? {
        return context.dataStore.data.first()[LAST_TRACKED_DAY]
    }

    suspend fun clear(context: Context) {
        context.dataStore.edit { it.clear() }
    }
}