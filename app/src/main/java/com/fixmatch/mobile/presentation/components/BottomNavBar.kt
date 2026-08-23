package com.fixmatch.mobile.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun UserBottomNavBar(
    currentRoute: String,
    onNavigate: (String) -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth().shadow(8.dp, RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
        color = MaterialTheme.colorScheme.surface,
        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 8.dp)
                .navigationBarsPadding(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            BottomNavItem(icon = Icons.Default.Home, label = "Home", isSelected = currentRoute == "home", onClick = { onNavigate("home") })
            BottomNavItem(icon = Icons.Default.List, label = "Requests", isSelected = currentRoute == "requests", onClick = { onNavigate("requests") })
            BottomNavItem(icon = Icons.Default.Email, label = "Messages", isSelected = currentRoute == "messages", hasBadge = true, onClick = { onNavigate("messages") })
            BottomNavItem(icon = Icons.Default.Person, label = "Settings", isSelected = currentRoute == "profile", onClick = { onNavigate("profile") })
        }
    }
}

@Composable
fun WorkerBottomNavBar(
    currentRoute: String,
    onNavigate: (String) -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth().shadow(8.dp, RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
        color = MaterialTheme.colorScheme.surface,
        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .navigationBarsPadding(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            BottomNavItem(icon = Icons.Default.Home, label = "Home", isSelected = currentRoute == "worker_home", onClick = { onNavigate("worker_home") })
            BottomNavItem(icon = Icons.Default.Build, label = "Jobs", isSelected = currentRoute == "jobs", hasBadge = true, onClick = { onNavigate("jobs") })
            BottomNavItem(icon = Icons.Default.Email, label = "Messages", isSelected = currentRoute == "messages", onClick = { onNavigate("messages") })
            BottomNavItem(icon = Icons.Default.Person, label = "Profile", isSelected = currentRoute == "worker_settings", onClick = { onNavigate("worker_settings") })
        }
    }
}

@Composable
fun BottomNavItem(
    icon: ImageVector,
    label: String,
    isSelected: Boolean,
    hasBadge: Boolean = false,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .clickable(onClick = onClick)
            .padding(horizontal = 8.dp, vertical = 4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (isSelected) {
            Box(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.primaryContainer, CircleShape)
                    .padding(horizontal = 16.dp, vertical = 4.dp)
            ) {
                Icon(icon, contentDescription = label, tint = MaterialTheme.colorScheme.onPrimaryContainer)
            }
        } else {
            Box(modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)) {
                Icon(icon, contentDescription = label, tint = MaterialTheme.colorScheme.onSurfaceVariant)
                if (hasBadge) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .size(8.dp)
                            .background(MaterialTheme.colorScheme.error, CircleShape)
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}
