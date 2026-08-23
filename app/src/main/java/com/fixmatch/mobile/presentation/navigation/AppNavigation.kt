package com.fixmatch.mobile.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.core.tween
import androidx.navigation.compose.NavHost
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.fixmatch.mobile.presentation.auth.LoginScreen
import com.fixmatch.mobile.presentation.home.HomeScreen
import com.fixmatch.mobile.presentation.onboarding.OnboardingScreen
import com.fixmatch.mobile.presentation.splash.SplashScreen
import com.fixmatch.mobile.presentation.main.MainScreen

// Import all newly generated screens
import com.fixmatch.mobile.presentation.profile.ProfileScreen
import com.fixmatch.mobile.presentation.workerprofile.WorkerProfileScreen
import com.fixmatch.mobile.presentation.requestservice.RequestServiceScreen
import com.fixmatch.mobile.presentation.findingworker.FindingWorkerScreen
import com.fixmatch.mobile.presentation.workerfound.WorkerFoundScreen
import com.fixmatch.mobile.presentation.trackjob.TrackJobScreen
import com.fixmatch.mobile.presentation.payment.PaymentScreen
import com.fixmatch.mobile.presentation.payment.PaymentResultScreen
import com.fixmatch.mobile.presentation.review.ReviewScreen
import com.fixmatch.mobile.presentation.subscription.SubscriptionScreen
import com.fixmatch.mobile.presentation.messages.MessagesScreen
import com.fixmatch.mobile.presentation.chat.ChatScreen
import com.fixmatch.mobile.presentation.myearnings.MyEarningsScreen
import com.fixmatch.mobile.presentation.workersettings.WorkerSettingsScreen
import com.fixmatch.mobile.presentation.newjobrequest.NewJobRequestScreen
import com.fixmatch.mobile.presentation.workerhome.WorkerHomeScreen

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Onboarding : Screen("onboarding")
    object Login : Screen("login")
    object Home : Screen("home")
    object Profile : Screen("profile")
    object WorkerProfile : Screen("worker_profile")
    object RequestService : Screen("request_service")
    object FindingWorker : Screen("finding_worker")
    object WorkerFound : Screen("worker_found")
    object TrackJob : Screen("track_job")
    object Payment : Screen("payment")
    object PaymentResult : Screen("payment_result")
    object Review : Screen("review")
    object Messages : Screen("messages")
    object Chat : Screen("chat")
    object MyEarnings : Screen("my_earnings")
    object WorkerSettings : Screen("worker_settings")
    object NewJobRequest : Screen("new_job_request")
    object WorkerHome : Screen("worker_home")
    object Subscription : Screen("subscription")
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.Splash.route,
        enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left, animationSpec = tween(300)) },
        exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Left, animationSpec = tween(300)) },
        popEnterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Right, animationSpec = tween(300)) },
        popExitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right, animationSpec = tween(300)) }) {
        composable(Screen.Splash.route, exitTransition = { fadeOut(animationSpec = tween(500)) }) {
            SplashScreen(
                onSplashFinished = {
                    navController.navigate(Screen.Onboarding.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                }
            )
        }
        composable(Screen.Onboarding.route, enterTransition = { fadeIn(animationSpec = tween(500)) }, exitTransition = { fadeOut(animationSpec = tween(500)) }) {
            OnboardingScreen(
                onFinishOnboarding = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Onboarding.route) { inclusive = true }
                    }
                }
            )
        }
        composable(Screen.Login.route) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
            )
        }
        composable(Screen.Home.route) {
            MainScreen(rootNavController = navController)
        }
        composable(Screen.WorkerProfile.route) { 
            WorkerProfileScreen(
                onNavigateBack = { navController.popBackStack() },
                onNavigateToRequestService = { navController.navigate(Screen.RequestService.route) },
                onNavigateToMessages = { navController.navigate(Screen.Messages.route) }
            ) 
        }
        composable(Screen.RequestService.route) { 
            RequestServiceScreen(
                onNavigateBack = { navController.popBackStack() },
                onSubmitRequest = { navController.navigate(Screen.FindingWorker.route) }
            ) 
        }
        composable(Screen.FindingWorker.route, enterTransition = { fadeIn(animationSpec = tween(400)) }, exitTransition = { fadeOut(animationSpec = tween(400)) }) { 
            FindingWorkerScreen(
                onCancel = { navController.popBackStack() },
                onWorkerFound = { navController.navigate(Screen.WorkerFound.route) {
                    popUpTo(Screen.FindingWorker.route) { inclusive = true }
                } }
            ) 
        }
        composable(Screen.WorkerFound.route, enterTransition = { fadeIn(animationSpec = tween(400)) }, exitTransition = { fadeOut(animationSpec = tween(400)) }) { 
            WorkerFoundScreen(
                onNavigateBack = { navController.popBackStack() },
                onContinue = { navController.navigate(Screen.TrackJob.route) }
            ) 
        }
        composable(Screen.TrackJob.route) { 
            TrackJobScreen(
                onNavigateBack = { navController.popBackStack() },
                onNavigateToReview = { navController.navigate(Screen.Payment.route) {
                    popUpTo(Screen.TrackJob.route) { inclusive = true }
                } }
            ) 
        }
        composable(Screen.Payment.route) { 
            PaymentScreen(
                onNavigateBack = { navController.popBackStack() },
                onPaymentComplete = { navController.navigate(Screen.PaymentResult.route) {
                    popUpTo(Screen.Payment.route) { inclusive = true }
                } }
            ) 
        }
        composable(Screen.PaymentResult.route) { 
            PaymentResultScreen(
                onNavigateToReview = { navController.navigate(Screen.Review.route) {
                    popUpTo(Screen.PaymentResult.route) { inclusive = true }
                } }
            ) 
        }
        composable(Screen.Review.route) { 
            ReviewScreen(
                onNavigateHome = { navController.navigate(Screen.Home.route) {
                    popUpTo(Screen.Home.route) { inclusive = true }
                } }
            ) 
        }
        composable(Screen.Messages.route) { 
            MessagesScreen(
                onNavigateToChat = { navController.navigate(Screen.Chat.route) }
            ) 
        }
        composable(Screen.Chat.route) { 
            ChatScreen(onNavigateBack = { navController.popBackStack() }) 
        }
        composable(Screen.MyEarnings.route) { MyEarningsScreen() }
        composable(Screen.WorkerSettings.route) { 
            WorkerSettingsScreen(
                onNavigateBack = { navController.popBackStack() },
                onNavigateToSubscription = { navController.navigate("worker_subscription") }
            ) 
        }
        composable(Screen.NewJobRequest.route) { 
            NewJobRequestScreen(
                onDecline = { navController.popBackStack() },
                onAccept = { navController.popBackStack() } // Back to WorkerHome for now
            ) 
        }
        composable(Screen.WorkerHome.route) { 
            MainScreen(rootNavController = navController, isWorkerMode = true) 
        }
        composable(Screen.Subscription.route) { 
            SubscriptionScreen(
                isWorker = false,
                onNavigateBack = { navController.popBackStack() }
            ) 
        }
        composable("worker_subscription") { 
            SubscriptionScreen(
                isWorker = true,
                onNavigateBack = { navController.popBackStack() }
            ) 
        }
    }
}
