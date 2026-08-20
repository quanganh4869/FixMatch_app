package com.findtho.mobile.presentation.customer

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.findtho.mobile.presentation.home.HomeScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomerMainScreen() {
    var selectedItem by remember { mutableIntStateOf(0) }
    val items = listOf(
        BottomNavItem("Home", Icons.Filled.Home),
        BottomNavItem("Explore", Icons.Filled.Search),
        BottomNavItem("Orders", Icons.Filled.List),
        BottomNavItem("Profile", Icons.Filled.Person)
    )

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.primary
            ) {
                items.forEachIndexed { index, item ->
                    NavigationBarItem(
                        icon = { Icon(item.icon, contentDescription = item.label) },
                        label = { Text(item.label) },
                        selected = selectedItem == index,
                        onClick = { selectedItem = index }
                    )
                }
            }
        }
    ) { innerPadding ->
        Surface(modifier = Modifier.padding(innerPadding)) {
            when (selectedItem) {
                0 -> HomeScreen()
                1 -> Text("Search & Explore", modifier = Modifier.padding(16.dp))
                2 -> Text("My Orders & Tracking", modifier = Modifier.padding(16.dp))
                3 -> Text("Profile & Settings", modifier = Modifier.padding(16.dp))
            }
        }
    }
}

data class BottomNavItem(val label: String, val icon: ImageVector)
