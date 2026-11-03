package com.veremoonstudio.retirementtimer

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.LocalContext
import androidx.glance.LocalSize
import androidx.glance.action.ActionParameters
import androidx.glance.action.clickable
import androidx.glance.appwidget.AndroidRemoteViews
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.SizeMode
import androidx.glance.appwidget.action.ActionCallback
import androidx.glance.appwidget.action.actionRunCallback
import androidx.glance.appwidget.cornerRadius
import androidx.glance.appwidget.provideContent
import androidx.glance.appwidget.updateAll
import androidx.glance.background
import androidx.glance.color.ColorProvider
import androidx.glance.layout.Alignment
import androidx.glance.layout.Box
import androidx.glance.layout.Column
import androidx.glance.layout.Row
import androidx.glance.layout.Spacer
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.height
import androidx.glance.layout.padding
import androidx.glance.layout.width
import androidx.glance.text.FontFamily as GlanceFontFamily
import androidx.glance.text.FontWeight as GlanceFontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextAlign
import androidx.glance.text.TextStyle
import com.veremoonstudio.retirementtimer.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Locale

private val SETTINGS_KEY = stringPreferencesKey("all_settings")
private val jsonParser = Json { ignoreUnknownKeys = true; isLenient = true }

fun calculateWidgetTimeLeft(settings: SettingsData): TimeLeftData {
    val now = LocalDateTime.now()
    val retirementDate = if (settings.retirementDateEpoch > 0L) LocalDate.ofEpochDay(settings.retirementDateEpoch) else LocalDate.now().plusYears(5)

    if (!settings.isWorkTimeMode) {
        val target = retirementDate.atTime(LocalTime.MAX)
        if (now.isAfter(target)) {
            val duration = Duration.between(target, now)
            val days = duration.toDays()
            val hours = duration.toHours() % 24
            val minutes = duration.toMinutes() % 60
            val seconds = duration.seconds % 60
            val formattedDays = if (days == 0L) "\u221200" else "\u2212${String.format(Locale.US, "%02d", days)}"
            return TimeLeftData(days = formattedDays, hours = String.format(Locale.US, "%02d", hours), minutes = String.format(Locale.US, "%02d", minutes), seconds = String.format(Locale.US, "%02d", seconds))
        }
        val duration = Duration.between(now, target)
        val days = duration.toDays()
        val hours = duration.toHours() % 24
        val minutes = duration.toMinutes() % 60
        val seconds = duration.seconds % 60
        return TimeLeftData(days = String.format(Locale.US, "%02d", days), hours = String.format(Locale.US, "%02d", hours), minutes = String.format(Locale.US, "%02d", minutes), seconds = String.format(Locale.US, "%02d", seconds))
    }

    val startTime = try { LocalTime.parse(settings.startTimeStr) } catch (_: Exception) { LocalTime.of(9, 0) }
    val endTime = try { LocalTime.parse(settings.endTimeStr) } catch (_: Exception) { LocalTime.of(18, 0) }
    val workSecondsPerDay = Duration.between(startTime, endTime).seconds
    if (workSecondsPerDay <= 0) return TimeLeftData("00", "00", "00", "00")

    val targetDateTime = retirementDate.atTime(endTime)
    val today = now.toLocalDate()
    val workDaysSet = settings.workDays.toSet()

    if (now.isAfter(targetDateTime)) {
        var pastWorkDays = 0L
        var cursor = retirementDate.plusDays(1)
        val maxCursor = today.minusDays(1)
        var iterations = 0
        while (!cursor.isAfter(maxCursor) && iterations < 3650) {
            if (cursor.dayOfWeek in workDaysSet && settings.holidays.none { it.isEnabled && it.month == cursor.monthValue && it.day == cursor.dayOfMonth }) {
                pastWorkDays++
            }
            cursor = cursor.plusDays(1)
            iterations++
        }

        var todaySeconds = 0L
        if (today.dayOfWeek in workDaysSet && !today.isEqual(retirementDate) && settings.holidays.none { it.isEnabled && it.month == today.monthValue && it.day == today.dayOfMonth }) {
            val nowTime = now.toLocalTime()
            if (nowTime.isAfter(startTime)) {
                val effectiveEnd = if (nowTime.isAfter(endTime)) endTime else nowTime
                todaySeconds = Duration.between(startTime, effectiveEnd).seconds
            }
        }

        var totalPastWorkSeconds = (pastWorkDays * workSecondsPerDay) + todaySeconds
        if (totalPastWorkSeconds < 0) totalPastWorkSeconds = 0

        val daysPast = totalPastWorkSeconds / workSecondsPerDay
        var remainder = totalPastWorkSeconds % workSecondsPerDay

        val hoursPast = remainder / 3600
        remainder %= 3600
        val minutesPast = remainder / 60
        val secondsPast = remainder % 60

        val formattedDays = if (daysPast == 0L) "\u221200" else "\u2212${String.format(Locale.US, "%02d", daysPast)}"

        return TimeLeftData(days = formattedDays, hours = String.format(Locale.US, "%02d", hoursPast), minutes = String.format(Locale.US, "%02d", minutesPast), seconds = String.format(Locale.US, "%02d", secondsPast))
    }

    var futureWorkDays = 0L
    var cursor = today.plusDays(1)
    var iterations = 0
    while (!cursor.isAfter(retirementDate) && iterations < 36500) {
        if (cursor.dayOfWeek in workDaysSet && settings.holidays.none { it.isEnabled && it.month == cursor.monthValue && it.day == cursor.dayOfMonth }) {
            futureWorkDays++
        }
        cursor = cursor.plusDays(1)
        iterations++
    }

    var todaySeconds = 0L
    if (today.dayOfWeek in workDaysSet && settings.holidays.none { it.isEnabled && it.month == today.monthValue && it.day == today.dayOfMonth }) {
        val nowTime = now.toLocalTime()
        if (nowTime.isBefore(endTime)) {
            val effectiveStart = if (nowTime.isBefore(startTime)) startTime else nowTime
            todaySeconds = Duration.between(effectiveStart, endTime).seconds
        }
    }

    var totalWorkSeconds = (futureWorkDays * workSecondsPerDay) + todaySeconds
    totalWorkSeconds -= (settings.vacationHours * 3600L)
    if (totalWorkSeconds < 0) totalWorkSeconds = 0

    val days = totalWorkSeconds / workSecondsPerDay
    var remainder = totalWorkSeconds % workSecondsPerDay

    val hours = remainder / 3600
    remainder %= 3600
    val minutes = remainder / 60
    val seconds = remainder % 60

    return TimeLeftData(days = String.format(Locale.US, "%02d", days), hours = String.format(Locale.US, "%02d", hours), minutes = String.format(Locale.US, "%02d", minutes), seconds = String.format(Locale.US, "%02d", seconds))
}

class WidgetRefreshCallback : ActionCallback {
    override suspend fun onAction(
        context: Context,
        glanceId: GlanceId,
        parameters: ActionParameters
    ) {
        try {
            RetirementGlanceWidget().updateAll(context)
        } catch (_: Throwable) {
        }
    }
}

class RetirementGlanceWidget : GlanceAppWidget() {

    override val sizeMode: SizeMode = SizeMode.Exact

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        var settings = SettingsData()
        try {
            val json = context.dataStore.data.firstOrNull()?.get(SETTINGS_KEY)
            if (json != null) {
                settings = jsonParser.decodeFromString<SettingsData>(json)
            }
        } catch (_: Throwable) {
        }

        try {
            RetirementWidgetReceiver.scheduleNextMinuteAlarm(context)
        } catch (_: Throwable) {}

        val timeLeft = try {
            calculateWidgetTimeLeft(settings)
        } catch (_: Throwable) {
            TimeLeftData("00", "00", "00", "00")
        }

        val retirementDate = try {
            if (settings.retirementDateEpoch > 0L) LocalDate.ofEpochDay(settings.retirementDateEpoch) else LocalDate.now().plusYears(5)
        } catch (_: Throwable) {
            LocalDate.now().plusYears(5)
        }

        provideContent {
            val currentSettings = remember { mutableStateOf(settings) }
            val currentTimeLeft = remember { mutableStateOf(timeLeft) }
            val currentRetirementDate = remember { mutableStateOf(retirementDate) }

            LaunchedEffect(Unit) {
                launch {
                    try {
                        context.dataStore.data.collect { prefs ->
                            val json = prefs[SETTINGS_KEY]
                            if (json != null) {
                                val newSettings = jsonParser.decodeFromString<SettingsData>(json)
                                currentSettings.value = newSettings
                                currentRetirementDate.value = if (newSettings.retirementDateEpoch > 0L) {
                                    LocalDate.ofEpochDay(newSettings.retirementDateEpoch)
                                } else {
                                    LocalDate.now().plusYears(5)
                                }
                            }
                        }
                    } catch (_: Throwable) {}
                }

                while (isActive) {
                    currentTimeLeft.value = try {
                        calculateWidgetTimeLeft(currentSettings.value)
                    } catch (_: Throwable) {
                        TimeLeftData("00", "00", "00", "00")
                    }
                    delay(1000)
                }
            }

            GlanceTheme {
                WidgetContent(
                    retirementDate = currentRetirementDate.value,
                    days = currentTimeLeft.value.days,
                    hours = currentTimeLeft.value.hours,
                    minutes = currentTimeLeft.value.minutes,
                    seconds = currentTimeLeft.value.seconds
                )
            }
        }
    }

    @Composable
    internal fun WidgetContent(
        retirementDate: LocalDate,
        days: String,
        hours: String,
        minutes: String,
        seconds: String
    ) {
        val goldColor = Color(0xFFC9A961)
        val creamColor = Color(0xFFF5F0E3)
        val bgColor = ColorProvider(day = Color(0xFF1C1C1E), night = Color(0xFF1C1C1E))

        val currentSize = LocalSize.current
        val width = currentSize.width

        val showSeconds = width >= 180.dp

        Box(
            modifier = GlanceModifier
                .fillMaxSize()
                .padding(vertical = 4.dp, horizontal = 2.dp),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = GlanceModifier
                    .fillMaxWidth()
                    .background(bgColor)
                    .cornerRadius(12.dp)
                    .padding(horizontal = 4.dp, vertical = 2.dp)
                    .clickable(actionRunCallback<WidgetRefreshCallback>()),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = GlanceModifier.fillMaxWidth()
                ) {
                    Text(
                        text = "RETIREMENT: " + retirementDate.format(DateTimeFormatter.ofPattern("MMM d, yyyy", Locale.US)),
                        style = TextStyle(
                            color = ColorProvider(day = goldColor, night = goldColor),
                            fontSize = 13.sp,
                            fontWeight = GlanceFontWeight.Bold,
                            fontFamily = GlanceFontFamily.SansSerif,
                            textAlign = TextAlign.Center
                        )
                    )

                    Spacer(GlanceModifier.height(1.dp))

                    Row(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = GlanceModifier.fillMaxWidth()
                    ) {
                        CounterWidgetCellCompact(value = days, label = "DAYS", goldColor = goldColor, creamColor = creamColor, modifier = GlanceModifier.defaultWeight())
                        Spacer(GlanceModifier.width(3.dp))
                        CounterWidgetCellCompact(value = hours, label = "HRS", goldColor = goldColor, creamColor = creamColor, modifier = GlanceModifier.defaultWeight())
                        Spacer(GlanceModifier.width(3.dp))
                        CounterWidgetCellCompact(value = minutes, label = "MIN", goldColor = goldColor, creamColor = creamColor, modifier = GlanceModifier.defaultWeight())
                        if (showSeconds) {
                            Spacer(GlanceModifier.width(3.dp))
                            CounterWidgetCellCompact(value = seconds, label = "SEC", goldColor = goldColor, creamColor = creamColor, modifier = GlanceModifier.defaultWeight())
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun CounterWidgetCellCompact(
        value: String,
        label: String,
        goldColor: Color,
        creamColor: Color,
        modifier: GlanceModifier = GlanceModifier
    ) {
        val cardBg = ColorProvider(day = Color(0xFF262628), night = Color(0xFF262628))
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .background(cardBg)
                .cornerRadius(8.dp)
                .padding(horizontal = 2.dp, vertical = 2.dp)
        ) {
            Text(
                text = value,
                style = TextStyle(
                    color = ColorProvider(day = creamColor, night = creamColor),
                    fontSize = 18.sp,
                    fontWeight = GlanceFontWeight.Bold,
                    fontFamily = GlanceFontFamily.SansSerif,
                    textAlign = TextAlign.Center
                )
            )
            Text(
                text = label,
                style = TextStyle(
                    color = ColorProvider(day = goldColor, night = goldColor),
                    fontSize = 8.sp,
                    fontWeight = GlanceFontWeight.Bold,
                    fontFamily = GlanceFontFamily.SansSerif,
                    textAlign = TextAlign.Center
                )
            )
        }
    }
}
