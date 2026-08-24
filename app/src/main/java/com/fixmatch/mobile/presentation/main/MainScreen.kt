package com.fixmatch.mobile.presentation.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.navigation.NavHostController
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import kotlinx.coroutines.launch
import com.fixmatch.mobile.presentation.home.HomeScreen
import com.fixmatch.mobile.presentation.workerhome.WorkerHomeScreen
import com.fixmatch.mobile.presentation.profile.ProfileScreen
import com.fixmatch.mobile.presentation.myrequests.MyRequestsScreen
import com.fixmatch.mobile.presentation.messages.MessagesScreen
import com.fixmatch.mobile.presentation.workersettings.WorkerSettingsScreen
import com.fixmatch.mobile.presentation.navigation.Screen
import com.fixmatch.mobile.presentation.components.UserBottomNavBar
import com.fixmatch.mobile.presentation.components.WorkerBottomNavBar

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainScreen(
    rootNavController: NavHostController,
    isWorkerMode: Boolean = false
) {
    val userRoutes = listOf("home", "requests", "messages", "profile")
    val workerRoutes = listOf("worker_home", "jobs", "messages", "worker_settings")
    val currentRoutes = if (isWorkerMode) workerRoutes else userRoutes
    
    val pagerState = rememberPagerState(pageCount = { currentRoutes.size })
    val coroutineScope = rememberCoroutineScope()
    
    val currentRoute = currentRoutes.getOrNull(pagerState.currentPage) ?: currentRoutes[0]

    Scaffold(
        bottomBar = {
            if (isWorkerMode) {
                WorkerBottomNavBar(
                    currentRoute = currentRoute,
                    onNavigate = { route ->
                        val index = workerRoutes.indexOf(route)
                        if (index != -1) {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(index)
                            }
                        }
                    }
                )
            } else {
                UserBottomNavBar(
                    currentRoute = currentRoute,
                    onNavigate = { route ->
                        val index = userRoutes.indexOf(route)
                        if (index != -1) {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(index)
                            }
                        }
                    }
                )
            }
        }
    ) { paddingValues ->
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.padding(paddingValues)
        ) { page ->
            if (isWorkerMode) {
                when (workerRoutes[page]) {
                    "worker_home" -> WorkerHomeScreen(
                        onNavigateToNewRequest = { rootNavController.navigate(Screen.NewJobRequest.route) },
                        onNavigateToActiveJob = { rootNavController.navigate("worker_active_job") }
                    )
                    "jobs" -> MyRequestsScreen(
                        onNavigateToTrackJob = { rootNavController.navigate(Screen.TrackJob.route) }
                    )
                    "messages" -> MessagesScreen(onNavigateToChat = { rootNavController.navigate(Screen.Chat.route) })
                    "worker_settings" -> WorkerSettingsScreen(
                        onNavigateBack = { coroutineScope.launch { pagerState.animateScrollToPage(0) } },
                        onNavigateToSubscription = { rootNavController.navigate("worker_subscription") },
                        onSwitchToCustomer = { 
                            com.fixmatch.mobile.di.ServiceLocator.accountState.value = com.fixmatch.mobile.di.ServiceLocator.accountState.value.copy(currentMode = com.fixmatch.mobile.domain.model.AppMode.CUSTOMER)
                            rootNavController.navigate(Screen.Home.route) { popUpTo(Screen.WorkerHome.route) { inclusive = true } } 
                        }
                    )
                }
            } else {
                when (userRoutes[page]) {
                    "home" -> HomeScreen(
                        onNavigateToWorkerProfile = { rootNavController.navigate(Screen.WorkerProfile.route) },
                        onNavigateToNewRequest = { rootNavController.navigate(Screen.RequestService.route) },
                        onNavigateToFindingWorker = { rootNavController.navigate(Screen.RequestService.route) } // Navigate to RequestService to start Grab flow
                    )
                    "requests" -> MyRequestsScreen(
                        onNavigateToTrackJob = { rootNavController.navigate(Screen.TrackJob.route) }
                    )
                    "messages" -> MessagesScreen(onNavigateToChat = { rootNavController.navigate(Screen.Chat.route) })
                    "profile" -> ProfileScreen(
                        onNavigateToSubscription = { rootNavController.navigate("subscription") },
                        onNavigateToBecomeWorker = { rootNavController.navigate(Screen.BecomeWorker.route) },
                        onNavigateToApplicationStatus = { rootNavController.navigate(Screen.ApplicationStatus.route) },
                        onSwitchMode = { 
                            com.fixmatch.mobile.di.ServiceLocator.accountState.value = com.fixmatch.mobile.di.ServiceLocator.accountState.value.copy(currentMode = com.fixmatch.mobile.domain.model.AppMode.WORKER)
                            rootNavController.navigate(Screen.WorkerHome.route) { popUpTo(Screen.Home.route) { inclusive = true } } 
                        }
                    )
                }
            }
        }
    }
}
