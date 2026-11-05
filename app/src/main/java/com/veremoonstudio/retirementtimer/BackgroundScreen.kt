package com.veremoonstudio.retirementtimer

import android.graphics.BitmapFactory
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.veremoonstudio.retirementtimer.BuildConfig
import com.veremoonstudio.retirementtimer.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BackgroundScreen(
    viewModel: WorkTimeViewModel = viewModel(),
    onBack: () -> Unit,
    onNavigateToPremium: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()
    val isPremiumUnlocked by viewModel.billingManager.isPremiumUnlocked.collectAsState()
    val isPremium = BuildConfig.IS_PREMIUM || isPremiumUnlocked

    val backgrounds = listOf("None", "Palm Beach", "Mountain Road", "Golf Course", "Lake")
    val unlockedBackgrounds = remember(isPremium) {
        if (isPremium) backgrounds.toSet() else setOf("None")
    }

    MarbleBackground(selectedBackground = uiState.selectedBackground) {
        Scaffold(
            containerColor = Color.Transparent,
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            text = stringResource(R.string.backgrounds_title),
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
                    .padding(horizontal = 12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(24.dp))

                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
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

                if (!isPremium) {
                    Text(
                        text = stringResource(R.string.tap_to_unlock),
                        style = TextStyle(
                            fontFamily = PlayfairDisplay,
                            fontWeight = FontWeight.SemiBold,
                            fontStyle = FontStyle.Italic,
                            fontSize = 19.sp,
                            color = RetirementColors.Gold,
                            textAlign = TextAlign.Center
                        ),
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .clickable { onNavigateToPremium() }
                            .padding(top = 16.dp, bottom = 110.dp)
                    )
                } else {
                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
        }
    }
}

@Composable
fun rememberThumbnailPainter(resId: Int): Painter? {
    val context = LocalContext.current
    val bitmap = remember(resId) {
        if (resId == 0) return@remember null
        try {
            val options = BitmapFactory.Options().apply {
                inSampleSize = 4
            }
            val stream = context.resources.openRawResource(resId)
            val bmp = BitmapFactory.decodeStream(stream, null, options)
            stream.close()
            bmp?.asImageBitmap()
        } catch (_: Throwable) {
            null
        }
    }
    return bitmap?.let { BitmapPainter(it) }
}

@Composable
fun BackgroundCard(
    name: String,
    isSelected: Boolean,
    isLocked: Boolean,
    onClick: () -> Unit
) {
    val imageResId = remember(name) { getBackgroundDrawableRes(name) }
    val cardPainter = rememberThumbnailPainter(imageResId)

    val displayName = when (name) {
        "None" -> stringResource(R.string.bg_none)
        "Palm Beach" -> stringResource(R.string.bg_palm_beach)
        "Mountain Road" -> stringResource(R.string.bg_mountain_road)
        "Golf Course", "Golf" -> stringResource(R.string.bg_golf_course)
        "Lake" -> stringResource(R.string.bg_lake)
        else -> name
    }

    val gradientColors = when (name) {
        "Palm Beach" -> listOf(Color(0xFFD4A373), Color(0xFFFAEDCD))
        "Mountain Road" -> listOf(Color(0xFF4A4E69), Color(0xFFC9ADA7))
        "Lake" -> listOf(Color(0xFF1D3557), Color(0xFFA8DADC))
        else -> listOf(Color(0xFF262628), Color(0xFF1C1C1E))
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .aspectRatio(0.55f)
                .clip(RoundedCornerShape(12.dp))
                .then(
                    if (name == "None") {
                        Modifier.background(Brush.verticalGradient(gradientColors))
                    } else {
                        Modifier.background(Color.Black)
                    }
                )
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
                    text = displayName,
                    style = TextStyle(
                        fontFamily = PlayfairDisplay,
                        fontSize = 16.sp,
                        color = RetirementColors.Cream,
                        fontWeight = FontWeight.Bold
                    )
                )
            } else {
                if (cardPainter != null) {
                    Image(
                        painter = cardPainter,
                        contentDescription = displayName,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }

                if (isLocked) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.MilitaryTech,
                            contentDescription = "Locked",
                            tint = RetirementColors.Gold,
                            modifier = Modifier.size(36.dp)
                        )
                    }
                } else if (isSelected) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.TopEnd
                    ) {
                        Surface(
                            color = RetirementColors.Gold,
                            shape = RoundedCornerShape(bottomStart = 8.dp)
                        ) {
                            Text(
                                text = "ACTIVE",
                                style = TextStyle(
                                    color = Color(0xFF111315),
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold
                                ),
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = displayName,
            style = TextStyle(
                fontFamily = PlayfairDisplay,
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold,
                color = if (isSelected) RetirementColors.Gold else RetirementColors.Cream,
                textAlign = TextAlign.Center
            ),
            textAlign = TextAlign.Center,
            maxLines = 2,
            lineHeight = 18.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 2.dp)
        )
    }
}
