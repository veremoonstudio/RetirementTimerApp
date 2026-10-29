package com.veremoonstudio.retirementtimer

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.WorkspacePremium
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.veremoonstudio.retirementtimer.BuildConfig
import com.veremoonstudio.retirementtimer.ui.theme.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkTimeScreen(
    viewModel: WorkTimeViewModel = viewModel(),
    onNavigateToPremium: () -> Unit = {},
    onNavigateToBackground: () -> Unit = {},
    onOpenMenu: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()
    var showDatePicker by remember { mutableStateOf(false) }

    MarbleBackground(selectedBackground = uiState.selectedBackground) {
        Scaffold(
            containerColor = Color.Transparent,
            topBar = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 32.dp, start = 20.dp, end = 20.dp)
                        .statusBarsPadding(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    TopBarItem(label = stringResource(R.string.work_time)) {
                        GoldSwitch(
                            checked = uiState.isWorkTimeMode,
                            onCheckedChange = { viewModel.toggleMode(it) }
                        )
                    }

                    Box(
                        modifier = Modifier.alpha(if (BuildConfig.IS_PREMIUM) 0f else 1f)
                    ) {
                        TopBarItem(label = stringResource(R.string.premium)) {
                            IconSquareButton(
                                icon = Icons.Filled.WorkspacePremium,
                                onClick = onNavigateToPremium
                            )
                        }
                    }

                    TopBarItem(label = stringResource(R.string.background)) {
                        IconSquareButton(
                            icon = Icons.Filled.Image,
                            onClick = onNavigateToBackground
                        )
                    }

                    Box(modifier = Modifier.offset(x = (-8).dp).padding(top = 10.dp)) {
                        IconButton(onClick = onOpenMenu, modifier = Modifier.size(36.dp)) {
                            Icon(
                                imageVector = Icons.Filled.MoreHoriz,
                                contentDescription = "More",
                                tint = RetirementColors.Gold.copy(alpha = 0.9f),
                                modifier = Modifier.size(28.dp)
                            )
                        }
                    }
                }
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = { showDatePicker = true },
                    containerColor = RetirementColors.Gold,
                    contentColor = RetirementColors.Background,
                    shape = CircleShape,
                    modifier = Modifier
                        .padding(end = 20.dp, bottom = 100.dp)
                        .size(64.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.CalendarMonth,
                        contentDescription = stringResource(R.string.select_date)
                    )
                }
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = 28.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.weight(1.4f))

                Text(
                    text = stringResource(R.string.retirement_date),
                    color = RetirementColors.Gold,
                    fontFamily = CormorantGaramond,
                    fontStyle = FontStyle.Italic,
                    fontWeight = FontWeight.Medium,
                    fontSize = 18.sp,
                    letterSpacing = 2.sp
                )
                Spacer(Modifier.height(12.dp))
                Text(
                    text = uiState.retirementDate.format(DateTimeFormatter.ofPattern("MMMM d, yyyy", Locale.US)),
                    color = RetirementColors.Cream,
                    fontFamily = PlayfairDisplay,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 34.sp,
                    textAlign = TextAlign.Center
                )

                Spacer(Modifier.height(50.dp))

                Text(
                    text = if (uiState.isWorkTimeMode) stringResource(R.string.work_time_left) else stringResource(R.string.total_time_left),
                    color = RetirementColors.Gold,
                    fontFamily = PlayfairDisplay,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 20.sp
                )
                Spacer(Modifier.height(20.dp))

                Row(horizontalArrangement = Arrangement.spacedBy(14.dp)) {
                    CounterCell(value = uiState.timeLeft.days, label = stringResource(R.string.days))
                    CounterCell(value = uiState.timeLeft.hours, label = stringResource(R.string.hrs))
                    CounterCell(value = uiState.timeLeft.minutes, label = stringResource(R.string.min))
                    CounterCell(value = uiState.timeLeft.seconds, label = stringResource(R.string.sec))
                }

                Spacer(modifier = Modifier.weight(2f))
            }
        }

        if (showDatePicker) {
            val datePickerState = rememberDatePickerState(
                initialSelectedDateMillis = uiState.retirementDate.toEpochDay() * 24 * 60 * 60 * 1000
            )
            DatePickerDialog(
                onDismissRequest = { showDatePicker = false },
                confirmButton = {
                    TextButton(onClick = {
                        datePickerState.selectedDateMillis?.let { millis ->
                            viewModel.updateRetirementDate(LocalDate.ofEpochDay(millis / (24 * 60 * 60 * 1000)))
                        }
                        showDatePicker = false
                    }) {
                        Text(stringResource(R.string.ok))
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDatePicker = false }) {
                        Text(stringResource(R.string.cancel))
                    }
                }
            ) {
                DatePicker(state = datePickerState)
            }
        }
    }
}

fun getBackgroundDrawableRes(name: String): Int {
    return when (name) {
        "Palm Beach" -> R.drawable.bg_palm_beach
        "Mountain Road" -> R.drawable.bg_mountain_road
        "Lake" -> R.drawable.bg_lake
        else -> 0
    }
}

@Composable
fun MarbleBackground(
    selectedBackground: String = "None",
    content: @Composable () -> Unit
) {
    val imageResId = remember(selectedBackground) { getBackgroundDrawableRes(selectedBackground) }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        if (imageResId != 0) {
            Image(
                painter = painterResource(id = imageResId),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.35f))
            )
        } else {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MarbleBackground)
                    .drawBehind {
                        drawOval(
                            brush = Brush.radialGradient(
                                0.0f to MarbleVeinWhite,
                                1.0f to Color.Transparent,
                                center = Offset(-150f, -100f),
                                radius = 500f
                            ),
                            topLeft = Offset(-150f, -100f),
                            size = Size(500f, 200f)
                        )
                        drawOval(
                            brush = Brush.radialGradient(
                                0.0f to Color(0xFFFFFFFF).copy(alpha = 0.05f),
                                1.0f to Color.Transparent,
                                center = Offset(size.width - 200f, -50f),
                                radius = 400f
                            ),
                            topLeft = Offset(size.width - 200f, -50f),
                            size = Size(400f, 300f)
                        )
                    }
            )
        }

        content()
    }
}

@Composable
private fun TopBarItem(label: String, content: @Composable () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        content()
        Text(
            text = label,
            color = RetirementColors.Gold,
            fontFamily = CormorantGaramond,
            fontWeight = FontWeight.SemiBold,
            fontSize = 13.sp
        )
    }
}

@Composable
private fun GoldSwitch(checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Switch(
        checked = checked,
        onCheckedChange = onCheckedChange,
        colors = SwitchDefaults.colors(
            checkedThumbColor = RetirementColors.Background,
            checkedTrackColor = RetirementColors.Gold,
            uncheckedThumbColor = Color.LightGray,
            uncheckedTrackColor = Color(0xFF4A4A4A),
            uncheckedBorderColor = Color.Transparent
        ),
        modifier = Modifier.scale(0.8f)
    )
}

@Composable
private fun IconSquareButton(icon: ImageVector, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(46.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(
                Brush.linearGradient(
                    listOf(RetirementColors.IconBgStart, RetirementColors.IconBgEnd)
                )
            )
            .border(1.dp, RetirementColors.Gold, RoundedCornerShape(12.dp))
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick
            ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = RetirementColors.Gold,
            modifier = Modifier.size(20.dp)
        )
    }
}

@Composable
private fun CounterCell(value: String, label: String) {
    Column(
        modifier = Modifier
            .width(64.dp)
            .border(1.dp, RetirementColors.Gold, RoundedCornerShape(4.dp))
            .background(RetirementColors.Cream.copy(alpha = 0.03f))
            .padding(vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = value,
            color = RetirementColors.Cream,
            fontFamily = PlayfairDisplay,
            fontWeight = FontWeight.SemiBold,
            fontSize = 24.sp
        )
        Spacer(Modifier.height(4.dp))
        Text(
            text = label,
            color = RetirementColors.Gold,
            fontFamily = CormorantGaramond,
            fontSize = 10.sp,
            letterSpacing = 1.sp
        )
    }
}

@Preview(showBackground = true, widthDp = 375, heightDp = 780, name = "Lux Preview")
@Composable
fun WorkTimeScreenLuxPreview() {
    RetirementTimerAppTheme {
        WorkTimeScreen()
    }
}
