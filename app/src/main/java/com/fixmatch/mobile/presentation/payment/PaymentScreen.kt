package com.fixmatch.mobile.presentation.payment

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Wallet
import androidx.compose.material.icons.filled.Money
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.fixmatch.mobile.di.ServiceLocator
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentScreen(
    onNavigateBack: () -> Unit = {},
    onPaymentComplete: () -> Unit = {}
) {
    var selectedPaymentMethod by remember { mutableStateOf("Tiền mặt") }
    var isProcessing by remember { mutableStateOf(false) }
    var isSuccess by remember { mutableStateOf(false) }
    var paymentError by remember { mutableStateOf<String?>(null) }
    
    val coroutineScope = rememberCoroutineScope()
    val activeJobId = ServiceLocator.currentActiveJobId.collectAsState().value
    val job by ServiceLocator.jobRepository.observeJob(activeJobId ?: "").collectAsState(initial = null)
    
    val basePrice = job?.basePrice ?: 150000.0
    val additionalCost = job?.additionalCost ?: 0.0
    val total = basePrice + additionalCost

    if (isSuccess) {
        AlertDialog(
            onDismissRequest = {},
            title = { Text("Thanh toán thành công") },
            text = { Text("Cảm ơn bạn đã sử dụng dịch vụ của FixMatch!") },
            confirmButton = {
                Button(onClick = { onPaymentComplete() }) {
                    Text("Đánh giá")
                }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Thanh Toán", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        },
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(16.dp)
                    .navigationBarsPadding()
            ) {
                Button(
                    onClick = {
                        isProcessing = true
                        paymentError = null
                        coroutineScope.launch {
                            delay(1500) // Mock API
                            if (activeJobId != null) {
                                ServiceLocator.jobRepository.updateJobStatus(activeJobId, "COMPLETED")
                                isProcessing = false
                                isSuccess = true
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .shadow(4.dp, RoundedCornerShape(16.dp)),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                    enabled = !isProcessing
                ) {
                    if (isProcessing) {
                        CircularProgressIndicator(color = MaterialTheme.colorScheme.onPrimary, modifier = Modifier.size(24.dp))
                        Spacer(modifier = Modifier.width(12.dp))
                        Text("Đang xử lý...", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    } else {
                        Text("Thanh toán ${String.format("%,.0f", total)}đ", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            if (paymentError != null) {
                Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer), modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)) {
                    Text(paymentError!!, modifier = Modifier.padding(16.dp), color = MaterialTheme.colorScheme.onErrorContainer)
                }
            }
        
            Text("Tổng quan dịch vụ", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(16.dp))
            
            Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface), elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Phí dịch vụ cơ bản", style = MaterialTheme.typography.bodyLarge)
                        Text("${String.format("%,.0f", basePrice)}đ", style = MaterialTheme.typography.bodyLarge)
                    }
                    if (additionalCost > 0) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("Phụ phí (Linh kiện...)", style = MaterialTheme.typography.bodyLarge)
                            Text("${String.format("%,.0f", additionalCost)}đ", style = MaterialTheme.typography.bodyLarge)
                        }
                    }
                    Divider(modifier = Modifier.padding(vertical = 12.dp))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Tổng cộng", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                        Text("${String.format("%,.0f", total)}đ", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(32.dp))
            Text("Phương thức thanh toán", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(16.dp))
            
            val methods = listOf("Tiền mặt", "Thẻ tín dụng", "Momo", "ZaloPay")
            methods.forEach { method ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .clickable { selectedPaymentMethod = method }
                        .border(
                            if (selectedPaymentMethod == method) BorderStroke(2.dp, MaterialTheme.colorScheme.primary) else BorderStroke(0.dp, Color.Transparent),
                            RoundedCornerShape(12.dp)
                        ),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = selectedPaymentMethod == method,
                            onClick = { selectedPaymentMethod = method }
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(method, style = MaterialTheme.typography.bodyLarge)
                    }
                }
            }
        }
    }
}
