package com.example.retirementtimerapp

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.retirementtimerapp.ui.theme.RetirementTimerAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Прозрачный статус-бар и СВЕТЛЫЕ иконки для темного фона приложения
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(Color.TRANSPARENT)
        )
        setContent {
            RetirementTimerAppTheme {
                WorkTimeScreen()
            }
        }
    }
}
