package com.veremoonstudio.retirementtimer

import android.app.AlarmManager
import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.glance.appwidget.updateAll
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RetirementWidgetReceiver : GlanceAppWidgetReceiver() {
    override val glanceAppWidget: GlanceAppWidget = RetirementGlanceWidget()

    companion object {
        const val ACTION_UPDATE_WIDGET = "com.veremoonstudio.retirementtimer.ACTION_UPDATE_WIDGET"

        fun scheduleNextMinuteAlarm(context: Context) {
            try {
                val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as? AlarmManager ?: return
                val appWidgetManager = AppWidgetManager.getInstance(context)
                val appWidgetIds = appWidgetManager.getAppWidgetIds(
                    ComponentName(context, RetirementWidgetReceiver::class.java)
                )
                if (appWidgetIds.isEmpty()) return

                val widgetAction = "${context.packageName}.ACTION_UPDATE_WIDGET"
                val intent = Intent(context, RetirementWidgetReceiver::class.java).apply {
                    action = widgetAction
                    putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds)
                }
                val pendingIntent = PendingIntent.getBroadcast(
                    context,
                    1001,
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                )

                val showIntent = Intent(context, MainActivity::class.java)
                val showPendingIntent = PendingIntent.getActivity(
                    context,
                    0,
                    showIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                )

                val now = System.currentTimeMillis()
                val nextMinute = now + (60000 - (now % 60000)) + 100L

                try {
                    val canScheduleExact = try {
                        Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && alarmManager.canScheduleExactAlarms()
                    } catch (_: Throwable) {
                        false
                    }

                    if (canScheduleExact) {
                        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, nextMinute, pendingIntent)
                    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                        alarmManager.setAlarmClock(
                            AlarmManager.AlarmClockInfo(nextMinute, showPendingIntent),
                            pendingIntent
                        )
                    } else {
                        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, nextMinute, pendingIntent)
                    }
                } catch (_: Throwable) {
                    try {
                        alarmManager.setAlarmClock(
                            AlarmManager.AlarmClockInfo(nextMinute, showPendingIntent),
                            pendingIntent
                        )
                    } catch (_: Throwable) {
                        try {
                            alarmManager.setAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, nextMinute, pendingIntent)
                        } catch (_: Throwable) {}
                    }
                }
            } catch (_: Throwable) {}
        }
    }

    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)

        val action = intent.action
        if (action == ACTION_UPDATE_WIDGET ||
            action?.endsWith(".ACTION_UPDATE_WIDGET") == true ||
            action == AppWidgetManager.ACTION_APPWIDGET_UPDATE ||
            action == Intent.ACTION_BOOT_COMPLETED ||
            action == Intent.ACTION_MY_PACKAGE_REPLACED ||
            action == Intent.ACTION_TIME_CHANGED ||
            action == Intent.ACTION_TIMEZONE_CHANGED ||
            action == Intent.ACTION_USER_PRESENT) {
            val pendingResult = try { goAsync() } catch (_: Throwable) { null }
            CoroutineScope(Dispatchers.Main).launch {
                try {
                    RetirementGlanceWidget().updateAll(context)
                } catch (_: Throwable) {
                } finally {
                    scheduleNextMinuteAlarm(context)
                    try { pendingResult?.finish() } catch (_: Throwable) {}
                }
            }
        } else {
            scheduleNextMinuteAlarm(context)
        }
    }

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)
        scheduleNextMinuteAlarm(context)
    }

    override fun onEnabled(context: Context) {
        super.onEnabled(context)
        scheduleNextMinuteAlarm(context)
    }
}
