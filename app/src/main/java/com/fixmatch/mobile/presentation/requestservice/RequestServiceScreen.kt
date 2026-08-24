package com.fixmatch.mobile.presentation.requestservice

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import com.fixmatch.mobile.di.ServiceLocator

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RequestServiceScreen(
    onNavigateBack: () -> Unit = {},
    onSubmitRequest: () -> Unit = {}
) {
    var selectedIssue by remember { mutableStateOf("") }
    var note by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("123 Nguyễn Văn Linh, Quận 7, TP.HCM") }
    var isScheduling by remember { mutableStateOf(false) }
    var isSubmitting by remember { mutableStateOf(false) }
    
    val coroutineScope = rememberCoroutineScope()
    val jobRepo = ServiceLocator.jobRepository

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Đặt dịch vụ", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
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
                        if (selectedIssue.isNotBlank()) {
                            isSubmitting = true
                            coroutineScope.launch {
                                val result = jobRepo.requestService(
                                    title = "Sửa Máy Lạnh",
                                    description = if (note.isNotBlank()) "$selectedIssue - $note" else selectedIssue,
                                    category = "Máy lạnh",
                                    location = location
                                )
                                isSubmitting = false
                                if (result is com.fixmatch.mobile.domain.util.NetworkResult.Success) {
                                    ServiceLocator.currentActiveJobId.value = result.data.id
                                    onSubmitRequest()
                                }
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    shape = RoundedCornerShape(12.dp),
                    enabled = selectedIssue.isNotBlank() && !isSubmitting
                ) {
                    if (isSubmitting) {
                        CircularProgressIndicator(color = MaterialTheme.colorScheme.onPrimary, modifier = Modifier.size(24.dp))
                    } else {
                        Text(if (isScheduling) "Đặt Lịch" else "Tìm Thợ Ngay", style = MaterialTheme.typography.titleMedium)
                    }
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            Text("Vấn đề bạn đang gặp phải?", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(16.dp))
            
            // Mock issues for Air Conditioner
            val issues = listOf("Máy không lạnh", "Chảy nước", "Quạt không quay", "Cần vệ sinh/Bảo trì")
            issues.forEach { issue ->
                Row(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = selectedIssue == issue,
                        onClick = { selectedIssue = issue }
                    )
                    Text(issue, modifier = Modifier.padding(start = 8.dp))
                }
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            Text("Mô tả thêm (Không bắt buộc)", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = note,
                onValueChange = { note = it },
                modifier = Modifier.fillMaxWidth().height(100.dp),
                placeholder = { Text("Ví dụ: Máy kêu to khi chạy...") },
                shape = RoundedCornerShape(12.dp)
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            Text("Địa chỉ", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = location,
                onValueChange = { location = it },
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = { Icon(Icons.Default.LocationOn, contentDescription = null, tint = MaterialTheme.colorScheme.primary) },
                shape = RoundedCornerShape(12.dp)
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text("Thời gian", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Làm ngay")
                    Switch(checked = isScheduling, onCheckedChange = { isScheduling = it }, modifier = Modifier.padding(horizontal = 8.dp))
                    Text("Đặt lịch")
                }
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Phí dịch vụ cơ bản", style = MaterialTheme.typography.titleSmall)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("150,000 đ", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("*Phí có thể thay đổi tùy tình trạng thực tế sau khi thợ kiểm tra. Bạn sẽ được báo giá và cần xác nhận trước khi sửa.", style = MaterialTheme.typography.bodySmall)
                }
            }
            
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}
