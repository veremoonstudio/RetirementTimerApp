@file:JvmName("CurrentSettingsPagePreviewDotFormat")
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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Delete
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
import com.veremoonstudio.retirementtimer.ui.theme.CormorantGaramond
import com.veremoonstudio.retirementtimer.ui.theme.PlayfairDisplay
import com.veremoonstudio.retirementtimer.ui.theme.RetirementColors
import com.veremoonstudio.retirementtimer.ui.theme.RetirementTimerAppTheme

@Composable
fun CurrentSettingsPagePreviewContent(
    holidaysHeaderGap: Int = 6 // Slightly shortened gap between HOLIDAYS header and + Add New Holiday button
) {
    var activeReorderIndex by remember { mutableStateOf<Int?>(1) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(RetirementColors.Background)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        // Top App Bar
        Row(
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = RetirementColors.Gold, modifier = Modifier.size(24.dp))
            Text(
                text = "SETTINGS",
                style = TextStyle(
                    fontFamily = PlayfairDisplay,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 20.sp,
                    letterSpacing = 2.sp,
                    color = RetirementColors.Gold
                )
            )
            Spacer(modifier = Modifier.width(24.dp))
        }

        // WORK DAYS
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(
                text = "WORK DAYS",
                style = TextStyle(fontFamily = PlayfairDisplay, fontWeight = FontWeight.SemiBold, fontSize = 16.sp, letterSpacing = 2.sp, color = RetirementColors.Gold),
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                listOf("MON", "TUE", "WED", "THU", "FRI", "SAT", "SUN").forEachIndexed { i, day ->
                    val isWork = i < 5
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = day,
                            style = TextStyle(
                                fontFamily = PlayfairDisplay,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 14.sp,
                                color = if (isWork) RetirementColors.Gold else RetirementColors.Cream.copy(alpha = 0.5f)
                            )
                        )
                        Checkbox(
                            checked = isWork,
                            onCheckedChange = {},
                            colors = CheckboxDefaults.colors(
                                checkedColor = RetirementColors.Gold,
                                uncheckedColor = RetirementColors.Gold.copy(alpha = 0.5f),
                                checkmarkColor = RetirementColors.Background
                            )
                        )
                    }
                }
            }
        }

        // WORKING HOURS
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(
                text = "WORKING HOURS",
                style = TextStyle(fontFamily = PlayfairDisplay, fontWeight = FontWeight.SemiBold, fontSize = 16.sp, letterSpacing = 2.sp, color = RetirementColors.Gold),
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Row(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text("Start Time", style = TextStyle(fontFamily = PlayfairDisplay, fontSize = 17.sp, fontWeight = FontWeight.SemiBold, color = RetirementColors.Cream))
                Text("09:00", style = TextStyle(fontFamily = PlayfairDisplay, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = RetirementColors.Gold))
            }
            Spacer(modifier = Modifier.height(2.dp))
            Row(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text("End Time", style = TextStyle(fontFamily = PlayfairDisplay, fontSize = 17.sp, fontWeight = FontWeight.SemiBold, color = RetirementColors.Cream))
                Text("18:00", style = TextStyle(fontFamily = PlayfairDisplay, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = RetirementColors.Gold))
            }
        }

        // VACATION DAYS
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text("VACATION DAYS", style = TextStyle(fontFamily = PlayfairDisplay, fontWeight = FontWeight.SemiBold, fontSize = 16.sp, letterSpacing = 2.sp, color = RetirementColors.Gold))
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    Icon(imageVector = Icons.Default.CalendarMonth, contentDescription = null, tint = RetirementColors.Gold, modifier = Modifier.size(15.dp))
                    Text("total: 31 d", style = TextStyle(fontFamily = PlayfairDisplay, fontSize = 14.5.sp, fontWeight = FontWeight.Bold, color = RetirementColors.Gold))
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            
            // Vacation Period Cards with Increased 14.dp Inter-Card Spacing
            Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
                SampleVacationItem("28.12.2026 – 10.01.2027", "9 d", "DEC-JAN")
                SampleVacationItem("09.02.2027 – 19.02.2027", "9 d", "FEB")
                SampleVacationItem("24.03.2027 – 10.04.2027", "13 d", "MAR-APR")
            }
            
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color.White.copy(alpha = 0.03f))
                    .border(1.dp, RetirementColors.Gold.copy(alpha = 0.6f), RoundedCornerShape(20.dp)),
                contentAlignment = Alignment.Center
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Icon(imageVector = Icons.Filled.Add, contentDescription = null, tint = RetirementColors.Gold, modifier = Modifier.size(18.dp))
                    Text("Add Vacation Period", style = TextStyle(fontFamily = PlayfairDisplay, fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = RetirementColors.Gold))
                }
            }
        }

        // HOLIDAYS SECTION WITH DROPDOWN SORT BUTTON
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "HOLIDAYS",
                    style = TextStyle(fontFamily = PlayfairDisplay, fontWeight = FontWeight.SemiBold, fontSize = 16.sp, letterSpacing = 2.sp, color = RetirementColors.Gold)
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    modifier = Modifier.clip(RoundedCornerShape(8.dp)).padding(horizontal = 6.dp, vertical = 4.dp)
                ) {
                    Icon(imageVector = Icons.Default.Sort, contentDescription = "Sort Options", tint = RetirementColors.Gold, modifier = Modifier.size(16.dp))
                    Text(text = "By Date", style = TextStyle(fontFamily = PlayfairDisplay, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = RetirementColors.Gold))
                    Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = null, tint = RetirementColors.Gold, modifier = Modifier.size(16.dp))
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color.White.copy(alpha = 0.03f))
                    .border(1.dp, RetirementColors.Gold.copy(alpha = 0.6f), RoundedCornerShape(20.dp)),
                contentAlignment = Alignment.Center
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Icon(imageVector = Icons.Filled.Add, contentDescription = null, tint = RetirementColors.Gold, modifier = Modifier.size(18.dp))
                    Text("Add New Holiday", style = TextStyle(fontFamily = PlayfairDisplay, fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = RetirementColors.Gold))
                }
            }

            Spacer(modifier = Modifier.height(2.dp))

            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                SampleHolidayRow("New Year's Day", "1/1", isChecked = true, isReordering = false)
                SampleHolidayRow("Martin Luther King Jr. Day", "1/16", isChecked = false, isReordering = false)
                SampleHolidayRow("Presidents' Day", "2/20", isChecked = false, isReordering = false)
            }
        }

        Spacer(modifier = Modifier.height(10.dp))
        SampleTestAdBanner()
    }
}

@Composable
private fun SampleVacationItem(periodText: String, daysText: String, badgeText: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 2.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("VACATION", style = TextStyle(fontFamily = PlayfairDisplay, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = RetirementColors.Gold, letterSpacing = 1.sp))
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(5.dp))
                        .background(Color(0xFF10B981).copy(alpha = 0.22f))
                        .border(1.dp, Color(0xFF10B981), RoundedCornerShape(5.dp))
                        .padding(horizontal = 7.dp, vertical = 2.dp)
                ) {
                    Text(badgeText, style = TextStyle(fontFamily = FontFamily.SansSerif, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFF34D399)))
                }
            }
            Text(periodText, style = TextStyle(fontFamily = CormorantGaramond, fontSize = 15.sp, fontWeight = FontWeight.Medium, color = RetirementColors.Cream))
        }

        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            Box(
                modifier = Modifier
                    .width(78.dp)
                    .height(36.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .background(Color.White.copy(alpha = 0.03f))
                    .border(1.dp, RetirementColors.Gold.copy(alpha = 0.5f), RoundedCornerShape(6.dp))
                    .padding(horizontal = 4.dp),
                contentAlignment = Alignment.Center
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    Icon(imageVector = Icons.Filled.CalendarMonth, contentDescription = null, tint = RetirementColors.Gold, modifier = Modifier.size(16.dp))
                    Text(daysText, style = TextStyle(fontFamily = PlayfairDisplay, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = RetirementColors.Gold))
                }
            }
            Icon(imageVector = Icons.Filled.Delete, contentDescription = null, tint = RetirementColors.Cream.copy(alpha = 0.6f), modifier = Modifier.size(18.dp))
        }
    }
}

@Composable
private fun SampleHolidayRow(name: String, date: String, isChecked: Boolean, isReordering: Boolean) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(if (isReordering) Color.White.copy(alpha = 0.08f) else Color.Transparent)
            .padding(horizontal = 8.dp, vertical = 3.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(modifier = Modifier.weight(1f), verticalAlignment = Alignment.CenterVertically) {
            if (isReordering) {
                Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(end = 6.dp)) {
                    Icon(imageVector = Icons.Default.ArrowDropUp, contentDescription = null, tint = RetirementColors.Gold, modifier = Modifier.size(18.dp))
                    Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = null, tint = RetirementColors.Gold, modifier = Modifier.size(18.dp))
                }
            }
            Text(text = "$name ($date)", style = TextStyle(fontFamily = PlayfairDisplay, fontSize = 17.sp, fontWeight = FontWeight.SemiBold, color = if (isReordering) RetirementColors.Gold else RetirementColors.Cream), modifier = Modifier.weight(1f))
        }
        Checkbox(checked = isChecked, onCheckedChange = {}, colors = CheckboxDefaults.colors(checkedColor = RetirementColors.Gold, uncheckedColor = RetirementColors.Gold.copy(alpha = 0.5f)))
    }
}

@Composable
private fun SampleTestAdBanner() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(90.dp)
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(4.dp))
                    .background(Color(0xFF333333))
                    .padding(horizontal = 8.dp, vertical = 2.dp)
            ) {
                Text(
                    text = "Тестовое объявление",
                    style = TextStyle(fontSize = 10.sp, color = Color.White)
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.92f)
                    .height(50.dp)
                    .background(Color.White)
                    .padding(horizontal = 12.dp, vertical = 6.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Nice job!",
                        style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1A73E8))
                    )
                    Text(
                        text = "This is a 468x60 test ad.",
                        style = TextStyle(fontSize = 12.sp, color = Color.Black)
                    )
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFEA4335)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("A", style = TextStyle(fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.White))
                    }
                }
            }
        }
    }
}

// PREVIEW FUNCTION
@Preview(showBackground = true, widthDp = 380, heightDp = 1250, name = "Current Settings Page Preview (Shortened Gap)")
@Composable
fun CurrentSettingsPagePreview() {
    RetirementTimerAppTheme {
        CurrentSettingsPagePreviewContent(holidaysHeaderGap = 6)
    }
}
