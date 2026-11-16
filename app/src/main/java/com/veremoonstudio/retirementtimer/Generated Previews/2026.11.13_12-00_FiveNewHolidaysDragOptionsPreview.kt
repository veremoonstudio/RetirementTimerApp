@file:JvmName("FiveNewHolidaysDragOptionsPreviewDot")
package com.veremoonstudio.retirementtimer

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
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
fun FiveNewHolidaysDragOptionsPreviewScreen() {
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
            text = "5 НОВЫХ ВАРИАНТОВ ПЕРЕМЕЩЕНИЯ",
            style = TextStyle(
                fontFamily = PlayfairDisplay,
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                color = RetirementColors.Gold,
                textAlign = TextAlign.Center,
                letterSpacing = 1.sp
            )
        )

        // VARIANT 6: 3D Elevation Shadow Card
        OptionCardContainer(
            title = "Вариант 6: Объемный подъём с тенью (3D Elevation + Glow)",
            description = "• Активация: Длительное зажатие карточки.\n• Визуал: Карточка 'приподнимается' над списком с мягкой объёмной тенью и серо-белым свечением.\n• Движение: Карточка плавно скользит поверх остальных элементов.\n• Фиксация: Мягкое опущение на новую позицию при отпускании."
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                HolidayItemRow(name = "New Year's Day (1/1)", checked = true)
                // Active Dragging 3D Elevated Card
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

        // VARIANT 7: Double Line Left Grip (||)
        OptionCardContainer(
            title = "Вариант 7: Двойная рельефная полоса || (Double Grip Bar)",
            description = "• Активация: Зажатие двух вертикальных полосок || слева или длительное нажатие.\n• Визуал: Слева отображаются две тонкие параллельные полоски `||`.\n• Движение: При зажатии полоски и ободок становятся ярко-белыми.\n• Фиксация: Мгновенное сохранение порядка при отпускании."
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                HolidayItemRow(name = "New Year's Day (1/1)", checked = true)
                // Active Item with Double Grip Bar
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
                        Box(
                            modifier = Modifier
                                .width(2.dp)
                                .height(18.dp)
                                .background(Color.White.copy(alpha = 0.8f))
                        )
                        Spacer(modifier = Modifier.width(3.dp))
                        Box(
                            modifier = Modifier
                                .width(2.dp)
                                .height(18.dp)
                                .background(Color.White.copy(alpha = 0.8f))
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
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

        // VARIANT 8: Pill Capsule Badge (Shape 24.dp)
        OptionCardContainer(
            title = "Вариант 8: Капсула Pill Shape (Rounded Capsule 24.dp)",
            description = "• Активация: Длительный клик по карточке.\n• Визуал: Праздник трансформируется в закругленную капсулу с мягким серо-белым ободком.\n• Движение: Сглаженный современный вид капсулы при движении.\n• Фиксация: Сбрасывает закругление до стандартного вида при фиксации."
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                HolidayItemRow(name = "New Year's Day (1/1)", checked = true)
                // Active Dragging Pill Capsule
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
                        style = TextStyle(
                            fontFamily = PlayfairDisplay,
                            fontSize = 15.sp,
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

        // VARIANT 9: Right Vertical Arrow Unfold Indicator (⇅)
        OptionCardContainer(
            title = "Вариант 9: Иконка стрелок ⇅ (Unfold Arrows Indicator)",
            description = "• Активация: Зажатие иконки стрелок ⇅ справа от текста или удерживание.\n• Визуал: Справа отображается минималистичная иконка двойных стрелок вверх/вниз ⇅.\n• Движение: При свайпе карточка выделяется аккуратным контуром.\n• Фиксация: Завершение перемещения по клику вне карточки."
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                HolidayItemRow(name = "New Year's Day (1/1)", checked = true)
                // Active Dragging Arrow Row
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
                        style = TextStyle(
                            fontFamily = PlayfairDisplay,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = RetirementColors.Cream
                        ),
                        modifier = Modifier.weight(1f)
                    )
                    Icon(
                        imageVector = Icons.Default.UnfoldMore,
                        contentDescription = "Reorder Arrows",
                        tint = Color.White,
                        modifier = Modifier.size(22.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
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

        // VARIANT 10: Inverted Contrast Dark Card with White Frame
        OptionCardContainer(
            title = "Вариант 10: Инверсная карточка (Contrast Inverted Dark Card)",
            description = "• Активация: Длительное зажатие строки.\n• Визуал: Карточка принимает контрастную подложку `#18171C` с выразительной белой рамкой.\n• Движение: Максимальная читаемость и контраст во время свайпа.\n• Фиксация: Возврат к прозрачному фону списка."
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                HolidayItemRow(name = "New Year's Day (1/1)", checked = true)
                // Active Inverted Card
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

@Preview(showBackground = true, widthDp = 380, heightDp = 320, name = "Option 6: 3D Elevation")
@Composable
fun Option6Preview() {
    RetirementTimerAppTheme {
        Box(modifier = Modifier.background(RetirementColors.Background).padding(16.dp)) {
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
                            style = TextStyle(
                                fontFamily = PlayfairDisplay,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            ),
                            modifier = Modifier.weight(1f)
                        )
                        Checkbox(checked = false, onCheckedChange = {})
                    }
                    HolidayItemRow(name = "Presidents' Day (2/20)", checked = false)
                }
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 380, heightDp = 320, name = "Option 7: Double Line Grip ||")
@Composable
fun Option7Preview() {
    RetirementTimerAppTheme {
        Box(modifier = Modifier.background(RetirementColors.Background).padding(16.dp)) {
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
                            Box(
                                modifier = Modifier
                                    .width(2.dp)
                                    .height(18.dp)
                                    .background(Color.White.copy(alpha = 0.8f))
                            )
                            Spacer(modifier = Modifier.width(3.dp))
                            Box(
                                modifier = Modifier
                                    .width(2.dp)
                                    .height(18.dp)
                                    .background(Color.White.copy(alpha = 0.8f))
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
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
                        Checkbox(checked = false, onCheckedChange = {})
                    }
                    HolidayItemRow(name = "Presidents' Day (2/20)", checked = false)
                }
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 380, heightDp = 320, name = "Option 8: Pill Shape Capsule")
@Composable
fun Option8Preview() {
    RetirementTimerAppTheme {
        Box(modifier = Modifier.background(RetirementColors.Background).padding(16.dp)) {
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
                            style = TextStyle(
                                fontFamily = PlayfairDisplay,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = RetirementColors.Cream
                            ),
                            modifier = Modifier.weight(1f)
                        )
                        Checkbox(checked = false, onCheckedChange = {})
                    }
                    HolidayItemRow(name = "Presidents' Day (2/20)", checked = false)
                }
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 380, heightDp = 320, name = "Option 9: Unfold Arrows ⇅")
@Composable
fun Option9Preview() {
    RetirementTimerAppTheme {
        Box(modifier = Modifier.background(RetirementColors.Background).padding(16.dp)) {
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
                            style = TextStyle(
                                fontFamily = PlayfairDisplay,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = RetirementColors.Cream
                            ),
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
    }
}

@Preview(showBackground = true, widthDp = 380, heightDp = 320, name = "Option 10: Inverted Dark Card")
@Composable
fun Option10Preview() {
    RetirementTimerAppTheme {
        Box(modifier = Modifier.background(RetirementColors.Background).padding(16.dp)) {
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
                            style = TextStyle(
                                fontFamily = PlayfairDisplay,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            ),
                            modifier = Modifier.weight(1f)
                        )
                        Checkbox(checked = false, onCheckedChange = {})
                    }
                    HolidayItemRow(name = "Presidents' Day (2/20)", checked = false)
                }
            }
        }
    }
}
