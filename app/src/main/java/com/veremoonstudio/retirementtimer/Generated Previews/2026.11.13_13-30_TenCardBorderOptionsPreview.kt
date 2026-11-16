@file:JvmName("TenCardBorderOptionsPreviewDotFormat")
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
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material3.*
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
import com.veremoonstudio.retirementtimer.ui.theme.PlayfairDisplay
import com.veremoonstudio.retirementtimer.ui.theme.RetirementColors
import com.veremoonstudio.retirementtimer.ui.theme.RetirementTimerAppTheme

@Composable
fun TenCardBorderOptionsPreviewScreen() {
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
            text = "10 ВАРИАНТОВ ОКАНТОВКИ КАРТОЧКИ",
            style = TextStyle(
                fontFamily = PlayfairDisplay,
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                color = RetirementColors.Gold,
                textAlign = TextAlign.Center,
                letterSpacing = 1.sp
            )
        )

        BorderOption1Content()
        BorderOption2Content()
        BorderOption3Content()
        BorderOption4Content()
        BorderOption5Content()
        BorderOption6Content()
        BorderOption7Content()
        BorderOption8Content()
        BorderOption9Content()
        BorderOption10Content()
    }
}

// BORDER OPTION 1: Classic Solid Gold Border (8.dp)
@Composable
private fun BorderOption1Content() {
    BorderCardContainer(
        title = "Вариант 1: Классический золотой контур (8.dp)",
        description = "Четкая строгая золотая рамка 1.5.dp с классическим закруглением 8.dp и золотым текстом."
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(Color.White.copy(alpha = 0.08f))
                .border(1.5.dp, RetirementColors.Gold, RoundedCornerShape(8.dp))
                .padding(horizontal = 10.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            CardArrowStack()
            Text(text = "New Year's Day (1/1)", style = TextStyle(fontFamily = PlayfairDisplay, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = RetirementColors.Gold), modifier = Modifier.weight(1f))
            Checkbox(checked = true, onCheckedChange = {})
        }
    }
}

// BORDER OPTION 2: Pill Shape Gold Capsule (24.dp)
@Composable
private fun BorderOption2Content() {
    BorderCardContainer(
        title = "Вариант 2: Золотая капсула Pill (24.dp)",
        description = "Закругленная окантовка капсульной формы (24.dp), идеально сочетающаяся с кнопкой добаления."
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(24.dp))
                .background(RetirementColors.Gold.copy(alpha = 0.12f))
                .border(1.5.dp, RetirementColors.Gold, RoundedCornerShape(24.dp))
                .padding(horizontal = 14.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            CardArrowStack()
            Text(text = "New Year's Day (1/1)", style = TextStyle(fontFamily = PlayfairDisplay, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = RetirementColors.Gold), modifier = Modifier.weight(1f))
            Checkbox(checked = true, onCheckedChange = {})
        }
    }
}

// BORDER OPTION 3: Glassmorphic White Glow Frame
@Composable
private fun BorderOption3Content() {
    BorderCardContainer(
        title = "Вариант 3: Стекло с контуром (Glassmorphic Glow)",
        description = "Контрастный ярко-белый кант (White 60%) с полупрозрачной стеклянной подложкой."
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(Color.White.copy(alpha = 0.10f))
                .border(1.5.dp, Color.White.copy(alpha = 0.6f), RoundedCornerShape(8.dp))
                .padding(horizontal = 10.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            CardArrowStack(arrowTint = Color.White)
            Text(text = "New Year's Day (1/1)", style = TextStyle(fontFamily = PlayfairDisplay, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.White), modifier = Modifier.weight(1f))
            Checkbox(checked = true, onCheckedChange = {})
        }
    }
}

// BORDER OPTION 4: Double Gold Frame
@Composable
private fun BorderOption4Content() {
    BorderCardContainer(
        title = "Вариант 4: Двойной золотой контур (Double Frame)",
        description = "Выразительный двойной контур с тонкой внутренней и внешней золотой рамкой."
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .border(1.dp, RetirementColors.Gold.copy(alpha = 0.4f), RoundedCornerShape(10.dp))
                .padding(3.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.White.copy(alpha = 0.08f))
                    .border(1.5.dp, RetirementColors.Gold, RoundedCornerShape(8.dp))
                    .padding(horizontal = 10.dp, vertical = 7.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                CardArrowStack()
                Text(text = "New Year's Day (1/1)", style = TextStyle(fontFamily = PlayfairDisplay, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = RetirementColors.Gold), modifier = Modifier.weight(1f))
                Checkbox(checked = true, onCheckedChange = {})
            }
        }
    }
}

// BORDER OPTION 5: Left Gold Accent Bar & Frame
@Composable
private fun BorderOption5Content() {
    BorderCardContainer(
        title = "Вариант 5: Акцентная золотая полоса слева",
        description = "Золотой контур с утолщенной 4.dp вертикальной акцентной полосой на левом торце."
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(Color.White.copy(alpha = 0.06f))
                .border(1.dp, RetirementColors.Gold.copy(alpha = 0.5f), RoundedCornerShape(8.dp))
                .padding(start = 4.dp, end = 10.dp, top = 8.dp, bottom = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box(modifier = Modifier.width(4.dp).height(28.dp).clip(RoundedCornerShape(2.dp)).background(RetirementColors.Gold))
            Spacer(modifier = Modifier.width(6.dp))
            CardArrowStack()
            Text(text = "New Year's Day (1/1)", style = TextStyle(fontFamily = PlayfairDisplay, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = RetirementColors.Gold), modifier = Modifier.weight(1f))
            Checkbox(checked = true, onCheckedChange = {})
        }
    }
}

// BORDER OPTION 6: Soft Translucent Gray Border
@Composable
private fun BorderOption6Content() {
    BorderCardContainer(
        title = "Вариант 6: Мягкий серый контур (Soft Gray Border)",
        description = "Лаконичный серый контур средней яркости (`White 50%`) с полупрозрачным фоном."
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(Color.White.copy(alpha = 0.06f))
                .border(1.5.dp, Color.White.copy(alpha = 0.5f), RoundedCornerShape(8.dp))
                .padding(horizontal = 10.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            CardArrowStack()
            Text(text = "New Year's Day (1/1)", style = TextStyle(fontFamily = PlayfairDisplay, fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = RetirementColors.Cream), modifier = Modifier.weight(1f))
            Checkbox(checked = true, onCheckedChange = {})
        }
    }
}

// BORDER OPTION 7: Contrast Inverted Dark Card (Frame #18171C)
@Composable
private fun BorderOption7Content() {
    BorderCardContainer(
        title = "Вариант 7: Темно-угольная инверсная карточка",
        description = "Глубокая темная подложка `#18171C` с выразительной золотой рамкой."
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(Color(0xFF18171C))
                .border(1.5.dp, RetirementColors.Gold, RoundedCornerShape(8.dp))
                .padding(horizontal = 10.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            CardArrowStack()
            Text(text = "New Year's Day (1/1)", style = TextStyle(fontFamily = PlayfairDisplay, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = RetirementColors.Gold), modifier = Modifier.weight(1f))
            Checkbox(checked = true, onCheckedChange = {})
        }
    }
}

// BORDER OPTION 8: Thin Semi-Transparent Gold Outline (1.dp)
@Composable
private fun BorderOption8Content() {
    BorderCardContainer(
        title = "Вариант 8: Тонкая полупрозрачная золотая нить (1.dp)",
        description = "Легкий утонченный контур 1.dp золотого оттенка (`Gold 50%`)."
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(Color.White.copy(alpha = 0.04f))
                .border(1.dp, RetirementColors.Gold.copy(alpha = 0.5f), RoundedCornerShape(8.dp))
                .padding(horizontal = 10.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            CardArrowStack()
            Text(text = "New Year's Day (1/1)", style = TextStyle(fontFamily = PlayfairDisplay, fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = RetirementColors.Cream), modifier = Modifier.weight(1f))
            Checkbox(checked = true, onCheckedChange = {})
        }
    }
}

// BORDER OPTION 9: Geometric Curved Radius (14.dp)
@Composable
private fun BorderOption9Content() {
    BorderCardContainer(
        title = "Вариант 9: Увеличенное закругление (14.dp)",
        description = "Геометрическое закругление углов 14.dp с контрастным золотым ободком."
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(14.dp))
                .background(RetirementColors.Gold.copy(alpha = 0.08f))
                .border(1.5.dp, RetirementColors.Gold, RoundedCornerShape(14.dp))
                .padding(horizontal = 12.dp, vertical = 9.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            CardArrowStack()
            Text(text = "New Year's Day (1/1)", style = TextStyle(fontFamily = PlayfairDisplay, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = RetirementColors.Gold), modifier = Modifier.weight(1f))
            Checkbox(checked = true, onCheckedChange = {})
        }
    }
}

// BORDER OPTION 10: Gold Gradient Border Brush
@Composable
private fun BorderOption10Content() {
    BorderCardContainer(
        title = "Вариант 10: Золотой градиентный контур",
        description = "Градиентная рамка от насыщенного золота `#E5C78B` к светлой слоновой кости."
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(Color.White.copy(alpha = 0.08f))
                .border(
                    width = 1.5.dp,
                    brush = Brush.horizontalGradient(
                        listOf(
                            RetirementColors.Gold,
                            RetirementColors.Cream,
                            RetirementColors.Gold
                        )
                    ),
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(horizontal = 10.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            CardArrowStack()
            Text(text = "New Year's Day (1/1)", style = TextStyle(fontFamily = PlayfairDisplay, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = RetirementColors.Gold), modifier = Modifier.weight(1f))
            Checkbox(checked = true, onCheckedChange = {})
        }
    }
}

@Composable
private fun CardArrowStack(arrowTint: Color = RetirementColors.Gold) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(end = 8.dp)) {
        Icon(imageVector = Icons.Default.ArrowDropUp, contentDescription = "Up", tint = arrowTint, modifier = Modifier.size(20.dp))
        Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = "Down", tint = arrowTint, modifier = Modifier.size(20.dp))
    }
}

@Composable
private fun BorderCardContainer(
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

// INDIVIDUAL PREVIEWS
@Preview(showBackground = true, widthDp = 380, heightDp = 220, name = "1: Classic Gold 8.dp")
@Composable
fun PreviewBorder1() { RetirementTimerAppTheme { Box(modifier = Modifier.background(RetirementColors.Background).padding(16.dp)) { BorderOption1Content() } } }

@Preview(showBackground = true, widthDp = 380, heightDp = 220, name = "2: Gold Pill Capsule 24.dp")
@Composable
fun PreviewBorder2() { RetirementTimerAppTheme { Box(modifier = Modifier.background(RetirementColors.Background).padding(16.dp)) { BorderOption2Content() } } }

@Preview(showBackground = true, widthDp = 380, heightDp = 220, name = "3: Glassmorphic White Glow")
@Composable
fun PreviewBorder3() { RetirementTimerAppTheme { Box(modifier = Modifier.background(RetirementColors.Background).padding(16.dp)) { BorderOption3Content() } } }

@Preview(showBackground = true, widthDp = 380, heightDp = 220, name = "4: Double Gold Frame")
@Composable
fun PreviewBorder4() { RetirementTimerAppTheme { Box(modifier = Modifier.background(RetirementColors.Background).padding(16.dp)) { BorderOption4Content() } } }

@Preview(showBackground = true, widthDp = 380, heightDp = 220, name = "5: Left Accent Bar")
@Composable
fun PreviewBorder5() { RetirementTimerAppTheme { Box(modifier = Modifier.background(RetirementColors.Background).padding(16.dp)) { BorderOption5Content() } } }

@Preview(showBackground = true, widthDp = 380, heightDp = 220, name = "6: Soft Gray Border")
@Composable
fun PreviewBorder6() { RetirementTimerAppTheme { Box(modifier = Modifier.background(RetirementColors.Background).padding(16.dp)) { BorderOption6Content() } } }

@Preview(showBackground = true, widthDp = 380, heightDp = 220, name = "7: Dark Inverted Card")
@Composable
fun PreviewBorder7() { RetirementTimerAppTheme { Box(modifier = Modifier.background(RetirementColors.Background).padding(16.dp)) { BorderOption7Content() } } }

@Preview(showBackground = true, widthDp = 380, heightDp = 220, name = "8: Thin Gold Outline 1.dp")
@Composable
fun PreviewBorder8() { RetirementTimerAppTheme { Box(modifier = Modifier.background(RetirementColors.Background).padding(16.dp)) { BorderOption8Content() } } }

@Preview(showBackground = true, widthDp = 380, heightDp = 220, name = "9: Curved Radius 14.dp")
@Composable
fun PreviewBorder9() { RetirementTimerAppTheme { Box(modifier = Modifier.background(RetirementColors.Background).padding(16.dp)) { BorderOption9Content() } } }

@Preview(showBackground = true, widthDp = 380, heightDp = 220, name = "10: Gold Gradient Border")
@Composable
fun PreviewBorder10() { RetirementTimerAppTheme { Box(modifier = Modifier.background(RetirementColors.Background).padding(16.dp)) { BorderOption10Content() } } }
