package com.findtho.mobile.presentation.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

enum class MascotState {
    WELCOME,
    SEARCHING,
    LOADING,
    WORKER_FOUND,
    SUCCESS,
    COMPLETED,
    EMPTY,
    ERROR,
    OFFLINE
}

@Composable
fun AnimatedMascot(
    state: MascotState,
    modifier: Modifier = Modifier,
    useBackground: Boolean = true
) {
    // A simple infinite floating animation to make the placeholder feel alive
    val infiniteTransition = rememberInfiniteTransition(label = "mascot_float")
    val offsetY by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 10f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = EaseInOutSine),
            repeatMode = RepeatMode.Reverse
        ),
        label = "mascot_float"
    )

    val boxModifier = if (useBackground) {
        modifier
            .clip(MaterialTheme.shapes.large)
            .background(MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f))
    } else {
        modifier
    }

    Box(
        modifier = boxModifier.offset(y = offsetY.dp),
        contentAlignment = Alignment.Center
    ) {
        // TODO: Replace this entire Column with an actual LottieAnimation or AsyncImage
        // based on the `state` parameter.
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .background(MaterialTheme.colorScheme.primary, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text("Mascot", color = MaterialTheme.colorScheme.onPrimary)
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "State:\n${state.name}",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center
            )
        }
    }
}
