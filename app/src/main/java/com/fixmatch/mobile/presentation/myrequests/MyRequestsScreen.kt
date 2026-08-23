package com.fixmatch.mobile.presentation.myrequests

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.fixmatch.mobile.presentation.components.RequestCard
import com.fixmatch.mobile.presentation.components.EmptyState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.fixmatch.mobile.data.repository.fake.FakeJobRepository
import com.fixmatch.mobile.presentation.common.UiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyRequestsScreen(
    onNavigateBack: () -> Unit = {},
    viewModel: MyRequestsViewModel = viewModel(factory = object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MyRequestsViewModel(FakeJobRepository()) as T
        }
    })
) {
    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("Active", "Past", "Cancelled")
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        TopAppBar(
            title = {
                Text(
                    "My Requests",
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

        if (selectedTab == 0) {
            when (uiState) {
                is UiState.Loading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }
                is UiState.Success -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize().padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        val jobs = (uiState as UiState.Success).data
                        items(jobs.size) { index ->
                            val job = jobs[index]
                            RequestCard(
                                title = job.title,
                                description = job.description,
                                status = job.status.name,
                                statusColor = MaterialTheme.colorScheme.primaryContainer,
                                statusTextColor = MaterialTheme.colorScheme.onPrimaryContainer,
                                date = job.scheduledTime,
                                bottomText = "View Details",
                                bottomColor = MaterialTheme.colorScheme.primary,
                                onClick = {}
                            )
                        }
                    }
                }
                else -> {
                    Box(modifier = Modifier.fillMaxSize().padding(16.dp), contentAlignment = Alignment.Center) {
                        EmptyState(
                            title = "No Active Requests",
                            description = "You don't have any active service requests.",
                            actionText = "Find a Worker",
                            onActionClick = {}
                        )
                    }
                }
            }
        } else if (selectedTab == 1) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    RequestCard(
                        title = "Electrical Short Circuit",
                        description = "Main breaker keeps tripping",
                        status = "Completed",
                        statusColor = MaterialTheme.colorScheme.surfaceVariant,
                        statusTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        date = "Yesterday, 09:00",
                        bottomText = "Done",
                        bottomColor = MaterialTheme.colorScheme.outline,
                        onClick = {}
                    )
                }
            }
        } else {
            Box(
                modifier = Modifier.fillMaxSize().padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                EmptyState(
                    title = "No Cancelled Requests",
                    description = "You don't have any cancelled job requests.",
                    actionText = "Find a Worker",
                    onActionClick = {}
                )
            }
        }
    }
}
