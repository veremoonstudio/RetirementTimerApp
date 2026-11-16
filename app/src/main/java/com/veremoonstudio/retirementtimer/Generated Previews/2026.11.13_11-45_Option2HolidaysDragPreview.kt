@file:JvmName("Option2HolidaysDragPreviewDotFormat")
package com.veremoonstudio.retirementtimer

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DragIndicator
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

@Composable
fun Option2HolidaysDragPreviewScreen() {
    var holidaysList by remember {
        mutableStateOf(
            listOf(
                Holiday("New Year's Day", 1, 1, true),
                Holiday("Martin Luther King Jr. Day", 1, 16, false),
                Holiday("Presidents' Day", 2, 20, false),
                Holiday("Memorial Day", 5, 29, false)
            )
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(RetirementColors.Background)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "ПРЕВЬЮ: ВАРИАНТ 2 (РУКОЯТКА ::)",
            style = TextStyle(
                fontFamily = PlayfairDisplay,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = RetirementColors.Gold,
                textAlign = TextAlign.Center,
                letterSpacing = 1.sp
            )
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(Color.White.copy(alpha = 0.05f))
                .border(1.dp, RetirementColors.Gold.copy(alpha = 0.3f), RoundedCornerShape(8.dp))
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Gold Pill Add Button
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .background(Color.White.copy(alpha = 0.03f))
                    .border(1.dp, RetirementColors.Gold.copy(alpha = 0.6f), RoundedCornerShape(24.dp)),
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
                        modifier = Modifier.size(20.dp)
                    )
                    Text(
                        text = "+ Add New Holiday",
                        style = TextStyle(
                            fontFamily = PlayfairDisplay,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = RetirementColors.Gold
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(2.dp))

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                holidaysList.forEachIndexed { index, holiday ->
                    val isBeingDragged = index == 1

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(
                                if (isBeingDragged) Color.White.copy(alpha = 0.08f)
                                else Color.Transparent
                            )
                            .border(
                                width = if (isBeingDragged) 1.5.dp else 0.dp,
                                color = if (isBeingDragged) Color.White.copy(alpha = 0.5f) else Color.Transparent,
                                shape = RoundedCornerShape(8.dp)
                            )
                            .padding(horizontal = 8.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            modifier = Modifier.weight(1f),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.DragIndicator,
                                contentDescription = "Drag Handle",
                                tint = if (isBeingDragged) Color.White else Color.White.copy(alpha = 0.4f),
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "${holiday.name} (${holiday.month}/${holiday.day})",
                                style = TextStyle(
                                    fontFamily = PlayfairDisplay,
                                    fontSize = 17.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = RetirementColors.Cream
                                ),
                                modifier = Modifier.weight(1f)
                            )
                        }

                        Checkbox(
                            checked = holiday.isEnabled,
                            onCheckedChange = { checked ->
                                holidaysList = holidaysList.map {
                                    if (it == holiday) it.copy(isEnabled = checked) else it
                                }
                            },
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

@Preview(showBackground = true, widthDp = 380, heightDp = 420, name = "Option 2 Holidays Drag Preview")
@Composable
fun Option2HolidaysDragPreview() {
    RetirementTimerAppTheme {
        Option2HolidaysDragPreviewScreen()
    }
}
