package com.example.cairometroapp.presentation.ui.route

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.ConfirmationNumber
import androidx.compose.material.icons.filled.DirectionsSubway
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.cairometroapp.domain.model.Station
import com.example.cairometroapp.presentation.ui.theme.FigmaBg
import com.example.cairometroapp.presentation.ui.theme.FigmaBorder
import com.example.cairometroapp.presentation.ui.theme.FigmaPrimary
import com.example.cairometroapp.presentation.ui.theme.FigmaTextBody
import com.example.cairometroapp.presentation.ui.theme.FigmaTextCaption
import com.example.cairometroapp.presentation.ui.theme.FigmaTextTitle
import com.example.cairometroapp.presentation.ui.theme.MetroLine1
import com.example.cairometroapp.presentation.ui.theme.MetroLine2
import com.example.cairometroapp.presentation.ui.theme.MetroLine3

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RouteResultScreen(
    uiState: RouteUiState,
    onBackClick: () -> Unit
) {
    Scaffold(
        containerColor = FigmaBg,
        topBar = {
            TopAppBar(
                title = { Text("Trip Details", style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        }
    ) { padding ->
        if (uiState.isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = FigmaPrimary)
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                // Trip Summary Header (Dhaka Style)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .padding(24.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        SummaryInfoBox(Icons.Default.AccessTime, "${uiState.time} min", "Duration")
                        VerticalDivider(modifier = Modifier.height(40.dp), color = FigmaBorder)
                        SummaryInfoBox(Icons.Default.ConfirmationNumber, "${uiState.fare} EGP", "Fare")
                        VerticalDivider(modifier = Modifier.height(40.dp), color = FigmaBorder)
                        SummaryInfoBox(Icons.Default.DirectionsSubway, "${uiState.stations.size}", "Stations")
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Route Path
                Card(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp)
                        .clip(RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(24.dp)
                    ) {
                        itemsIndexed(uiState.stations) { index, station ->
                            StationRouteItemModern(
                                station = station,
                                isFirst = index == 0,
                                isLast = index == uiState.stations.size - 1
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SummaryInfoBox(icon: ImageVector, value: String, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(icon, contentDescription = null, tint = FigmaPrimary, modifier = Modifier.size(20.dp))
        Text(text = value, style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, color = FigmaTextTitle))
        Text(text = label, style = MaterialTheme.typography.labelSmall, color = FigmaTextCaption)
    }
}

@Composable
fun StationRouteItemModern(
    station: Station,
    isFirst: Boolean,
    isLast: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
    ) {
        // Timeline
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.width(32.dp)
        ) {
            Box(
                modifier = Modifier
                    .width(3.dp)
                    .weight(1f)
                    .background(if (isFirst) Color.Transparent else FigmaPrimary)
            )
            Box(
                modifier = Modifier
                    .size(if (isFirst || isLast) 20.dp else 12.dp)
                    .background(if (isFirst || isLast) FigmaPrimary else Color.White, CircleShape)
                    .then(
                        if (!(isFirst || isLast)) Modifier.background(Color.White, CircleShape).padding(2.dp).background(
                            FigmaPrimary, CircleShape)
                        else Modifier
                    )
            )
            Box(
                modifier = Modifier
                    .width(3.dp)
                    .weight(1f)
                    .background(if (isLast) Color.Transparent else FigmaPrimary)
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        // Content
        Column(
            modifier = Modifier
                .padding(bottom = 24.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = station.name,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = if (isFirst || isLast) FontWeight.Bold else FontWeight.Medium,
                    color = if (isFirst || isLast) FigmaTextTitle else FigmaTextBody
                )
            )
            Text(
                text = station.line.label,
                style = MaterialTheme.typography.labelMedium,
                color = when(station.line.label) {
                    "Line 1" -> MetroLine1
                    "Line 2" -> MetroLine2
                    else -> MetroLine3
                }
            )
        }
    }
}
