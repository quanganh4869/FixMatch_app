package com.findtho.mobile.presentation.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.findtho.mobile.presentation.components.AnimatedMascot
import com.findtho.mobile.presentation.components.MascotState
import com.findtho.mobile.presentation.components.PrimaryButton

@Composable
fun OnboardingScreen(
    onFinishOnboarding: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.weight(1f))
        
        // Mascot representation
        AnimatedMascot(
            state = MascotState.WELCOME,
            modifier = Modifier.size(250.dp)
        )
        
        Spacer(modifier = Modifier.height(48.dp))
        
        Text(
            text = "Tìm thợ nhanh chóng,\nViệc nhà dễ dàng!",
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Text(
            text = "Kết nối trực tiếp với những người thợ lành nghề nhất xung quanh khu vực của bạn.",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )
        
        Spacer(modifier = Modifier.weight(1f))
        
        PrimaryButton(
            text = "Bắt đầu ngay",
            onClick = onFinishOnboarding
        )
        Spacer(modifier = Modifier.height(24.dp))
    }
}
