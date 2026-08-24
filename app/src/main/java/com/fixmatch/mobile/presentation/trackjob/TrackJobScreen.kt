package com.fixmatch.mobile.presentation.trackjob

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Message
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.fixmatch.mobile.domain.model.JobStatus
import com.fixmatch.mobile.di.ServiceLocator
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrackJobScreen(
    onNavigateBack: () -> Unit = {},
    onNavigateToReview: () -> Unit = {} // Actually to Payment
) {
    val activeJobId = ServiceLocator.currentActiveJobId.collectAsState().value
    val jobRepo = ServiceLocator.jobRepository
    val job by jobRepo.observeJob(activeJobId ?: "").collectAsState(initial = null)
    
    val status = job?.status ?: JobStatus.CREATED
    
    var showCancelDialog by remember { mutableStateOf(false) }
    var showCostApprovalDialog by remember { mutableStateOf(false) }
    
    val coroutineScope = rememberCoroutineScope()
    
    LaunchedEffect(status) {
        if (status == JobStatus.ADDITIONAL_COST_PENDING) {
            showCostApprovalDialog = true
        } else {
            showCostApprovalDialog = false
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Theo dõi yêu cầu", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    if (status != JobStatus.COMPLETED && status != JobStatus.REVIEWED && status != JobStatus.CANCELLED_BY_CUSTOMER && status != JobStatus.CANCELLED_BY_WORKER) {
                        TextButton(onClick = { showCancelDialog = true }) {
                            Text("Huỷ", color = MaterialTheme.colorScheme.error)
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        }
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize()) {
            // 1. MOCK MAP LAYER (Background)
            MockMapOverlay(status = status)
            
            // 2. BOTTOM SHEET LAYER
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.BottomCenter
            ) {
                JobStatusBottomSheet(
                    status = status,
                    onNavigateToPayment = onNavigateToReview,
                    workerId = job?.workerId
                )
            }
        }
    }

    if (showCancelDialog) {
        AlertDialog(
            onDismissRequest = { showCancelDialog = false },
            title = { Text("Huỷ yêu cầu?") },
            text = { Text("Bạn có chắc chắn muốn huỷ yêu cầu này không?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        showCancelDialog = false
                        if (activeJobId != null) {
                            coroutineScope.launch {
                                jobRepo.updateJobStatus(activeJobId, "CANCELLED_BY_CUSTOMER")
                            }
                        }
                        onNavigateBack()
                    },
                    colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.error)
                ) {
                    Text("Đồng ý huỷ")
                }
            },
            dismissButton = {
                TextButton(onClick = { showCancelDialog = false }) {
                    Text("Không")
                }
            }
        )
    }
    
    if (showCostApprovalDialog && job != null) {
        AlertDialog(
            onDismissRequest = { },
            title = { Text("Xác nhận phụ phí") },
            text = { 
                Column {
                    Text("Thợ báo phát sinh phụ phí:")
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Lý do: ${job!!.additionalCostReason ?: "Linh kiện thay thế"}")
                    Text("Phí dịch vụ: 150,000 đ")
                    Text("Phụ phí: ${job!!.additionalCost} đ")
                    Divider(modifier = Modifier.padding(vertical = 8.dp))
                    Text("Tổng cộng: ${job!!.finalPrice} đ", fontWeight = FontWeight.Bold)
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (activeJobId != null) {
                            coroutineScope.launch {
                                jobRepo.updateJobStatus(activeJobId, "REPAIRING")
                            }
                        }
                    }
                ) {
                    Text("Đồng ý")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        if (activeJobId != null) {
                            coroutineScope.launch {
                                jobRepo.updateJobStatus(activeJobId, "CANCELLED_BY_CUSTOMER")
                            }
                        }
                        onNavigateBack()
                    }
                ) {
                    Text("Từ chối & Huỷ", color = MaterialTheme.colorScheme.error)
                }
            }
        )
    }
}

@Composable
fun MockMapOverlay(status: JobStatus) {
    // A simple mock map background that changes based on status
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE0E0E0)) // Light gray map background
    ) {
        // Grid pattern to simulate streets
        for (i in 0..10) {
            Box(modifier = Modifier.fillMaxWidth().height(2.dp).offset(y = (i * 100).dp).background(Color.White.copy(alpha = 0.5f)))
            Box(modifier = Modifier.fillMaxHeight().width(2.dp).offset(x = (i * 60).dp).background(Color.White.copy(alpha = 0.5f)))
        }
        
        // Customer Location Pin
        Box(
            modifier = Modifier.align(Alignment.Center).offset(y = (-50).dp)
        ) {
            Icon(Icons.Default.LocationOn, contentDescription = null, tint = Color.Blue, modifier = Modifier.size(48.dp))
        }
        
        // Mock route line
        if (status == JobStatus.WORKER_ON_THE_WAY || status == JobStatus.WORKER_ACCEPTED || status == JobStatus.WORKER_FOUND) {
            // Draw a fake route using a rotated box
            Box(modifier = Modifier.align(Alignment.Center).offset(y = 75.dp, x = 25.dp).width(4.dp).height(200.dp).background(Color.Blue.copy(alpha = 0.5f)))
        }
        
        // Worker Location Pin (Animated if ON_THE_WAY)
        if (status == JobStatus.WORKER_ON_THE_WAY || status == JobStatus.WORKER_ACCEPTED || status == JobStatus.WORKER_FOUND) {
            val infiniteTransition = rememberInfiniteTransition(label = "move")
            val yOffset by infiniteTransition.animateFloat(
                initialValue = 200f,
                targetValue = 0f,
                animationSpec = infiniteRepeatable(
                    animation = tween(10000, easing = LinearEasing),
                    repeatMode = RepeatMode.Restart
                ),
                label = "worker_move"
            )
            
            Box(
                modifier = Modifier.align(Alignment.Center).offset(y = yOffset.dp, x = 50.dp)
            ) {
                Icon(Icons.Default.DirectionsCar, contentDescription = null, tint = Color.Red, modifier = Modifier.size(40.dp).background(Color.White, CircleShape).padding(8.dp))
            }
        }
        
        if (status == JobStatus.SEARCHING_WORKER || status == JobStatus.CREATED) {
            // Radar effect
            val infiniteTransition = rememberInfiniteTransition(label = "radar_anim")
            val scale by infiniteTransition.animateFloat(
                initialValue = 1f,
                targetValue = 4f,
                animationSpec = infiniteRepeatable(animation = tween(2000), repeatMode = RepeatMode.Restart),
                label = "radar"
            )
            val alpha by infiniteTransition.animateFloat(
                initialValue = 0.5f,
                targetValue = 0f,
                animationSpec = infiniteRepeatable(animation = tween(2000), repeatMode = RepeatMode.Restart),
                label = "radar_alpha"
            )
            
            Box(
                modifier = Modifier.align(Alignment.Center).offset(y = (-50).dp).size(48.dp).scale(scale).background(Color.Blue.copy(alpha = alpha), CircleShape)
            )
        }
    }
}

@Composable
fun JobStatusBottomSheet(
    status: JobStatus,
    onNavigateToPayment: () -> Unit,
    workerId: String?
) {
    Surface(
        modifier = Modifier.fillMaxWidth().shadow(16.dp, RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)),
        color = MaterialTheme.colorScheme.surface,
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
    ) {
        Column(modifier = Modifier.padding(24.dp)) {
            // Handle
            Box(modifier = Modifier.width(40.dp).height(4.dp).background(Color.LightGray, CircleShape).align(Alignment.CenterHorizontally))
            Spacer(modifier = Modifier.height(16.dp))
            
            val statusText = when(status) {
                JobStatus.CREATED -> "Đã tạo yêu cầu"
                JobStatus.SEARCHING_WORKER -> "Đang tìm thợ gần bạn..."
                JobStatus.WORKER_FOUND, JobStatus.WORKER_ACCEPTED -> "Thợ đã nhận việc"
                JobStatus.WORKER_ON_THE_WAY -> "Thợ đang trên đường (Khoảng 8 phút, 1.8km)"
                JobStatus.WORKER_ARRIVED -> "Thợ đã đến nơi"
                JobStatus.INSPECTION -> "Thợ đang kiểm tra thiết bị"
                JobStatus.REPAIRING -> "Thợ đang tiến hành sửa chữa"
                JobStatus.ADDITIONAL_COST_PENDING -> "Đang chờ xác nhận phụ phí"
                JobStatus.JOB_COMPLETED -> "Sửa chữa hoàn tất"
                JobStatus.PAYMENT_PENDING -> "Đang chờ thanh toán"
                JobStatus.COMPLETED -> "Đã thanh toán"
                JobStatus.REVIEWED -> "Đã đánh giá"
                JobStatus.CANCELLED_BY_WORKER -> "Thợ đã huỷ"
                JobStatus.CANCELLED_BY_CUSTOMER -> "Bạn đã huỷ"
                JobStatus.NO_WORKER_FOUND -> "Không tìm thấy thợ"
                else -> "Không xác định"
            }
            
            Text(statusText, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
            Spacer(modifier = Modifier.height(24.dp))
            
            if (workerId != null && status != JobStatus.SEARCHING_WORKER && status != JobStatus.CREATED && status != JobStatus.NO_WORKER_FOUND) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    AsyncImage(
                        model = "https://i.pravatar.cc/150?img=11",
                        contentDescription = "Avatar",
                        modifier = Modifier.size(60.dp).clip(CircleShape)
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Nguyễn Văn Hải", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Star, contentDescription = null, tint = Color(0xFFFFB300), modifier = Modifier.size(16.dp))
                            Text(" 4.9 (124 việc)", style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                    if (status != JobStatus.JOB_COMPLETED && status != JobStatus.COMPLETED && status != JobStatus.REVIEWED) {
                        IconButton(onClick = { /* Call */ }, modifier = Modifier.background(MaterialTheme.colorScheme.primaryContainer, CircleShape)) {
                            Icon(Icons.Default.Phone, contentDescription = "Call", tint = MaterialTheme.colorScheme.primary)
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        IconButton(onClick = { /* Message */ }, modifier = Modifier.background(MaterialTheme.colorScheme.primaryContainer, CircleShape)) {
                            Icon(Icons.Default.Message, contentDescription = "Message", tint = MaterialTheme.colorScheme.primary)
                        }
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            if (status == JobStatus.JOB_COMPLETED || status == JobStatus.PAYMENT_PENDING) {
                Button(
                    onClick = onNavigateToPayment,
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Thanh toán ngay", style = MaterialTheme.typography.titleMedium)
                }
            }
        }
    }
}
