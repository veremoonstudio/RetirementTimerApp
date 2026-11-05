package com.veremoonstudio.retirementtimer

import android.app.Application
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.glance.appwidget.updateAll
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RetirementApp : Application() {

    override fun onCreate() {
        super.onCreate()

        try {
            RetirementWidgetReceiver.scheduleNextMinuteAlarm(this)
        } catch (_: Throwable) {}

        val filter = IntentFilter().apply {
            addAction(Intent.ACTION_SCREEN_ON)
            addAction(Intent.ACTION_USER_PRESENT)
            addAction(Intent.ACTION_TIME_TICK)
        }

        val screenReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                try {
                    RetirementWidgetReceiver.scheduleNextMinuteAlarm(context)
                    CoroutineScope(Dispatchers.IO).launch {
                        try {
                            RetirementGlanceWidget().updateAll(context)
                        } catch (_: Throwable) {}
                    }
                } catch (_: Throwable) {}
            }
        }

        try {
            registerReceiver(screenReceiver, filter)
        } catch (_: Throwable) {}
    }
}
