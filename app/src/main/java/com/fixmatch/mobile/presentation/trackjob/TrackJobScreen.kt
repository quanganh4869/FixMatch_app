package com.fixmatch.mobile.presentation.trackjob

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import com.fixmatch.mobile.domain.model.JobStatus
import com.fixmatch.mobile.domain.util.LatLng
import com.fixmatch.mobile.domain.util.MockLocationProvider

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun TrackJobScreen(
    onNavigateBack: () -> Unit = {},
    onNavigateToReview: () -> Unit = {}
) {
    val coroutineScope = rememberCoroutineScope()
    
    // Khởi tạo MockLocationProvider
    // Giả sử thợ đi từ Landmark 81 đến Bến Thành, với vận tốc 30km/h
    val startLoc = LatLng(10.794, 106.722)
    val endLoc = LatLng(10.772, 106.698)
    // Tăng tốc độ mô phỏng cho người xem dễ thấy (200km/h thay vì 30)
    val locationProvider = remember { MockLocationProvider(startLoc, endLoc, 400.0, 500) }
    
    val currentLocation by locationProvider.currentLocation.collectAsState(initial = startLoc)
    var jobStatus by remember { mutableStateOf(JobStatus.ON_THE_WAY) }
    
    // Tính khoảng cách còn lại (km)
    val remainingDistance = currentLocation?.let { MockLocationProvider.calculateDistance(it, endLoc) } ?: 0.0
    // Ước tính thời gian (phút). Giả sử trung bình đi 20km/h nội thành => 3 phút/km
    val etaMinutes = (remainingDistance * 3).toInt().coerceAtLeast(1)

    LaunchedEffect(Unit) {
        // Bắt đầu mô phỏng di chuyển
        locationProvider.startTracking()
        
        // Khi di chuyển xong
        jobStatus = JobStatus.ARRIVED
        delay(3000)
        
        // Bắt đầu sửa
        jobStatus = JobStatus.IN_PROGRESS
        delay(4000)
        
        // Sửa xong
        jobStatus = JobStatus.COMPLETED
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Text(
                        "Theo dõi đơn hàng", 
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                    ) 
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        bottomBar = {
            if (jobStatus == JobStatus.COMPLETED) {
                Box(modifier = Modifier.fillMaxWidth().padding(16.dp).navigationBarsPadding()) {
                    Button(
                        onClick = onNavigateToReview,
                        modifier = Modifier.fillMaxWidth().height(56.dp),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Thanh Toán & Đánh Giá", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(paddingValues)
        ) {
            
            // MAP MOCKUP (Mô phỏng 1 box với toạ độ thay đổi)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .background(Color(0xFFE0E0E0)) // Map placeholder
            ) {
                // Ta có thể vẽ một route giả lập bằng Box
                // Giả lập toạ độ thành padding/offset
                // Đây chỉ là demo trực quan nên ta dùng layout đơn giản
                
                // Point B (Đích - Bến Thành)
                Box(
                    modifier = Modifier.align(Alignment.Center).size(30.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.Home, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(30.dp))
                }
                
                // Point A (Marker đang chạy)
                // Cần tính offset dựa trên tiến trình đi từ start->end
                val progress = if (currentLocation != null) {
                    val total = MockLocationProvider.calculateDistance(startLoc, endLoc)
                    val done = MockLocationProvider.calculateDistance(startLoc, currentLocation!!)
                    (done / total).coerceIn(0.0, 1.0)
                } else 0.0
                
                // Giả lập di chuyển từ trên góc phải xuống giữa màn hình
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(
                            top = (100 + 200 * progress).dp, 
                            start = (200 - 100 * progress).dp
                        )
                ) {
                    Box(modifier = Modifier.size(40.dp).background(Color.White, CircleShape).border(2.dp, MaterialTheme.colorScheme.primary, CircleShape), contentAlignment = Alignment.Center) {
                         AsyncImage(model = "https://i.pravatar.cc/150?img=11", contentDescription = null, modifier = Modifier.size(36.dp).clip(CircleShape))
                    }
                }
            }
            
            // STATUS PANEL
            Surface(
                modifier = Modifier.fillMaxWidth().shadow(16.dp, RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)),
                shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
                color = MaterialTheme.colorScheme.surface
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    
                    // Animated Status Header
                    AnimatedContent(targetState = jobStatus) { status ->
                        when(status) {
                            JobStatus.ON_THE_WAY -> {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text("Thợ đang trên đường", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text("Đến trong khoảng $etaMinutes phút (${String.format("%.1f", remainingDistance)} km)", style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
                                }
                            }
                            JobStatus.ARRIVED -> {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text("Thợ đã đến nơi", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text("Vui lòng mở cửa cho thợ", style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                }
                            }
                            JobStatus.IN_PROGRESS -> {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text("Đang thực hiện công việc", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = Color(0xFFFFA000))
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text("Thợ đang tiến hành sửa chữa...", style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                }
                            }
                            JobStatus.COMPLETED -> {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text("Công việc đã hoàn thành", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = Color(0xFF4CAF50))
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text("Vui lòng thanh toán cho thợ", style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                }
                            }
                            else -> {}
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(24.dp))
                    
                    // Worker Info
                    Row(
                        modifier = Modifier.fillMaxWidth().background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha=0.3f), RoundedCornerShape(12.dp)).padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        AsyncImage(
                            model = "https://i.pravatar.cc/150?img=11",
                            contentDescription = "Avatar",
                            modifier = Modifier.size(50.dp).clip(CircleShape)
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text("Nguyễn Văn Hải", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                            Text("Thợ Điện", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                        
                        if (jobStatus != JobStatus.COMPLETED) {
                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                IconButton(onClick = { }, modifier = Modifier.background(MaterialTheme.colorScheme.primaryContainer, CircleShape).size(40.dp)) {
                                    Icon(Icons.Default.Call, contentDescription = "Call", tint = MaterialTheme.colorScheme.onPrimaryContainer, modifier = Modifier.size(20.dp))
                                }
                                IconButton(onClick = { }, modifier = Modifier.background(MaterialTheme.colorScheme.secondaryContainer, CircleShape).size(40.dp)) {
                                    Icon(Icons.Default.Chat, contentDescription = "Chat", tint = MaterialTheme.colorScheme.onSecondaryContainer, modifier = Modifier.size(20.dp))
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
