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
import androidx.glance.LocalSize
import androidx.glance.action.ActionParameters
import androidx.glance.action.actionStartActivity
import androidx.glance.action.clickable
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.SizeMode
import androidx.glance.appwidget.action.ActionCallback
import androidx.glance.appwidget.cornerRadius
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
import kotlinx.serialization.json.Json
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

private val Context.dataStore by preferencesDataStore(name = "work_time_settings")
private val SETTINGS_KEY = stringPreferencesKey("all_settings")
private val jsonParser = Json { ignoreUnknownKeys = true; isLenient = true }

class WidgetRefreshCallback : ActionCallback {
    override suspend fun onAction(
        context: Context,
        glanceId: GlanceId,
        parameters: ActionParameters
    ) {
        RetirementGlanceWidget().update(context, glanceId)
    }
}

class RetirementGlanceWidget : GlanceAppWidget() {

    override val sizeMode: SizeMode = SizeMode.Exact

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        val json = context.dataStore.data.firstOrNull()?.get(SETTINGS_KEY)
        var settings = SettingsData()
        if (json != null) {
            try {
                settings = jsonParser.decodeFromString<SettingsData>(json)
            } catch (e: Exception) {
                // Keep default settings
            }
        }

        val timeLeft = calculateWidgetTimeLeft(settings)
        val retirementDate = if (settings.retirementDateEpoch > 0) LocalDate.ofEpochDay(settings.retirementDateEpoch) else LocalDate.now().plusYears(5)

        provideContent {
            GlanceTheme {
                WidgetContent(
                    retirementDate = retirementDate,
                    days = timeLeft.days,
                    hours = timeLeft.hours,
                    minutes = timeLeft.minutes,
                    seconds = timeLeft.seconds
                )
            }
        }
    }

    @Composable
    private fun WidgetContent(
        retirementDate: LocalDate,
        days: String,
        hours: String,
        minutes: String,
        seconds: String
    ) {
        val goldColor = ColorProvider(day = Color(0xFFC9A961), night = Color(0xFFC9A961))
        val creamColor = ColorProvider(day = Color(0xFFF5F0E3), night = Color(0xFFF5F0E3))
        val bgColor = ColorProvider(day = Color(0xFF1C1C1E), night = Color(0xFF1C1C1E))

        val currentSize = LocalSize.current
        val showSeconds = currentSize.width >= 310.dp
        val widgetHeight = currentSize.height

        if (widgetHeight < 90.dp) {
            // 1 CELL HIGH MODE: Single compact row
            Box(
                modifier = GlanceModifier
                    .fillMaxSize()
                    .background(bgColor)
                    .cornerRadius(12.dp)
                    .padding(horizontal = 10.dp, vertical = 4.dp)
                    .clickable(actionStartActivity<MainActivity>()),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = GlanceModifier.fillMaxWidth()
                ) {
                    CounterWidgetCellCompact(value = days, label = "DAYS", goldColor = goldColor, creamColor = creamColor, modifier = GlanceModifier.defaultWeight())
                    Spacer(GlanceModifier.width(6.dp))
                    CounterWidgetCellCompact(value = hours, label = "HRS", goldColor = goldColor, creamColor = creamColor, modifier = GlanceModifier.defaultWeight())
                    Spacer(GlanceModifier.width(6.dp))
                    CounterWidgetCellCompact(value = minutes, label = "MIN", goldColor = goldColor, creamColor = creamColor, modifier = GlanceModifier.defaultWeight())
                    if (showSeconds) {
                        Spacer(GlanceModifier.width(6.dp))
                        CounterWidgetCellCompact(value = seconds, label = "SEC", goldColor = goldColor, creamColor = creamColor, modifier = GlanceModifier.defaultWeight())
                    }
                }
            }
        } else if (widgetHeight < 140.dp) {
            // 2 CELLS HIGH MODE: Compact header + counter row
            Box(
                modifier = GlanceModifier
                    .fillMaxSize()
                    .background(bgColor)
                    .cornerRadius(14.dp)
                    .padding(10.dp)
                    .clickable(actionStartActivity<MainActivity>()),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = GlanceModifier.fillMaxSize()
                ) {
                    Text(
                        text = "RETIREMENT: " + retirementDate.format(DateTimeFormatter.ofPattern("MMM d, yyyy", Locale.US)),
                        style = TextStyle(
                            color = goldColor,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        )
                    )

                    Spacer(GlanceModifier.height(6.dp))

                    Row(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = GlanceModifier.fillMaxWidth()
                    ) {
                        CounterWidgetCellMedium(value = days, label = "DAYS", goldColor = goldColor, creamColor = creamColor, modifier = GlanceModifier.defaultWeight())
                        Spacer(GlanceModifier.width(6.dp))
                        CounterWidgetCellMedium(value = hours, label = "HRS", goldColor = goldColor, creamColor = creamColor, modifier = GlanceModifier.defaultWeight())
                        Spacer(GlanceModifier.width(6.dp))
                        CounterWidgetCellMedium(value = minutes, label = "MIN", goldColor = goldColor, creamColor = creamColor, modifier = GlanceModifier.defaultWeight())
                        if (showSeconds) {
                            Spacer(GlanceModifier.width(6.dp))
                            CounterWidgetCellMedium(value = seconds, label = "SEC", goldColor = goldColor, creamColor = creamColor, modifier = GlanceModifier.defaultWeight())
                        }
                    }
                }
            }
        } else {
            // 3+ CELLS HIGH MODE: Full large display
            Box(
                modifier = GlanceModifier
                    .fillMaxSize()
                    .background(bgColor)
                    .cornerRadius(18.dp)
                    .padding(16.dp)
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
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        )
                    )

                    Spacer(GlanceModifier.height(8.dp))

                    Text(
                        text = retirementDate.format(DateTimeFormatter.ofPattern("MMMM d, yyyy", Locale.US)),
                        style = TextStyle(
                            color = creamColor,
                            fontSize = 34.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        )
                    )

                    Spacer(GlanceModifier.height(18.dp))

                    Row(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = GlanceModifier.fillMaxWidth()
                    ) {
                        CounterWidgetCellLarge(value = days, label = "DAYS", goldColor = goldColor, creamColor = creamColor, modifier = GlanceModifier.defaultWeight())
                        Spacer(GlanceModifier.width(8.dp))
                        CounterWidgetCellLarge(value = hours, label = "HRS", goldColor = goldColor, creamColor = creamColor, modifier = GlanceModifier.defaultWeight())
                        Spacer(GlanceModifier.width(8.dp))
                        CounterWidgetCellLarge(value = minutes, label = "MIN", goldColor = goldColor, creamColor = creamColor, modifier = GlanceModifier.defaultWeight())
                        if (showSeconds) {
                            Spacer(GlanceModifier.width(8.dp))
                            CounterWidgetCellLarge(value = seconds, label = "SEC", goldColor = goldColor, creamColor = creamColor, modifier = GlanceModifier.defaultWeight())
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
        goldColor: GlanceColorProvider,
        creamColor: GlanceColorProvider,
        modifier: GlanceModifier = GlanceModifier
    ) {
        val cardBg = ColorProvider(day = Color(0xFF262628), night = Color(0xFF262628))
        Row(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .background(cardBg)
                .cornerRadius(8.dp)
                .padding(horizontal = 6.dp, vertical = 4.dp)
        ) {
            Text(
                text = value,
                style = TextStyle(
                    color = creamColor,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            )
            Spacer(GlanceModifier.width(4.dp))
            Text(
                text = label,
                style = TextStyle(
                    color = goldColor,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold
                )
            )
        }
    }

    @Composable
    private fun CounterWidgetCellMedium(
        value: String,
        label: String,
        goldColor: GlanceColorProvider,
        creamColor: GlanceColorProvider,
        modifier: GlanceModifier = GlanceModifier
    ) {
        val cardBg = ColorProvider(day = Color(0xFF262628), night = Color(0xFF262628))
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .background(cardBg)
                .cornerRadius(10.dp)
                .padding(horizontal = 6.dp, vertical = 8.dp)
        ) {
            Text(
                text = value,
                style = TextStyle(
                    color = creamColor,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold
                )
            )
            Spacer(GlanceModifier.height(2.dp))
            Text(
                text = label,
                style = TextStyle(
                    color = goldColor,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold
                )
            )
        }
    }

    @Composable
    private fun CounterWidgetCellLarge(
        value: String,
        label: String,
        goldColor: GlanceColorProvider,
        creamColor: GlanceColorProvider,
        modifier: GlanceModifier = GlanceModifier
    ) {
        val cardBg = ColorProvider(day = Color(0xFF262628), night = Color(0xFF262628))
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .background(cardBg)
                .cornerRadius(12.dp)
                .padding(horizontal = 8.dp, vertical = 14.dp)
        ) {
            Text(
                text = value,
                style = TextStyle(
                    color = creamColor,
                    fontSize = 48.sp,
                    fontWeight = FontWeight.Bold
                )
            )
            Spacer(GlanceModifier.height(4.dp))
            Text(
                text = label,
                style = TextStyle(
                    color = goldColor,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            )
        }
    }
}
