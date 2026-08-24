package com.fixmatch.mobile.presentation.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material.icons.outlined.Bolt
import androidx.compose.material.icons.outlined.WaterDrop
import androidx.compose.material.icons.outlined.AcUnit
import androidx.compose.material.icons.outlined.CleaningServices
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.fixmatch.mobile.data.settings.SettingsManager
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavigateToWorkerProfile: () -> Unit = {},
    onNavigateToNewRequest: () -> Unit = {},
    onNavigateToFindingWorker: () -> Unit = {}
) {
    val context = LocalContext.current
    val settingsManager = remember { SettingsManager(context) }
    val coroutineScope = rememberCoroutineScope()
    
    val savedLocation by settingsManager.locationFlow.collectAsState(initial = null)
    var showLocationSheet by remember { mutableStateOf(false) }
    
    // Automatically show location sheet if no location is saved (Module 2)
    LaunchedEffect(savedLocation) {
        if (savedLocation == null) {
            showLocationSheet = true
        }
    }

    var searchQuery by remember { mutableStateOf("") }
    
    var showDebugMenu by remember { mutableStateOf(false) }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { showDebugMenu = true }, containerColor = Color.Red) {
                Text("DEV", color = Color.White, fontWeight = FontWeight.Bold)
            }
        },
        topBar = {
            TopAppBar(
                title = {
                    Column(modifier = Modifier.clickable { showLocationSheet = true }) {
                        Text(
                            text = "Vị trí hiện tại",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.LocationOn,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = savedLocation ?: "Đang cập nhật...",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                },
                actions = {
                    IconButton(onClick = { }) {
                        Icon(Icons.Default.Notifications, contentDescription = "Alerts")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(paddingValues),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            contentPadding = PaddingValues(bottom = 24.dp, top = 8.dp)
        ) {
            // 2. Primary Search
            item {
                Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                    Text(
                        text = "Bạn đang cần tìm thợ gì?",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        placeholder = { Text("Tìm thợ điện, nước, máy lạnh...") },
                        leadingIcon = {
                            Icon(Icons.Default.Search, contentDescription = "Search", tint = MaterialTheme.colorScheme.primary)
                        },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        shape = RoundedCornerShape(16.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
                            unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = Color.Transparent
                        )
                    )
                }
            }

            // 3. Dịch vụ phổ biến
            item {
                Column {
                    SectionHeader(title = "Dịch vụ phổ biến", actionText = "Xem tất cả", onActionClick = { })
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        CompactCategoryItem("Điện", Icons.Outlined.Bolt, onClick = onNavigateToFindingWorker)
                        CompactCategoryItem("Nước", Icons.Outlined.WaterDrop, onClick = onNavigateToFindingWorker)
                        CompactCategoryItem("Máy lạnh", Icons.Outlined.AcUnit, onClick = onNavigateToFindingWorker)
                        CompactCategoryItem("Dọn dẹp", Icons.Outlined.CleaningServices, onClick = onNavigateToFindingWorker)
                    }
                }
            }

            // 4. Secondary CTA (Đăng Việc)
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .clickable { onNavigateToNewRequest() },
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f)),
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(0.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Create,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier
                                .size(40.dp)
                                .background(MaterialTheme.colorScheme.background, CircleShape)
                                .padding(8.dp)
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Column {
                            Text(
                                text = "Bạn chưa biết tìm thợ nào?",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                            Text(
                                text = "Đăng yêu cầu để thợ phù hợp liên hệ.",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
                            )
                        }
                    }
                }
            }

            // 5. Yêu cầu gần đây
            item {
                Column {
                    SectionHeader(title = "Yêu cầu gần đây", actionText = "Xem tất cả", onActionClick = { })
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    Card(
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp).fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text("Sửa ống nước rò rỉ", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodyLarge)
                                Spacer(modifier = Modifier.height(4.dp))
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Box(modifier = Modifier.size(8.dp).background(Color(0xFFFFA000), CircleShape))
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text("Đang tìm thợ · 3 thợ đã xem", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                }
                            }
                            Icon(Icons.Default.KeyboardArrowRight, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                    }
                }
            }

            // 6. Thợ nổi bật gần bạn
            item {
                Column {
                    SectionHeader(title = "Thợ nổi bật gần bạn", actionText = "Xem bản đồ", onActionClick = { })
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    CompactWorkerCard(
                        name = "Nguyễn Văn Hải",
                        title = "Thợ Điện Chuyên Nghiệp",
                        rating = "4.9",
                        distance = "1.2 km",
                        jobsCompleted = "120+",
                        imageUrl = "https://i.pravatar.cc/150?img=11",
                        onClick = onNavigateToWorkerProfile
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    CompactWorkerCard(
                        name = "Trần Trọng Bình",
                        title = "Chuyên gia Ống Nước",
                        rating = "4.8",
                        distance = "2.5 km",
                        jobsCompleted = "85+",
                        imageUrl = "https://i.pravatar.cc/150?img=12",
                        onClick = onNavigateToWorkerProfile
                    )
                }
            }
        }
    }
    
    // Module 2: Location Selection Bottom Sheet
    if (showDebugMenu) {
        ModalBottomSheet(onDismissRequest = { showDebugMenu = false }) {
            Column(modifier = Modifier.padding(16.dp).verticalScroll(rememberScrollState())) {
                Text("Debug Mock Scenarios", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(16.dp))
                val jobRepo = com.fixmatch.mobile.di.ServiceLocator.jobRepository as com.fixmatch.mobile.data.repository.fake.FakeJobRepository
                val activeJobId = com.fixmatch.mobile.di.ServiceLocator.currentActiveJobId.value ?: "j_mock"
                
                Button(onClick = { com.fixmatch.mobile.di.ServiceLocator.currentActiveJobId.value = null; showDebugMenu = false }) { Text("1. Chưa có request") }
                Button(onClick = { coroutineScope.launch { jobRepo.updateJobStatus(activeJobId, "SEARCHING_WORKER") }; showDebugMenu = false }) { Text("2. Đang tìm thợ") }
                Button(onClick = { coroutineScope.launch { jobRepo.assignWorker(activeJobId, "w1") }; showDebugMenu = false }) { Text("3. Tìm thấy thợ (WORKER_FOUND)") }
                Button(onClick = { coroutineScope.launch { jobRepo.updateJobStatus(activeJobId, "WORKER_ON_THE_WAY") }; showDebugMenu = false }) { Text("4. Đang trên đường") }
                Button(onClick = { coroutineScope.launch { jobRepo.updateJobStatus(activeJobId, "WORKER_ARRIVED") }; showDebugMenu = false }) { Text("5. Đã đến") }
                Button(onClick = { coroutineScope.launch { jobRepo.updateJobStatus(activeJobId, "INSPECTION") }; showDebugMenu = false }) { Text("6. Đang kiểm tra (INSPECTION)") }
                Button(onClick = { coroutineScope.launch { jobRepo.updateJobStatus(activeJobId, "REPAIRING") }; showDebugMenu = false }) { Text("7. Đang sửa (REPAIRING)") }
                Button(onClick = { coroutineScope.launch { jobRepo.updateJobPrice(activeJobId, 150000.0, "Thay thế linh kiện") }; showDebugMenu = false }) { Text("8. Phát sinh chi phí") }
                Button(onClick = { coroutineScope.launch { jobRepo.updateJobStatus(activeJobId, "JOB_COMPLETED") }; showDebugMenu = false }) { Text("9. Sửa xong / Chờ thanh toán") }
                Button(onClick = { coroutineScope.launch { jobRepo.updateJobStatus(activeJobId, "COMPLETED") }; showDebugMenu = false }) { Text("10. Thanh toán xong") }
                
                Spacer(modifier = Modifier.height(16.dp))
                Text("Worker Application (Mock Admin)", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                Button(onClick = { 
                    val state = com.fixmatch.mobile.di.ServiceLocator.accountState.value
                    if(state.application != null) {
                        com.fixmatch.mobile.di.ServiceLocator.accountState.value = state.copy(application = state.application.copy(status = com.fixmatch.mobile.domain.model.WorkerApplicationStatus.APPROVED))
                    }
                    showDebugMenu = false 
                }) { Text("Duyệt hồ sơ (Approve)") }
                Button(onClick = { 
                    val state = com.fixmatch.mobile.di.ServiceLocator.accountState.value
                    if(state.application != null) {
                        com.fixmatch.mobile.di.ServiceLocator.accountState.value = state.copy(application = state.application.copy(status = com.fixmatch.mobile.domain.model.WorkerApplicationStatus.REJECTED, rejectionReason = "Ảnh mờ, vui lòng chụp lại CCCD."))
                    }
                    showDebugMenu = false 
                }) { Text("Từ chối hồ sơ (Reject)") }
                
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }

    if (showLocationSheet) {
        ModalBottomSheet(
            onDismissRequest = { 
                if (savedLocation != null) showLocationSheet = false 
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Cập nhật địa chỉ của bạn",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Để FixMatch tìm thợ gần bạn nhất",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                
                Spacer(modifier = Modifier.height(24.dp))
                
                // Mock Map UI (Just a placeholder box for now)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color(0xFFE0E0E0))
                        .clickable {
                            coroutineScope.launch {
                                settingsManager.setLocation("Phường Bến Nghé, Quận 1, TP. HCM")
                                showLocationSheet = false
                            }
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = "Pin",
                            tint = Color.Red,
                            modifier = Modifier.size(48.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Bấm vào đây để ghim vị trí", fontWeight = FontWeight.Medium)
                    }
                }
                
                Spacer(modifier = Modifier.height(24.dp))
                
                Button(
                    onClick = {
                        coroutineScope.launch {
                            settingsManager.setLocation("Phường Bến Nghé, Quận 1, TP. HCM")
                            showLocationSheet = false
                        }
                    },
                    modifier = Modifier.fillMaxWidth().height(50.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Xác nhận vị trí", fontSize = MaterialTheme.typography.titleMedium.fontSize)
                }
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}

@Composable
fun CompactCategoryItem(name: String, icon: androidx.compose.ui.graphics.vector.ImageVector, onClick: () -> Unit = {}) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable { onClick() }
    ) {
        Box(
            modifier = Modifier
                .size(56.dp)
                .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(imageVector = icon, contentDescription = name, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(28.dp))
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = name, style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Medium)
    }
}

@Composable
fun CompactWorkerCard(
    name: String,
    title: String,
    rating: String,
    distance: String,
    jobsCompleted: String,
    imageUrl: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clickable { onClick() }
            .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(12.dp))
            .border(1.dp, MaterialTheme.colorScheme.outlineVariant, RoundedCornerShape(12.dp))
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = imageUrl,
            contentDescription = "Avatar",
            modifier = Modifier.size(56.dp).clip(CircleShape),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(text = name, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Text(text = title, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Spacer(modifier = Modifier.height(4.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Star, contentDescription = "Rating", tint = Color(0xFFFFC107), modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = rating, style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold)
                Text(text = " · 📍 $distance · $jobsCompleted jobs", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
    }
}

@Composable
fun SectionHeader(title: String, actionText: String, onActionClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )
        Text(
            text = actionText,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.clickable { onActionClick() }
        )
    }
}