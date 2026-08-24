package com.fixmatch.mobile.presentation.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.clickable
import androidx.compose.ui.graphics.Color
import com.fixmatch.mobile.di.ServiceLocator
import com.fixmatch.mobile.domain.model.WorkerApplication
import com.fixmatch.mobile.domain.model.WorkerApplicationStatus
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BecomeWorkerScreen(
    onNavigateBack: () -> Unit = {},
    onApplicationSubmitted: () -> Unit = {}
) {
    var currentStep by remember { mutableStateOf(1) }
    var category by remember { mutableStateOf("") }
    var experience by remember { mutableStateOf("") }
    var serviceArea by remember { mutableStateOf("") }
    var isUploading by remember { mutableStateOf(false) }
    var cccdUploaded by remember { mutableStateOf(false) }
    var isSubmitting by remember { mutableStateOf(false) }

    val coroutineScope = rememberCoroutineScope()
    val totalSteps = 4

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Đăng ký làm thợ", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        bottomBar = {
            Surface(modifier = Modifier.fillMaxWidth().padding(16.dp), color = MaterialTheme.colorScheme.background) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    if (currentStep > 1) {
                        OutlinedButton(onClick = { currentStep-- }, modifier = Modifier.weight(1f).height(56.dp), shape = RoundedCornerShape(12.dp)) {
                            Text("Quay lại")
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                    }
                    Button(
                        onClick = {
                            if (currentStep < totalSteps) {
                                currentStep++
                            } else {
                                isSubmitting = true
                                coroutineScope.launch {
                                    delay(1500)
                                    val accountState = ServiceLocator.accountState.value
                                    val newApp = WorkerApplication(
                                        id = "app_1",
                                        userId = accountState.user.id,
                                        status = WorkerApplicationStatus.UNDER_REVIEW,
                                        workerCategory = category,
                                        experienceYears = experience.toIntOrNull() ?: 0,
                                        serviceArea = serviceArea,
                                        verificationDocuments = if (cccdUploaded) listOf("cccd_front", "cccd_back") else emptyList()
                                    )
                                    ServiceLocator.accountState.value = accountState.copy(application = newApp)
                                    isSubmitting = false
                                    onApplicationSubmitted()
                                }
                            }
                        },
                        modifier = Modifier.weight(if (currentStep > 1) 1f else 2f).height(56.dp),
                        shape = RoundedCornerShape(12.dp),
                        enabled = !isSubmitting && (
                            (currentStep == 1) ||
                            (currentStep == 2 && category.isNotBlank() && experience.isNotBlank()) ||
                            (currentStep == 3 && serviceArea.isNotBlank()) ||
                            (currentStep == 4 && cccdUploaded)
                        )
                    ) {
                        if (isSubmitting) {
                            CircularProgressIndicator(modifier = Modifier.size(24.dp), color = MaterialTheme.colorScheme.onPrimary)
                        } else {
                            Text(if (currentStep == totalSteps) "Gửi hồ sơ" else "Tiếp tục")
                        }
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
            // Progress Indicator
            LinearProgressIndicator(
                progress = currentStep.toFloat() / totalSteps,
                modifier = Modifier.fillMaxWidth().height(8.dp).clip(RoundedCornerShape(4.dp))
            )
            Spacer(modifier = Modifier.height(24.dp))

            when (currentStep) {
                1 -> {
                    Text("Bước 1: Thông tin cơ bản", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Kiểm tra lại thông tin cá nhân của bạn.", color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Spacer(modifier = Modifier.height(24.dp))
                    OutlinedTextField(value = ServiceLocator.accountState.value.user.name, onValueChange = {}, label = { Text("Họ và tên") }, modifier = Modifier.fillMaxWidth(), enabled = false)
                    Spacer(modifier = Modifier.height(16.dp))
                    OutlinedTextField(value = ServiceLocator.accountState.value.user.email, onValueChange = {}, label = { Text("Email / Số điện thoại") }, modifier = Modifier.fillMaxWidth(), enabled = false)
                }
                2 -> {
                    Text("Bước 2: Thông tin chuyên môn", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(24.dp))
                    Text("Bạn chuyên sửa chữa về lĩnh vực gì?", fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))
                    val categories = listOf("Điện, Nước", "Máy lạnh", "Điện lạnh", "Sửa máy giặt")
                    categories.forEach { cat ->
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            RadioButton(selected = category == cat, onClick = { category = cat })
                            Text(cat)
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    OutlinedTextField(value = experience, onValueChange = { experience = it }, label = { Text("Số năm kinh nghiệm") }, modifier = Modifier.fillMaxWidth())
                }
                3 -> {
                    Text("Bước 3: Khu vực hoạt động", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(24.dp))
                    OutlinedTextField(value = serviceArea, onValueChange = { serviceArea = it }, label = { Text("Quận / Thành phố bạn có thể nhận đơn") }, modifier = Modifier.fillMaxWidth())
                }
                4 -> {
                    Text("Bước 4: Xác minh danh tính", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Vui lòng tải lên ảnh CCCD/CMND để chúng tôi xác minh.", color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Spacer(modifier = Modifier.height(24.dp))
                    
                    Card(
                        modifier = Modifier.fillMaxWidth().height(160.dp).clickable { 
                            isUploading = true
                            coroutineScope.launch { delay(1000); cccdUploaded = true; isUploading = false }
                        },
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
                    ) {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            if (isUploading) {
                                CircularProgressIndicator()
                            } else if (cccdUploaded) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Icon(Icons.Default.CheckCircle, contentDescription = null, tint = Color(0xFF4CAF50), modifier = Modifier.size(48.dp))
                                    Text("Đã tải lên", color = Color(0xFF4CAF50), fontWeight = FontWeight.Bold)
                                }
                            } else {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Icon(Icons.Default.UploadFile, contentDescription = null, modifier = Modifier.size(48.dp))
                                    Text("Nhấn để tải lên")
                                }
                            }
                        }
                    }
                    
                    if (cccdUploaded) {
                        Spacer(modifier = Modifier.height(24.dp))
                        Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text("Xem lại thông tin trước khi gửi", fontWeight = FontWeight.Bold)
                                Spacer(modifier = Modifier.height(8.dp))
                                Text("Chuyên môn: $category")
                                Text("Kinh nghiệm: $experience năm")
                                Text("Khu vực: $serviceArea")
                            }
                        }
                    }
                }
            }
        }
    }
}
