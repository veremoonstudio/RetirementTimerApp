@file:JvmName("FifteenSettingsDesignOptionsPreviewDotFormat")
package com.veremoonstudio.retirementtimer

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.veremoonstudio.retirementtimer.ui.theme.CormorantGaramond
import com.veremoonstudio.retirementtimer.ui.theme.PlayfairDisplay
import com.veremoonstudio.retirementtimer.ui.theme.RetirementColors
import com.veremoonstudio.retirementtimer.ui.theme.RetirementTimerAppTheme

@Composable
fun FifteenSettingsDesignOptionsPreviewScreen() {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(RetirementColors.Background)
            .verticalScroll(scrollState)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Text(
            text = "15 ВАРИАНТОВ ДИЗАЙНА СТРАНИЦЫ НАСТРОЕК",
            style = TextStyle(
                fontFamily = PlayfairDisplay,
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                color = RetirementColors.Gold,
                textAlign = TextAlign.Center,
                letterSpacing = 1.sp
            )
        )

        SettingsVariant1Content()
        SettingsVariant2Content()
        SettingsVariant3Content()
        SettingsVariant4Content()
        SettingsVariant5Content()
        SettingsVariant6Content()
        SettingsVariant7Content()
        SettingsVariant8Content()
        SettingsVariant9Content()
        SettingsVariant10Content()
        SettingsVariant11Content()
        SettingsVariant12Content()
        SettingsVariant13Content()
        SettingsVariant14Content()
        SettingsVariant15Content()
    }
}

// VARIANT 1: Classic Gold Card Sections (Structured)
@Composable
private fun SettingsVariant1Content() {
    SettingsCardPreviewContainer(
        title = "Вариант 1: Элегантные золотые карточки секций (Классический)",
        description = "Каждый блок настроек помещен в отдельную тёмную карточку с золотой тонкой рамкой (1.dp) и бежевыми кнопками дней недели."
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            SampleWorkDaysRow()
            SampleTimeRow("Start Time", "09:00")
            SampleTimeRow("End Time", "18:00")
        }
    }
}

// VARIANT 2: Compact Integrated Dashboard
@Composable
private fun SettingsVariant2Content() {
    SettingsCardPreviewContainer(
        title = "Вариант 2: Интегрированный дашборд с чипами статистики",
        description = "Вверху отображается плашка суммарной статистики (31 день отпуска, 12 праздников), снизу — сплошной список настроек."
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .background(RetirementColors.Gold.copy(alpha = 0.12f))
                    .border(1.dp, RetirementColors.Gold, RoundedCornerShape(20.dp))
                    .padding(horizontal = 14.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("VACATION: 31 DAYS", style = TextStyle(fontFamily = PlayfairDisplay, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = RetirementColors.Gold))
                Text("HOLIDAYS: 12", style = TextStyle(fontFamily = PlayfairDisplay, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = RetirementColors.Gold))
            }
            SampleWorkDaysRow()
        }
    }
}

// VARIANT 3: Glassmorphic Glass Containers
@Composable
private fun SettingsVariant3Content() {
    SettingsCardPreviewContainer(
        title = "Вариант 3: Стеклянный глянец (Glassmorphic White Glow)",
        description = "Полупрозрачные стеклянные контейнеры со светлым кантом (`White 40%`) и размытой белой подложкой."
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(14.dp))
                .background(Color.White.copy(alpha = 0.08f))
                .border(1.dp, Color.White.copy(alpha = 0.4f), RoundedCornerShape(14.dp))
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            SampleTimeRow("Working Hours", "09:00 - 18:00")
        }
    }
}

// VARIANT 4: Split Two-Column Grid
@Composable
private fun SettingsVariant4Content() {
    SettingsCardPreviewContainer(
        title = "Вариант 4: Двухколоночная сетка часов работы",
        description = "Время начала ('Start Time') и завершения ('End Time') разбиты на 2 компактные симметричные карточки."
    ) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color.White.copy(alpha = 0.04f))
                    .border(1.dp, RetirementColors.Gold.copy(alpha = 0.3f), RoundedCornerShape(10.dp))
                    .padding(10.dp)
            ) {
                Column {
                    Text("START", style = TextStyle(fontSize = 11.sp, color = RetirementColors.Gold, fontWeight = FontWeight.Bold))
                    Text("09:00", style = TextStyle(fontFamily = PlayfairDisplay, fontSize = 18.sp, color = RetirementColors.Cream, fontWeight = FontWeight.Bold))
                }
            }
            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color.White.copy(alpha = 0.04f))
                    .border(1.dp, RetirementColors.Gold.copy(alpha = 0.3f), RoundedCornerShape(10.dp))
                    .padding(10.dp)
            ) {
                Column {
                    Text("END", style = TextStyle(fontSize = 11.sp, color = RetirementColors.Gold, fontWeight = FontWeight.Bold))
                    Text("18:00", style = TextStyle(fontFamily = PlayfairDisplay, fontSize = 18.sp, color = RetirementColors.Cream, fontWeight = FontWeight.Bold))
                }
            }
        }
    }
}

// VARIANT 5: Left Gold Accent Bar Sections
@Composable
private fun SettingsVariant5Content() {
    SettingsCardPreviewContainer(
        title = "Вариант 5: Левые золотые индикаторные полосы (Accent Bar)",
        description = "Слева у каждого блока карточки размещена вертикальная золотая полоса-акцент 4.dp."
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(Color.White.copy(alpha = 0.04f))
                .border(1.dp, RetirementColors.Gold.copy(alpha = 0.3f), RoundedCornerShape(8.dp))
                .padding(start = 4.dp, end = 12.dp, top = 10.dp, bottom = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(modifier = Modifier.width(4.dp).height(32.dp).clip(RoundedCornerShape(2.dp)).background(RetirementColors.Gold))
            Spacer(modifier = Modifier.width(10.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text("WORK SCHEDULE", style = TextStyle(fontSize = 11.sp, color = RetirementColors.Gold, fontWeight = FontWeight.Bold))
                Text("Mon - Fri (09:00 - 18:00)", style = TextStyle(fontFamily = PlayfairDisplay, fontSize = 15.sp, color = RetirementColors.Cream))
            }
        }
    }
}

// VARIANT 6: Pill-Shape Capsule Cards (24.dp)
@Composable
private fun SettingsVariant6Content() {
    SettingsCardPreviewContainer(
        title = "Вариант 6: Капсульные закругленные блоки (Pill 24.dp)",
        description = "Все карточки настроек имеют фирменное капсульное закругление 24.dp в стиле премиум кнопки."
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(24.dp))
                .background(RetirementColors.Gold.copy(alpha = 0.08f))
                .border(1.dp, RetirementColors.Gold, RoundedCornerShape(24.dp))
                .padding(horizontal = 16.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Work Days", style = TextStyle(fontFamily = PlayfairDisplay, fontSize = 15.sp, fontWeight = FontWeight.Bold, color = RetirementColors.Gold))
            Text("5 Days / Week", style = TextStyle(fontSize = 13.sp, color = RetirementColors.Cream))
        }
    }
}

// VARIANT 7: Minimalist Inset Border Lines
@Composable
private fun SettingsVariant7Content() {
    SettingsCardPreviewContainer(
        title = "Вариант 7: Минималистичные тонкие линии (Linear Dividers)",
        description = "Без тяжелых фоновых плашек — настройки разделены утонченными золотыми линиями 1.dp."
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Start Time", style = TextStyle(fontFamily = PlayfairDisplay, fontSize = 16.sp, color = RetirementColors.Cream))
                Text("09:00", style = TextStyle(fontFamily = PlayfairDisplay, fontSize = 16.sp, color = RetirementColors.Gold, fontWeight = FontWeight.Bold))
            }
            HorizontalDivider(color = RetirementColors.Gold.copy(alpha = 0.25f), thickness = 1.dp)
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("End Time", style = TextStyle(fontFamily = PlayfairDisplay, fontSize = 16.sp, color = RetirementColors.Cream))
                Text("18:00", style = TextStyle(fontFamily = PlayfairDisplay, fontSize = 16.sp, color = RetirementColors.Gold, fontWeight = FontWeight.Bold))
            }
        }
    }
}

// VARIANT 8: Contrast Dark Inverted Cards (#18171C)
@Composable
private fun SettingsVariant8Content() {
    SettingsCardPreviewContainer(
        title = "Вариант 8: Контрастные темно-угольные карточки (#18171C)",
        description = "Выразительный тёмно-угольный фон блоков `#18171C` с контрастными бежевыми элементами."
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .background(Color(0xFF18171C))
                .border(1.5.dp, RetirementColors.Gold, RoundedCornerShape(10.dp))
                .padding(12.dp)
        ) {
            SampleTimeRow("Vacation Total", "31 Days")
        }
    }
}

// VARIANT 9: Accordion Expandable Sections
@Composable
private fun SettingsVariant9Content() {
    SettingsCardPreviewContainer(
        title = "Вариант 9: Сворачиваемые секции-аккордеоны (Expandable Header)",
        description = "Секции настроек можно сворачивать и разворачивать по клику на заголовок со стрелкой."
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .background(Color.White.copy(alpha = 0.04f))
                .border(1.dp, RetirementColors.Gold.copy(alpha = 0.3f), RoundedCornerShape(10.dp))
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text("HOLIDAYS & OFF DAYS", style = TextStyle(fontFamily = PlayfairDisplay, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = RetirementColors.Gold))
                Icon(imageVector = Icons.Default.KeyboardArrowUp, contentDescription = null, tint = RetirementColors.Gold)
            }
            Text("12 Active Holidays Configured", style = TextStyle(fontSize = 12.sp, color = Color.White.copy(alpha = 0.6f)))
        }
    }
}

// VARIANT 10: Horizontal Tabbed Navigation
@Composable
private fun SettingsVariant10Content() {
    SettingsCardPreviewContainer(
        title = "Вариант 10: Вкладки категорий [WORK | VACATION | HOLIDAYS]",
        description = "Верхняя панель переключения вкладок позволяет быстро переходить между группами настроек."
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(20.dp))
                .background(Color.White.copy(alpha = 0.05f))
                .padding(3.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box(modifier = Modifier.weight(1f).clip(RoundedCornerShape(16.dp)).background(RetirementColors.Gold).padding(vertical = 6.dp), contentAlignment = Alignment.Center) {
                Text("WORK", style = TextStyle(fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFF111315)))
            }
            Box(modifier = Modifier.weight(1f).padding(vertical = 6.dp), contentAlignment = Alignment.Center) {
                Text("VACATION", style = TextStyle(fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color.White.copy(alpha = 0.6f)))
            }
            Box(modifier = Modifier.weight(1f).padding(vertical = 6.dp), contentAlignment = Alignment.Center) {
                Text("HOLIDAYS", style = TextStyle(fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color.White.copy(alpha = 0.6f)))
            }
        }
    }
}

// VARIANT 11: Hero Stats Summary Header
@Composable
private fun SettingsVariant11Content() {
    SettingsCardPreviewContainer(
        title = "Вариант 11: Заголовок крупной статистики (Hero Summary Card)",
        description = "Верхняя карточка карточка с крупным отображением рабочих часов в год и баланса отпусков."
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(Brush.verticalGradient(listOf(Color(0xFF2B2830), Color(0xFF1C1A20))))
                .border(1.dp, RetirementColors.Gold, RoundedCornerShape(12.dp))
                .padding(14.dp)
        ) {
            Column {
                Text("ANNUAL WORKTIME STATS", style = TextStyle(fontSize = 10.sp, fontWeight = FontWeight.Bold, color = RetirementColors.Gold))
                Spacer(modifier = Modifier.height(4.dp))
                Text("2,080 Work Hours / Year", style = TextStyle(fontFamily = PlayfairDisplay, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = RetirementColors.Cream))
            }
        }
    }
}

// VARIANT 12: Premium Gold Gradient Border Cards
@Composable
private fun SettingsVariant12Content() {
    SettingsCardPreviewContainer(
        title = "Вариант 12: Золотые градиентные рамки карточек",
        description = "Роскошный градиентный ободок от насыщенного золота к светлой слоновой кости."
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .background(Color.White.copy(alpha = 0.05f))
                .border(1.5.dp, Brush.horizontalGradient(listOf(RetirementColors.Gold, RetirementColors.Cream, RetirementColors.Gold)), RoundedCornerShape(10.dp))
                .padding(12.dp)
        ) {
            SampleTimeRow("Schedule Mode", "Custom Work Days")
        }
    }
}

// VARIANT 13: Floating Action Badges
@Composable
private fun SettingsVariant13Content() {
    SettingsCardPreviewContainer(
        title = "Вариант 13: Чипы-бейджи статусов (Pill Badges)",
        description = "Интерактивные компактные капсулы статусов с иконками галочек."
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Box(modifier = Modifier.clip(RoundedCornerShape(16.dp)).background(RetirementColors.Gold).padding(horizontal = 10.dp, vertical = 5.dp)) {
                Text("✔ 5 Work Days", style = TextStyle(fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFF111315)))
            }
            Box(modifier = Modifier.clip(RoundedCornerShape(16.dp)).background(Color.White.copy(alpha = 0.08f)).border(1.dp, RetirementColors.Gold.copy(alpha = 0.4f), RoundedCornerShape(16.dp)).padding(horizontal = 10.dp, vertical = 5.dp)) {
                Text("12 Holidays", style = TextStyle(fontSize = 11.sp, fontWeight = FontWeight.Bold, color = RetirementColors.Gold))
            }
        }
    }
}

// VARIANT 14: Serif Roman Monumental Typography
@Composable
private fun SettingsVariant14Content() {
    SettingsCardPreviewContainer(
        title = "Вариант 14: Монументальная римская типографика (Cormorant Serif)",
        description = "Классический академический стиль с крупными римскими засечками и прописными золотыми буквами."
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Text("WORKTIME CONFIGURATION", style = TextStyle(fontFamily = CormorantGaramond, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = RetirementColors.Gold, letterSpacing = 2.sp))
            SampleTimeRow("Daily Shift", "09:00 AM - 06:00 PM")
        }
    }
}

// VARIANT 15: Streamlined Single-Card Sheet
@Composable
private fun SettingsVariant15Content() {
    SettingsCardPreviewContainer(
        title = "Вариант 15: Монолитный единый лист (Single Master Sheet)",
        description = "Единая большая премиум-карта для всех настроек с мягкими разделителями секций."
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(Color(0xFF1A191F))
                .border(1.dp, RetirementColors.Gold.copy(alpha = 0.4f), RoundedCornerShape(16.dp))
                .padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            SampleTimeRow("Start Time", "09:00 AM")
            HorizontalDivider(color = Color.White.copy(alpha = 0.08f))
            SampleTimeRow("End Time", "06:00 PM")
        }
    }
}

@Composable
private fun SampleWorkDaysRow() {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        listOf("M", "T", "W", "T", "F", "S", "S").forEachIndexed { i, day ->
            val isWork = i < 5
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(if (isWork) RetirementColors.Gold else Color.Transparent)
                    .border(if (isWork) 0.dp else 1.dp, RetirementColors.Gold.copy(alpha = 0.3f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(text = day, style = TextStyle(fontSize = 13.sp, fontWeight = FontWeight.Bold, color = if (isWork) Color(0xFF111315) else RetirementColors.Cream))
            }
        }
    }
}

@Composable
private fun SampleTimeRow(label: String, value: String) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
        Text(text = label, style = TextStyle(fontFamily = PlayfairDisplay, fontSize = 15.sp, color = RetirementColors.Cream))
        Text(text = value, style = TextStyle(fontFamily = PlayfairDisplay, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = RetirementColors.Gold))
    }
}

@Composable
private fun SettingsCardPreviewContainer(
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

// INDIVIDUAL PREVIEWS FOR ALL 15 SETTINGS DESIGN VARIANTS
@Preview(showBackground = true, widthDp = 380, heightDp = 260, name = "1: Classic Gold Cards")
@Composable
fun PreviewSettings1() { RetirementTimerAppTheme { Box(modifier = Modifier.background(RetirementColors.Background).padding(16.dp)) { SettingsVariant1Content() } } }

@Preview(showBackground = true, widthDp = 380, heightDp = 240, name = "2: Integrated Dashboard")
@Composable
fun PreviewSettings2() { RetirementTimerAppTheme { Box(modifier = Modifier.background(RetirementColors.Background).padding(16.dp)) { SettingsVariant2Content() } } }

@Preview(showBackground = true, widthDp = 380, heightDp = 200, name = "3: Glassmorphic Glow")
@Composable
fun PreviewSettings3() { RetirementTimerAppTheme { Box(modifier = Modifier.background(RetirementColors.Background).padding(16.dp)) { SettingsVariant3Content() } } }

@Preview(showBackground = true, widthDp = 380, heightDp = 200, name = "4: Two-Column Grid")
@Composable
fun PreviewSettings4() { RetirementTimerAppTheme { Box(modifier = Modifier.background(RetirementColors.Background).padding(16.dp)) { SettingsVariant4Content() } } }

@Preview(showBackground = true, widthDp = 380, heightDp = 200, name = "5: Left Accent Bar")
@Composable
fun PreviewSettings5() { RetirementTimerAppTheme { Box(modifier = Modifier.background(RetirementColors.Background).padding(16.dp)) { SettingsVariant5Content() } } }

@Preview(showBackground = true, widthDp = 380, heightDp = 200, name = "6: Pill Capsule Cards 24.dp")
@Composable
fun PreviewSettings6() { RetirementTimerAppTheme { Box(modifier = Modifier.background(RetirementColors.Background).padding(16.dp)) { SettingsVariant6Content() } } }

@Preview(showBackground = true, widthDp = 380, heightDp = 200, name = "7: Minimalist Linear Dividers")
@Composable
fun PreviewSettings7() { RetirementTimerAppTheme { Box(modifier = Modifier.background(RetirementColors.Background).padding(16.dp)) { SettingsVariant7Content() } } }

@Preview(showBackground = true, widthDp = 380, heightDp = 200, name = "8: Contrast Dark Cards #18171C")
@Composable
fun PreviewSettings8() { RetirementTimerAppTheme { Box(modifier = Modifier.background(RetirementColors.Background).padding(16.dp)) { SettingsVariant8Content() } } }

@Preview(showBackground = true, widthDp = 380, heightDp = 200, name = "9: Accordion Expandable")
@Composable
fun PreviewSettings9() { RetirementTimerAppTheme { Box(modifier = Modifier.background(RetirementColors.Background).padding(16.dp)) { SettingsVariant9Content() } } }

@Preview(showBackground = true, widthDp = 380, heightDp = 200, name = "10: Category Tabs Navigation")
@Composable
fun PreviewSettings10() { RetirementTimerAppTheme { Box(modifier = Modifier.background(RetirementColors.Background).padding(16.dp)) { SettingsVariant10Content() } } }

@Preview(showBackground = true, widthDp = 380, heightDp = 220, name = "11: Hero Stats Summary Header")
@Composable
fun PreviewSettings11() { RetirementTimerAppTheme { Box(modifier = Modifier.background(RetirementColors.Background).padding(16.dp)) { SettingsVariant11Content() } } }

@Preview(showBackground = true, widthDp = 380, heightDp = 200, name = "12: Gold Gradient Border")
@Composable
fun PreviewSettings12() { RetirementTimerAppTheme { Box(modifier = Modifier.background(RetirementColors.Background).padding(16.dp)) { SettingsVariant12Content() } } }

@Preview(showBackground = true, widthDp = 380, heightDp = 200, name = "13: Floating Action Badges")
@Composable
fun PreviewSettings13() { RetirementTimerAppTheme { Box(modifier = Modifier.background(RetirementColors.Background).padding(16.dp)) { SettingsVariant13Content() } } }

@Preview(showBackground = true, widthDp = 380, heightDp = 220, name = "14: Roman Serif Typography")
@Composable
fun PreviewSettings14() { RetirementTimerAppTheme { Box(modifier = Modifier.background(RetirementColors.Background).padding(16.dp)) { SettingsVariant14Content() } } }

@Preview(showBackground = true, widthDp = 380, heightDp = 220, name = "15: Streamlined Single Master Sheet")
@Composable
fun PreviewSettings15() { RetirementTimerAppTheme { Box(modifier = Modifier.background(RetirementColors.Background).padding(16.dp)) { SettingsVariant15Content() } } }
