package com.fixmatch.mobile.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.fixmatch.mobile.data.settings.SettingsManager
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GlobalSettingsBottomSheet(
    onDismissRequest: () -> Unit
) {
    val context = LocalContext.current
    val settingsManager = remember { SettingsManager(context) }
    val coroutineScope = rememberCoroutineScope()
    
    val themeMode by settingsManager.themeModeFlow.collectAsState(initial = null)
    val language by settingsManager.languageFlow.collectAsState(initial = null)

    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        containerColor = MaterialTheme.colorScheme.surface,
        dragHandle = { BottomSheetDefaults.DragHandle() }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .padding(bottom = 32.dp)
        ) {
            Text(
                text = "App Settings",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            
            Text(
                text = "Theme",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                FilterChip(
                    selected = themeMode == null,
                    onClick = { coroutineScope.launch { settingsManager.setThemeMode(null) } },
                    label = { Text("System") }
                )
                FilterChip(
                    selected = themeMode == false,
                    onClick = { coroutineScope.launch { settingsManager.setThemeMode(false) } },
                    label = { Text("Light") }
                )
                FilterChip(
                    selected = themeMode == true,
                    onClick = { coroutineScope.launch { settingsManager.setThemeMode(true) } },
                    label = { Text("Dark") }
                )
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            Text(
                text = "Language",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                FilterChip(
                    selected = language == "en" || language == null, // default to en
                    onClick = { coroutineScope.launch { settingsManager.setLanguage("en") } },
                    label = { Text("English") },
                    leadingIcon = { 
                        if (language == "en" || language == null) {
                            Icon(Icons.Default.Check, contentDescription = null)
                        } 
                    }
                )
                FilterChip(
                    selected = language == "vi",
                    onClick = { coroutineScope.launch { settingsManager.setLanguage("vi") } },
                    label = { Text("Tiếng Việt") },
                    leadingIcon = { 
                        if (language == "vi") {
                            Icon(Icons.Default.Check, contentDescription = null)
                        } 
                    }
                )
            }
            
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}
