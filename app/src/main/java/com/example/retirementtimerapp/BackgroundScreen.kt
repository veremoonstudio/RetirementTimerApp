package com.example.retirementtimerapp

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BackgroundScreen(onBack: () -> Unit) {
    val backgrounds = listOf("None", "Palm Beach", "Mountain Road", "Golf Course", "Lake")
    var selectedBackground by remember { mutableStateOf("None") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Background Page", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(backgrounds) { bg ->
                    BackgroundItem(
                        name = bg,
                        isSelected = bg == selectedBackground,
                        isLocked = bg != "None",
                        onClick = { if (bg == "None") selectedBackground = bg }
                    )
                }
            }

            Text(
                text = "Tap to unlock new backgrounds!",
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(vertical = 16.dp)
            )
        }
    }
}

@Composable
fun BackgroundItem(name: String, isSelected: Boolean, isLocked: Boolean, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .aspectRatio(0.6f)
            .border(
                width = 3.dp,
                color = if (isSelected) Color(0xFF3E2723) else Color.Transparent,
                shape = MaterialTheme.shapes.medium
            )
            .background(
                color = if (name == "None") MaterialTheme.colorScheme.background else Color.LightGray,
                shape = MaterialTheme.shapes.medium
            )
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        if (name == "None") {
            Text(name, fontSize = 18.sp, fontWeight = FontWeight.Bold)
        } else {
            // Placeholder for image
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(name, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.White)
                if (isLocked) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Icon(Icons.Default.Star, contentDescription = "Locked", tint = Color.Yellow)
                }
            }
        }
    }
}
