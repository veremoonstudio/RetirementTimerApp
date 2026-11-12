package com.veremoonstudio.retirementtimer

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.material.icons.filled.Delete
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
import com.veremoonstudio.retirementtimer.ui.theme.CormorantGaramond
import com.veremoonstudio.retirementtimer.ui.theme.PlayfairDisplay
import com.veremoonstudio.retirementtimer.ui.theme.RetirementColors
import com.veremoonstudio.retirementtimer.ui.theme.RetirementTimerAppTheme

@Composable
fun MultiVacationPreviewScreen() {
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
            text = "10 ВАРІАНТИВ МНОГОКАРТОЧНОГО ОТПУСКА",
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

        // Version 1
        VersionCardWrapper(
            title = "Версия 1: Карточки отпуска + золотая кнопка снизу",
            description = "Каждый период в своей карточке с иконкой удаления + кнопка «+ ADD VACATION»"
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                VacationCardItem(monthTag = "NOV", dateText = "13.11.2026 – 19.11.2026", daysText = "5 d")
                VacationCardItem(monthTag = "DEC", dateText = "20.12.2026 – 28.12.2026", daysText = "7 d")

                Button(
                    onClick = {},
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(44.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White.copy(alpha = 0.05f),
                        contentColor = RetirementColors.Gold
                    ),
                    border = BorderStroke(1.dp, RetirementColors.Gold.copy(alpha = 0.5f)),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "ADD VACATION PERIOD",
                        style = TextStyle(fontFamily = PlayfairDisplay, fontSize = 13.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
                    )
                }
            }
        }

        // Version 2
        VersionCardWrapper(
            title = "Версия 2: Кнопка «+» в самом заголовке секции",
            description = "Кнопка «+» находится в заголовке VACATION DAYS для компактности"
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "VACATION DAYS",
                        style = TextStyle(fontFamily = PlayfairDisplay, fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = RetirementColors.Gold, letterSpacing = 1.5.sp)
                    )
                    IconButton(
                        onClick = {},
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(Color.White.copy(alpha = 0.08f))
                            .border(1.dp, RetirementColors.Gold.copy(alpha = 0.5f), CircleShape)
                    ) {
                        Icon(Icons.Default.Add, contentDescription = null, tint = RetirementColors.Gold, modifier = Modifier.size(18.dp))
                    }
                }

                VacationCardItem(monthTag = "NOV", dateText = "13.11.2026 – 19.11.2026", daysText = "5 d")
                VacationCardItem(monthTag = "DEC", dateText = "20.12.2026 – 28.12.2026", daysText = "7 d")
            }
        }

        // Version 3
        VersionCardWrapper(
            title = "Версия 3: Пунктирный слот-кнопка для новой карточки",
            description = "Карточка-слот с пунктирной рамкой «+ Добавить период отпуска»"
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                VacationCardItem(monthTag = "NOV", dateText = "13.11.2026 – 19.11.2026", daysText = "5 d")

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.White.copy(alpha = 0.02f))
                        .border(1.dp, RetirementColors.Gold.copy(alpha = 0.4f), RoundedCornerShape(8.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Add, contentDescription = null, tint = RetirementColors.Gold, modifier = Modifier.size(20.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Add Vacation Period",
                            style = TextStyle(fontFamily = PlayfairDisplay, fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = RetirementColors.Gold)
                        )
                    }
                }
            }
        }

        // Version 4
        VersionCardWrapper(
            title = "Версия 4: Горизонтальные плашки периодов + Кнопка «+»",
            description = "Компактный горизонтальный ряд периодов с кнопкой добавления"
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color.White.copy(alpha = 0.05f))
                            .border(1.dp, Color(0xFF10B981), RoundedCornerShape(8.dp))
                            .padding(10.dp)
                    ) {
                        Column {
                            Text("NOV (5d)", style = TextStyle(fontFamily = PlayfairDisplay, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFF34D399)))
                            Text("13.11 – 19.11", style = TextStyle(fontFamily = CormorantGaramond, fontSize = 13.sp, color = RetirementColors.Cream))
                        }
                    }

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color.White.copy(alpha = 0.05f))
                            .border(1.dp, Color(0xFF10B981), RoundedCornerShape(8.dp))
                            .padding(10.dp)
                    ) {
                        Column {
                            Text("DEC (7d)", style = TextStyle(fontFamily = PlayfairDisplay, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFF34D399)))
                            Text("20.12 – 28.12", style = TextStyle(fontFamily = CormorantGaramond, fontSize = 13.sp, color = RetirementColors.Cream))
                        }
                    }

                    IconButton(
                        onClick = {},
                        modifier = Modifier
                            .size(42.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color.White.copy(alpha = 0.05f))
                            .border(1.dp, RetirementColors.Gold.copy(alpha = 0.5f), RoundedCornerShape(8.dp))
                    ) {
                        Icon(Icons.Default.Add, contentDescription = null, tint = RetirementColors.Gold)
                    }
                }
            }
        }

        // Version 5
        VersionCardWrapper(
            title = "Версия 5: Карточки + Итоговый счётчик суммарных дней",
            description = "Вверху отображается «TOTAL: 12 DAYS», а снизу список карточек и кнопка"
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(RetirementColors.Gold.copy(alpha = 0.12f))
                        .border(1.dp, RetirementColors.Gold.copy(alpha = 0.4f), RoundedCornerShape(8.dp))
                        .padding(horizontal = 14.dp, vertical = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "TOTAL VACATION DAYS:",
                        style = TextStyle(fontFamily = PlayfairDisplay, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = RetirementColors.Gold, letterSpacing = 1.sp)
                    )
                    Text(
                        text = "12 DAYS",
                        style = TextStyle(fontFamily = PlayfairDisplay, fontSize = 15.sp, fontWeight = FontWeight.Bold, color = RetirementColors.Gold)
                    )
                }

                VacationCardItem(monthTag = "NOV", dateText = "13.11.2026 – 19.11.2026", daysText = "5 d")
                VacationCardItem(monthTag = "DEC", dateText = "20.12.2026 – 28.12.2026", daysText = "7 d")

                Button(
                    onClick = {},
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent,
                        contentColor = RetirementColors.Gold
                    ),
                    border = BorderStroke(1.dp, RetirementColors.Gold.copy(alpha = 0.4f)),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("+ Add Another Period", style = TextStyle(fontFamily = PlayfairDisplay, fontSize = 13.sp, fontWeight = FontWeight.Bold))
                }
            }
        }

        // Version 6
        VersionCardWrapper(
            title = "Версия 6: Карточки с плавающей золотой кнопкой (FAB)",
            description = "Карточки списком + компактная золотая плавающая кнопка «+» справа"
        ) {
            Box(modifier = Modifier.fillMaxWidth()) {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.fillMaxWidth()) {
                    VacationCardItem(monthTag = "NOV", dateText = "13.11.2026 – 19.11.2026", daysText = "5 d")
                    VacationCardItem(monthTag = "DEC", dateText = "20.12.2026 – 28.12.2026", daysText = "7 d")
                }

                SmallFloatingActionButton(
                    onClick = {},
                    containerColor = RetirementColors.Gold,
                    contentColor = Color(0xFF111315),
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .offset(x = 6.dp, y = 14.dp)
                ) {
                    Icon(Icons.Default.Add, contentDescription = null)
                }
            }
        }

        // Version 7
        VersionCardWrapper(
            title = "Версия 7: Вертикальный таймлайн периодов отпуска",
            description = "Стильный вертикальный таймлайн с узловыми точками отпусков"
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    Box(modifier = Modifier.size(10.dp).clip(CircleShape).background(Color(0xFF34D399)))
                    Text("13.11.2026 – 19.11.2026 (5 days)", style = TextStyle(fontFamily = PlayfairDisplay, fontSize = 14.sp, color = RetirementColors.Cream, fontWeight = FontWeight.SemiBold))
                }
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    Box(modifier = Modifier.size(10.dp).clip(CircleShape).background(Color(0xFF34D399)))
                    Text("20.12.2026 – 28.12.2026 (7 days)", style = TextStyle(fontFamily = PlayfairDisplay, fontSize = 14.sp, color = RetirementColors.Cream, fontWeight = FontWeight.SemiBold))
                }
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    Box(modifier = Modifier.size(10.dp).clip(CircleShape).border(1.5.dp, RetirementColors.Gold, CircleShape))
                    Text("+ Add Next Vacation Period", style = TextStyle(fontFamily = PlayfairDisplay, fontSize = 14.sp, color = RetirementColors.Gold, fontWeight = FontWeight.Bold))
                }
            }
        }

        // Version 8
        VersionCardWrapper(
            title = "Версия 8: Капсульные бейджи отпусков (Pill Chips)",
            description = "Компактные бейджи-чипы с границей и кнопкой добавления"
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color.White.copy(alpha = 0.05f))
                        .border(1.dp, RetirementColors.Gold.copy(alpha = 0.4f), RoundedCornerShape(16.dp))
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Text("NOV 13-19 (5d)", style = TextStyle(fontFamily = CormorantGaramond, fontSize = 14.sp, color = RetirementColors.Gold, fontWeight = FontWeight.Bold))
                }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color.White.copy(alpha = 0.05f))
                        .border(1.dp, RetirementColors.Gold.copy(alpha = 0.4f), RoundedCornerShape(16.dp))
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Text("DEC 20-28 (7d)", style = TextStyle(fontFamily = CormorantGaramond, fontSize = 14.sp, color = RetirementColors.Gold, fontWeight = FontWeight.Bold))
                }

                IconButton(
                    onClick = {},
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(RetirementColors.Gold)
                ) {
                    Icon(Icons.Default.Add, contentDescription = null, tint = Color(0xFF111315), modifier = Modifier.size(18.dp))
                }
            }
        }

        // Version 9
        VersionCardWrapper(
            title = "Версия 9: Раскрывающиеся списки-аккордеоны",
            description = "Сворачиваемые строки с заголовком и датами"
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.White.copy(alpha = 0.04f))
                        .border(1.dp, RetirementColors.Gold.copy(alpha = 0.3f), RoundedCornerShape(8.dp))
                        .padding(12.dp)
                ) {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Period 1: Nov 13 – Nov 19 (5 days)", style = TextStyle(fontFamily = PlayfairDisplay, fontSize = 14.sp, color = RetirementColors.Cream))
                        Text("✕", style = TextStyle(color = RetirementColors.Gold))
                    }
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.White.copy(alpha = 0.04f))
                        .border(1.dp, RetirementColors.Gold.copy(alpha = 0.3f), RoundedCornerShape(8.dp))
                        .padding(12.dp)
                ) {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Period 2: Dec 20 – Dec 28 (7 days)", style = TextStyle(fontFamily = PlayfairDisplay, fontSize = 14.sp, color = RetirementColors.Cream))
                        Text("✕", style = TextStyle(color = RetirementColors.Gold))
                    }
                }

                TextButton(onClick = {}) {
                    Text("+ Add Vacation Card", style = TextStyle(fontFamily = PlayfairDisplay, fontSize = 14.sp, color = RetirementColors.Gold, fontWeight = FontWeight.Bold))
                }
            }
        }

        // Version 10
        VersionCardWrapper(
            title = "Версия 10: Премиум золотая таблица периодов",
            description = "Табличная сетка с заголовками колонок и строкой добавления"
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .border(1.dp, RetirementColors.Gold.copy(alpha = 0.3f), RoundedCornerShape(8.dp))
            ) {
                // Table Header
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(RetirementColors.Gold.copy(alpha = 0.15f))
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("MONTH", style = TextStyle(fontFamily = PlayfairDisplay, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = RetirementColors.Gold))
                    Text("DATES", style = TextStyle(fontFamily = PlayfairDisplay, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = RetirementColors.Gold))
                    Text("DAYS", style = TextStyle(fontFamily = PlayfairDisplay, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = RetirementColors.Gold))
                }

                HorizontalDivider(color = RetirementColors.Gold.copy(alpha = 0.2f))

                // Row 1
                Row(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp, vertical = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("NOV", style = TextStyle(fontFamily = FontFamily.SansSerif, fontSize = 12.sp, color = Color(0xFF34D399), fontWeight = FontWeight.Bold))
                    Text("13.11 – 19.11.2026", style = TextStyle(fontFamily = CormorantGaramond, fontSize = 14.sp, color = RetirementColors.Cream))
                    Text("5 d  🗑", style = TextStyle(fontFamily = PlayfairDisplay, fontSize = 13.sp, color = RetirementColors.Gold))
                }

                HorizontalDivider(color = RetirementColors.Gold.copy(alpha = 0.15f))

                // Row 2
                Row(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp, vertical = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("DEC", style = TextStyle(fontFamily = FontFamily.SansSerif, fontSize = 12.sp, color = Color(0xFF34D399), fontWeight = FontWeight.Bold))
                    Text("20.12 – 28.12.2026", style = TextStyle(fontFamily = CormorantGaramond, fontSize = 14.sp, color = RetirementColors.Cream))
                    Text("7 d  🗑", style = TextStyle(fontFamily = PlayfairDisplay, fontSize = 13.sp, color = RetirementColors.Gold))
                }

                HorizontalDivider(color = RetirementColors.Gold.copy(alpha = 0.2f))

                // Add row button
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White.copy(alpha = 0.02f))
                        .padding(vertical = 10.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text("+ Add Vacation Row", style = TextStyle(fontFamily = PlayfairDisplay, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = RetirementColors.Gold))
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Composable
private fun VacationCardItem(monthTag: String, dateText: String, daysText: String) {
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
        Column(verticalArrangement = Arrangement.spacedBy(4.dp), modifier = Modifier.weight(1f, fill = false)) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = "VACATION",
                    style = TextStyle(
                        fontFamily = PlayfairDisplay,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = RetirementColors.Gold,
                        letterSpacing = 1.sp
                    )
                )

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(5.dp))
                        .background(Color(0xFF10B981).copy(alpha = 0.22f))
                        .border(1.dp, Color(0xFF10B981), RoundedCornerShape(5.dp))
                        .padding(horizontal = 7.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = monthTag,
                        style = TextStyle(
                            fontFamily = FontFamily.SansSerif,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF34D399),
                            letterSpacing = 0.5.sp
                        )
                    )
                }
            }

            Text(
                text = dateText,
                style = TextStyle(
                    fontFamily = CormorantGaramond,
                    fontSize = 14.5.sp,
                    fontWeight = FontWeight.Medium,
                    color = RetirementColors.Cream
                )
            )
        }

        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            Box(
                modifier = Modifier
                    .width(75.dp)
                    .height(36.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .background(Color.White.copy(alpha = 0.03f))
                    .border(1.dp, RetirementColors.Gold.copy(alpha = 0.5f), RoundedCornerShape(6.dp))
                    .padding(horizontal = 4.dp),
                contentAlignment = Alignment.Center
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    Icon(
                        imageVector = Icons.Filled.CalendarMonth,
                        contentDescription = null,
                        tint = RetirementColors.Gold,
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        text = daysText,
                        style = TextStyle(
                            fontFamily = PlayfairDisplay,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = RetirementColors.Gold
                        )
                    )
                }
            }

            IconButton(onClick = {}, modifier = Modifier.size(32.dp)) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete",
                    tint = RetirementColors.Cream.copy(alpha = 0.6f),
                    modifier = Modifier.size(18.dp)
                )
            }
        }
    }
}

@Composable
private fun VersionCardWrapper(
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

@Preview(showBackground = true, widthDp = 375, heightDp = 3000, name = "10 Multi Vacation Options Preview")
@Composable
fun MultiVacationPreview() {
    RetirementTimerAppTheme {
        MultiVacationPreviewScreen()
    }
}
