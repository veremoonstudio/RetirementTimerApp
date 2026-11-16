@file:JvmName("AllTenHolidaysDragOptionsPreview")
package com.veremoonstudio.retirementtimer

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DragIndicator
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
fun AllTenHolidaysDragOptionsPreviewScreen() {
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
            text = "ВСЕ 10 ВАРИАНТОВ ПЕРЕМЕЩЕНИЯ ПРАЗДНИКОВ",
            style = TextStyle(
                fontFamily = PlayfairDisplay,
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                color = RetirementColors.Gold,
                textAlign = TextAlign.Center,
                letterSpacing = 1.sp
            )
        )

        Option1CardContent()
        Option2CardContent()
        Option3CardContent()
        Option4CardContent()
        Option5CardContent()
        Option6CardContent()
        Option7CardContent()
        Option8CardContent()
        Option9CardContent()
        Option10CardContent()
    }
}

// OPTION 1
@Composable
private fun Option1CardContent() {
    OptionCardContainer(
        title = "Вариант 1: Серый плавающий контур (Offset + Gray Border)",
        description = "• Активация: Длительное нажатие на строку карточки.\n• Визуал: Карточка подсвечивается серой рамкой (White 50%) и сдвигается вправо на 6.dp.\n• Движение: Карточка плавно скользит по вертикали за пальцем.\n• Фиксация: Клик вне карточки или отпускание пальца сохраняет новый порядок."
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
            HolidayItemRow(name = "New Year's Day (1/1)", checked = true)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .offset(x = 6.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.White.copy(alpha = 0.08f))
                    .border(1.5.dp, Color.White.copy(alpha = 0.5f), RoundedCornerShape(8.dp))
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Martin Luther King Jr. Day (1/16)",
                    style = TextStyle(fontFamily = PlayfairDisplay, fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = RetirementColors.Cream),
                    modifier = Modifier.weight(1f)
                )
                Checkbox(checked = false, onCheckedChange = {})
            }
            HolidayItemRow(name = "Presidents' Day (2/20)", checked = false)
        }
    }
}

// OPTION 2
@Composable
private fun Option2CardContent() {
    OptionCardContainer(
        title = "Вариант 2: С рукояткой захвата (Drag Handle Icon ::)",
        description = "• Активация: Зажатие иконки :: слева или длительное нажатие на карточку.\n• Визуал: Слева отображаются 6 точек (рукоятка захвата), при перетаскивании иконка и рамка подсвечиваются ярко-белым.\n• Движение: Понятный визуальный маркер захвата с поддержкой свайпа.\n• Фиксация: При отпускании порядок моментально фиксируется."
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
            HolidayItemWithHandleRow(name = "New Year's Day (1/1)", checked = true, isDragging = false)
            HolidayItemWithHandleRow(name = "Martin Luther King Jr. Day (1/16)", checked = false, isDragging = true)
            HolidayItemWithHandleRow(name = "Presidents' Day (2/20)", checked = false, isDragging = false)
        }
    }
}

// OPTION 3
@Composable
private fun Option3CardContent() {
    OptionCardContainer(
        title = "Вариант 3: Стекло с контуром (Glassmorphic Glow)",
        description = "• Активация: Длительное удерживание карточки.\n• Визуал: Карточка превращается в объемную полупрозрачную стеклянную плашку с радиусом 12.dp и ярким белым кантом.\n• Движение: Текст становится жирнее, а плашка эффектом парения движется за пальцем.\n• Фиксация: Завершение жеста плавно возвращает карточку в список."
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
            HolidayItemRow(name = "New Year's Day (1/1)", checked = true)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.White.copy(alpha = 0.12f))
                    .border(1.5.dp, Color.White.copy(alpha = 0.7f), RoundedCornerShape(12.dp))
                    .padding(horizontal = 14.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Martin Luther King Jr. Day (1/16)",
                    style = TextStyle(fontFamily = PlayfairDisplay, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.White),
                    modifier = Modifier.weight(1f)
                )
                Checkbox(checked = false, onCheckedChange = {})
            }
            HolidayItemRow(name = "Presidents' Day (2/20)", checked = false)
        }
    }
}

// OPTION 4
@Composable
private fun Option4CardContent() {
    OptionCardContainer(
        title = "Вариант 4: Акцентная полоса слева (Left Accent Strip)",
        description = "• Активация: Длительное нажатие на карточку.\n• Визуал: Слева появится вертикальная контрастная полоса-маркер (White 70%), подложка карточки становится темнее (`#2A2830`).\n• Движение: Четкая индикация удерживаемой карточки во время перемещения.\n• Фиксация: По завершении перетаскивания полоса исчезает."
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
            HolidayItemRow(name = "New Year's Day (1/1)", checked = true)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0xFF2A2830))
                    .border(1.dp, Color.White.copy(alpha = 0.3f), RoundedCornerShape(8.dp))
                    .padding(start = 4.dp, end = 12.dp, top = 8.dp, bottom = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Box(
                    modifier = Modifier
                        .width(4.dp)
                        .height(24.dp)
                        .clip(RoundedCornerShape(2.dp))
                        .background(Color.White.copy(alpha = 0.7f))
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Martin Luther King Jr. Day (1/16)",
                    style = TextStyle(fontFamily = PlayfairDisplay, fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = RetirementColors.Cream),
                    modifier = Modifier.weight(1f)
                )
                Checkbox(checked = false, onCheckedChange = {})
            }
            HolidayItemRow(name = "Presidents' Day (2/20)", checked = false)
        }
    }
}

// OPTION 5
@Composable
private fun Option5CardContent() {
    OptionCardContainer(
        title = "Вариант 5: Золотой премиум-контур (Gold Border Card)",
        description = "• Активация: Длительное удерживание праздника.\n• Визуал: Карточка выделяется ярко-золотым ободком (`RetirementColors.Gold`), легкой золотистой заливкой и золотым текстом.\n• Движение: Вписывается в золотую стилистику интерфейса приложении.\n• Фиксация: Возвращается к стандартному стилю при сохранении позиции."
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
            HolidayItemRow(name = "New Year's Day (1/1)", checked = true)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(RetirementColors.Gold.copy(alpha = 0.12f))
                    .border(1.5.dp, RetirementColors.Gold, RoundedCornerShape(8.dp))
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Martin Luther King Jr. Day (1/16)",
                    style = TextStyle(fontFamily = PlayfairDisplay, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = RetirementColors.Gold),
                    modifier = Modifier.weight(1f)
                )
                Checkbox(checked = false, onCheckedChange = {})
            }
            HolidayItemRow(name = "Presidents' Day (2/20)", checked = false)
        }
    }
}

// OPTION 6
@Composable
private fun Option6CardContent() {
    OptionCardContainer(
        title = "Вариант 6: Объемный подъём с тенью (3D Elevation + Glow)",
        description = "• Активация: Длительное зажатие карточки.\n• Визуал: Карточка 'приподнимается' над списком с мягкой объёмной тенью и серо-белым свечением.\n• Движение: Карточка плавно скользит поверх остальных элементов.\n• Фиксация: Мягкое опущение на новую позицию при отпускании."
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
            HolidayItemRow(name = "New Year's Day (1/1)", checked = true)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color(0xFF2C2A34))
                    .border(1.5.dp, Color.White.copy(alpha = 0.5f), RoundedCornerShape(10.dp))
                    .padding(horizontal = 12.dp, vertical = 9.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Martin Luther King Jr. Day (1/16)",
                    style = TextStyle(fontFamily = PlayfairDisplay, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.White),
                    modifier = Modifier.weight(1f)
                )
                Checkbox(checked = false, onCheckedChange = {})
            }
            HolidayItemRow(name = "Presidents' Day (2/20)", checked = false)
        }
    }
}

// OPTION 7
@Composable
private fun Option7CardContent() {
    OptionCardContainer(
        title = "Вариант 7: Двойная рельефная полоса || (Double Grip Bar)",
        description = "• Активация: Зажатие двух вертикальных полосок || слева или длительное нажатие.\n• Визуал: Слева отображаются две тонкие параллельные полоски `||`.\n• Движение: При зажатии полоски и ободок становятся ярко-белыми.\n• Фиксация: Мгновенное сохранение порядка при отпускании."
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
            HolidayItemRow(name = "New Year's Day (1/1)", checked = true)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.White.copy(alpha = 0.08f))
                    .border(1.5.dp, Color.White.copy(alpha = 0.5f), RoundedCornerShape(8.dp))
                    .padding(start = 8.dp, end = 12.dp, top = 8.dp, bottom = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(modifier = Modifier.width(2.dp).height(18.dp).background(Color.White.copy(alpha = 0.8f)))
                    Spacer(modifier = Modifier.width(3.dp))
                    Box(modifier = Modifier.width(2.dp).height(18.dp).background(Color.White.copy(alpha = 0.8f)))
                }
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = "Martin Luther King Jr. Day (1/16)",
                    style = TextStyle(fontFamily = PlayfairDisplay, fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = RetirementColors.Cream),
                    modifier = Modifier.weight(1f)
                )
                Checkbox(checked = false, onCheckedChange = {})
            }
            HolidayItemRow(name = "Presidents' Day (2/20)", checked = false)
        }
    }
}

// OPTION 8
@Composable
private fun Option8CardContent() {
    OptionCardContainer(
        title = "Вариант 8: Капсула Pill Shape (Rounded Capsule 24.dp)",
        description = "• Активация: Длительный клик по карточке.\n• Визуал: Праздник трансформируется в закругленную капсулу с мягким серо-белым ободком.\n• Движение: Сглаженный современный вид капсулы при движении.\n• Фиксация: Сбрасывает закругление до стандартного вида при фиксации."
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
            HolidayItemRow(name = "New Year's Day (1/1)", checked = true)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(24.dp))
                    .background(Color.White.copy(alpha = 0.10f))
                    .border(1.5.dp, Color.White.copy(alpha = 0.5f), RoundedCornerShape(24.dp))
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Martin Luther King Jr. Day (1/16)",
                    style = TextStyle(fontFamily = PlayfairDisplay, fontSize = 15.sp, fontWeight = FontWeight.SemiBold, color = RetirementColors.Cream),
                    modifier = Modifier.weight(1f)
                )
                Checkbox(checked = false, onCheckedChange = {})
            }
            HolidayItemRow(name = "Presidents' Day (2/20)", checked = false)
        }
    }
}

// OPTION 9
@Composable
private fun Option9CardContent() {
    OptionCardContainer(
        title = "Вариант 9: Иконка стрелок ⇅ (Unfold Arrows Indicator)",
        description = "• Активация: Зажатие иконки стрелок ⇅ справа от текста или удерживание.\n• Визуал: Справа отображается минималистичная иконка двойных стрелок вверх/вниз ⇅.\n• Движение: При свайпе карточка выделяется аккуратным контуром.\n• Фиксация: Завершение перемещения по клику вне карточки."
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
            HolidayItemRow(name = "New Year's Day (1/1)", checked = true)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.White.copy(alpha = 0.08f))
                    .border(1.5.dp, Color.White.copy(alpha = 0.5f), RoundedCornerShape(8.dp))
                    .padding(horizontal = 10.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Martin Luther King Jr. Day (1/16)",
                    style = TextStyle(fontFamily = PlayfairDisplay, fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = RetirementColors.Cream),
                    modifier = Modifier.weight(1f)
                )
                Icon(
                    imageVector = Icons.Default.UnfoldMore,
                    contentDescription = "Reorder Arrows",
                    tint = Color.White,
                    modifier = Modifier.size(22.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Checkbox(checked = false, onCheckedChange = {})
            }
            HolidayItemRow(name = "Presidents' Day (2/20)", checked = false)
        }
    }
}

// OPTION 10
@Composable
private fun Option10CardContent() {
    OptionCardContainer(
        title = "Вариант 10: Инверсная карточка (Contrast Inverted Dark Card)",
        description = "• Активация: Длительное зажатие строки.\n• Визуал: Карточка принимает контрастную подложку `#18171C` с выразительной белой рамкой.\n• Движение: Максимальная читаемость и контраст во время свайпа.\n• Фиксация: Возврат к прозрачному фону списка."
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
            HolidayItemRow(name = "New Year's Day (1/1)", checked = true)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0xFF18171C))
                    .border(1.5.dp, Color.White.copy(alpha = 0.6f), RoundedCornerShape(8.dp))
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Martin Luther King Jr. Day (1/16)",
                    style = TextStyle(fontFamily = PlayfairDisplay, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.White),
                    modifier = Modifier.weight(1f)
                )
                Checkbox(checked = false, onCheckedChange = {})
            }
            HolidayItemRow(name = "Presidents' Day (2/20)", checked = false)
        }
    }
}

@Composable
private fun OptionCardContainer(
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
private fun HolidayItemRow(name: String, checked: Boolean) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = name,
            style = TextStyle(
                fontFamily = PlayfairDisplay,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = RetirementColors.Cream
            ),
            modifier = Modifier.weight(1f)
        )
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

@Composable
private fun HolidayItemWithHandleRow(name: String, checked: Boolean, isDragging: Boolean) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(
                if (isDragging) Color.White.copy(alpha = 0.08f)
                else Color.Transparent
            )
            .border(
                width = if (isDragging) 1.5.dp else 0.dp,
                color = if (isDragging) Color.White.copy(alpha = 0.5f) else Color.Transparent,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(horizontal = 8.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Icon(
            imageVector = Icons.Default.DragIndicator,
            contentDescription = "Drag Handle",
            tint = if (isDragging) Color.White else Color.White.copy(alpha = 0.4f),
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = name,
            style = TextStyle(
                fontFamily = PlayfairDisplay,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = RetirementColors.Cream
            ),
            modifier = Modifier.weight(1f)
        )
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

// INDIVIDUAL PREVIEWS FOR EVERY OPTION
@Preview(showBackground = true, widthDp = 380, heightDp = 430, name = "1: Gray Floating Border")
@Composable
fun PreviewOption1() { RetirementTimerAppTheme { Box(modifier = Modifier.background(RetirementColors.Background).padding(16.dp)) { Option1CardContent() } } }

@Preview(showBackground = true, widthDp = 380, heightDp = 430, name = "2: Drag Handle ::")
@Composable
fun PreviewOption2() { RetirementTimerAppTheme { Box(modifier = Modifier.background(RetirementColors.Background).padding(16.dp)) { Option2CardContent() } } }

@Preview(showBackground = true, widthDp = 380, heightDp = 430, name = "3: Glassmorphic Glow")
@Composable
fun PreviewOption3() { RetirementTimerAppTheme { Box(modifier = Modifier.background(RetirementColors.Background).padding(16.dp)) { Option3CardContent() } } }

@Preview(showBackground = true, widthDp = 380, heightDp = 430, name = "4: Left Accent Strip")
@Composable
fun PreviewOption4() { RetirementTimerAppTheme { Box(modifier = Modifier.background(RetirementColors.Background).padding(16.dp)) { Option4CardContent() } } }

@Preview(showBackground = true, widthDp = 380, heightDp = 430, name = "5: Gold Premium Border")
@Composable
fun PreviewOption5() { RetirementTimerAppTheme { Box(modifier = Modifier.background(RetirementColors.Background).padding(16.dp)) { Option5CardContent() } } }

@Preview(showBackground = true, widthDp = 380, heightDp = 430, name = "6: 3D Elevation Shadow")
@Composable
fun PreviewOption6() { RetirementTimerAppTheme { Box(modifier = Modifier.background(RetirementColors.Background).padding(16.dp)) { Option6CardContent() } } }

@Preview(showBackground = true, widthDp = 380, heightDp = 430, name = "7: Double Line ||")
@Composable
fun PreviewOption7() { RetirementTimerAppTheme { Box(modifier = Modifier.background(RetirementColors.Background).padding(16.dp)) { Option7CardContent() } } }

@Preview(showBackground = true, widthDp = 380, heightDp = 430, name = "8: Pill Capsule")
@Composable
fun PreviewOption8() { RetirementTimerAppTheme { Box(modifier = Modifier.background(RetirementColors.Background).padding(16.dp)) { Option8CardContent() } } }

@Preview(showBackground = true, widthDp = 380, heightDp = 430, name = "9: Unfold Arrows ⇅")
@Composable
fun PreviewOption9() { RetirementTimerAppTheme { Box(modifier = Modifier.background(RetirementColors.Background).padding(16.dp)) { Option9CardContent() } } }

@Preview(showBackground = true, widthDp = 380, heightDp = 430, name = "10: Inverted Dark Card")
@Composable
fun PreviewOption10() { RetirementTimerAppTheme { Box(modifier = Modifier.background(RetirementColors.Background).padding(16.dp)) { Option10CardContent() } } }
