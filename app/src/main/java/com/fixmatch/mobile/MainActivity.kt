package com.fixmatch.mobile

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import com.fixmatch.mobile.data.settings.SettingsManager
import com.fixmatch.mobile.presentation.theme.FixMatchTheme
import java.util.Locale

class MainActivity : ComponentActivity() {
    private lateinit var settingsManager: SettingsManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        settingsManager = SettingsManager(applicationContext)

        setContent {
            val themeMode by settingsManager.themeModeFlow.collectAsState(initial = null)
            val language by settingsManager.languageFlow.collectAsState(initial = null)

            val isDark = themeMode ?: isSystemInDarkTheme()
            val currentLanguage = language ?: Locale.getDefault().language

            // Update Configuration for Localization
            val context = LocalContext.current
            val updatedContext = remember(currentLanguage) {
                val locale = Locale(currentLanguage)
                Locale.setDefault(locale)
                val config = Configuration(context.resources.configuration)
                config.setLocale(locale)
                context.createConfigurationContext(config)
            }

            CompositionLocalProvider(
                LocalContext provides updatedContext,
                LocalConfiguration provides updatedContext.resources.configuration
            ) {
                FixMatchTheme(darkTheme = isDark) {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        com.fixmatch.mobile.presentation.navigation.AppNavigation()
                    }
                }
            }
        }
    }
}
