package com.findtho.mobile.presentation.splash

import android.net.Uri
import android.widget.VideoView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.findtho.mobile.R
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    onSplashFinished: () -> Unit
) {
    val context = LocalContext.current
    
    // We will automatically navigate after 4 seconds as a fallback, 
    // or when the video finishes (whichever happens).
    LaunchedEffect(Unit) {
        delay(4000)
        onSplashFinished()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        AndroidView(
            factory = { ctx ->
                VideoView(ctx).apply {
                    val uri = Uri.parse("android.resource://${ctx.packageName}/${R.raw.worker}")
                    setVideoURI(uri)
                    setOnCompletionListener {
                        onSplashFinished()
                    }
                    // Optional: remove audio if it's just a mascot animation
                    setOnPreparedListener { mp -> 
                        mp.setVolume(0f, 0f) 
                        mp.isLooping = false
                    }
                    start()
                }
            },
            modifier = Modifier.fillMaxSize()
        )
    }
}
