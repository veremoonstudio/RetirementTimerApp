package com.example.retirementtimerapp

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MilitaryTech
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.retirementtimerapp.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BackgroundScreen(onBack: () -> Unit) {
    val backgrounds = listOf("None", "Palm Beach", "Mountain Road", "Golf Course", "Lake")
    var selectedBg by remember { mutableStateOf("None") }

    MarbleBackground {
        Scaffold(
            containerColor = Color.Transparent,
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            text = "BACKGROUNDS",
                            style = TextStyle(
                                fontFamily = PlayfairDisplay,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 20.sp,
                                letterSpacing = 2.sp,
                                color = RetirementColors.Gold
                            )
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = onBack) {
                            Icon(
                                Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back",
                                tint = RetirementColors.Gold
                            )
                        }
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.Transparent)
                )
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(24.dp))
                
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalArrangement = Arrangement.spacedBy(20.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    items(backgrounds) { bg ->
                        BackgroundCard(
                            name = bg,
                            isSelected = bg == selectedBg,
                            isLocked = bg != "None",
                            onClick = { if (bg == "None") selectedBg = bg }
                        )
                    }
                }

                Text(
                    text = "Tap to unlock new backgrounds!",
                    style = TextStyle(
                        fontFamily = CormorantGaramond,
                        fontStyle = FontStyle.Italic,
                        fontSize = 16.sp,
                        color = RetirementColors.Gold.copy(alpha = 0.8f)
                    ),
                    modifier = Modifier.padding(vertical = 32.dp)
                )
            }
        }
    }
}

@Composable
fun BackgroundCard(name: String, isSelected: Boolean, isLocked: Boolean, onClick: () -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .aspectRatio(0.7f)
                .clip(RoundedCornerShape(12.dp))
                .background(Color.White.copy(alpha = 0.05f))
                .border(
                    width = if (isSelected) 2.dp else 1.dp,
                    color = if (isSelected) RetirementColors.Gold else RetirementColors.Gold.copy(alpha = 0.2f),
                    shape = RoundedCornerShape(12.dp)
                )
                .clickable { onClick() },
            contentAlignment = Alignment.Center
        ) {
            if (name == "None") {
                Text(
                    text = "None",
                    style = TextStyle(
                        fontFamily = PlayfairDisplay,
                        fontSize = 18.sp,
                        color = RetirementColors.Cream
                    )
                )
            } else {
                // Placeholder for real image
                Box(modifier = Modifier.fillMaxSize().background(Color.Gray.copy(alpha = 0.2f)))
                if (isLocked) {
                    Icon(
                        imageVector = Icons.Default.MilitaryTech,
                        contentDescription = "Locked",
                        tint = RetirementColors.Gold,
                        modifier = Modifier.size(40.dp)
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = name,
            style = TextStyle(
                fontFamily = CormorantGaramond,
                fontSize = 14.sp,
                color = if (isSelected) RetirementColors.Gold else RetirementColors.Cream.copy(alpha = 0.7f)
            )
        )
    }
}
