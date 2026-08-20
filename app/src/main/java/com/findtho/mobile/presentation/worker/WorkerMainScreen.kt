package com.findtho.mobile.presentation.worker

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.findtho.mobile.presentation.customer.BottomNavItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkerMainScreen() {
    var selectedItem by remember { mutableIntStateOf(0) }
    val items = listOf(
        BottomNavItem("Dashboard", Icons.Filled.Home),
        BottomNavItem("My Jobs", Icons.Filled.List),
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
                0 -> WorkerDashboard()
                1 -> Text("Jobs & Requests", modifier = Modifier.padding(16.dp))
                2 -> Text("Worker Profile & Settings", modifier = Modifier.padding(16.dp))
            }
        }
    }
}
