package com.fixmatch.mobile.presentation.splash

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import android.media.MediaPlayer

import android.widget.VideoView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.fixmatch.mobile.R
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

        var isVideoReady by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
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
                    setOnInfoListener { _, what, _ ->
                        if (what == MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START) {
                            isVideoReady = true
                        }
                        true
                    }
                    setOnPreparedListener { mp -> 
                        mp.setVolume(0f, 0f) 
                        mp.isLooping = false
                    }
                    setZOrderOnTop(true)
                    start()
                }
            },
            modifier = Modifier.fillMaxSize()
        )
        
        if (!isVideoReady) {
            Box(modifier = Modifier.fillMaxSize().background(Color.White))
        }
    }

}
