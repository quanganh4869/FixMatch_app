package com.findtho.mobile.presentation.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.findtho.mobile.presentation.components.AnimatedMascot
import com.findtho.mobile.presentation.components.AppTextButton
import com.findtho.mobile.presentation.components.AppTextField
import com.findtho.mobile.presentation.components.MascotState
import com.findtho.mobile.presentation.components.PrimaryButton

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit
) {
    var phone by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(64.dp))
        
        AnimatedMascot(
            state = MascotState.WELCOME,
            modifier = Modifier.size(150.dp)
        )
        
        Spacer(modifier = Modifier.height(32.dp))
        
        Text(
            text = "Đăng nhập",
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.align(Alignment.Start)
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        AppTextField(
            value = phone,
            onValueChange = { phone = it },
            label = "Số điện thoại",
            placeholder = "Nhập số điện thoại"
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        AppTextField(
            value = password,
            onValueChange = { password = it },
            label = "Mật khẩu",
            placeholder = "Nhập mật khẩu"
        )
        
        Spacer(modifier = Modifier.height(32.dp))
        
        PrimaryButton(
            text = "Đăng nhập",
            onClick = onLoginSuccess
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        AppTextButton(
            text = "Chưa có tài khoản? Đăng ký ngay",
            onClick = { /* TODO */ }
        )
    }
}
