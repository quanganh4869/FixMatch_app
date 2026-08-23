package com.fixmatch.mobile.presentation.myearnings

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import com.fixmatch.mobile.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyEarningsScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Text(
                        "FixMatch", 
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    ) 
                },
                navigationIcon = {
                    Box(modifier = Modifier.padding(start = 16.dp)) {
                        AsyncImage(
                            model = "https://i.pravatar.cc/150?img=12",
                            contentDescription = "User",
                            modifier = Modifier
                                .size(32.dp)
                                .clip(CircleShape)
                                .border(1.dp, MaterialTheme.colorScheme.outlineVariant, CircleShape),
                            contentScale = ContentScale.Crop
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { }) {
                        Icon(Icons.Default.Notifications, contentDescription = "Alerts", tint = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Spacer(modifier = Modifier.height(8.dp))
            
            // Hero Section
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(4.dp, RoundedCornerShape(12.dp), spotColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.05f))
                    .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(12.dp))
                    .clip(RoundedCornerShape(12.dp))
            ) {
                // Background decoration
                Box(modifier = Modifier.fillMaxWidth().height(200.dp)) {
                    Box(modifier = Modifier.align(Alignment.TopEnd).offset(x = 40.dp, y = (-40).dp).size(150.dp).background(MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.1f), CircleShape))
                    Box(modifier = Modifier.align(Alignment.BottomStart).offset(x = (-40).dp, y = 40.dp).size(150.dp).background(MaterialTheme.colorScheme.primary.copy(alpha = 0.05f), CircleShape))
                }
                
                Column(modifier = Modifier.fillMaxWidth().padding(top = 24.dp, start = 24.dp, end = 24.dp)) {
                    Text("TOTAL EARNINGS", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("$1,245.00", style = MaterialTheme.typography.displayMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onBackground)
                    
                    Spacer(modifier = Modifier.height(12.dp))
                    
                    Row(
                        modifier = Modifier
                            .background(Color(0xFFE8F0FE), RoundedCornerShape(50))
                            .padding(horizontal = 12.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("↗", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("+12% vs last month", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Medium, color = MaterialTheme.colorScheme.primary)
                    }
                    
                    Spacer(modifier = Modifier.height(16.dp))
                }
                
                Image(
                    painter = painterResource(id = R.drawable.worker_mascot),
                    contentDescription = "Mascot",
                    modifier = Modifier.fillMaxWidth().height(180.dp).align(Alignment.BottomEnd).offset(x = 60.dp, y = 20.dp),
                    contentScale = ContentScale.Fit
                )
            }
            
            // Summary Cards Grid
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                SummaryCard(title = "Today", amount = "$85.00", modifier = Modifier.weight(1f))
                SummaryCard(title = "This Week", amount = "$420.00", modifier = Modifier.weight(1f))
            }
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                SummaryCard(title = "This Month", amount = "$1,245.00", modifier = Modifier.weight(1f))
                SummaryCard(title = "Available Balance", amount = "$310.00", modifier = Modifier.weight(1f))
            }
            
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun SummaryCard(title: String, amount: String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .shadow(4.dp, RoundedCornerShape(12.dp), spotColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.05f))
            .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(12.dp))
            .padding(16.dp)
    ) {
        Text(title, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Spacer(modifier = Modifier.height(8.dp))
        Text(amount, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onBackground)
    }
}
