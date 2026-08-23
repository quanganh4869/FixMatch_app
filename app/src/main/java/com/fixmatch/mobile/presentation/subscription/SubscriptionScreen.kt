package com.fixmatch.mobile.presentation.subscription

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubscriptionScreen(
    isWorker: Boolean = false,
    onNavigateBack: () -> Unit = {}
) {
    var selectedPlan by remember { mutableStateOf(if (isWorker) "Pro" else "Premium") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Subscription Plans", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background)
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
                    onClick = { /* Handle Checkout */ },
                    modifier = Modifier.fillMaxWidth().height(56.dp).shadow(4.dp, RoundedCornerShape(16.dp)),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Text("Subscribe to $selectedPlan", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
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
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = if (isWorker) "Boost Your Earnings" else "Unlock Premium Features",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            
            Text(
                text = if (isWorker) "Get priority matching, advanced analytics, and reduced platform fees." 
                       else "Enjoy zero service fees, priority booking, and exclusive discounts.",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            if (isWorker) {
                SubscriptionCard(
                    title = "Basic Worker",
                    price = "Free",
                    period = "",
                    features = listOf("Standard matching", "10% platform fee", "Basic analytics"),
                    isSelected = selectedPlan == "Basic",
                    onClick = { selectedPlan = "Basic" }
                )
                SubscriptionCard(
                    title = "Pro Worker",
                    price = "$14.99",
                    period = "/month",
                    features = listOf("Priority matching", "5% platform fee", "Advanced analytics & insights", "Priority support"),
                    isPopular = true,
                    isSelected = selectedPlan == "Pro",
                    onClick = { selectedPlan = "Pro" }
                )
            } else {
                SubscriptionCard(
                    title = "Free Tier",
                    price = "Free",
                    period = "",
                    features = listOf("Access to all workers", "Standard booking", "Standard support"),
                    isSelected = selectedPlan == "Free",
                    onClick = { selectedPlan = "Free" }
                )
                SubscriptionCard(
                    title = "Premium Member",
                    price = "$9.99",
                    period = "/month",
                    features = listOf("Zero service fees", "Priority booking", "Exclusive monthly discounts", "Dedicated 24/7 support"),
                    isPopular = true,
                    isSelected = selectedPlan == "Premium",
                    onClick = { selectedPlan = "Premium" }
                )
            }
            
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
fun SubscriptionCard(
    title: String,
    price: String,
    period: String,
    features: List<String>,
    isPopular: Boolean = false,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val borderColor = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)
    val backgroundColor = if (isSelected) MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.1f) else MaterialTheme.colorScheme.surface
    
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(if (isSelected) 8.dp else 2.dp, RoundedCornerShape(20.dp), spotColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f))
            .clip(RoundedCornerShape(20.dp))
            .background(backgroundColor)
            .border(if (isSelected) 2.dp else 1.dp, borderColor, RoundedCornerShape(20.dp))
            .clickable { onClick() }
    ) {
        Column(modifier = Modifier.padding(24.dp)) {
            if (isPopular) {
                Row(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(50))
                        .padding(horizontal = 12.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("MOST POPULAR", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onPrimary)
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
            
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text(title, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                if (isSelected) {
                    Box(modifier = Modifier.size(24.dp).background(MaterialTheme.colorScheme.primary, CircleShape), contentAlignment = Alignment.Center) {
                        Icon(Icons.Default.Check, contentDescription = null, tint = MaterialTheme.colorScheme.onPrimary, modifier = Modifier.size(16.dp))
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Row(verticalAlignment = Alignment.Bottom) {
                Text(price, style = MaterialTheme.typography.displaySmall, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                if (period.isNotEmpty()) {
                    Text(period, style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.padding(bottom = 6.dp))
                }
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            Divider(color = MaterialTheme.colorScheme.surfaceVariant)
            Spacer(modifier = Modifier.height(24.dp))
            
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                features.forEach { feature ->
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(modifier = Modifier.size(20.dp).background(MaterialTheme.colorScheme.secondaryContainer, CircleShape), contentAlignment = Alignment.Center) {
                            Icon(Icons.Default.Check, contentDescription = null, tint = MaterialTheme.colorScheme.secondary, modifier = Modifier.size(12.dp))
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(feature, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurface)
                    }
                }
            }
        }
    }
}
