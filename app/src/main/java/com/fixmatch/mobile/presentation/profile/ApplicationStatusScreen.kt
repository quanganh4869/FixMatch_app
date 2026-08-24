package com.fixmatch.mobile.presentation.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.HourglassEmpty
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.fixmatch.mobile.di.ServiceLocator
import com.fixmatch.mobile.domain.model.WorkerApplicationStatus
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ApplicationStatusScreen(
    onNavigateBack: () -> Unit = {},
    onNavigateToResubmit: () -> Unit = {}
) {
    val accountState = ServiceLocator.accountState.collectAsState().value
    val app = accountState.application

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Trạng thái hồ sơ", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            when (app?.status) {
                WorkerApplicationStatus.SUBMITTED, WorkerApplicationStatus.UNDER_REVIEW -> {
                    Box(modifier = Modifier.size(120.dp).background(MaterialTheme.colorScheme.primaryContainer, CircleShape), contentAlignment = Alignment.Center) {
                        Icon(Icons.Default.HourglassEmpty, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(60.dp))
                    }
                    Spacer(modifier = Modifier.height(24.dp))
                    Text("Đang được xét duyệt", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Hồ sơ của bạn đang được ban quản trị xem xét. Quá trình này có thể mất từ 1-3 ngày làm việc.", textAlign = androidx.compose.ui.text.style.TextAlign.Center, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
                WorkerApplicationStatus.REJECTED -> {
                    Box(modifier = Modifier.size(120.dp).background(MaterialTheme.colorScheme.errorContainer, CircleShape), contentAlignment = Alignment.Center) {
                        Icon(Icons.Default.Close, contentDescription = null, tint = MaterialTheme.colorScheme.error, modifier = Modifier.size(60.dp))
                    }
                    Spacer(modifier = Modifier.height(24.dp))
                    Text("Hồ sơ bị từ chối", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.error)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(app.rejectionReason ?: "Thông tin chưa hợp lệ.", textAlign = androidx.compose.ui.text.style.TextAlign.Center, color = MaterialTheme.colorScheme.onErrorContainer)
                    Spacer(modifier = Modifier.height(32.dp))
                    Button(onClick = onNavigateToResubmit, modifier = Modifier.fillMaxWidth().height(56.dp), shape = RoundedCornerShape(12.dp)) {
                        Text("Cập nhật và gửi lại")
                    }
                }
                WorkerApplicationStatus.APPROVED -> {
                    Box(modifier = Modifier.size(120.dp).background(Color(0xFFE8F5E9), CircleShape), contentAlignment = Alignment.Center) {
                        Icon(Icons.Default.Check, contentDescription = null, tint = Color(0xFF4CAF50), modifier = Modifier.size(60.dp))
                    }
                    Spacer(modifier = Modifier.height(24.dp))
                    Text("Bạn đã trở thành FixMatch Worker", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold, color = Color(0xFF4CAF50))
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Chế độ Thợ sửa chữa đã được mở khóa. Hãy trở về trang cá nhân để chuyển đổi chế độ và bắt đầu nhận đơn.", textAlign = androidx.compose.ui.text.style.TextAlign.Center, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Spacer(modifier = Modifier.height(32.dp))
                    Button(onClick = onNavigateBack, modifier = Modifier.fillMaxWidth().height(56.dp), shape = RoundedCornerShape(12.dp)) {
                        Text("Quay lại trang cá nhân")
                    }
                }
                else -> {
                    Text("Không tìm thấy hồ sơ")
                }
            }
        }
    }
}
