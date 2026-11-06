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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.veremoonstudio.retirementtimer.BuildConfig
import com.veremoonstudio.retirementtimer.ui.theme.*
import kotlinx.coroutines.delay
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun WorkTimeScreen(
    viewModel: WorkTimeViewModel = viewModel(),
    onNavigateToPremium: () -> Unit = {},
    onNavigateToBackground: () -> Unit = {},
    onOpenMenu: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()
    WorkTimeScreenContent(
        uiState = uiState,
        onToggleMode = { viewModel.toggleMode(it) },
        onUpdateRetirementDate = { viewModel.updateRetirementDate(it) },
        onUpdateTimes = { start, end -> viewModel.updateTimes(start, end) },
        onNavigateToPremium = onNavigateToPremium,
        onNavigateToBackground = onNavigateToBackground,
        onOpenMenu = onOpenMenu
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkTimeScreenContent(
    uiState: WorkTimeUiState,
    onToggleMode: (Boolean) -> Unit = {},
    onUpdateRetirementDate: (LocalDate) -> Unit = {},
    onUpdateTimes: (LocalTime, LocalTime) -> Unit = { _, _ -> },
    onNavigateToPremium: () -> Unit = {},
    onNavigateToBackground: () -> Unit = {},
    onOpenMenu: () -> Unit = {}
) {
    var showDatePicker by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }
    var pendingDate by remember { mutableStateOf<LocalDate?>(null) }

    MarbleBackground(selectedBackground = uiState.selectedBackground) {
        Scaffold(
            containerColor = Color.Transparent,
            topBar = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 28.dp, start = 12.dp, end = 12.dp)
                        .statusBarsPadding(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    TopBarItem(
                        label = stringResource(R.string.work_time),
                        onClick = { onToggleMode(!uiState.isWorkTimeMode) }
                    ) {
                        GoldSwitch(
                            checked = uiState.isWorkTimeMode,
                            onCheckedChange = onToggleMode
                        )
                    }

                    Box(
                        modifier = Modifier.alpha(if (BuildConfig.IS_PREMIUM) 0f else 1f)
                    ) {
                        TopBarItem(
                            label = stringResource(R.string.premium),
                            onClick = if (BuildConfig.IS_PREMIUM) null else onNavigateToPremium
                        ) {
                            IconSquareButton(
                                icon = Icons.Filled.WorkspacePremium
                            )
                        }
                    }

                    TopBarItem(
                        label = stringResource(R.string.background),
                        onClick = onNavigateToBackground
                    ) {
                        IconSquareButton(
                            icon = Icons.Filled.Image
                        )
                    }

                    Box(
                        modifier = Modifier
                            .offset(x = (-2).dp)
                            .padding(top = 2.dp)
                    ) {
                        IconButton(
                            onClick = onOpenMenu,
                            modifier = Modifier.size(52.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.MoreHoriz,
                                contentDescription = "More",
                                tint = RetirementColors.Gold.copy(alpha = 0.9f),
                                modifier = Modifier.size(30.dp)
                            )
                        }
                    }
                }
            },
            floatingActionButton = {
                Box(
                    modifier = Modifier
                        .padding(end = 4.dp, bottom = 84.dp)
                        .size(110.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size(110.dp)
                            .drawBehind {
                                drawCircle(
                                    brush = Brush.radialGradient(
                                        colors = listOf(
                                            Color(0xFFC9A961).copy(alpha = 0.40f),
                                            Color(0xFF8A6D2F).copy(alpha = 0.18f),
                                            Color(0xFF4A3815).copy(alpha = 0.08f),
                                            Color.Transparent
                                        ),
                                        center = Offset(size.width / 2f, size.height / 2f + 16f),
                                        radius = size.width * 0.48f
                                    )
                                )
                            }
                    )
                    Box(
                        modifier = Modifier
                            .size(64.dp)
                            .clip(CircleShape)
                            .background(
                                brush = Brush.verticalGradient(
                                    colors = listOf(
                                        Color(0xFFE5C78B),
                                        Color(0xFFD4B26F),
                                        Color(0xFFB5934E),
                                        Color(0xFF8E6E32)
                                    )
                                )
                            )
                            .clickable { showDatePicker = true },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Filled.CalendarMonth,
                            contentDescription = stringResource(R.string.select_date),
                            tint = Color(0xFF111315),
                            modifier = Modifier.size(28.dp)
                        )
                    }
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
                    fontSize = 14.sp,
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

                Box(
                    modifier = Modifier.height(50.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = ("AT " + uiState.endTime.format(DateTimeFormatter.ofPattern("h:mm a", Locale.US))).uppercase(Locale.US),
                        color = RetirementColors.Cream,
                        fontFamily = FontFamily.Default,
                        fontWeight = FontWeight.Medium,
                        fontSize = 17.sp,
                        letterSpacing = 1.sp,
                        textAlign = TextAlign.Center
                    )
                }

                val isDatePassed = remember(uiState.retirementDate, uiState.endTime, uiState.timeLeft) {
                    LocalDateTime.now().isAfter(uiState.retirementDate.atTime(uiState.endTime))
                }
                val counterTitle = when {
                    isDatePassed && uiState.isWorkTimeMode -> stringResource(R.string.work_time_past)
                    isDatePassed && !uiState.isWorkTimeMode -> stringResource(R.string.total_time_past)
                    !isDatePassed && uiState.isWorkTimeMode -> stringResource(R.string.work_time_left)
                    else -> stringResource(R.string.total_time_left)
                }

                Text(
                    text = counterTitle,
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
                initialSelectedDateMillis = null,
                initialDisplayedMonthMillis = uiState.retirementDate.toEpochDay() * 24 * 60 * 60 * 1000
            )

            LaunchedEffect(datePickerState.selectedDateMillis) {
                datePickerState.selectedDateMillis?.let { millis ->
                    pendingDate = LocalDate.ofEpochDay(millis / (24 * 60 * 60 * 1000))
                    showDatePicker = false
                    showTimePicker = true
                }
            }

            DatePickerDialog(
                onDismissRequest = { showDatePicker = false },
                confirmButton = {
                    TextButton(onClick = {
                        val millis = datePickerState.selectedDateMillis ?: (uiState.retirementDate.toEpochDay() * 24 * 60 * 60 * 1000)
                        pendingDate = LocalDate.ofEpochDay(millis / (24 * 60 * 60 * 1000))
                        showDatePicker = false
                        showTimePicker = true
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
                DatePicker(
                    state = datePickerState,
                    colors = DatePickerDefaults.colors(
                        selectedDayContainerColor = Color.Transparent,
                        selectedDayContentColor = RetirementColors.Cream,
                        todayDateBorderColor = RetirementColors.Gold,
                        todayContentColor = RetirementColors.Gold
                    )
                )
            }
        }

        if (showTimePicker) {
            TimePickerDialog(
                onDismiss = { showTimePicker = false },
                onConfirm = { hour, minute ->
                    pendingDate?.let { date ->
                        onUpdateRetirementDate(date)
                    }
                    onUpdateTimes(uiState.startTime, LocalTime.of(hour, minute))
                    showTimePicker = false
                },
                initialHour = uiState.endTime.hour,
                initialMinute = uiState.endTime.minute
            )
        }
    }
}

fun getBackgroundDrawableRes(name: String): Int {
    return when (name) {
        "Palm Beach" -> R.drawable.bg_palm_beach
        "Mountain Road" -> R.drawable.bg_mountain_road
        "Golf Course", "Golf" -> R.drawable.bg_golf
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
private fun TopBarItem(
    label: String,
    onClick: (() -> Unit)? = null,
    content: @Composable () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(6.dp),
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .then(
                if (onClick != null) {
                    Modifier.clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        onClick = onClick
                    )
                } else Modifier
            )
            .padding(horizontal = 8.dp, vertical = 6.dp)
    ) {
        content()
        Text(
            text = label,
            color = RetirementColors.Gold,
            fontFamily = PlayfairDisplay,
            fontWeight = FontWeight.SemiBold,
            fontSize = 14.sp
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
private fun IconSquareButton(icon: ImageVector) {
    Box(
        modifier = Modifier
            .size(46.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(
                Brush.linearGradient(
                    listOf(RetirementColors.IconBgStart, RetirementColors.IconBgEnd)
                )
            )
            .border(1.dp, RetirementColors.Gold, RoundedCornerShape(12.dp)),
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
        WorkTimeScreenContent(uiState = WorkTimeUiState())
    }
}
