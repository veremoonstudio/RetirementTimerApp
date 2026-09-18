package com.example.retirementtimerapp

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.CalendarToday
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material.icons.outlined.Landscape
import androidx.compose.material.icons.outlined.MilitaryTech
import androidx.compose.material.icons.outlined.Photo
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.retirementtimerapp.ui.theme.*
import java.time.format.DateTimeFormatter
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    onNavigateToSettings: () -> Unit,
    onNavigateToPremium: () -> Unit,
    onNavigateToBackground: () -> Unit,
    viewModel: RetirementViewModel = viewModel()
) {
    val settings by viewModel.settings.collectAsState()
    val isWorkTimeMode by viewModel.isWorkTimeMode.collectAsState()
    val timeLeft by viewModel.timeLeft.collectAsState()
    
    var showDatePicker by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(LuxBlack)
            .drawBehind {
                drawOval(
                    brush = Brush.radialGradient(
                        0.0f to Color.White.copy(alpha = 0.08f),
                        1.0f to Color.Transparent,
                        center = Offset(size.width * 0.2f, size.height * 0.1f),
                        radius = 500f
                    ),
                    topLeft = Offset(size.width * 0.2f - 250f, size.height * 0.1f - 100f),
                    size = Size(500f, 200f)
                )
                drawOval(
                    brush = Brush.radialGradient(
                        0.0f to Color.White.copy(alpha = 0.05f),
                        1.0f to Color.Transparent,
                        center = Offset(size.width * 0.8f, size.height * 0.3f),
                        radius = 400f
                    ),
                    topLeft = Offset(size.width * 0.8f - 200f, size.height * 0.3f - 150f),
                    size = Size(400f, 300f)
                )
                drawOval(
                    brush = Brush.radialGradient(
                        0.0f to Color.White.copy(alpha = 0.06f),
                        1.0f to Color.Transparent,
                        center = Offset(size.width * 0.4f, size.height * 0.7f),
                        radius = 600f
                    ),
                    topLeft = Offset(size.width * 0.4f - 300f, size.height * 0.7f - 125f),
                    size = Size(600f, 250f)
                )
                drawOval(
                    brush = Brush.radialGradient(
                        0.0f to Color.White.copy(alpha = 0.04f),
                        1.0f to Color.Transparent,
                        center = Offset(size.width * 0.9f, size.height * 0.85f),
                        radius = 350f
                    ),
                    topLeft = Offset(size.width * 0.9f - 175f, size.height * 0.85f - 100f),
                    size = Size(350f, 200f)
                )
            }
    ) {
        Scaffold(
            containerColor = Color.Transparent,
            topBar = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 32.dp, start = 20.dp, end = 20.dp, bottom = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    LuxSwitch(
                        checked = isWorkTimeMode,
                        onCheckedChange = { viewModel.toggleMode(it) },
                        label = "Work Time"
                    )

                    LuxHeaderButton(
                        icon = Icons.Outlined.MilitaryTech,
                        label = "Premium Lux",
                        onClick = onNavigateToPremium
                    )

                    LuxHeaderButton(
                        icon = Icons.Outlined.Photo,
                        label = "Background Lux",
                        onClick = onNavigateToBackground
                    )

                    IconButton(
                        onClick = onNavigateToSettings,
                        modifier = Modifier.padding(top = 8.dp)
                    ) {
                        Text(
                            text = "•••",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = LuxGold.copy(alpha = 0.8f),
                            letterSpacing = (-1).sp
                        )
                    }
                }
            },
            floatingActionButton = {
                Box(
                    modifier = Modifier
                        .padding(bottom = 32.dp, end = 16.dp)
                        .size(80.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size(60.dp)
                            .drawBehind {
                                drawCircle(
                                    brush = Brush.radialGradient(
                                        0.0f to LuxGold.copy(alpha = 0.35f),
                                        1.0f to Color.Transparent
                                    ),
                                    radius = 140f
                                )
                            }
                    )
                    FloatingActionButton(
                        onClick = { showDatePicker = true },
                        containerColor = Color.Transparent,
                        contentColor = LuxBlack,
                        modifier = Modifier
                            .size(64.dp)
                            .background(
                                brush = Brush.linearGradient(listOf(GoldGradStart, GoldGradEnd)),
                                shape = CircleShape
                            ),
                        shape = CircleShape,
                        elevation = FloatingActionButtonDefaults.elevation(0.dp)
                    ) {
                        Icon(
                            Icons.Outlined.CalendarToday, 
                            contentDescription = "Select Date", 
                            modifier = Modifier.size(28.dp),
                            tint = LuxBlack
                        )
                    }
                }
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "RETIREMENT DATE",
                    fontSize = 13.sp,
                    fontFamily = PlayfairFontFamily,
                    fontStyle = FontStyle.Italic,
                    letterSpacing = 2.sp,
                    fontWeight = FontWeight.Medium,
                    color = LuxGold
                )
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = settings.retirementDate.format(DateTimeFormatter.ofPattern("MMMM d, yyyy")),
                    fontSize = 44.sp,
                    fontFamily = PlayfairFontFamily,
                    fontWeight = FontWeight.Normal,
                    color = LuxBeige,
                    textAlign = TextAlign.Center
                )
                
                Spacer(modifier = Modifier.height(72.dp))
                
                Text(
                    text = (if (isWorkTimeMode) "Work Time Left" else "Total Time Left"),
                    fontSize = 18.sp,
                    fontFamily = PlayfairFontFamily,
                    fontStyle = FontStyle.Italic,
                    fontWeight = FontWeight.Medium,
                    color = LuxGold
                )
                
                Spacer(modifier = Modifier.height(40.dp))
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    val timeUnits = listOf(
                        Triple("YRS", timeLeft.years, settings.timeUnits.showYears && timeLeft.years > 0),
                        Triple("MTH", timeLeft.months, settings.timeUnits.showMonths && (timeLeft.months > 0 || timeLeft.years > 0)),
                        Triple("DAYS", timeLeft.days, settings.timeUnits.showDays),
                        Triple("HRS", timeLeft.hours, settings.timeUnits.showHours),
                        Triple("MIN", timeLeft.minutes, settings.timeUnits.showMinutes),
                        Triple("SEC", timeLeft.seconds, settings.timeUnits.showSeconds)
                    )
                    
                    val visibleUnits = timeUnits.filter { it.third }.take(4)
                    
                    visibleUnits.forEachIndexed { index, unit ->
                        LuxTimeCard(
                            label = unit.first, 
                            value = unit.second, 
                            modifier = Modifier.width(72.dp)
                        )
                        if (index < visibleUnits.size - 1) {
                            Spacer(modifier = Modifier.width(16.dp))
                        }
                    }
                }
            }

            if (showDatePicker) {
                val datePickerState = rememberDatePickerState(
                    initialSelectedDateMillis = settings.retirementDateEpoch * 24 * 60 * 60 * 1000
                )
                DatePickerDialog(
                    onDismissRequest = { showDatePicker = false },
                    confirmButton = {
                        TextButton(onClick = {
                            datePickerState.selectedDateMillis?.let { millis ->
                                val newDate = LocalDate.ofEpochDay(millis / (24 * 60 * 60 * 1000))
                                viewModel.updateSettings(settings.copy(retirementDateEpoch = newDate.toEpochDay()))
                            }
                            showDatePicker = false
                        }) {
                            Text("OK", color = LuxGold)
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { showDatePicker = false }) {
                            Text("Cancel", color = LuxBeige)
                        }
                    },
                    colors = DatePickerDefaults.colors(containerColor = LuxBlack)
                ) {
                    DatePicker(
                        state = datePickerState,
                        colors = DatePickerDefaults.colors(
                            containerColor = LuxBlack,
                            titleContentColor = LuxGold,
                            headlineContentColor = LuxBeige,
                            weekdayContentColor = LuxSecondaryGold,
                            subheadContentColor = LuxSecondaryGold,
                            selectedDayContainerColor = LuxGold,
                            selectedDayContentColor = LuxBlack,
                            todayContentColor = LuxGold,
                            todayDateBorderColor = LuxGold
                        )
                    )
                }
            }
        }
    }
}

@Composable
fun LuxSwitch(checked: Boolean, onCheckedChange: (Boolean) -> Unit, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = LuxBlack,
                checkedTrackColor = LuxGold,
                uncheckedThumbColor = LuxGold,
                uncheckedTrackColor = LuxBlack,
                uncheckedBorderColor = LuxGold
            ),
            modifier = Modifier.scale(0.8f)
        )
        Text(
            text = label,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            color = LuxGold
        )
    }
}

@Composable
fun LuxHeaderButton(icon: ImageVector, label: String, onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable { onClick() }
    ) {
        Box(
            modifier = Modifier
                .size(54.dp)
                .border(1.dp, LuxGold.copy(alpha = 0.5f), RoundedCornerShape(16.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(icon, contentDescription = label, tint = LuxGold, modifier = Modifier.size(24.dp))
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = label,
            fontSize = 13.sp,
            fontFamily = PlayfairFontFamily,
            color = LuxGold,
            fontWeight = FontWeight.Normal
        )
    }
}

@Composable
fun LuxTimeCard(label: String, value: Long, fontSize: TextUnit = 32.sp, modifier: Modifier = Modifier) {
    val formattedValue = if (value in 0..9) "0$value" else value.toString()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .height(105.dp)
            .border(1.dp, LuxGold.copy(alpha = 0.7f), RoundedCornerShape(8.dp))
            .padding(top = 16.dp, bottom = 12.dp, start = 2.dp, end = 2.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Box(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = formattedValue,
                fontSize = fontSize,
                fontFamily = PlayfairFontFamily,
                fontWeight = FontWeight.Medium,
                color = LuxBeige,
                maxLines = 1,
                softWrap = false
            )
        }
        Text(
            text = label,
            fontSize = 10.sp,
            fontFamily = MontserratFontFamily,
            letterSpacing = 1.sp,
            color = LuxGold,
            fontWeight = FontWeight.Bold
        )
    }
}
