package com.fixmatch.mobile.presentation.workerfound

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import kotlinx.coroutines.delay

@Composable
fun WorkerFoundScreen(
    onNavigateBack: () -> Unit = {},
    onContinue: () -> Unit = {}
) {
    LaunchedEffect(Unit) {
        // Mock updating the state to WORKER_FOUND
        val activeJobId = com.fixmatch.mobile.di.ServiceLocator.currentActiveJobId.value
        if (activeJobId != null) {
            com.fixmatch.mobile.di.ServiceLocator.jobRepository.assignWorker(activeJobId, "w1")
        }
        delay(3000)
        onContinue()
    }

    Box(
        modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(32.dp)
        ) {
            Text("Đã tìm thấy thợ!", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
            Spacer(modifier = Modifier.height(32.dp))
            
            AsyncImage(
                model = "https://i.pravatar.cc/150?img=11",
                contentDescription = "Avatar",
                modifier = Modifier.size(120.dp).clip(CircleShape)
            )
            Spacer(modifier = Modifier.height(16.dp))
            
            Text("Nguyễn Văn Hải", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Star, contentDescription = null, tint = Color(0xFFFFB300), modifier = Modifier.size(20.dp))
                Text(" 4.9 (124 việc)", style = MaterialTheme.typography.bodyLarge)
            }
            
            Spacer(modifier = Modifier.height(32.dp))
            CircularProgressIndicator(modifier = Modifier.size(24.dp))
            Spacer(modifier = Modifier.height(16.dp))
            Text("Đang kết nối chuyến đi...", color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}
