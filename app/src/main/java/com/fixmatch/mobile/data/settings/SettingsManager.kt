package com.fixmatch.mobile.data.settings

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class SettingsManager(private val context: Context) {
    companion object {
        val THEME_MODE = booleanPreferencesKey("theme_mode") // true for dark, false for light, null for system
        val LANGUAGE = stringPreferencesKey("language") // "en", "vi", null for system
    }

    val themeModeFlow: Flow<Boolean?> = context.dataStore.data.map { preferences ->
        preferences[THEME_MODE]
    }

    val languageFlow: Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[LANGUAGE]
    }

    suspend fun setThemeMode(isDark: Boolean?) {
        context.dataStore.edit { preferences ->
            if (isDark == null) {
                preferences.remove(THEME_MODE)
            } else {
                preferences[THEME_MODE] = isDark
            }
        }
    }

    suspend fun setLanguage(language: String?) {
        context.dataStore.edit { preferences ->
            if (language == null) {
                preferences.remove(LANGUAGE)
            } else {
                preferences[LANGUAGE] = language
            }
        }
    }
}
