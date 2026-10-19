package com.valentsytnik.retirementtimer

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.action.actionStartActivity
import androidx.glance.action.clickable
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.provideContent
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
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextAlign
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider as GlanceColorProvider
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.time.DayOfWeek
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Locale

private val Context.dataStore by preferencesDataStore(name = "work_time_settings")
private val SETTINGS_KEY = stringPreferencesKey("all_settings")

@Serializable
private data class WidgetSettingsData(
    val isWorkTimeMode: Boolean = true,
    val retirementDateEpoch: Long,
    val workDays: List<DayOfWeek>,
    val startTimeStr: String,
    val endTimeStr: String,
    val vacationHours: Int = 0,
    val holidays: List<WidgetHoliday> = emptyList(),
    val selectedBackground: String = "None"
)

@Serializable
private data class WidgetHoliday(
    val name: String,
    val month: Int,
    val day: Int,
    val isEnabled: Boolean
)

class RetirementGlanceWidget : GlanceAppWidget() {

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        val json = context.dataStore.data.firstOrNull()?.get(SETTINGS_KEY)
        var settings: WidgetSettingsData? = null
        if (json != null) {
            try {
                settings = Json.decodeFromString<WidgetSettingsData>(json)
            } catch (e: Exception) {
                // Ignore parse errors
            }
        }

        val now = LocalDateTime.now()
        val retirementDate = if (settings != null) {
            LocalDate.ofEpochDay(settings.retirementDateEpoch)
        } else {
            LocalDate.now().plusYears(5)
        }

        val target = retirementDate.atTime(LocalTime.MAX)
        val duration = if (now.isBefore(target)) Duration.between(now, target) else Duration.ZERO
        val days = duration.toDays()
        val hours = duration.toHours() % 24
        val minutes = duration.toMinutes() % 60

        provideContent {
            GlanceTheme {
                WidgetContent(
                    retirementDate = retirementDate,
                    days = days.toString(),
                    hours = String.format(Locale.US, "%02d", hours),
                    minutes = String.format(Locale.US, "%02d", minutes)
                )
            }
        }
    }

    @Composable
    private fun WidgetContent(
        retirementDate: LocalDate,
        days: String,
        hours: String,
        minutes: String
    ) {
        val goldColor = ColorProvider(day = Color(0xFFC9A961), night = Color(0xFFC9A961))
        val creamColor = ColorProvider(day = Color(0xFFF5F0E3), night = Color(0xFFF5F0E3))
        val bgColor = ColorProvider(day = Color(0xFF1C1C1E), night = Color(0xFF1C1C1E))

        Box(
            modifier = GlanceModifier
                .fillMaxSize()
                .background(bgColor)
                .padding(12.dp)
                .clickable(actionStartActivity<MainActivity>()),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalAlignment = Alignment.CenterVertically,
                modifier = GlanceModifier.fillMaxSize()
            ) {
                Text(
                    text = "RETIREMENT COUNTDOWN",
                    style = TextStyle(
                        color = goldColor,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                )

                Spacer(GlanceModifier.height(4.dp))

                Text(
                    text = retirementDate.format(DateTimeFormatter.ofPattern("MMM d, yyyy", Locale.US)),
                    style = TextStyle(
                        color = creamColor,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        textAlign = TextAlign.Center
                    )
                )

                Spacer(GlanceModifier.height(8.dp))

                Row(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = GlanceModifier.fillMaxWidth()
                ) {
                    CounterWidgetCell(value = days, label = "DAYS", goldColor = goldColor, creamColor = creamColor)
                    Spacer(GlanceModifier.width(8.dp))
                    CounterWidgetCell(value = hours, label = "HRS", goldColor = goldColor, creamColor = creamColor)
                    Spacer(GlanceModifier.width(8.dp))
                    CounterWidgetCell(value = minutes, label = "MIN", goldColor = goldColor, creamColor = creamColor)
                }
            }
        }
    }

    @Composable
    private fun CounterWidgetCell(
        value: String,
        label: String,
        goldColor: GlanceColorProvider,
        creamColor: GlanceColorProvider
    ) {
        val cardBg = ColorProvider(day = Color(0xFF262628), night = Color(0xFF262628))
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = GlanceModifier
                .background(cardBg)
                .padding(horizontal = 8.dp, vertical = 6.dp)
        ) {
            Text(
                text = value,
                style = TextStyle(
                    color = creamColor,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            )
            Text(
                text = label,
                style = TextStyle(
                    color = goldColor,
                    fontSize = 9.sp
                )
            )
        }
    }
}
