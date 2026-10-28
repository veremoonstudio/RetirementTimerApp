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
import androidx.glance.action.clickable
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.SizeMode
import androidx.glance.appwidget.action.ActionCallback
import androidx.glance.appwidget.action.actionRunCallback
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
        try {
            RetirementGlanceWidget().update(context, glanceId)
        } catch (e: Throwable) {
            // Ignore errors
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
        } catch (e: Throwable) {
            // Safe fallback
        }

        val timeLeft = try {
            calculateWidgetTimeLeft(settings)
        } catch (e: Throwable) {
            TimeLeftData("00", "00", "00", "00")
        }

        val retirementDate = try {
            if (settings.retirementDateEpoch > 0) LocalDate.ofEpochDay(settings.retirementDateEpoch) else LocalDate.now().plusYears(5)
        } catch (e: Throwable) {
            LocalDate.now().plusYears(5)
        }

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
    internal fun WidgetContent(
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
        val width = currentSize.width

        val showSeconds = width >= 180.dp

        Box(
            modifier = GlanceModifier
                .fillMaxSize()
                .background(bgColor)
                .cornerRadius(12.dp)
                .padding(horizontal = 4.dp, vertical = 2.dp)
                .clickable(actionRunCallback<WidgetRefreshCallback>()),
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
                    ),
                    maxLines = 1
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

    @Composable
    private fun CounterWidgetCellCompact(
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
                .cornerRadius(8.dp)
                .padding(horizontal = 2.dp, vertical = 2.dp)
        ) {
            Text(
                text = value,
                style = TextStyle(
                    color = creamColor,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            )
            Text(
                text = label,
                style = TextStyle(
                    color = goldColor,
                    fontSize = 8.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            )
        }
    }


}
