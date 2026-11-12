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
fun ButtonOptionsPreviewScreen() {
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
            text = "10 ВАРИАНТОВ ДИЗАЙНА КНОПОК ДОБАВЛЕНИЯ",
            style = TextStyle(
                fontFamily = PlayfairDisplay,
                fontWeight = FontWeight.Bold,
                fontSize = 17.sp,
                color = RetirementColors.Gold,
                textAlign = TextAlign.Center,
                letterSpacing = 1.5.sp
            ),
            modifier = Modifier.padding(vertical = 8.dp)
        )

        // Variant 1
        ButtonOptionCard(
            variantNumber = 1,
            title = "Вариант 1: Тёмная стеклянная карточка с тонким золотом (Текущий)",
            description = "Фон 2% белого + золотая рамка 1dp, закругление 8dp"
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.White.copy(alpha = 0.02f))
                    .border(1.dp, RetirementColors.Gold.copy(alpha = 0.45f), RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Icon(Icons.Default.Add, contentDescription = null, tint = RetirementColors.Gold, modifier = Modifier.size(18.dp))
                    Text("Add Vacation Period", style = TextStyle(fontFamily = PlayfairDisplay, fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = RetirementColors.Gold))
                }
            }
        }

        // Variant 2
        ButtonOptionCard(
            variantNumber = 2,
            title = "Вариант 2: Изумрудно-зелёный акцент календаря",
            description = "Изумрудная рамка #10B981 в цвет календаря и бейджей отпуска"
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0xFF10B981).copy(alpha = 0.08f))
                    .border(1.dp, Color(0xFF10B981).copy(alpha = 0.6f), RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Icon(Icons.Default.Add, contentDescription = null, tint = Color(0xFF34D399), modifier = Modifier.size(18.dp))
                    Text("Add Vacation Period", style = TextStyle(fontFamily = PlayfairDisplay, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color(0xFF34D399)))
                }
            }
        }

        // Variant 3
        ButtonOptionCard(
            variantNumber = 3,
            title = "Вариант 3: Премиальная золотая капсула (Pill Shape)",
            description = "Закругление капсулой 24dp + яркая золотая рамка"
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(46.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .background(Color.White.copy(alpha = 0.03f))
                    .border(1.dp, RetirementColors.Gold.copy(alpha = 0.6f), RoundedCornerShape(24.dp)),
                contentAlignment = Alignment.Center
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Icon(Icons.Default.Add, contentDescription = null, tint = RetirementColors.Gold, modifier = Modifier.size(18.dp))
                    Text("Add Vacation Period", style = TextStyle(fontFamily = PlayfairDisplay, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = RetirementColors.Gold))
                }
            }
        }

        // Variant 4
        ButtonOptionCard(
            variantNumber = 4,
            title = "Вариант 4: Яркая закрашенная золотая кнопка",
            description = "Сплошной золотой градиентний фон с темным текстом"
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(Color(0xFFE5C78B), Color(0xFFD4B26F), Color(0xFFB5934E))
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Icon(Icons.Default.Add, contentDescription = null, tint = Color(0xFF111315), modifier = Modifier.size(18.dp))
                    Text("ADD VACATION PERIOD", style = TextStyle(fontFamily = PlayfairDisplay, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color(0xFF111315), letterSpacing = 1.sp))
                }
            }
        }

        // Variant 5
        ButtonOptionCard(
            variantNumber = 5,
            title = "Вариант 5: Кнопка с круглым золотым бейджем плюса",
            description = "Слева находится круглый бейдж «+», затем название действия"
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.White.copy(alpha = 0.02f))
                    .border(1.dp, RetirementColors.Gold.copy(alpha = 0.35f), RoundedCornerShape(8.dp))
                    .padding(horizontal = 12.dp),
                contentAlignment = Alignment.Center
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .clip(CircleShape)
                            .background(RetirementColors.Gold.copy(alpha = 0.2f))
                            .border(1.dp, RetirementColors.Gold, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.Add, contentDescription = null, tint = RetirementColors.Gold, modifier = Modifier.size(14.dp))
                    }
                    Text("Add Vacation Period", style = TextStyle(fontFamily = PlayfairDisplay, fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = RetirementColors.Gold))
                }
            }
        }

        // Variant 6
        ButtonOptionCard(
            variantNumber = 6,
            title = "Вариант 6: Римский монументальный (Cinzel All-Caps)",
            description = "Античный шрифтовой стиль с широкой разрядкой букв"
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.White.copy(alpha = 0.02f))
                    .border(1.dp, RetirementColors.Gold.copy(alpha = 0.45f), RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Icon(Icons.Default.Add, contentDescription = null, tint = RetirementColors.Gold, modifier = Modifier.size(16.dp))
                    Text("+ ADD VACATION PERIOD", style = TextStyle(fontFamily = CinzelFontFamily, fontSize = 12.5.sp, fontWeight = FontWeight.Bold, color = RetirementColors.Gold, letterSpacing = 1.2.sp))
                }
            }
        }

        // Variant 7
        ButtonOptionCard(
            variantNumber = 7,
            title = "Вариант 7: Подсвеченный бежевый фон с золотом",
            description = "Фон с теплым бежевым оттенком Cream + золотой контур"
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(RetirementColors.Cream.copy(alpha = 0.08f))
                    .border(1.dp, RetirementColors.Gold.copy(alpha = 0.5f), RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Icon(Icons.Default.Add, contentDescription = null, tint = RetirementColors.Gold, modifier = Modifier.size(18.dp))
                    Text("Add Vacation Period", style = TextStyle(fontFamily = CormorantGaramond, fontSize = 17.sp, fontWeight = FontWeight.Bold, color = RetirementColors.Gold))
                }
            }
        }

        // Variant 8
        ButtonOptionCard(
            variantNumber = 8,
            title = "Вариант 8: Двойная тонкая золотая рамка",
            description = "Двойной контур в стиле люксовых эмблем"
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .border(1.dp, RetirementColors.Gold.copy(alpha = 0.6f), RoundedCornerShape(8.dp))
                    .padding(3.dp)
                    .border(1.dp, RetirementColors.Gold.copy(alpha = 0.25f), RoundedCornerShape(6.dp))
                    .background(Color.White.copy(alpha = 0.02f)),
                contentAlignment = Alignment.Center
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Icon(Icons.Default.Add, contentDescription = null, tint = RetirementColors.Gold, modifier = Modifier.size(16.dp))
                    Text("Add Vacation Period", style = TextStyle(fontFamily = PlayfairDisplay, fontSize = 13.5.sp, fontWeight = FontWeight.Bold, color = RetirementColors.Gold))
                }
            }
        }

        // Variant 9
        ButtonOptionCard(
            variantNumber = 9,
            title = "Вариант 9: Минималистичный системный Sans-Serif",
            description = "Современный лаконичный шрифт без засечек"
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.White.copy(alpha = 0.03f))
                    .border(1.dp, RetirementColors.Gold.copy(alpha = 0.4f), RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Icon(Icons.Default.Add, contentDescription = null, tint = RetirementColors.Gold, modifier = Modifier.size(18.dp))
                    Text("+ Add Vacation Period", style = TextStyle(fontFamily = FontFamily.SansSerif, fontSize = 14.sp, fontWeight = FontWeight.Medium, color = RetirementColors.Gold))
                }
            }
        }

        // Variant 10
        ButtonOptionCard(
            variantNumber = 10,
            title = "Вариант 10: Золотое градиентное свечение (Gradient Tint)",
            description = "Полупрозрачный радиальный золотистый отблеск"
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                RetirementColors.Gold.copy(alpha = 0.15f),
                                Color.Transparent,
                                RetirementColors.Gold.copy(alpha = 0.15f)
                            )
                        )
                    )
                    .border(1.dp, RetirementColors.Gold.copy(alpha = 0.5f), RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Icon(Icons.Default.Add, contentDescription = null, tint = RetirementColors.Gold, modifier = Modifier.size(18.dp))
                    Text("Add Vacation Period", style = TextStyle(fontFamily = PlayfairDisplay, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = RetirementColors.Gold))
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Composable
private fun ButtonOptionCard(
    variantNumber: Int,
    title: String,
    description: String,
    content: @Composable () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White.copy(alpha = 0.02f), RoundedCornerShape(12.dp))
            .border(1.dp, RetirementColors.Gold.copy(alpha = 0.25f), RoundedCornerShape(12.dp))
            .padding(14.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(6.dp)
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
                fontFamily = CormorantGaramond,
                fontSize = 12.sp,
                color = RetirementColors.Cream.copy(alpha = 0.65f)
            ),
            modifier = Modifier.padding(bottom = 6.dp)
        )
        content()
    }
}

@Preview(showBackground = true, widthDp = 375, heightDp = 1800, name = "10 Button Design Options Preview")
@Composable
fun ButtonOptionsPreview() {
    RetirementTimerAppTheme {
        ButtonOptionsPreviewScreen()
    }
}
