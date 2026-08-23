package com.fixmatch.mobile.presentation.newjobrequest

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.fixmatch.mobile.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewJobRequestScreen(
    onDecline: () -> Unit = {},
    onAccept: () -> Unit = {}
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Text(
                        "FixMatch", 
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    ) 
                },
                actions = {
                    IconButton(onClick = { }) {
                        Icon(Icons.Default.Notifications, contentDescription = "Alerts", tint = MaterialTheme.colorScheme.primary)
                    }
                    Box(modifier = Modifier.padding(end = 16.dp, start = 8.dp)) {
                        AsyncImage(
                            model = "https://i.pravatar.cc/150?img=12",
                            contentDescription = "User",
                            modifier = Modifier
                                .size(32.dp)
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        },
        
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Spacer(modifier = Modifier.height(8.dp))
            
            // High-Impact Notification Card
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(4.dp, RoundedCornerShape(16.dp), spotColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.05f))
                    .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(16.dp))
            ) {
                Box(modifier = Modifier.fillMaxWidth().height(8.dp).background(MaterialTheme.colorScheme.secondaryContainer))
                
                Column(modifier = Modifier.padding(24.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Top
                    ) {
                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                val infiniteTransition = rememberInfiniteTransition(label = "pulse")
                                val scale by infiniteTransition.animateFloat(
                                    initialValue = 0.8f,
                                    targetValue = 1.5f,
                                    animationSpec = infiniteRepeatable(
                                        animation = tween(2000),
                                        repeatMode = RepeatMode.Restart
                                    ),
                                    label = "pulseScale"
                                )
                                val alpha by infiniteTransition.animateFloat(
                                    initialValue = 0.5f,
                                    targetValue = 0f,
                                    animationSpec = infiniteRepeatable(
                                        animation = tween(2000),
                                        repeatMode = RepeatMode.Restart
                                    ),
                                    label = "pulseAlpha"
                                )
                                
                                Box(
                                    modifier = Modifier.size(12.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(10.dp)
                                            .clip(CircleShape)
                                            .background(MaterialTheme.colorScheme.primaryContainer)
                                    )
                                    Box(
                                        modifier = Modifier
                                            .size(12.dp)
                                            .clip(CircleShape)
                                            .background(MaterialTheme.colorScheme.primaryContainer.copy(alpha = alpha))
                                    ) // Simplistic pulse
                                }
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("NEW JOB NEARBY!", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primaryContainer)
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text("Leaky Pipe Repair", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                        }
                        
                        Box(
                            modifier = Modifier
                                .size(64.dp)
                                .background(Color(0xFFE8F0FE), RoundedCornerShape(12.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            androidx.compose.foundation.Image(
                                painter = androidx.compose.ui.res.painterResource(id = com.fixmatch.mobile.R.drawable.worker_mascot),
                                contentDescription = "Mascot",
                                modifier = Modifier.fillMaxSize().padding(top = 4.dp),
                                contentScale = ContentScale.Fit
                            )
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    // Job Details Grid
                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                            InfoBox(icon = Icons.Default.Person, title = "Customer", value = "Sarah J.")
                            InfoBox(icon = Icons.Default.LocationOn, title = "Distance", value = "1.2 km away")
                        }
                        Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                            InfoBox(icon = Icons.Default.Build, title = "When", value = "Today, 2:30 PM")
                            InfoBox(icon = Icons.Default.Build, title = "Est. Earnings", value = "$80 - $120", valueColor = MaterialTheme.colorScheme.primaryContainer)
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    // Mini Map Area
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(160.dp)
                            .background(MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(12.dp))
                            .border(1.dp, MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(12.dp))
                            .clip(RoundedCornerShape(12.dp))
                    ) {
                        AsyncImage(
                            model = "https://www.gstatic.com/labs-code/stitch/stitch-placeholder-300x300.svg",
                            contentDescription = "Map",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                        
                        // Gradient Overlay
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(androidx.compose.ui.graphics.Brush.verticalGradient(
                                    colors = listOf(Color.Transparent, MaterialTheme.colorScheme.surface.copy(alpha = 0.6f))
                                ))
                        )
                        
                        // Marker
                        Box(
                            modifier = Modifier.align(Alignment.Center),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Box(
                                    modifier = Modifier.background(MaterialTheme.colorScheme.secondaryContainer, CircleShape).padding(6.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(Icons.Default.Build, contentDescription = null, tint = MaterialTheme.colorScheme.onSecondaryContainer, modifier = Modifier.size(20.dp))
                                }
                                Box(modifier = Modifier.width(4.dp).height(12.dp).background(MaterialTheme.colorScheme.secondaryContainer))
                            }
                        }
                        
                        // Distance Pill
                        Row(
                            modifier = Modifier
                                .align(Alignment.BottomStart)
                                .padding(12.dp)
                                .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.9f), RoundedCornerShape(50))
                                .border(1.dp, MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(50))
                                .padding(horizontal = 12.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(Icons.Default.Build, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(14.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("4 min drive", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Medium, color = MaterialTheme.colorScheme.onSurface)
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(24.dp))
                    
                    // Action Buttons
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        Button(
                            onClick = onAccept,
                            modifier = Modifier.fillMaxWidth().height(56.dp),
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                        ) {
                            Text("Accept Job", style = MaterialTheme.typography.titleMedium)
                        }
                        
                        OutlinedButton(
                            onClick = onDecline,
                            modifier = Modifier.fillMaxWidth().height(56.dp),
                            shape = RoundedCornerShape(8.dp),
                            border = androidx.compose.foundation.BorderStroke(1.5.dp, MaterialTheme.colorScheme.primary),
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.primary)
                        ) {
                            Text("Decline", style = MaterialTheme.typography.titleMedium)
                        }
                    }
                }
            }
            
            // Customer Notes
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(4.dp, RoundedCornerShape(12.dp), spotColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.05f))
                    .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(12.dp))
                    .border(1.dp, MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f), RoundedCornerShape(12.dp))
                    .padding(16.dp)
            ) {
                Text("Customer Notes", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "\"The pipe under the kitchen sink started leaking this morning. I've turned off the main valve, but it needs fixing ASAP. Please ring the doorbell upon arrival.\"",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
            }
            
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun InfoBox(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    value: String,
    valueColor: Color = MaterialTheme.colorScheme.onSurface
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(12.dp))
            .border(1.dp, MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(12.dp))
            .padding(12.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(icon, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.size(18.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text(title, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(value, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Medium, color = valueColor)
    }
}
