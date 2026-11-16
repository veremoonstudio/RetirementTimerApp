@file:JvmName("FiveAdMonetizationOptionsPreviewDotFormat")
package com.veremoonstudio.retirementtimer

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayCircleFilled
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.veremoonstudio.retirementtimer.ui.theme.PlayfairDisplay
import com.veremoonstudio.retirementtimer.ui.theme.RetirementColors
import com.veremoonstudio.retirementtimer.ui.theme.RetirementTimerAppTheme

@Composable
fun FiveAdMonetizationOptionsPreviewScreen() {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(RetirementColors.Background)
            .verticalScroll(scrollState)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Text(
            text = "5 ВАРИАНТОВ ДОПОЛНИТЕЛЬНОЙ МОНЕТИЗАЦИИ РЕКЛАМОЙ",
            style = TextStyle(
                fontFamily = PlayfairDisplay,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = RetirementColors.Gold,
                textAlign = TextAlign.Center,
                letterSpacing = 1.sp
            )
        )

        AdOption1Content()
        AdOption2Content()
        AdOption3Content()
        AdOption4Content()
        AdOption5Content()
    }
}

// AD OPTION 1: Native Banner Card on Main Screen
@Composable
private fun AdOption1Content() {
    AdCardContainer(
        title = "Вариант 1: Нативный плавающий баннер на главном экране",
        description = "• Формат: AdMob Native Banner в стилистике приложения.\n• Размещение: Под таймером обратного отсчета.\n• eCPM: Выше стандартного баннера, гармонично вписывается в тёмно-золотой дизайн."
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(Color(0xFF212121))
                .border(1.dp, RetirementColors.Gold.copy(alpha = 0.3f), RoundedCornerShape(12.dp))
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .background(RetirementColors.Gold)
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                    Text("SPONSORED", style = TextStyle(fontSize = 9.sp, fontWeight = FontWeight.Bold, color = Color(0xFF111315)))
                }
                Text("Ad", style = TextStyle(fontSize = 11.sp, color = Color.White.copy(alpha = 0.5f)))
            }
            Text(
                text = "Plan Your Dream Retirement Today with Expert Wealth Advice",
                style = TextStyle(fontFamily = PlayfairDisplay, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = RetirementColors.Cream)
            )
            Button(
                onClick = {},
                colors = ButtonDefaults.buttonColors(containerColor = RetirementColors.Gold),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth().height(36.dp)
            ) {
                Text("Learn More", style = TextStyle(fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFF111315)))
            }
        }
    }
}

// AD OPTION 2: Rewarded Video for Background Unlock (24h)
@Composable
private fun AdOption2Content() {
    AdCardContainer(
        title = "Вариант 2: Rewarded Video — Временный разблокировщик фона",
        description = "• Формат: Видеореклама с вознаграждением (Rewarded Video).\n• Размещение: Экран выбора фонов ('Смотреть рекламу и разблокировать Palm Beach на 24 часа').\n• eCPM: Самый высокий eCPM ($15–$35). Высокая конверсия пользователей без подписки."
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(Color(0xFF1C1B20))
                .border(1.dp, RetirementColors.Gold, RoundedCornerShape(12.dp))
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(imageVector = Icons.Default.PlayCircleFilled, contentDescription = "Watch Ad", tint = RetirementColors.Gold, modifier = Modifier.size(22.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text("UNLOCK PALM BEACH (24H)", style = TextStyle(fontFamily = PlayfairDisplay, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = RetirementColors.Gold))
            }
            Text(
                text = "Watch a 15-sec video to get free 24-hour access to premium background!",
                style = TextStyle(fontSize = 12.sp, color = Color.White.copy(alpha = 0.8f), textAlign = TextAlign.Center)
            )
            Button(
                onClick = {},
                colors = ButtonDefaults.buttonColors(containerColor = RetirementColors.Gold),
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier.fillMaxWidth().height(38.dp)
            ) {
                Text("WATCH VIDEO TO UNLOCK", style = TextStyle(fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFF111315)))
            }
        }
    }
}

// AD OPTION 3: Interstitial Ad on Settings Confirmation
@Composable
private fun AdOption3Content() {
    AdCardContainer(
        title = "Вариант 3: Межстраничная реклама (Interstitial Ad)",
        description = "• Формат: Межстраничное окно (Interstitial Ad).\n• Размещение: После сохранения новых дат отпуска или изменения целевой даты.\n• eCPM: Выход $10–$20. Ограничивается тайм-аутом 1 показ в 5-10 минут."
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(Color.White.copy(alpha = 0.05f))
                .border(1.dp, Color.White.copy(alpha = 0.3f), RoundedCornerShape(12.dp))
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text("✔ Vacation Period Saved", style = TextStyle(fontFamily = PlayfairDisplay, fontSize = 15.sp, fontWeight = FontWeight.Bold, color = RetirementColors.Gold))
            Text("Full-screen Interstitial displays after confirmation dialog dismisses.", style = TextStyle(fontSize = 12.sp, color = Color.White.copy(alpha = 0.7f)))
        }
    }
}

// AD OPTION 4: Inline Native Ad Unit in Settings
@Composable
private fun AdOption4Content() {
    AdCardContainer(
        title = "Вариант 4: Встроенный нативный блок в Меню Настроек",
        description = "• Формат: AdMob Native Advanced (In-feed).\n• Размещение: Между секциями настроек (например, между Выходными и Отпусками).\n• eCPM: 100% заменяемость, выглядит как нативная рекомендация сервиса."
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(Color(0xFF26242B))
                .border(1.dp, RetirementColors.Gold.copy(alpha = 0.4f), RoundedCornerShape(12.dp))
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(imageVector = Icons.Default.Star, contentDescription = "Recommended", tint = RetirementColors.Gold, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text("RECOMMENDED FOR YOU", style = TextStyle(fontSize = 10.sp, fontWeight = FontWeight.Bold, color = RetirementColors.Gold))
            }
            Text("Calculate Your Pension & Investment Growth with Smart Planner", style = TextStyle(fontFamily = PlayfairDisplay, fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = RetirementColors.Cream))
        }
    }
}

// AD OPTION 5: App Open Ad on Cold Start
@Composable
private fun AdOption5Content() {
    AdCardContainer(
        title = "Вариант 5: Реклама при входе в приложение (App Open Ad)",
        description = "• Формат: AdMob App Open Ad.\n• Размещение: Показывается при старте приложения или возврате из фонового режима.\n• eCPM: Максимальное количество просмотров среди активных пользователей."
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(Color.White.copy(alpha = 0.05f))
                .border(1.dp, Color.White.copy(alpha = 0.3f), RoundedCornerShape(12.dp))
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text("📱 App Launch Screen Ad", style = TextStyle(fontFamily = PlayfairDisplay, fontSize = 15.sp, fontWeight = FontWeight.Bold, color = RetirementColors.Gold))
            Text("Displays on cold start before entering the main countdown screen.", style = TextStyle(fontSize = 12.sp, color = Color.White.copy(alpha = 0.7f)))
        }
    }
}

@Composable
private fun AdCardContainer(
    title: String,
    description: String,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White.copy(alpha = 0.04f))
            .border(1.dp, RetirementColors.Gold.copy(alpha = 0.25f), RoundedCornerShape(12.dp))
            .padding(14.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(
            text = title,
            style = TextStyle(
                fontFamily = PlayfairDisplay,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = RetirementColors.Gold
            )
        )
        Text(
            text = description,
            style = TextStyle(
                fontFamily = FontFamily.SansSerif,
                fontSize = 12.sp,
                fontWeight = FontWeight.Normal,
                color = Color.White.copy(alpha = 0.7f),
                lineHeight = 16.sp
            )
        )
        Spacer(modifier = Modifier.height(2.dp))
        content()
    }
}

// INDIVIDUAL PREVIEWS FOR EACH AD OPTION
@Preview(showBackground = true, widthDp = 380, heightDp = 330, name = "1: Native Banner Card")
@Composable
fun PreviewAdOption1() { RetirementTimerAppTheme { Box(modifier = Modifier.background(RetirementColors.Background).padding(16.dp)) { AdOption1Content() } } }

@Preview(showBackground = true, widthDp = 380, heightDp = 330, name = "2: Rewarded Video Unlock")
@Composable
fun PreviewAdOption2() { RetirementTimerAppTheme { Box(modifier = Modifier.background(RetirementColors.Background).padding(16.dp)) { AdOption2Content() } } }

@Preview(showBackground = true, widthDp = 380, heightDp = 260, name = "3: Interstitial Ad")
@Composable
fun PreviewAdOption3() { RetirementTimerAppTheme { Box(modifier = Modifier.background(RetirementColors.Background).padding(16.dp)) { AdOption3Content() } } }

@Preview(showBackground = true, widthDp = 380, heightDp = 260, name = "4: Inline Native Ad Unit")
@Composable
fun PreviewAdOption4() { RetirementTimerAppTheme { Box(modifier = Modifier.background(RetirementColors.Background).padding(16.dp)) { AdOption4Content() } } }

@Preview(showBackground = true, widthDp = 380, heightDp = 260, name = "5: App Open Ad")
@Composable
fun PreviewAdOption5() { RetirementTimerAppTheme { Box(modifier = Modifier.background(RetirementColors.Background).padding(16.dp)) { AdOption5Content() } } }
