package com.veremoonstudio.retirementtimer

import android.app.AlarmManager
import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver

class RetirementWidgetReceiver : GlanceAppWidgetReceiver() {
    override val glanceAppWidget: GlanceAppWidget = RetirementGlanceWidget()

    companion object {
        fun scheduleNextMinuteAlarm(context: Context) {
            try {
                val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as? AlarmManager ?: return
                val intent = Intent(context, RetirementWidgetReceiver::class.java).apply {
                    action = AppWidgetManager.ACTION_APPWIDGET_UPDATE
                }
                val pendingIntent = PendingIntent.getBroadcast(
                    context,
                    1001,
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                )
                val now = System.currentTimeMillis()
                val nextMinute = now + (60000 - (now % 60000)) + 100L

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    if (alarmManager.canScheduleExactAlarms()) {
                        alarmManager.setAndAllowWhileIdle(AlarmManager.RTC, nextMinute, pendingIntent)
                    } else {
                        alarmManager.set(AlarmManager.RTC, nextMinute, pendingIntent)
                    }
                } else {
                    alarmManager.setAndAllowWhileIdle(AlarmManager.RTC, nextMinute, pendingIntent)
                }
            } catch (_: Throwable) {}
        }
    }

    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)
        scheduleNextMinuteAlarm(context)
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
