package com.fixmatch.mobile.presentation.workerfound

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.fixmatch.mobile.domain.model.Worker

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkerFoundScreen(
    onNavigateBack: () -> Unit = {},
    onContinue: () -> Unit = {} // Now represents clicking a worker card to see profile
) {
    val mockWorkers = listOf(
        Worker(
            id = "w1", name = "Nguyễn Văn Hải", title = "Thợ sửa điện nước chuyên nghiệp", category = "Điện, Nước", 
            rating = 4.9, jobsCompleted = 124, hourlyRate = 150000.0, profileImageUrl = "https://i.pravatar.cc/150?img=11", isPro = true, location = "Cách 1.2 km"
        ),
        Worker(
            id = "w2", name = "Trần Tuấn Anh", title = "Chuyên gia điện lạnh", category = "Máy lạnh", 
            rating = 4.7, jobsCompleted = 89, hourlyRate = 120000.0, profileImageUrl = "https://i.pravatar.cc/150?img=12", isPro = false, location = "Cách 2.5 km"
        ),
        Worker(
            id = "w3", name = "Lê Hoàng Phúc", title = "Thợ sửa ống nước", category = "Nước", 
            rating = 4.5, jobsCompleted = 45, hourlyRate = 100000.0, profileImageUrl = "https://i.pravatar.cc/150?img=13", isPro = false, location = "Cách 3.1 km"
        )
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Kết quả tìm kiếm (${mockWorkers.size})", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(mockWorkers) { worker ->
                WorkerCard(worker = worker, onClick = onContinue)
            }
        }
    }
}

@Composable
fun WorkerCard(worker: Worker, onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            AsyncImage(
                model = worker.profileImageUrl,
                contentDescription = "Avatar",
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(64.dp).clip(CircleShape)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(worker.name, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    if (worker.isPro) {
                        Spacer(modifier = Modifier.width(4.dp))
                        Surface(color = MaterialTheme.colorScheme.primaryContainer, shape = RoundedCornerShape(4.dp)) {
                            Text("PRO", fontSize = MaterialTheme.typography.labelSmall.fontSize, modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp), color = MaterialTheme.colorScheme.onPrimaryContainer)
                        }
                    }
                }
                Text(worker.category, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Spacer(modifier = Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Star, contentDescription = null, tint = Color(0xFFFFB300), modifier = Modifier.size(16.dp))
                    Text(" ${worker.rating} (${worker.jobsCompleted} việc)", style = MaterialTheme.typography.bodySmall)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("• ${worker.location}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
        }
    }
}
