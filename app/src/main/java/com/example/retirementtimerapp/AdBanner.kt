package com.example.retirementtimerapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun BottomAdBanner(height: Dp = 100.dp) {
    // Тестовый баннер (заглушка)
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(height)
            .background(Color.Gray),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Test Ad - 320x${height.value.toInt()}",
            color = Color.White,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )
    }
}
