package com.fixmatch.mobile.presentation.requestservice

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RequestServiceScreen(
    onNavigateBack: () -> Unit = {},
    onSubmitRequest: () -> Unit = {}
) {
    var problemTitle by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("Điện") }
    
    var titleError by remember { mutableStateOf(false) }
    var descError by remember { mutableStateOf(false) }
    
    var showConfirmationSheet by remember { mutableStateOf(false) }
    var isSubmitting by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    
    val categories = listOf("Điện", "Nước", "Dọn dẹp", "Máy lạnh", "Sửa khoá", "Lắp ráp")

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Đăng Yêu Cầu",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        },
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(16.dp)
                    .navigationBarsPadding()
                    .imePadding()
            ) {
                Button(
                    onClick = {
                        titleError = problemTitle.isBlank()
                        descError = description.isBlank()
                        if (!titleError && !descError) {
                            showConfirmationSheet = true
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .shadow(4.dp, RoundedCornerShape(16.dp)),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Text("Tiếp tục", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(Icons.Default.ArrowForward, contentDescription = null, modifier = Modifier.size(20.dp))
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            
            // --- Category Selection ---
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text("Chọn Dịch Vụ", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onBackground)
                
                @OptIn(ExperimentalLayoutApi::class)
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    categories.forEach { category ->
                        val isSelected = selectedCategory == category
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(20.dp))
                                .background(if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant)
                                .clickable { selectedCategory = category }
                                .padding(horizontal = 16.dp, vertical = 10.dp)
                        ) {
                            Text(
                                text = category,
                                color = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant,
                                style = MaterialTheme.typography.labelLarge,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                            )
                        }
                    }
                }
            }

            // --- Problem Title ---
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("Vấn đề bạn đang gặp phải là gì?", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onBackground)
                OutlinedTextField(
                    value = problemTitle,
                    onValueChange = { 
                        problemTitle = it
                        if(it.isNotBlank()) titleError = false
                    },
                    placeholder = { Text("VD: Sửa vòi nước bị rò rỉ") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    isError = titleError,
                    supportingText = if (titleError) { { Text("Vui lòng nhập tóm tắt vấn đề") } } else null,
                    singleLine = true
                )
            }

            // --- Description ---
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("Mô tả chi tiết", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onBackground)
                OutlinedTextField(
                    value = description,
                    onValueChange = { 
                        description = it
                        if(it.isNotBlank()) descError = false
                    },
                    placeholder = { Text("Cung cấp thêm chi tiết giúp thợ mang đúng dụng cụ...") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp),
                    shape = RoundedCornerShape(12.dp),
                    isError = descError,
                    supportingText = if (descError) { { Text("Vui lòng nhập chi tiết vấn đề") } } else null
                )
            }
            
            // --- Address (Mock read-only) ---
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("Địa chỉ", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onBackground)
                OutlinedTextField(
                    value = "Vị trí hiện tại (Quận 1, TP. HCM)",
                    onValueChange = { },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    readOnly = true,
                    leadingIcon = { Icon(Icons.Default.LocationOn, contentDescription = null, tint = MaterialTheme.colorScheme.primary) }
                )
            }
            Spacer(modifier = Modifier.height(40.dp))
        }
    }
    
    if (showConfirmationSheet) {
        ModalBottomSheet(onDismissRequest = { if (!isSubmitting) showConfirmationSheet = false }) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Xác nhận đăng yêu cầu", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(24.dp))
                
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Dịch vụ: $selectedCategory", fontWeight = FontWeight.Medium)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Vấn đề: $problemTitle", fontWeight = FontWeight.Medium)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Ghi chú: $description", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
                
                Spacer(modifier = Modifier.height(32.dp))
                
                Button(
                    onClick = {
                        isSubmitting = true
                        coroutineScope.launch {
                            delay(1500) // Mock API call
                            isSubmitting = false
                            showConfirmationSheet = false
                            onSubmitRequest()
                        }
                    },
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    enabled = !isSubmitting,
                    shape = RoundedCornerShape(12.dp)
                ) {
                    if (isSubmitting) {
                        CircularProgressIndicator(color = MaterialTheme.colorScheme.onPrimary, modifier = Modifier.size(24.dp))
                        Spacer(modifier = Modifier.width(12.dp))
                        Text("Đang xử lý...")
                    } else {
                        Text("Xác nhận & Tìm Thợ", style = MaterialTheme.typography.titleMedium)
                    }
                }
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}
