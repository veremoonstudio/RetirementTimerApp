@file:JvmName("HolidaySortingOptionsPreviewDotFormat")
package com.veremoonstudio.retirementtimer

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Sort
import androidx.compose.material3.*
import androidx.compose.runtime.*
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

enum class HolidaySortOption(val label: String) {
    BY_DATE("By Date"),
    BY_NAME("By Name"),
    BY_ADDED("Last Added")
}

@Composable
fun HolidaySortingOptionsPreviewScreen() {
    var currentSortOption by remember { mutableStateOf(HolidaySortOption.BY_DATE) }
    
    val rawHolidays = remember {
        listOf(
            Holiday("Presidents' Day", 2, 20, false),
            Holiday("New Year's Day", 1, 1, true),
            Holiday("Memorial Day", 5, 29, false),
            Holiday("Martin Luther King Jr. Day", 1, 16, false),
            Holiday("Independence Day", 7, 4, true)
        )
    }

    val sortedHolidays = remember(currentSortOption, rawHolidays) {
        when (currentSortOption) {
            HolidaySortOption.BY_DATE -> rawHolidays.sortedWith(compareBy({ it.month }, { it.day }))
            HolidaySortOption.BY_NAME -> rawHolidays.sortedBy { it.name }
            HolidaySortOption.BY_ADDED -> rawHolidays
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(RetirementColors.Background)
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "ПРЕВЬЮ: СОРТИРОВКА ПРАЗДНИКОВ БЕЗ ПЕРЕМЕЩЕНИЯ",
            style = TextStyle(
                fontFamily = PlayfairDisplay,
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp,
                color = RetirementColors.Gold,
                textAlign = TextAlign.Center,
                letterSpacing = 1.sp
            )
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(Color.White.copy(alpha = 0.04f))
                .border(1.dp, RetirementColors.Gold.copy(alpha = 0.25f), RoundedCornerShape(12.dp))
                .padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Header Row with Sort Icon
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "HOLIDAYS",
                    style = TextStyle(
                        fontFamily = PlayfairDisplay,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = RetirementColors.Gold,
                        letterSpacing = 1.5.sp
                    )
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Sort,
                        contentDescription = "Sort Options",
                        tint = RetirementColors.Gold,
                        modifier = Modifier.size(18.dp)
                    )
                    Text(
                        text = "Sort: ${currentSortOption.label}",
                        style = TextStyle(
                            fontFamily = PlayfairDisplay,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = RetirementColors.Gold
                        )
                    )
                }
            }

            // Filter Chips Bar (By Date, By Name, Date Added)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                HolidaySortOption.entries.forEach { option ->
                    val isSelected = currentSortOption == option
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(34.dp)
                            .clip(RoundedCornerShape(17.dp))
                            .background(
                                if (isSelected) RetirementColors.Gold
                                else Color.White.copy(alpha = 0.04f)
                            )
                            .border(
                                width = if (isSelected) 0.dp else 1.dp,
                                color = RetirementColors.Gold.copy(alpha = 0.4f),
                                shape = RoundedCornerShape(17.dp)
                            )
                            .clickable { currentSortOption = option },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = option.label,
                            style = TextStyle(
                                fontFamily = FontFamily.SansSerif,
                                fontSize = 12.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                color = if (isSelected) Color(0xFF111315) else RetirementColors.Cream
                            )
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(2.dp))

            // Add New Holiday Button
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color.White.copy(alpha = 0.03f))
                    .border(1.dp, RetirementColors.Gold.copy(alpha = 0.6f), RoundedCornerShape(20.dp)),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = "Add New Holiday",
                        tint = RetirementColors.Gold,
                        modifier = Modifier.size(18.dp)
                    )
                    Text(
                        text = "+ Add New Holiday",
                        style = TextStyle(
                            fontFamily = PlayfairDisplay,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = RetirementColors.Gold
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            // Sorted Clean Holiday Cards (No Drag/Move Arrows)
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                sortedHolidays.forEach { holiday ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color.Transparent)
                            .padding(horizontal = 8.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "${holiday.name} (${holiday.month}/${holiday.day})",
                            style = TextStyle(
                                fontFamily = PlayfairDisplay,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = RetirementColors.Cream
                            ),
                            modifier = Modifier.weight(1f)
                        )
                        Checkbox(
                            checked = holiday.isEnabled,
                            onCheckedChange = {},
                            colors = CheckboxDefaults.colors(
                                checkedColor = RetirementColors.Gold,
                                uncheckedColor = RetirementColors.Gold.copy(alpha = 0.5f)
                            )
                        )
                    }
                }
            }
        }
    }
}

// INDIVIDUAL PREVIEWS FOR EACH SORTING MODE
@Preview(showBackground = true, widthDp = 380, heightDp = 450, name = "Sorted By Calendar Date")
@Composable
fun PreviewSortByDate() {
    RetirementTimerAppTheme {
        Box(modifier = Modifier.background(RetirementColors.Background).padding(16.dp)) {
            HolidaySortingOptionsPreviewScreen()
        }
    }
}
