package com.example.cairometroapp.presentation.ui.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.cairometroapp.presentation.ui.components.MetroButton
import com.example.cairometroapp.presentation.ui.theme.FigmaBg
import com.example.cairometroapp.presentation.ui.theme.FigmaBorder
import com.example.cairometroapp.presentation.ui.theme.FigmaPrimary
import com.example.cairometroapp.presentation.ui.theme.FigmaSecondary
import com.example.cairometroapp.presentation.ui.theme.FigmaSurface
import com.example.cairometroapp.presentation.ui.theme.FigmaTextCaption

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    uiState: HomeUiState,
    onStartStationChanged: (String) -> Unit,
    onEndStationChanged: (String) -> Unit,
    onSwapClick: () -> Unit,
    onSearchClick: () -> Unit
) {
    Scaffold(
        containerColor = FigmaBg
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            // Header Section
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(FigmaPrimary, FigmaPrimary.copy(alpha = 0.8f))
                        ),
                        shape = RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp)
                    )
                    .padding(24.dp)
            ) {
                Column {
                    Text(
                        text = "Hello Traveler,",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.White.copy(alpha = 0.8f)
                    )
                    Text(
                        text = "Where are you going?",
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    )
                }
            }

            // Search Card
            Box(modifier = Modifier.padding(horizontal = 20.dp)) {
                Card(
                    modifier = Modifier
                        .offset(y = (-60).dp)
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 12.dp),
                    colors = CardDefaults.cardColors(containerColor = FigmaSurface)
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        SearchableStationInput(
                            label = "From",
                            value = uiState.startStation,
                            stations = uiState.stations.map { it.name }.distinct(),
                            onSelected = onStartStationChanged,
                            icon = Icons.Default.TripOrigin,
                            iconTint = FigmaPrimary
                        )

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(40.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            HorizontalDivider(color = FigmaBorder)
                            Surface(
                                onClick = onSwapClick,
                                shape = CircleShape,
                                color = FigmaSurface,
                                border = BorderStroke(1.dp, FigmaBorder),
                                modifier = Modifier.size(40.dp)
                            ) {
                                Icon(
                                    Icons.Default.SwapVert,
                                    contentDescription = "Swap",
                                    tint = FigmaPrimary,
                                    modifier = Modifier.padding(8.dp)
                                )
                            }
                        }

                        SearchableStationInput(
                            label = "To",
                            value = uiState.endStation,
                            stations = uiState.stations.map { it.name }.distinct(),
                            onSelected = onEndStationChanged,
                            icon = Icons.Default.LocationOn,
                            iconTint = FigmaSecondary
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        MetroButton(
                            text = "Find Best Route",
                            onClick = onSearchClick
                        )
                    }
                }
            }

            // Recent Section
            Column(
                modifier = Modifier
                    .padding(horizontal = 24.dp)
                    .offset(y = (-40).dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Recent Searches",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    TextButton(onClick = { }) {
                        Text("See All", color = FigmaPrimary)
                    }
                }

                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(bottom = 24.dp)
                ) {
                    items(uiState.stations.distinctBy { it.name }.take(10)) { station ->
                        StationItemDhaka(station.name, station.line.label)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchableStationInput(
    label: String,
    value: String,
    stations: List<String>,
    onSelected: (String) -> Unit,
    icon: ImageVector,
    iconTint: Color
) {
    var expanded by remember { mutableStateOf(false) }
    var query by remember { mutableStateOf("") }

    val filteredStations by remember(query, stations) {
        derivedStateOf {
            if (query.isEmpty()) stations.take(5)
            else stations.filter { it.contains(query, ignoreCase = true) }.take(5)
        }
    }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = it }
    ) {
        OutlinedTextField(
            value = if (expanded) query else value,
            onValueChange = { query = it },
            label = { Text(label, style = MaterialTheme.typography.labelMedium) },
            leadingIcon = { Icon(icon, contentDescription = null, tint = iconTint) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier
                .menuAnchor(MenuAnchorType.PrimaryEditable, true)
                .fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = FigmaPrimary,
                unfocusedBorderColor = FigmaBorder,
                focusedContainerColor = FigmaBg,
                unfocusedContainerColor = FigmaBg
            ),
            singleLine = true,
            placeholder = { Text("Select station") }
        )

        if (expanded) {
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.background(FigmaSurface)
            ) {
                filteredStations.forEach { name ->
                    DropdownMenuItem(
                        text = { Text(name, style = MaterialTheme.typography.bodyMedium) },
                        onClick = {
                            onSelected(name)
                            query = ""
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun StationItemDhaka(name: String, lineLabel: String) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .clickable { },
        color = FigmaSurface,
        border = BorderStroke(1.dp, FigmaBorder)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                modifier = Modifier.size(44.dp),
                shape = RoundedCornerShape(12.dp),
                color = FigmaBg
            ) {
                Icon(
                    Icons.Default.Train,
                    contentDescription = null,
                    tint = FigmaPrimary,
                    modifier = Modifier.padding(10.dp)
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = name,
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold)
                )
                Text(
                    text = lineLabel,
                    style = MaterialTheme.typography.labelSmall,
                    color = FigmaTextCaption
                )
            }
            Icon(
                Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = null,
                tint = FigmaTextCaption
            )
        }
    }
}
