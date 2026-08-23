package com.fixmatch.mobile.presentation.findingworker

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FindingWorkerScreen(
    onCancel: () -> Unit = {},
    onWorkerFound: () -> Unit = {}
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedDistance by remember { mutableStateOf("Dưới 5km") }
    var selectedRating by remember { mutableStateOf("4.5+ Sao") }
    var selectedPrice by remember { mutableStateOf("Tất cả") }
    var isSearching by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Tìm kiếm thợ", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onCancel) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        bottomBar = {
            Surface(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                color = MaterialTheme.colorScheme.background
            ) {
                Button(
                    onClick = {
                        isSearching = true
                    },
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    shape = MaterialTheme.shapes.medium
                ) {
                    if (isSearching) {
                        CircularProgressIndicator(color = MaterialTheme.colorScheme.onPrimary, modifier = Modifier.size(24.dp))
                    } else {
                        Text("Tìm Kiếm", style = MaterialTheme.typography.titleMedium)
                    }
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            if (isSearching) {
                LaunchedEffect(Unit) {
                    delay(1500)
                    onWorkerFound()
                    isSearching = false
                }
            }

            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Bạn đang cần dịch vụ gì?") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                shape = MaterialTheme.shapes.medium,
                singleLine = true
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Tune, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Bộ lọc tìm kiếm", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.height(16.dp))

            Text("Khoảng cách", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Medium)
            Spacer(modifier = Modifier.height(8.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                listOf("Dưới 2km", "Dưới 5km", "Toàn thành phố").forEach { dist ->
                    FilterChip(
                        selected = selectedDistance == dist,
                        onClick = { selectedDistance = dist },
                        label = { Text(dist) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            Text("Đánh giá tối thiểu", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Medium)
            Spacer(modifier = Modifier.height(8.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                listOf("4.5+ Sao", "4.0+ Sao", "Tất cả").forEach { rating ->
                    FilterChip(
                        selected = selectedRating == rating,
                        onClick = { selectedRating = rating },
                        label = { Text(rating) }
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            Text("Mức giá dự kiến", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Medium)
            Spacer(modifier = Modifier.height(8.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                listOf("Dưới 200k", "200k - 500k", "Tất cả").forEach { price ->
                    FilterChip(
                        selected = selectedPrice == price,
                        onClick = { selectedPrice = price },
                        label = { Text(price) }
                    )
                }
            }
        }
    }
}
