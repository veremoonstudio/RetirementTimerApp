@file:JvmName("TenArrowReorderOptionsPreviewDot")
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
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.UnfoldMore
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
fun TenArrowReorderOptionsPreviewScreen() {
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
            text = "10 ВАРИАНТОВ ПЕРЕМЕЩЕНИЯ СТРЕЛОЧКАМИ",
            style = TextStyle(
                fontFamily = PlayfairDisplay,
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                color = RetirementColors.Gold,
                textAlign = TextAlign.Center,
                letterSpacing = 1.sp
            )
        )

        ArrowOption1Content()
        ArrowOption2Content()
        ArrowOption3Content()
        ArrowOption4Content()
        ArrowOption5Content()
        ArrowOption6Content()
        ArrowOption7Content()
        ArrowOption8Content()
        ArrowOption9Content()
        ArrowOption10Content()
    }
}

// ARROW OPTION 1: Vertical Stack Arrow Buttons ▲ ▼
@Composable
private fun ArrowOption1Content() {
    ArrowCardContainer(
        title = "Вариант 1: Вертикальный стек стрелок ▲ ▼ (справа)",
        description = "Две компактные вертикальные кнопки ▲ и ▼ на правой стороне карточки перед чекбоксом. Одно нажатие перемещает праздник вверх или вниз."
    ) {
        HolidayRowWrapper(name = "New Year's Day (1/1)", checked = true) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(imageVector = Icons.Default.ArrowDropUp, contentDescription = "Up", tint = RetirementColors.Gold, modifier = Modifier.size(18.dp).clickable {})
                    Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = "Down", tint = RetirementColors.Gold, modifier = Modifier.size(18.dp).clickable {})
                }
                Spacer(modifier = Modifier.width(8.dp))
            }
        }
        HolidayRowWrapper(name = "Martin Luther King Jr. Day (1/16)", checked = false) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(imageVector = Icons.Default.ArrowDropUp, contentDescription = "Up", tint = RetirementColors.Gold, modifier = Modifier.size(18.dp).clickable {})
                    Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = "Down", tint = RetirementColors.Gold, modifier = Modifier.size(18.dp).clickable {})
                }
                Spacer(modifier = Modifier.width(8.dp))
            }
        }
    }
}

// ARROW OPTION 2: Horizontal Arrow Row ▲ ▼
@Composable
private fun ArrowOption2Content() {
    ArrowCardContainer(
        title = "Вариант 2: Горизонтальные стрелки ▲ ▼ (рядом)",
        description = "Две круглые мини-кнопки со стрелками ▲ и ▼ расположены рядом друг с другом по горизонтали."
    ) {
        HolidayRowWrapper(name = "New Year's Day (1/1)", checked = true) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(modifier = Modifier.size(24.dp).clip(CircleShape).background(Color.White.copy(alpha = 0.08f)).clickable {}, contentAlignment = Alignment.Center) {
                    Icon(imageVector = Icons.Default.ArrowDropUp, contentDescription = "Up", tint = RetirementColors.Gold, modifier = Modifier.size(18.dp))
                }
                Spacer(modifier = Modifier.width(4.dp))
                Box(modifier = Modifier.size(24.dp).clip(CircleShape).background(Color.White.copy(alpha = 0.08f)).clickable {}, contentAlignment = Alignment.Center) {
                    Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = "Down", tint = RetirementColors.Gold, modifier = Modifier.size(18.dp))
                }
                Spacer(modifier = Modifier.width(8.dp))
            }
        }
        HolidayRowWrapper(name = "Martin Luther King Jr. Day (1/16)", checked = false) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(modifier = Modifier.size(24.dp).clip(CircleShape).background(Color.White.copy(alpha = 0.08f)).clickable {}, contentAlignment = Alignment.Center) {
                    Icon(imageVector = Icons.Default.ArrowDropUp, contentDescription = "Up", tint = RetirementColors.Gold, modifier = Modifier.size(18.dp))
                }
                Spacer(modifier = Modifier.width(4.dp))
                Box(modifier = Modifier.size(24.dp).clip(CircleShape).background(Color.White.copy(alpha = 0.08f)).clickable {}, contentAlignment = Alignment.Center) {
                    Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = "Down", tint = RetirementColors.Gold, modifier = Modifier.size(18.dp))
                }
                Spacer(modifier = Modifier.width(8.dp))
            }
        }
    }
}

// ARROW OPTION 3: Gold Outline Unfold Badge [ ⇅ ]
@Composable
private fun ArrowOption3Content() {
    ArrowCardContainer(
        title = "Вариант 3: Золотой бэдж-блок [ ⇅ ]",
        description = "Интерактивный золотой капсульный бэдж [ ⇅ ] на правой стороне карточки. Клик открывает меню смещения или сдвигает праздник."
    ) {
        HolidayRowWrapper(name = "New Year's Day (1/1)", checked = true) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(RetirementColors.Gold.copy(alpha = 0.12f))
                        .border(1.dp, RetirementColors.Gold, RoundedCornerShape(12.dp))
                        .clickable {}
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "⇅", style = TextStyle(fontFamily = FontFamily.SansSerif, fontSize = 14.sp, color = RetirementColors.Gold, fontWeight = FontWeight.Bold))
                    }
                }
                Spacer(modifier = Modifier.width(8.dp))
            }
        }
        HolidayRowWrapper(name = "Martin Luther King Jr. Day (1/16)", checked = false) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(RetirementColors.Gold.copy(alpha = 0.12f))
                        .border(1.dp, RetirementColors.Gold, RoundedCornerShape(12.dp))
                        .clickable {}
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "⇅", style = TextStyle(fontFamily = FontFamily.SansSerif, fontSize = 14.sp, color = RetirementColors.Gold, fontWeight = FontWeight.Bold))
                    }
                }
                Spacer(modifier = Modifier.width(8.dp))
            }
        }
    }
}

// ARROW OPTION 4: Left Side Arrow Controls ▲ ▼
@Composable
private fun ArrowOption4Content() {
    ArrowCardContainer(
        title = "Вариант 4: Кнопки стрелок слева ▲ ▼",
        description = "Управление перемещением размещено в левой части карточки перед названием праздника."
    ) {
        HolidayRowWrapper(
            name = "New Year's Day (1/1)",
            checked = true,
            leadingContent = {
                Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(end = 8.dp)) {
                    Icon(imageVector = Icons.Default.ArrowDropUp, contentDescription = "Up", tint = RetirementColors.Gold, modifier = Modifier.size(18.dp).clickable {})
                    Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = "Down", tint = RetirementColors.Gold, modifier = Modifier.size(18.dp).clickable {})
                }
            }
        )
        HolidayRowWrapper(
            name = "Martin Luther King Jr. Day (1/16)",
            checked = false,
            leadingContent = {
                Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(end = 8.dp)) {
                    Icon(imageVector = Icons.Default.ArrowDropUp, contentDescription = "Up", tint = RetirementColors.Gold, modifier = Modifier.size(18.dp).clickable {})
                    Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = "Down", tint = RetirementColors.Gold, modifier = Modifier.size(18.dp).clickable {})
                }
            }
        )
    }
}

// ARROW OPTION 5: Rounded Gold Buttons ▲ ▼
@Composable
private fun ArrowOption5Content() {
    ArrowCardContainer(
        title = "Вариант 5: Золотые круглые кнопки ▲ ▼",
        description = "Яркие круглые золотистые кнопочки с темными стрелочками для высокой видимости."
    ) {
        HolidayRowWrapper(name = "New Year's Day (1/1)", checked = true) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(modifier = Modifier.size(24.dp).clip(CircleShape).background(RetirementColors.Gold).clickable {}, contentAlignment = Alignment.Center) {
                    Text(text = "▲", style = TextStyle(fontSize = 11.sp, color = Color(0xFF111315), fontWeight = FontWeight.Bold))
                }
                Spacer(modifier = Modifier.width(4.dp))
                Box(modifier = Modifier.size(24.dp).clip(CircleShape).background(RetirementColors.Gold).clickable {}, contentAlignment = Alignment.Center) {
                    Text(text = "▼", style = TextStyle(fontSize = 11.sp, color = Color(0xFF111315), fontWeight = FontWeight.Bold))
                }
                Spacer(modifier = Modifier.width(8.dp))
            }
        }
        HolidayRowWrapper(name = "Martin Luther King Jr. Day (1/16)", checked = false) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(modifier = Modifier.size(24.dp).clip(CircleShape).background(RetirementColors.Gold).clickable {}, contentAlignment = Alignment.Center) {
                    Text(text = "▲", style = TextStyle(fontSize = 11.sp, color = Color(0xFF111315), fontWeight = FontWeight.Bold))
                }
                Spacer(modifier = Modifier.width(4.dp))
                Box(modifier = Modifier.size(24.dp).clip(CircleShape).background(RetirementColors.Gold).clickable {}, contentAlignment = Alignment.Center) {
                    Text(text = "▼", style = TextStyle(fontSize = 11.sp, color = Color(0xFF111315), fontWeight = FontWeight.Bold))
                }
                Spacer(modifier = Modifier.width(8.dp))
            }
        }
    }
}

// ARROW OPTION 6: Split Controls (▲ Left, ▼ Right)
@Composable
private fun ArrowOption6Content() {
    ArrowCardContainer(
        title = "Вариант 6: Раздельные стрелки (▲ Слева, ▼ Справа)",
        description = "Стрелка 'Вверх' ▲ расположена слева от названия, а стрелка 'Вниз' ▼ расположена на правой стороне."
    ) {
        HolidayRowWrapper(
            name = "New Year's Day (1/1)",
            checked = true,
            leadingContent = {
                Icon(imageVector = Icons.Default.ArrowDropUp, contentDescription = "Up", tint = RetirementColors.Gold, modifier = Modifier.size(22.dp).clickable {}.padding(end = 4.dp))
            }
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = "Down", tint = RetirementColors.Gold, modifier = Modifier.size(22.dp).clickable {})
                Spacer(modifier = Modifier.width(8.dp))
            }
        }
        HolidayRowWrapper(
            name = "Martin Luther King Jr. Day (1/16)",
            checked = false,
            leadingContent = {
                Icon(imageVector = Icons.Default.ArrowDropUp, contentDescription = "Up", tint = RetirementColors.Gold, modifier = Modifier.size(22.dp).clickable {}.padding(end = 4.dp))
            }
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = "Down", tint = RetirementColors.Gold, modifier = Modifier.size(22.dp).clickable {})
                Spacer(modifier = Modifier.width(8.dp))
            }
        }
    }
}

// ARROW OPTION 7: Glassmorphic Capsule Arrow Controller
@Composable
private fun ArrowOption7Content() {
    ArrowCardContainer(
        title = "Вариант 7: Стеклянная капсула стрелок (Glassmorphic Pill)",
        description = "Полупрозрачный стеклянный блок капсульной формы со светлыми стрелочками внутри."
    ) {
        HolidayRowWrapper(name = "New Year's Day (1/1)", checked = true) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(14.dp))
                        .background(Color.White.copy(alpha = 0.08f))
                        .border(1.dp, Color.White.copy(alpha = 0.25f), RoundedCornerShape(14.dp))
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(imageVector = Icons.Default.ArrowDropUp, contentDescription = "Up", tint = Color.White, modifier = Modifier.size(18.dp).clickable {})
                        Spacer(modifier = Modifier.width(2.dp))
                        Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = "Down", tint = Color.White, modifier = Modifier.size(18.dp).clickable {})
                    }
                }
                Spacer(modifier = Modifier.width(8.dp))
            }
        }
        HolidayRowWrapper(name = "Martin Luther King Jr. Day (1/16)", checked = false) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(14.dp))
                        .background(Color.White.copy(alpha = 0.08f))
                        .border(1.dp, Color.White.copy(alpha = 0.25f), RoundedCornerShape(14.dp))
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(imageVector = Icons.Default.ArrowDropUp, contentDescription = "Up", tint = Color.White, modifier = Modifier.size(18.dp).clickable {})
                        Spacer(modifier = Modifier.width(2.dp))
                        Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = "Down", tint = Color.White, modifier = Modifier.size(18.dp).clickable {})
                    }
                }
                Spacer(modifier = Modifier.width(8.dp))
            }
        }
    }
}

// ARROW OPTION 8: Elegant Serif Thin Arrows ↑ ↓
@Composable
private fun ArrowOption8Content() {
    ArrowCardContainer(
        title = "Вариант 8: Тонкие элегантные стрелки ↑ ↓ (Serif Elegance)",
        description = "Утонченные типографические стрелки ↑ и ↓ в классическом премиальном стиле Playfair Display."
    ) {
        HolidayRowWrapper(name = "New Year's Day (1/1)", checked = true) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "↑", style = TextStyle(fontFamily = PlayfairDisplay, fontSize = 18.sp, color = RetirementColors.Gold, fontWeight = FontWeight.Bold), modifier = Modifier.clickable {})
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(text = "↓", style = TextStyle(fontFamily = PlayfairDisplay, fontSize = 18.sp, color = RetirementColors.Gold, fontWeight = FontWeight.Bold), modifier = Modifier.clickable {})
                }
                Spacer(modifier = Modifier.width(8.dp))
            }
        }
        HolidayRowWrapper(name = "Martin Luther King Jr. Day (1/16)", checked = false) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "↑", style = TextStyle(fontFamily = PlayfairDisplay, fontSize = 18.sp, color = RetirementColors.Gold, fontWeight = FontWeight.Bold), modifier = Modifier.clickable {})
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(text = "↓", style = TextStyle(fontFamily = PlayfairDisplay, fontSize = 18.sp, color = RetirementColors.Gold, fontWeight = FontWeight.Bold), modifier = Modifier.clickable {})
                }
                Spacer(modifier = Modifier.width(8.dp))
            }
        }
    }
}

// ARROW OPTION 9: Modern Material Chevrons ˄ ˅
@Composable
private fun ArrowOption9Content() {
    ArrowCardContainer(
        title = "Вариант 9: Совремнные шевроны ˄ ˅ (Material Chevrons)",
        description = "Современные минималистичные значки-шевроны KeyboardArrowUp и KeyboardArrowDown."
    ) {
        HolidayRowWrapper(name = "New Year's Day (1/1)", checked = true) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(imageVector = Icons.Default.KeyboardArrowUp, contentDescription = "Up", tint = RetirementColors.Gold, modifier = Modifier.size(20.dp).clickable {})
                Icon(imageVector = Icons.Default.KeyboardArrowDown, contentDescription = "Down", tint = RetirementColors.Gold, modifier = Modifier.size(20.dp).clickable {})
                Spacer(modifier = Modifier.width(8.dp))
            }
        }
        HolidayRowWrapper(name = "Martin Luther King Jr. Day (1/16)", checked = false) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(imageVector = Icons.Default.KeyboardArrowUp, contentDescription = "Up", tint = RetirementColors.Gold, modifier = Modifier.size(20.dp).clickable {})
                Icon(imageVector = Icons.Default.KeyboardArrowDown, contentDescription = "Down", tint = RetirementColors.Gold, modifier = Modifier.size(20.dp).clickable {})
                Spacer(modifier = Modifier.width(8.dp))
            }
        }
    }
}

// ARROW OPTION 10: Contrast Card with Gold Arrow Pill
@Composable
private fun ArrowOption10Content() {
    ArrowCardContainer(
        title = "Вариант 10: Выразительная золотая капсула стрелок",
        description = "Контрастный темно-серый блок с выделенной золотой кнопкой-капсулой со стрелками."
    ) {
        HolidayRowWrapper(name = "New Year's Day (1/1)", checked = true) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(RetirementColors.Gold)
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(imageVector = Icons.Default.ArrowDropUp, contentDescription = "Up", tint = Color(0xFF111315), modifier = Modifier.size(18.dp).clickable {})
                        Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = "Down", tint = Color(0xFF111315), modifier = Modifier.size(18.dp).clickable {})
                    }
                }
                Spacer(modifier = Modifier.width(8.dp))
            }
        }
        HolidayRowWrapper(name = "Martin Luther King Jr. Day (1/16)", checked = false) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(RetirementColors.Gold)
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(imageVector = Icons.Default.ArrowDropUp, contentDescription = "Up", tint = Color(0xFF111315), modifier = Modifier.size(18.dp).clickable {})
                        Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = "Down", tint = Color(0xFF111315), modifier = Modifier.size(18.dp).clickable {})
                    }
                }
                Spacer(modifier = Modifier.width(8.dp))
            }
        }
    }
}

@Composable
private fun ArrowCardContainer(
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

@Composable
private fun HolidayRowWrapper(
    name: String,
    checked: Boolean,
    leadingContent: @Composable () -> Unit = {},
    trailingArrowContent: @Composable () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(Color.White.copy(alpha = 0.03f))
            .border(1.dp, RetirementColors.Gold.copy(alpha = 0.3f), RoundedCornerShape(8.dp))
            .padding(horizontal = 10.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        leadingContent()
        Text(
            text = name,
            style = TextStyle(
                fontFamily = PlayfairDisplay,
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold,
                color = RetirementColors.Cream
            ),
            modifier = Modifier.weight(1f)
        )
        trailingArrowContent()
        Checkbox(
            checked = checked,
            onCheckedChange = {},
            colors = CheckboxDefaults.colors(
                checkedColor = RetirementColors.Gold,
                uncheckedColor = RetirementColors.Gold.copy(alpha = 0.5f)
            )
        )
    }
}

// INDIVIDUAL PREVIEWS FOR ALL 10 ARROW OPTIONS
@Preview(showBackground = true, widthDp = 380, heightDp = 350, name = "1: Vertical Stack ▲ ▼")
@Composable
fun PreviewArrowOption1() { RetirementTimerAppTheme { Box(modifier = Modifier.background(RetirementColors.Background).padding(16.dp)) { ArrowOption1Content() } } }

@Preview(showBackground = true, widthDp = 380, heightDp = 350, name = "2: Horizontal Arrows ▲ ▼")
@Composable
fun PreviewArrowOption2() { RetirementTimerAppTheme { Box(modifier = Modifier.background(RetirementColors.Background).padding(16.dp)) { ArrowOption2Content() } } }

@Preview(showBackground = true, widthDp = 380, heightDp = 350, name = "3: Gold Badge [ ⇅ ]")
@Composable
fun PreviewArrowOption3() { RetirementTimerAppTheme { Box(modifier = Modifier.background(RetirementColors.Background).padding(16.dp)) { ArrowOption3Content() } } }

@Preview(showBackground = true, widthDp = 380, heightDp = 350, name = "4: Left Arrows ▲ ▼")
@Composable
fun PreviewArrowOption4() { RetirementTimerAppTheme { Box(modifier = Modifier.background(RetirementColors.Background).padding(16.dp)) { ArrowOption4Content() } } }

@Preview(showBackground = true, widthDp = 380, heightDp = 350, name = "5: Rounded Gold Buttons ▲ ▼")
@Composable
fun PreviewArrowOption5() { RetirementTimerAppTheme { Box(modifier = Modifier.background(RetirementColors.Background).padding(16.dp)) { ArrowOption5Content() } } }

@Preview(showBackground = true, widthDp = 380, heightDp = 350, name = "6: Split Controls (▲ Left, ▼ Right)")
@Composable
fun PreviewArrowOption6() { RetirementTimerAppTheme { Box(modifier = Modifier.background(RetirementColors.Background).padding(16.dp)) { ArrowOption6Content() } } }

@Preview(showBackground = true, widthDp = 380, heightDp = 350, name = "7: Glassmorphic Capsule Pill")
@Composable
fun PreviewArrowOption7() { RetirementTimerAppTheme { Box(modifier = Modifier.background(RetirementColors.Background).padding(16.dp)) { ArrowOption7Content() } } }

@Preview(showBackground = true, widthDp = 380, heightDp = 350, name = "8: Serif Thin Arrows ↑ ↓")
@Composable
fun PreviewArrowOption8() { RetirementTimerAppTheme { Box(modifier = Modifier.background(RetirementColors.Background).padding(16.dp)) { ArrowOption8Content() } } }

@Preview(showBackground = true, widthDp = 380, heightDp = 350, name = "9: Material Chevrons ˄ ˅")
@Composable
fun PreviewArrowOption9() { RetirementTimerAppTheme { Box(modifier = Modifier.background(RetirementColors.Background).padding(16.dp)) { ArrowOption9Content() } } }

@Preview(showBackground = true, widthDp = 380, heightDp = 350, name = "10: Contrast Gold Arrow Pill")
@Composable
fun PreviewArrowOption10() { RetirementTimerAppTheme { Box(modifier = Modifier.background(RetirementColors.Background).padding(16.dp)) { ArrowOption10Content() } } }
