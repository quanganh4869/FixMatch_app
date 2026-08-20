package com.findtho.mobile.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.findtho.mobile.presentation.components.AppTextField
import com.findtho.mobile.presentation.components.AnimatedMascot
import com.findtho.mobile.presentation.components.MascotState

@Composable
fun HomeScreen() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 24.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(48.dp))
            Text(
                text = "Chào buổi sáng, User! 👋",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(24.dp))
            
            AppTextField(
                value = "",
                onValueChange = {},
                placeholder = "Bạn cần sửa gì hôm nay?",
                leadingIcon = {
                    Icon(Icons.Default.Search, contentDescription = "Search")
                }
            )
            
            Spacer(modifier = Modifier.height(32.dp))
            
            Text(
                text = "Dịch vụ phổ biến",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
            
            Spacer(modifier = Modifier.height(16.dp))
        }
        
        items(3) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .padding(bottom = 16.dp)
                    .background(MaterialTheme.colorScheme.surfaceVariant, MaterialTheme.shapes.large)
            )
        }
        
        item {
            Spacer(modifier = Modifier.height(24.dp))
            AnimatedMascot(
                state = MascotState.SEARCHING,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )
        }
    }
}
