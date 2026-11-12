package com.veremoonstudio.retirementtimer

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.veremoonstudio.retirementtimer.ui.theme.CinzelFontFamily
import com.veremoonstudio.retirementtimer.ui.theme.CormorantGaramond
import com.veremoonstudio.retirementtimer.ui.theme.PlayfairDisplay
import com.veremoonstudio.retirementtimer.ui.theme.RetirementColors
import com.veremoonstudio.retirementtimer.ui.theme.RetirementTimerAppTheme

@Composable
fun FullSettingsPageButtonPreviewPart1Screen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(RetirementColors.Background)
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Text(
            text = "ВАРИАНТЫ КНОПОК 1 - 5 НА СТРАНИЦЕ НАСТРОЕК",
            style = TextStyle(
                fontFamily = PlayfairDisplay,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = RetirementColors.Gold,
                textAlign = TextAlign.Center,
                letterSpacing = 1.sp
            ),
            modifier = Modifier.padding(vertical = 8.dp)
        )

        // Variant 1
        SettingsSectionPreviewCard(
            variantNumber = 1,
            title = "Вариант 1: Тёмное стекло с тонким золотом (1dp)",
            description = "Фон 2% белого + тонкая рамка Gold 0.45"
        ) {
            ButtonVariant1(text = "Add Vacation Period")
            Spacer(modifier = Modifier.height(8.dp))
            ButtonVariant1(text = "Add New Holiday")
        }

        // Variant 2
        SettingsSectionPreviewCard(
            variantNumber = 2,
            title = "Вариант 2: Изумрудно-зеленый акцент",
            description = "Рамка и текст в цвет календаря и бейджей отпуска"
        ) {
            ButtonVariant2(text = "Add Vacation Period")
            Spacer(modifier = Modifier.height(8.dp))
            ButtonVariant2(text = "Add New Holiday")
        }

        // Variant 3
        SettingsSectionPreviewCard(
            variantNumber = 3,
            title = "Вариант 3: Премиальная золотая капсула (Pill 24dp)",
            description = "Закругленная форма капсулы с золотой рамкой"
        ) {
            ButtonVariant3(text = "Add Vacation Period")
            Spacer(modifier = Modifier.height(8.dp))
            ButtonVariant3(text = "Add New Holiday")
        }

        // Variant 4
        SettingsSectionPreviewCard(
            variantNumber = 4,
            title = "Вариант 4: Яркая закрашенная золотая кнопка (Текущая)",
            description = "Сплошной золотой градиент и тёмный контрастный текст"
        ) {
            ButtonVariant4(text = "ADD VACATION PERIOD")
            Spacer(modifier = Modifier.height(8.dp))
            ButtonVariant4(text = "ADD NEW HOLIDAY")
        }

        // Variant 5
        SettingsSectionPreviewCard(
            variantNumber = 5,
            title = "Вариант 5: Круглый золотой бейдж плюсика",
            description = "Слева круглая подсвеченная иконка + в круге"
        ) {
            ButtonVariant5(text = "Add Vacation Period")
            Spacer(modifier = Modifier.height(8.dp))
            ButtonVariant5(text = "Add New Holiday")
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
fun FullSettingsPageButtonPreviewPart2Screen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(RetirementColors.Background)
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Text(
            text = "ВАРИАНТЫ КНОПОК 6 - 10 НА СТРАНИЦЕ НАСТРОЕК",
            style = TextStyle(
                fontFamily = PlayfairDisplay,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = RetirementColors.Gold,
                textAlign = TextAlign.Center,
                letterSpacing = 1.sp
            ),
            modifier = Modifier.padding(vertical = 8.dp)
        )

        // Variant 6
        SettingsSectionPreviewCard(
            variantNumber = 6,
            title = "Вариант 6: Римский монументальный (Cinzel All-Caps)",
            description = "Античный шрифтовой стиль с широкими буквами"
        ) {
            ButtonVariant6(text = "+ ADD VACATION PERIOD")
            Spacer(modifier = Modifier.height(8.dp))
            ButtonVariant6(text = "+ ADD NEW HOLIDAY")
        }

        // Variant 7
        SettingsSectionPreviewCard(
            variantNumber = 7,
            title = "Вариант 7: Подсвеченный бежевый фон с золотом",
            description = "Фон с теплым оттенком Cream"
        ) {
            ButtonVariant7(text = "Add Vacation Period")
            Spacer(modifier = Modifier.height(8.dp))
            ButtonVariant7(text = "Add New Holiday")
        }

        // Variant 8
        SettingsSectionPreviewCard(
            variantNumber = 8,
            title = "Вариант 8: Двойная тонкая золотая рамка",
            description = "Двойной контур в стиле люксовых эмблем"
        ) {
            ButtonVariant8(text = "Add Vacation Period")
            Spacer(modifier = Modifier.height(8.dp))
            ButtonVariant8(text = "Add New Holiday")
        }

        // Variant 9
        SettingsSectionPreviewCard(
            variantNumber = 9,
            title = "Вариант 9: Минималистичный системный Sans-Serif",
            description = "Современный лаконичный стиль"
        ) {
            ButtonVariant9(text = "+ Add Vacation Period")
            Spacer(modifier = Modifier.height(8.dp))
            ButtonVariant9(text = "+ Add New Holiday")
        }

        // Variant 10
        SettingsSectionPreviewCard(
            variantNumber = 10,
            title = "Вариант 10: Золотое градиентное свечение (Gradient Tint)",
            description = "Мягкий градиентный золотистый отблеск"
        ) {
            ButtonVariant10(text = "Add Vacation Period")
            Spacer(modifier = Modifier.height(8.dp))
            ButtonVariant10(text = "Add New Holiday")
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
private fun SettingsSectionPreviewCard(
    variantNumber: Int,
    title: String,
    description: String,
    content: @Composable () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White.copy(alpha = 0.03f))
            .border(1.dp, RetirementColors.Gold.copy(alpha = 0.35f), RoundedCornerShape(12.dp))
            .padding(14.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "Вариант $variantNumber: $title",
            style = TextStyle(fontFamily = PlayfairDisplay, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = RetirementColors.Gold)
        )
        Text(
            text = description,
            style = TextStyle(fontFamily = CormorantGaramond, fontSize = 12.sp, color = RetirementColors.Cream.copy(alpha = 0.7f)),
            modifier = Modifier.padding(bottom = 6.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(Color.White.copy(alpha = 0.05f))
                .border(1.dp, RetirementColors.Gold.copy(alpha = 0.3f), RoundedCornerShape(8.dp))
                .padding(horizontal = 14.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(3.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text("VACATION", style = TextStyle(fontFamily = PlayfairDisplay, fontSize = 12.5.sp, fontWeight = FontWeight.Bold, color = RetirementColors.Gold))
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .background(Color(0xFF10B981).copy(alpha = 0.22f))
                            .border(1.dp, Color(0xFF10B981), RoundedCornerShape(4.dp))
                            .padding(horizontal = 6.dp, vertical = 1.dp)
                    ) {
                        Text("NOV", style = TextStyle(fontFamily = FontFamily.SansSerif, fontSize = 10.5.sp, fontWeight = FontWeight.Bold, color = Color(0xFF34D399)))
                    }
                }
                Text("13.11.2026 – 19.11.2026", style = TextStyle(fontFamily = CormorantGaramond, fontSize = 14.sp, color = RetirementColors.Cream))
            }

            Box(
                modifier = Modifier
                    .width(72.dp)
                    .height(34.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .background(Color.White.copy(alpha = 0.03f))
                    .border(1.dp, RetirementColors.Gold.copy(alpha = 0.5f), RoundedCornerShape(6.dp)),
                contentAlignment = Alignment.Center
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    Icon(Icons.Default.CalendarMonth, contentDescription = null, tint = RetirementColors.Gold, modifier = Modifier.size(15.dp))
                    Text("5 d", style = TextStyle(fontFamily = PlayfairDisplay, fontSize = 15.sp, fontWeight = FontWeight.Bold, color = RetirementColors.Gold))
                }
            }
        }

        Spacer(modifier = Modifier.height(2.dp))

        content()
    }
}

@Composable
private fun ButtonVariant1(text: String) {
    Box(
        modifier = Modifier.fillMaxWidth().height(46.dp).clip(RoundedCornerShape(8.dp)).background(Color.White.copy(alpha = 0.02f)).border(1.dp, RetirementColors.Gold.copy(alpha = 0.45f), RoundedCornerShape(8.dp)),
        contentAlignment = Alignment.Center
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Icon(Icons.Default.Add, contentDescription = null, tint = RetirementColors.Gold, modifier = Modifier.size(18.dp))
            Text(text, style = TextStyle(fontFamily = PlayfairDisplay, fontSize = 13.5.sp, fontWeight = FontWeight.SemiBold, color = RetirementColors.Gold))
        }
    }
}

@Composable
private fun ButtonVariant2(text: String) {
    Box(
        modifier = Modifier.fillMaxWidth().height(46.dp).clip(RoundedCornerShape(8.dp)).background(Color(0xFF10B981).copy(alpha = 0.08f)).border(1.dp, Color(0xFF10B981).copy(alpha = 0.6f), RoundedCornerShape(8.dp)),
        contentAlignment = Alignment.Center
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Icon(Icons.Default.Add, contentDescription = null, tint = Color(0xFF34D399), modifier = Modifier.size(18.dp))
            Text(text, style = TextStyle(fontFamily = PlayfairDisplay, fontSize = 13.5.sp, fontWeight = FontWeight.Bold, color = Color(0xFF34D399)))
        }
    }
}

@Composable
private fun ButtonVariant3(text: String) {
    Box(
        modifier = Modifier.fillMaxWidth().height(46.dp).clip(RoundedCornerShape(23.dp)).background(Color.White.copy(alpha = 0.03f)).border(1.dp, RetirementColors.Gold.copy(alpha = 0.6f), RoundedCornerShape(23.dp)),
        contentAlignment = Alignment.Center
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Icon(Icons.Default.Add, contentDescription = null, tint = RetirementColors.Gold, modifier = Modifier.size(18.dp))
            Text(text, style = TextStyle(fontFamily = PlayfairDisplay, fontSize = 13.5.sp, fontWeight = FontWeight.Bold, color = RetirementColors.Gold))
        }
    }
}

@Composable
private fun ButtonVariant4(text: String) {
    Box(
        modifier = Modifier.fillMaxWidth().height(46.dp).clip(RoundedCornerShape(8.dp)).background(brush = Brush.horizontalGradient(listOf(Color(0xFFE5C78B), Color(0xFFD4B26F), Color(0xFFB5934E)))),
        contentAlignment = Alignment.Center
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Icon(Icons.Default.Add, contentDescription = null, tint = Color(0xFF111315), modifier = Modifier.size(18.dp))
            Text(text, style = TextStyle(fontFamily = PlayfairDisplay, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color(0xFF111315), letterSpacing = 1.sp))
        }
    }
}

@Composable
private fun ButtonVariant5(text: String) {
    Box(
        modifier = Modifier.fillMaxWidth().height(46.dp).clip(RoundedCornerShape(8.dp)).background(Color.White.copy(alpha = 0.02f)).border(1.dp, RetirementColors.Gold.copy(alpha = 0.35f), RoundedCornerShape(8.dp)),
        contentAlignment = Alignment.Center
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            Box(modifier = Modifier.size(22.dp).clip(CircleShape).background(RetirementColors.Gold.copy(alpha = 0.2f)).border(1.dp, RetirementColors.Gold, CircleShape), contentAlignment = Alignment.Center) {
                Icon(Icons.Default.Add, contentDescription = null, tint = RetirementColors.Gold, modifier = Modifier.size(13.dp))
            }
            Text(text, style = TextStyle(fontFamily = PlayfairDisplay, fontSize = 13.5.sp, fontWeight = FontWeight.SemiBold, color = RetirementColors.Gold))
        }
    }
}

@Composable
private fun ButtonVariant6(text: String) {
    Box(
        modifier = Modifier.fillMaxWidth().height(46.dp).clip(RoundedCornerShape(8.dp)).background(Color.White.copy(alpha = 0.02f)).border(1.dp, RetirementColors.Gold.copy(alpha = 0.45f), RoundedCornerShape(8.dp)),
        contentAlignment = Alignment.Center
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Icon(Icons.Default.Add, contentDescription = null, tint = RetirementColors.Gold, modifier = Modifier.size(16.dp))
            Text(text, style = TextStyle(fontFamily = CinzelFontFamily, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = RetirementColors.Gold, letterSpacing = 1.sp))
        }
    }
}

@Composable
private fun ButtonVariant7(text: String) {
    Box(
        modifier = Modifier.fillMaxWidth().height(46.dp).clip(RoundedCornerShape(8.dp)).background(RetirementColors.Cream.copy(alpha = 0.08f)).border(1.dp, RetirementColors.Gold.copy(alpha = 0.5f), RoundedCornerShape(8.dp)),
        contentAlignment = Alignment.Center
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Icon(Icons.Default.Add, contentDescription = null, tint = RetirementColors.Gold, modifier = Modifier.size(18.dp))
            Text(text, style = TextStyle(fontFamily = CormorantGaramond, fontSize = 16.5.sp, fontWeight = FontWeight.Bold, color = RetirementColors.Gold))
        }
    }
}

@Composable
private fun ButtonVariant8(text: String) {
    Box(
        modifier = Modifier.fillMaxWidth().height(46.dp).clip(RoundedCornerShape(8.dp)).border(1.dp, RetirementColors.Gold.copy(alpha = 0.6f), RoundedCornerShape(8.dp)).padding(3.dp).border(1.dp, RetirementColors.Gold.copy(alpha = 0.25f), RoundedCornerShape(6.dp)).background(Color.White.copy(alpha = 0.02f)),
        contentAlignment = Alignment.Center
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Icon(Icons.Default.Add, contentDescription = null, tint = RetirementColors.Gold, modifier = Modifier.size(16.dp))
            Text(text, style = TextStyle(fontFamily = PlayfairDisplay, fontSize = 13.5.sp, fontWeight = FontWeight.Bold, color = RetirementColors.Gold))
        }
    }
}

@Composable
private fun ButtonVariant9(text: String) {
    Box(
        modifier = Modifier.fillMaxWidth().height(46.dp).clip(RoundedCornerShape(8.dp)).background(Color.White.copy(alpha = 0.03f)).border(1.dp, RetirementColors.Gold.copy(alpha = 0.4f), RoundedCornerShape(8.dp)),
        contentAlignment = Alignment.Center
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Icon(Icons.Default.Add, contentDescription = null, tint = RetirementColors.Gold, modifier = Modifier.size(18.dp))
            Text(text, style = TextStyle(fontFamily = FontFamily.SansSerif, fontSize = 13.5.sp, fontWeight = FontWeight.Medium, color = RetirementColors.Gold))
        }
    }
}

@Composable
private fun ButtonVariant10(text: String) {
    Box(
        modifier = Modifier.fillMaxWidth().height(46.dp).clip(RoundedCornerShape(8.dp)).background(brush = Brush.horizontalGradient(listOf(RetirementColors.Gold.copy(alpha = 0.15f), Color.Transparent, RetirementColors.Gold.copy(alpha = 0.15f)))).border(1.dp, RetirementColors.Gold.copy(alpha = 0.5f), RoundedCornerShape(8.dp)),
        contentAlignment = Alignment.Center
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Icon(Icons.Default.Add, contentDescription = null, tint = RetirementColors.Gold, modifier = Modifier.size(18.dp))
            Text(text, style = TextStyle(fontFamily = PlayfairDisplay, fontSize = 13.5.sp, fontWeight = FontWeight.Bold, color = RetirementColors.Gold))
        }
    }
}

@Preview(showBackground = true, widthDp = 375, heightDp = 1800, name = "Part 1: Variants 1 to 5")
@Composable
fun FullSettingsPageButtonPreviewPart1() {
    RetirementTimerAppTheme {
        FullSettingsPageButtonPreviewPart1Screen()
    }
}

@Preview(showBackground = true, widthDp = 375, heightDp = 1800, name = "Part 2: Variants 6 to 10")
@Composable
fun FullSettingsPageButtonPreviewPart2() {
    RetirementTimerAppTheme {
        FullSettingsPageButtonPreviewPart2Screen()
    }
}
