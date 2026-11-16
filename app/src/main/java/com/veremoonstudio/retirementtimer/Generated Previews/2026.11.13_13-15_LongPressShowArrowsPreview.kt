@file:JvmName("LongPressShowArrowsPreviewDotFormat")
package com.veremoonstudio.retirementtimer

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
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
fun LongPressShowArrowsPreviewScreen() {
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
    var activeReorderIndex by remember { mutableStateOf<Int?>(1) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(RetirementColors.Background)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "ПОЯВЛЕНИЕ СТРЕЛОК ПО ДЛИТЕЛЬНОМУ НАЖАТИЮ",
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
                    val isReorderingThis = activeReorderIndex == index

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(
                                if (isReorderingThis) Color.White.copy(alpha = 0.08f)
                                else Color.Transparent
                            )
                            .padding(horizontal = 8.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            modifier = Modifier.weight(1f),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            if (isReorderingThis) {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier.padding(end = 8.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.ArrowDropUp,
                                        contentDescription = "Move Up",
                                        tint = if (index > 0) RetirementColors.Gold else RetirementColors.Gold.copy(alpha = 0.3f),
                                        modifier = Modifier.size(22.dp)
                                    )
                                    Icon(
                                        imageVector = Icons.Default.ArrowDropDown,
                                        contentDescription = "Move Down",
                                        tint = if (index < holidaysList.lastIndex) RetirementColors.Gold else RetirementColors.Gold.copy(alpha = 0.3f),
                                        modifier = Modifier.size(22.dp)
                                    )
                                }
                            }

                            Text(
                                text = "${holiday.name} (${holiday.month}/${holiday.day})",
                                style = TextStyle(
                                    fontFamily = PlayfairDisplay,
                                    fontSize = 17.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = if (isReorderingThis) RetirementColors.Gold else RetirementColors.Cream
                                ),
                                modifier = Modifier.weight(1f)
                            )
                        }

                        Checkbox(
                            checked = holiday.isEnabled,
                            onCheckedChange = { checked ->
                                activeReorderIndex = null
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

@Preview(showBackground = true, widthDp = 380, heightDp = 420, name = "Long Press Show Arrows Preview")
@Composable
fun LongPressShowArrowsPreview() {
    RetirementTimerAppTheme {
        LongPressShowArrowsPreviewScreen()
    }
}
