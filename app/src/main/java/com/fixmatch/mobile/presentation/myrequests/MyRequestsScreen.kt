package com.fixmatch.mobile.presentation.myrequests

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.fixmatch.mobile.presentation.components.RequestCard
import com.fixmatch.mobile.presentation.components.EmptyState
import com.fixmatch.mobile.presentation.common.UiState
import com.fixmatch.mobile.domain.model.JobStatus
import com.fixmatch.mobile.di.ServiceLocator

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyRequestsScreen(
    onNavigateBack: () -> Unit = {},
    onNavigateToTrackJob: () -> Unit = {},
    viewModel: MyRequestsViewModel = viewModel(factory = object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return MyRequestsViewModel() as T
        }
    })
) {
    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("Đang hoạt động", "Lịch sử")
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        TopAppBar(
            title = {
                Text(
                    "Yêu cầu của tôi",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.surface)
        )

        TabRow(
            selectedTabIndex = selectedTab,
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.primary
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTab == index,
                    onClick = { selectedTab = index },
                    text = { Text(title, fontWeight = FontWeight.Medium) }
                )
            }
        }

        when (uiState) {
            is UiState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            is UiState.Success -> {
                val allJobs = (uiState as UiState.Success).data
                val activeStatuses = listOf(
                    JobStatus.CREATED, JobStatus.SEARCHING_WORKER, JobStatus.WORKER_FOUND,
                    JobStatus.WORKER_ACCEPTED, JobStatus.WORKER_ON_THE_WAY, JobStatus.WORKER_ARRIVED,
                    JobStatus.JOB_IN_PROGRESS, JobStatus.JOB_COMPLETED, JobStatus.PAYMENT_PENDING
                )
                
                val filteredJobs = if (selectedTab == 0) {
                    allJobs.filter { it.status in activeStatuses }
                } else {
                    allJobs.filter { it.status !in activeStatuses }
                }

                if (filteredJobs.isEmpty()) {
                    Box(modifier = Modifier.fillMaxSize().padding(16.dp), contentAlignment = Alignment.Center) {
                        EmptyState(
                            title = if (selectedTab == 0) "Chưa có yêu cầu nào" else "Lịch sử trống",
                            description = if (selectedTab == 0) "Bạn hiện không có yêu cầu dịch vụ nào đang được xử lý." else "Bạn chưa có yêu cầu dịch vụ nào đã hoàn thành.",
                            actionText = "Trang chủ",
                            onActionClick = {}
                        )
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize().padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(filteredJobs) { job ->
                            val statusText = when(job.status) {
                                JobStatus.CREATED -> "Đã tạo"
                                JobStatus.SEARCHING_WORKER -> "Đang tìm thợ"
                                JobStatus.WORKER_FOUND, JobStatus.WORKER_ACCEPTED -> "Thợ đã nhận việc"
                                JobStatus.WORKER_ON_THE_WAY -> "Thợ đang tới"
                                JobStatus.WORKER_ARRIVED -> "Thợ đã đến"
                                JobStatus.JOB_IN_PROGRESS -> "Đang sửa chữa"
                                JobStatus.JOB_COMPLETED, JobStatus.PAYMENT_PENDING -> "Chờ thanh toán"
                                JobStatus.COMPLETED -> "Đã thanh toán"
                                JobStatus.REVIEWED -> "Đã đánh giá"
                                JobStatus.CANCELLED_BY_CUSTOMER, JobStatus.CANCELLED_BY_WORKER -> "Đã huỷ"
                                JobStatus.NO_WORKER_FOUND -> "Không tìm thấy thợ"
                            }
                            
                            val statusColor = if (job.status in listOf(JobStatus.COMPLETED, JobStatus.REVIEWED)) {
                                MaterialTheme.colorScheme.surfaceVariant
                            } else if (job.status in listOf(JobStatus.CANCELLED_BY_CUSTOMER, JobStatus.CANCELLED_BY_WORKER, JobStatus.NO_WORKER_FOUND)) {
                                MaterialTheme.colorScheme.errorContainer
                            } else {
                                MaterialTheme.colorScheme.primaryContainer
                            }
                            
                            val textColor = if (job.status in listOf(JobStatus.COMPLETED, JobStatus.REVIEWED)) {
                                MaterialTheme.colorScheme.onSurfaceVariant
                            } else if (job.status in listOf(JobStatus.CANCELLED_BY_CUSTOMER, JobStatus.CANCELLED_BY_WORKER, JobStatus.NO_WORKER_FOUND)) {
                                MaterialTheme.colorScheme.onErrorContainer
                            } else {
                                MaterialTheme.colorScheme.onPrimaryContainer
                            }
                            
                            val actionText = if (selectedTab == 0) "Theo dõi" else "Xem lại"

                            RequestCard(
                                title = job.title,
                                description = job.description,
                                status = statusText,
                                statusColor = statusColor,
                                statusTextColor = textColor,
                                date = job.scheduledTime,
                                bottomText = actionText,
                                bottomColor = MaterialTheme.colorScheme.primary,
                                onClick = { 
                                    ServiceLocator.currentActiveJobId.value = job.id
                                    if (selectedTab == 0) {
                                        onNavigateToTrackJob()
                                    }
                                }
                            )
                        }
                    }
                }
            }
            else -> {}
        }
    }
}
