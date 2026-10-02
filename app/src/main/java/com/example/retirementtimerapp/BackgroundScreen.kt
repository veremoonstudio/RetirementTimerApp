package com.example.retirementtimerapp

import androidx.compose.foundation.Image
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
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.retirementtimerapp.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BackgroundScreen(
    viewModel: WorkTimeViewModel = viewModel(),
    onBack: () -> Unit,
    onNavigateToPremium: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()
    val backgrounds = listOf("None", "Palm Beach", "Mountain Road", "Golf Course", "Lake")
    val unlockedBackgrounds = remember(BuildConfig.IS_PREMIUM) {
        if (BuildConfig.IS_PREMIUM) backgrounds.toSet() else setOf("None")
    }

    MarbleBackground(selectedBackground = uiState.selectedBackground) {
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
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back",
                                tint = RetirementColors.Gold
                            )
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
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(24.dp))

                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    items(backgrounds) { bg ->
                        val isLocked = bg !in unlockedBackgrounds
                        BackgroundCard(
                            name = bg,
                            isSelected = bg == uiState.selectedBackground,
                            isLocked = isLocked,
                            onClick = {
                                if (isLocked) {
                                    onNavigateToPremium()
                                } else {
                                    viewModel.updateSelectedBackground(bg)
                                }
                            }
                        )
                    }
                }

                if (!BuildConfig.IS_PREMIUM) {
                    Text(
                        text = "Tap to unlock new backgrounds!",
                        style = TextStyle(
                            fontFamily = CormorantGaramond,
                            fontStyle = FontStyle.Italic,
                            fontSize = 16.sp,
                            color = RetirementColors.Gold.copy(alpha = 0.8f)
                        ),
                        modifier = Modifier
                            .clickable { onNavigateToPremium() }
                            .padding(top = 16.dp, bottom = 85.dp)
                    )
                } else {
                    Spacer(modifier = Modifier.height(85.dp))
                }
            }
        }
    }
}

@Composable
fun BackgroundCard(
    name: String,
    isSelected: Boolean,
    isLocked: Boolean,
    onClick: () -> Unit
) {
    val context = LocalContext.current
    val drawableName = remember(name) {
        when (name) {
            "Palm Beach" -> "bg_palm_beach"
            "Mountain Road" -> "bg_mountain_road"
            "Golf Course" -> "bg_golf_course"
            "Lake" -> "bg_lake"
            else -> ""
        }
    }
    val imageResId = remember(drawableName) {
        if (drawableName.isNotEmpty()) {
            context.resources.getIdentifier(drawableName, "drawable", context.packageName)
        } else 0
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .aspectRatio(0.6f)
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
                if (imageResId != 0) {
                    Image(
                        painter = painterResource(id = imageResId),
                        contentDescription = name,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Gray.copy(alpha = 0.2f))
                    )
                }

                if (isLocked) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Black.copy(alpha = 0.4f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.MilitaryTech,
                            contentDescription = "Locked",
                            tint = RetirementColors.Gold,
                            modifier = Modifier.size(40.dp)
                        )
                    }
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
