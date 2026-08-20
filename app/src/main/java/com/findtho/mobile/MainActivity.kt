package com.findtho.mobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

import com.findtho.mobile.core.theme.FindThoTheme
import com.findtho.mobile.presentation.auth.LoginScreen

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.findtho.mobile.presentation.customer.CustomerMainScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FindThoTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    var isLoggedIn by remember { mutableStateOf(false) }
                    var isWorkerMode by remember { mutableStateOf(false) }

                    if (!isLoggedIn) {
                        LoginScreen(
                            onLoginSuccess = { isWorker ->
                                isLoggedIn = true
                                isWorkerMode = isWorker
                            }
                        )
                    } else {
                        if (isWorkerMode) {
                            com.findtho.mobile.presentation.worker.WorkerMainScreen()
                        } else {
                            CustomerMainScreen()
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}
