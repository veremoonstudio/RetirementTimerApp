package com.veremoonstudio.retirementtimer

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DragIndicator
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
fun FiveHolidaysDragOptionsPreviewScreen() {
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
            text = "5 ВАРИАНТОВ ПЕРЕМЕЩЕНИЯ ПРАЗДНИКОВ",
            style = TextStyle(
                fontFamily = PlayfairDisplay,
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                color = RetirementColors.Gold,
                textAlign = TextAlign.Center,
                letterSpacing = 1.sp
            )
        )

        // VARIANT 1: Classic Floating Card with Gray Border & Offset
        OptionCardContainer(
            title = "Вариант 1: Серый плавающий контур (Offset + Gray Border)",
            description = "• Активация: Длительное нажатие на строку карточки.\n• Визуал: Карточка подсвечивается серой рамкой (White 50%) и сдвигается вправо на 6.dp.\n• Движение: Карточка плавно скользит по вертикали за пальцем.\n• Фиксация: Клик вне карточки или отпускание пальца сохраняет новый порядок."
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                HolidayItemRow(name = "New Year's Day (1/1)", checked = true)
                // Active Dragging State (Gray border + slight right offset)
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
                        style = TextStyle(
                            fontFamily = PlayfairDisplay,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = RetirementColors.Cream
                        ),
                        modifier = Modifier.weight(1f)
                    )
                    Checkbox(
                        checked = false,
                        onCheckedChange = {},
                        colors = CheckboxDefaults.colors(
                            checkedColor = RetirementColors.Gold,
                            uncheckedColor = RetirementColors.Gold.copy(alpha = 0.5f)
                        )
                    )
                }
                HolidayItemRow(name = "Presidents' Day (2/20)", checked = false)
            }
        }

        // VARIANT 2: Drag Handle Grip Icon + Gray Accent
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

        // VARIANT 3: Glassmorphic Glow Card
        OptionCardContainer(
            title = "Вариант 3: Стекло с контуром (Glassmorphic Glow)",
            description = "• Активация: Длительное удерживание карточки.\n• Визуал: Карточка превращается в объемную полупрозрачную стеклянную плашку с радиусом 12.dp и ярким белым кантом.\n• Движение: Текст становится жирнее, а плашка эффектом парения движется за пальцем.\n• Фиксация: Завершение жеста плавно возвращает карточку в список."
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                HolidayItemRow(name = "New Year's Day (1/1)", checked = true)
                // Active Dragging Glass Card
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
                        style = TextStyle(
                            fontFamily = PlayfairDisplay,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        ),
                        modifier = Modifier.weight(1f)
                    )
                    Checkbox(
                        checked = false,
                        onCheckedChange = {},
                        colors = CheckboxDefaults.colors(
                            checkedColor = RetirementColors.Gold,
                            uncheckedColor = RetirementColors.Gold.copy(alpha = 0.5f)
                        )
                    )
                }
                HolidayItemRow(name = "Presidents' Day (2/20)", checked = false)
            }
        }

        // VARIANT 4: Left Accent Strip (Highlight Strip)
        OptionCardContainer(
            title = "Вариант 4: Акцентная полоса слева (Left Accent Strip)",
            description = "• Активация: Длительное нажатие на карточку.\n• Визуал: Слева появится вертикальная контрастная полоса-маркер (White 70%), подложка становится темнее (`#2A2830`).\n• Движение: Четкая индикация удерживаемой карточки во время перемещения.\n• Фиксация: По завершении перетаскивания полоса исчезает."
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                HolidayItemRow(name = "New Year's Day (1/1)", checked = true)
                // Dragging item with left accent bar
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
                        style = TextStyle(
                            fontFamily = PlayfairDisplay,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = RetirementColors.Cream
                        ),
                        modifier = Modifier.weight(1f)
                    )
                    Checkbox(
                        checked = false,
                        onCheckedChange = {},
                        colors = CheckboxDefaults.colors(
                            checkedColor = RetirementColors.Gold,
                            uncheckedColor = RetirementColors.Gold.copy(alpha = 0.5f)
                        )
                    )
                }
                HolidayItemRow(name = "Presidents' Day (2/20)", checked = false)
            }
        }

        // VARIANT 5: Gold Premium Border
        OptionCardContainer(
            title = "Вариант 5: Золотой премиум-контур (Gold Border Card)",
            description = "• Активация: Длительное удерживание праздника.\n• Визуал: Карточка выделяется ярко-золотым ободком (`RetirementColors.Gold`), легкой золотистой заливкой и золотым текстом.\n• Движение: Вписывается в золотую стилистику интерфейса приложении.\n• Фиксация: Возвращается к стандартному стилю при сохранении позиции."
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                HolidayItemRow(name = "New Year's Day (1/1)", checked = true)
                // Dragging item with gold border
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
                        style = TextStyle(
                            fontFamily = PlayfairDisplay,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = RetirementColors.Gold
                        ),
                        modifier = Modifier.weight(1f)
                    )
                    Checkbox(
                        checked = false,
                        onCheckedChange = {},
                        colors = CheckboxDefaults.colors(
                            checkedColor = RetirementColors.Gold,
                            uncheckedColor = RetirementColors.Gold.copy(alpha = 0.5f)
                        )
                    )
                }
                HolidayItemRow(name = "Presidents' Day (2/20)", checked = false)
            }
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

@Preview(showBackground = true, widthDp = 380, heightDp = 300, name = "Option 4: Left Accent Strip")
@Composable
fun Option4Preview() {
    RetirementTimerAppTheme {
        Box(modifier = Modifier.background(RetirementColors.Background).padding(16.dp)) {
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
                            style = TextStyle(
                                fontFamily = PlayfairDisplay,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = RetirementColors.Cream
                            ),
                            modifier = Modifier.weight(1f)
                        )
                        Checkbox(
                            checked = false,
                            onCheckedChange = {},
                            colors = CheckboxDefaults.colors(
                                checkedColor = RetirementColors.Gold,
                                uncheckedColor = RetirementColors.Gold.copy(alpha = 0.5f)
                            )
                        )
                    }
                    HolidayItemRow(name = "Presidents' Day (2/20)", checked = false)
                }
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 380, heightDp = 300, name = "Option 5: Gold Border Card")
@Composable
fun Option5Preview() {
    RetirementTimerAppTheme {
        Box(modifier = Modifier.background(RetirementColors.Background).padding(16.dp)) {
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
                            style = TextStyle(
                                fontFamily = PlayfairDisplay,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = RetirementColors.Gold
                            ),
                            modifier = Modifier.weight(1f)
                        )
                        Checkbox(
                            checked = false,
                            onCheckedChange = {},
                            colors = CheckboxDefaults.colors(
                                checkedColor = RetirementColors.Gold,
                                uncheckedColor = RetirementColors.Gold.copy(alpha = 0.5f)
                            )
                        )
                    }
                    HolidayItemRow(name = "Presidents' Day (2/20)", checked = false)
                }
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 410, heightDp = 1350, name = "Five Holidays Drag Options Preview")
@Composable
fun FiveHolidaysDragOptionsPreview() {
    RetirementTimerAppTheme {
        FiveHolidaysDragOptionsPreviewScreen()
    }
}
