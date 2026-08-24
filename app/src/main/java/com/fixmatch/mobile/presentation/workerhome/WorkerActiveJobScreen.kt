package com.fixmatch.mobile.presentation.workerhome

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Message
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.fixmatch.mobile.di.ServiceLocator
import com.fixmatch.mobile.domain.model.JobStatus
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkerActiveJobScreen(
    onNavigateBack: () -> Unit = {}
) {
    val activeJobId = ServiceLocator.currentActiveJobId.collectAsState().value
    val jobRepo = ServiceLocator.jobRepository as com.fixmatch.mobile.data.repository.fake.FakeJobRepository
    val job by jobRepo.observeJob(activeJobId ?: "").collectAsState(initial = null)
    
    val status = job?.status ?: JobStatus.WORKER_ON_THE_WAY
    val coroutineScope = rememberCoroutineScope()
    
    var showAdditionalCostDialog by remember { mutableStateOf(false) }
    var additionalCostInput by remember { mutableStateOf("") }
    var costReasonInput by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Quản lý đơn sửa chữa", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) { Icon(Icons.Default.ArrowBack, contentDescription = "Back") }
                }
            )
        },
        bottomBar = {
            Surface(modifier = Modifier.fillMaxWidth().padding(16.dp), color = MaterialTheme.colorScheme.background) {
                when (status) {
                    JobStatus.WORKER_ON_THE_WAY -> {
                        Button(
                            onClick = { coroutineScope.launch { jobRepo.updateJobStatus(job!!.id, JobStatus.WORKER_ARRIVED.name) } },
                            modifier = Modifier.fillMaxWidth().height(56.dp), shape = RoundedCornerShape(12.dp)
                        ) { Text("Đã đến nơi") }
                    }
                    JobStatus.WORKER_ARRIVED -> {
                        Button(
                            onClick = { coroutineScope.launch { jobRepo.updateJobStatus(job!!.id, JobStatus.INSPECTION.name) } },
                            modifier = Modifier.fillMaxWidth().height(56.dp), shape = RoundedCornerShape(12.dp)
                        ) { Text("Bắt đầu kiểm tra (Inspecting)") }
                    }
                    JobStatus.INSPECTION -> {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                            OutlinedButton(
                                onClick = { showAdditionalCostDialog = true },
                                modifier = Modifier.weight(1f).height(56.dp), shape = RoundedCornerShape(12.dp)
                            ) { Text("Báo thêm phí") }
                            Button(
                                onClick = { coroutineScope.launch { jobRepo.updateJobStatus(job!!.id, JobStatus.REPAIRING.name) } },
                                modifier = Modifier.weight(1f).height(56.dp), shape = RoundedCornerShape(12.dp)
                            ) { Text("Tiến hành sửa") }
                        }
                    }
                    JobStatus.ADDITIONAL_COST_PENDING -> {
                        Button(
                            onClick = { },
                            modifier = Modifier.fillMaxWidth().height(56.dp), shape = RoundedCornerShape(12.dp), enabled = false
                        ) { Text("Chờ khách hàng duyệt phí...") }
                    }
                    JobStatus.ADDITIONAL_COST_APPROVED, JobStatus.REPAIRING -> {
                        Button(
                            onClick = { coroutineScope.launch { jobRepo.updateJobStatus(job!!.id, JobStatus.JOB_COMPLETED.name) } },
                            modifier = Modifier.fillMaxWidth().height(56.dp), shape = RoundedCornerShape(12.dp), colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50))
                        ) { Text("Hoàn thành công việc") }
                    }
                    JobStatus.JOB_COMPLETED, JobStatus.PAYMENT_PENDING -> {
                        Button(
                            onClick = { onNavigateBack() },
                            modifier = Modifier.fillMaxWidth().height(56.dp), shape = RoundedCornerShape(12.dp)
                        ) { Text("Đóng") }
                    }
                    else -> {
                        Button(
                            onClick = { onNavigateBack() },
                            modifier = Modifier.fillMaxWidth().height(56.dp), shape = RoundedCornerShape(12.dp)
                        ) { Text("Quay lại") }
                    }
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(padding).verticalScroll(rememberScrollState())
        ) {
            // Mock Map View
            Box(modifier = Modifier.fillMaxWidth().height(200.dp).background(Color(0xFFE0E0E0)), contentAlignment = Alignment.Center) {
                Text("Bản đồ điều hướng", color = Color.Gray, fontWeight = FontWeight.Bold)
            }
            
            Column(modifier = Modifier.padding(16.dp)) {
                // Status Header
                Text(
                    text = when(status) {
                        JobStatus.WORKER_ON_THE_WAY -> "Đang di chuyển đến khách"
                        JobStatus.WORKER_ARRIVED -> "Bạn đã đến nơi"
                        JobStatus.INSPECTION -> "Đang kiểm tra sự cố"
                        JobStatus.ADDITIONAL_COST_PENDING -> "Đang chờ duyệt phụ phí"
                        JobStatus.ADDITIONAL_COST_APPROVED -> "Khách đã duyệt phí"
                        JobStatus.REPAIRING -> "Đang sửa chữa"
                        JobStatus.JOB_COMPLETED -> "Đã hoàn thành"
                        else -> status.name
                    },
                    style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(16.dp))
                
                // Customer Info
                Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha=0.5f))) {
                    Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                        AsyncImage(model = "https://i.pravatar.cc/150?img=5", contentDescription = "Customer", modifier = Modifier.size(48.dp).clip(CircleShape))
                        Spacer(modifier = Modifier.width(16.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text("Chị Lan", fontWeight = FontWeight.Bold)
                            Text("Khách hàng mới", style = MaterialTheme.typography.bodySmall)
                        }
                        IconButton(onClick = {}) { Icon(Icons.Default.Phone, contentDescription = "Call", tint = MaterialTheme.colorScheme.primary) }
                        IconButton(onClick = {}) { Icon(Icons.Default.Message, contentDescription = "Message", tint = MaterialTheme.colorScheme.primary) }
                    }
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                // Job Details
                Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Dịch vụ: ${job?.title ?: "Sửa chập điện"}", fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(8.dp))
                        Row {
                            Icon(Icons.Default.LocationOn, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(20.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(job?.location ?: "123 Lê Lợi, Quận 1")
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Mô tả: ${job?.description ?: "Nhà vệ sinh bị chập điện."}", color = MaterialTheme.colorScheme.onSurfaceVariant)
                        
                        Divider(modifier = Modifier.padding(vertical = 12.dp))
                        
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("Phí cơ bản:")
                            Text("${job?.basePrice ?: 200000} đ", fontWeight = FontWeight.Bold)
                        }
                        if ((job?.additionalCost ?: 0.0) > 0) {
                            Spacer(modifier = Modifier.height(8.dp))
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Text("Phụ phí (${job?.additionalCostReason}):", color = MaterialTheme.colorScheme.error)
                                Text("+${job?.additionalCost} đ", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.error)
                            }
                        }
                    }
                }
            }
        }
        
        if (showAdditionalCostDialog) {
            AlertDialog(
                onDismissRequest = { showAdditionalCostDialog = false },
                title = { Text("Báo thêm phụ phí") },
                text = {
                    Column {
                        OutlinedTextField(value = additionalCostInput, onValueChange = { additionalCostInput = it }, label = { Text("Số tiền (đ)") }, modifier = Modifier.fillMaxWidth())
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(value = costReasonInput, onValueChange = { costReasonInput = it }, label = { Text("Lý do (VD: Thay linh kiện)") }, modifier = Modifier.fillMaxWidth())
                    }
                },
                confirmButton = {
                    Button(onClick = {
                        coroutineScope.launch {
                            job?.let {
                                val cost = additionalCostInput.toDoubleOrNull() ?: 0.0
                                jobRepo.updateJobPrice(it.id, cost, costReasonInput)
                            }
                        }
                        showAdditionalCostDialog = false
                    }) { Text("Gửi yêu cầu") }
                },
                dismissButton = {
                    TextButton(onClick = { showAdditionalCostDialog = false }) { Text("Hủy") }
                }
            )
        }
    }
}
