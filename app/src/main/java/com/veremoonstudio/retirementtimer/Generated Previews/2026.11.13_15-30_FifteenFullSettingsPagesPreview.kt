@file:JvmName("FifteenFullSettingsPagesPreviewDotFormat")
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
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import com.veremoonstudio.retirementtimer.ui.theme.CormorantGaramond
import com.veremoonstudio.retirementtimer.ui.theme.PlayfairDisplay
import com.veremoonstudio.retirementtimer.ui.theme.RetirementColors
import com.veremoonstudio.retirementtimer.ui.theme.RetirementTimerAppTheme

// REUSABLE SAMPLE COMPONENTS FOR FULL PAGE PREVIEWS
@Composable
private fun SampleTopBar(title: String = "SETTINGS") {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = RetirementColors.Gold, modifier = Modifier.size(24.dp))
        Text(
            text = title,
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
}

@Composable
private fun SampleWorkDaysRow() {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        listOf("M", "T", "W", "T", "F", "S", "S").forEachIndexed { i, day ->
            val isWork = i < 5
            Box(
                modifier = Modifier
                    .size(38.dp)
                    .clip(CircleShape)
                    .background(if (isWork) RetirementColors.Gold else Color.Transparent)
                    .border(if (isWork) 0.dp else 1.dp, RetirementColors.Gold.copy(alpha = 0.3f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(text = day, style = TextStyle(fontSize = 13.sp, fontWeight = FontWeight.Bold, color = if (isWork) Color(0xFF111315) else RetirementColors.Cream))
            }
        }
    }
}

@Composable
private fun SampleTimeRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(Color.White.copy(alpha = 0.03f))
            .border(1.dp, RetirementColors.Gold.copy(alpha = 0.3f), RoundedCornerShape(8.dp))
            .padding(14.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = label, style = TextStyle(fontFamily = PlayfairDisplay, fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = RetirementColors.Cream))
        Text(text = value, style = TextStyle(fontFamily = PlayfairDisplay, fontSize = 17.sp, fontWeight = FontWeight.Bold, color = RetirementColors.Gold))
    }
}

@Composable
private fun SampleVacationCard(period: String, days: String, monthTag: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(Color.White.copy(alpha = 0.03f))
            .border(1.dp, RetirementColors.Gold.copy(alpha = 0.3f), RoundedCornerShape(8.dp))
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                Text("VACATION", style = TextStyle(fontFamily = PlayfairDisplay, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = RetirementColors.Gold))
                Box(modifier = Modifier.clip(RoundedCornerShape(4.dp)).background(Color(0xFF10B981).copy(alpha = 0.15f)).border(1.dp, Color(0xFF10B981), RoundedCornerShape(4.dp)).padding(horizontal = 4.dp, vertical = 1.dp)) {
                    Text(monthTag, style = TextStyle(fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color(0xFF10B981)))
                }
            }
            Spacer(modifier = Modifier.height(2.dp))
            Text(period, style = TextStyle(fontFamily = PlayfairDisplay, fontSize = 14.sp, color = RetirementColors.Cream))
        }
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Box(modifier = Modifier.clip(RoundedCornerShape(6.dp)).border(1.dp, RetirementColors.Gold.copy(alpha = 0.5f), RoundedCornerShape(6.dp)).padding(horizontal = 8.dp, vertical = 4.dp)) {
                Text("📅 $days", style = TextStyle(fontFamily = PlayfairDisplay, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = RetirementColors.Gold))
            }
            Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete", tint = Color.White.copy(alpha = 0.4f), modifier = Modifier.size(18.dp))
        }
    }
}

@Composable
private fun SampleHolidayItem(name: String, date: String, isChecked: Boolean, isReorder: Boolean = false) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(if (isReorder) Color.White.copy(alpha = 0.08f) else Color.Transparent)
            .padding(horizontal = 8.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(modifier = Modifier.weight(1f), verticalAlignment = Alignment.CenterVertically) {
            if (isReorder) {
                Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(end = 8.dp)) {
                    Icon(imageVector = Icons.Default.ArrowDropUp, contentDescription = null, tint = RetirementColors.Gold, modifier = Modifier.size(20.dp))
                    Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = null, tint = RetirementColors.Gold, modifier = Modifier.size(20.dp))
                }
            }
            Text(text = "$name ($date)", style = TextStyle(fontFamily = PlayfairDisplay, fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = if (isReorder) RetirementColors.Gold else RetirementColors.Cream), modifier = Modifier.weight(1f))
        }
        Checkbox(checked = isChecked, onCheckedChange = {}, colors = CheckboxDefaults.colors(checkedColor = RetirementColors.Gold, uncheckedColor = RetirementColors.Gold.copy(alpha = 0.5f)))
    }
}

// ----------------------------------------------------------------------------------
// 15 FULL PAGE PREVIEW IMPLEMENTATIONS
// ----------------------------------------------------------------------------------

// FULL PAGE 1: Classic Gold Card Stack
@Composable
fun FullPageVariant1() {
    Column(modifier = Modifier.fillMaxSize().background(RetirementColors.Background).verticalScroll(rememberScrollState()).padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        SampleTopBar()
        Text("WORK DAYS", style = TextStyle(fontFamily = PlayfairDisplay, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = RetirementColors.Gold))
        SampleWorkDaysRow()
        Text("WORKING HOURS", style = TextStyle(fontFamily = PlayfairDisplay, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = RetirementColors.Gold))
        SampleTimeRow("Start Time", "09:00 AM")
        SampleTimeRow("End Time", "06:00 PM")
        Text("VACATION DAYS", style = TextStyle(fontFamily = PlayfairDisplay, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = RetirementColors.Gold))
        SampleVacationCard("28.12.2026 - 10.01.2027", "9 d", "DEC-JAN")
        SampleVacationCard("09.02.2027 - 19.02.2027", "9 d", "FEB")
        Text("HOLIDAYS", style = TextStyle(fontFamily = PlayfairDisplay, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = RetirementColors.Gold))
        SampleHolidayItem("New Year's Day", "1/1", true)
        SampleHolidayItem("Martin Luther King Jr. Day", "1/16", false, isReorder = true)
        SampleHolidayItem("Presidents' Day", "2/20", false)
    }
}

// FULL PAGE 2: Top Hero Dashboard
@Composable
fun FullPageVariant2() {
    Column(modifier = Modifier.fillMaxSize().background(RetirementColors.Background).verticalScroll(rememberScrollState()).padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        SampleTopBar()
        Box(modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(16.dp)).background(RetirementColors.Gold.copy(alpha = 0.12f)).border(1.5.dp, RetirementColors.Gold, RoundedCornerShape(16.dp)).padding(16.dp)) {
            Column {
                Text("RETIREMENT DASHBOARD STATS", style = TextStyle(fontSize = 11.sp, fontWeight = FontWeight.Bold, color = RetirementColors.Gold))
                Spacer(modifier = Modifier.height(4.dp))
                Text("2,080 Work Hours • 31 Vac Days • 12 Holidays", style = TextStyle(fontFamily = PlayfairDisplay, fontSize = 15.sp, fontWeight = FontWeight.Bold, color = RetirementColors.Cream))
            }
        }
        Text("WORK DAYS & HOURS", style = TextStyle(fontFamily = PlayfairDisplay, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = RetirementColors.Gold))
        SampleWorkDaysRow()
        SampleTimeRow("Daily Shift", "09:00 AM - 06:00 PM")
        Text("VACATION PERIODS", style = TextStyle(fontFamily = PlayfairDisplay, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = RetirementColors.Gold))
        SampleVacationCard("09.02.2027 - 19.02.2027", "9 d", "FEB")
    }
}

// FULL PAGE 3: Glassmorphic Translucent Dark Theme
@Composable
fun FullPageVariant3() {
    Column(modifier = Modifier.fillMaxSize().background(RetirementColors.Background).verticalScroll(rememberScrollState()).padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        SampleTopBar()
        Column(modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(14.dp)).background(Color.White.copy(alpha = 0.06f)).border(1.dp, Color.White.copy(alpha = 0.4f), RoundedCornerShape(14.dp)).padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Text("WORK SCHEDULE", style = TextStyle(fontFamily = PlayfairDisplay, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.White))
            SampleWorkDaysRow()
            SampleTimeRow("Shift Hours", "09:00 - 18:00")
        }
        Column(modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(14.dp)).background(Color.White.copy(alpha = 0.06f)).border(1.dp, Color.White.copy(alpha = 0.4f), RoundedCornerShape(14.dp)).padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Text("VACATIONS & HOLIDAYS", style = TextStyle(fontFamily = PlayfairDisplay, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.White))
            SampleVacationCard("09.02.2027 - 19.02.2027", "9 d", "FEB")
        }
    }
}

// FULL PAGE 4: Split Two-Column Grid Layout
@Composable
fun FullPageVariant4() {
    Column(modifier = Modifier.fillMaxSize().background(RetirementColors.Background).verticalScroll(rememberScrollState()).padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        SampleTopBar()
        Text("SHIFT HOURS GRID", style = TextStyle(fontFamily = PlayfairDisplay, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = RetirementColors.Gold))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            Box(modifier = Modifier.weight(1f).clip(RoundedCornerShape(10.dp)).background(Color.White.copy(alpha = 0.04f)).border(1.dp, RetirementColors.Gold.copy(alpha = 0.3f), RoundedCornerShape(10.dp)).padding(10.dp)) {
                Column {
                    Text("START TIME", style = TextStyle(fontSize = 10.sp, color = RetirementColors.Gold, fontWeight = FontWeight.Bold))
                    Text("09:00 AM", style = TextStyle(fontFamily = PlayfairDisplay, fontSize = 17.sp, color = RetirementColors.Cream, fontWeight = FontWeight.Bold))
                }
            }
            Box(modifier = Modifier.weight(1f).clip(RoundedCornerShape(10.dp)).background(Color.White.copy(alpha = 0.04f)).border(1.dp, RetirementColors.Gold.copy(alpha = 0.3f), RoundedCornerShape(10.dp)).padding(10.dp)) {
                Column {
                    Text("END TIME", style = TextStyle(fontSize = 10.sp, color = RetirementColors.Gold, fontWeight = FontWeight.Bold))
                    Text("06:00 PM", style = TextStyle(fontFamily = PlayfairDisplay, fontSize = 17.sp, color = RetirementColors.Cream, fontWeight = FontWeight.Bold))
                }
            }
        }
        Text("WORK DAYS", style = TextStyle(fontFamily = PlayfairDisplay, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = RetirementColors.Gold))
        SampleWorkDaysRow()
        Text("VACATION PERIODS", style = TextStyle(fontFamily = PlayfairDisplay, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = RetirementColors.Gold))
        SampleVacationCard("09.02.2027 - 19.02.2027", "9 d", "FEB")
    }
}

// FULL PAGE 5: Left Accent Indicator Strip Page
@Composable
fun FullPageVariant5() {
    Column(modifier = Modifier.fillMaxSize().background(RetirementColors.Background).verticalScroll(rememberScrollState()).padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        SampleTopBar()
        Row(modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(8.dp)).background(Color.White.copy(alpha = 0.04f)).border(1.dp, RetirementColors.Gold.copy(alpha = 0.3f), RoundedCornerShape(8.dp)).padding(start = 4.dp, end = 12.dp, top = 10.dp, bottom = 10.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(modifier = Modifier.width(4.dp).height(36.dp).clip(RoundedCornerShape(2.dp)).background(RetirementColors.Gold))
            Spacer(modifier = Modifier.width(10.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text("WORK DAYS & HOURS", style = TextStyle(fontSize = 11.sp, color = RetirementColors.Gold, fontWeight = FontWeight.Bold))
                Text("Mon - Fri (09:00 AM - 06:00 PM)", style = TextStyle(fontFamily = PlayfairDisplay, fontSize = 15.sp, color = RetirementColors.Cream))
            }
        }
        SampleVacationCard("09.02.2027 - 19.02.2027", "9 d", "FEB")
        SampleHolidayItem("Martin Luther King Jr. Day", "1/16", false, isReorder = true)
    }
}

// FULL PAGE 6: Pill-Capsule Full Theme (24.dp)
@Composable
fun FullPageVariant6() {
    Column(modifier = Modifier.fillMaxSize().background(RetirementColors.Background).verticalScroll(rememberScrollState()).padding(16.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
        SampleTopBar()
        Box(modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(24.dp)).background(RetirementColors.Gold.copy(alpha = 0.12f)).border(1.dp, RetirementColors.Gold, RoundedCornerShape(24.dp)).padding(horizontal = 16.dp, vertical = 12.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text("WORK SCHEDULE", style = TextStyle(fontFamily = PlayfairDisplay, fontSize = 15.sp, fontWeight = FontWeight.Bold, color = RetirementColors.Gold))
                Text("5 Days / 09:00 - 18:00", style = TextStyle(fontSize = 13.sp, color = RetirementColors.Cream))
            }
        }
        SampleWorkDaysRow()
        SampleVacationCard("09.02.2027 - 19.02.2027", "9 d", "FEB")
    }
}

// FULL PAGE 7: Ultra-Minimalist Linear Dividers
@Composable
fun FullPageVariant7() {
    Column(modifier = Modifier.fillMaxSize().background(RetirementColors.Background).verticalScroll(rememberScrollState()).padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        SampleTopBar()
        Text("WORKTIME CONFIGURATION", style = TextStyle(fontFamily = PlayfairDisplay, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = RetirementColors.Gold))
        SampleWorkDaysRow()
        HorizontalDivider(color = RetirementColors.Gold.copy(alpha = 0.25f), thickness = 1.dp)
        SampleTimeRow("Start Time", "09:00 AM")
        SampleTimeRow("End Time", "06:00 PM")
        HorizontalDivider(color = RetirementColors.Gold.copy(alpha = 0.25f), thickness = 1.dp)
        SampleVacationCard("09.02.2027 - 19.02.2027", "9 d", "FEB")
    }
}

// FULL PAGE 8: Contrast Dark Charcoal Theme (#18171C)
@Composable
fun FullPageVariant8() {
    Column(modifier = Modifier.fillMaxSize().background(RetirementColors.Background).verticalScroll(rememberScrollState()).padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        SampleTopBar()
        Box(modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(10.dp)).background(Color(0xFF18171C)).border(1.5.dp, RetirementColors.Gold, RoundedCornerShape(10.dp)).padding(14.dp)) {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("DARK CHARCOAL SCHEDULE", style = TextStyle(fontSize = 11.sp, fontWeight = FontWeight.Bold, color = RetirementColors.Gold))
                SampleWorkDaysRow()
            }
        }
        SampleVacationCard("09.02.2027 - 19.02.2027", "9 d", "FEB")
    }
}

// FULL PAGE 9: Accordion Collapsible Sections
@Composable
fun FullPageVariant9() {
    Column(modifier = Modifier.fillMaxSize().background(RetirementColors.Background).verticalScroll(rememberScrollState()).padding(16.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
        SampleTopBar()
        Column(modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(10.dp)).background(Color.White.copy(alpha = 0.04f)).border(1.dp, RetirementColors.Gold.copy(alpha = 0.3f), RoundedCornerShape(10.dp)).padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text("WORK DAYS & HOURS ▲", style = TextStyle(fontFamily = PlayfairDisplay, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = RetirementColors.Gold))
            }
            SampleWorkDaysRow()
        }
        Column(modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(10.dp)).background(Color.White.copy(alpha = 0.04f)).border(1.dp, RetirementColors.Gold.copy(alpha = 0.3f), RoundedCornerShape(10.dp)).padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text("VACATION PERIODS ▼", style = TextStyle(fontFamily = PlayfairDisplay, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = RetirementColors.Gold))
            }
        }
    }
}

// FULL PAGE 10: Tabbed Category View
@Composable
fun FullPageVariant10() {
    Column(modifier = Modifier.fillMaxSize().background(RetirementColors.Background).verticalScroll(rememberScrollState()).padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        SampleTopBar()
        Row(modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(20.dp)).background(Color.White.copy(alpha = 0.05f)).padding(3.dp), horizontalArrangement = Arrangement.SpaceBetween) {
            Box(modifier = Modifier.weight(1f).clip(RoundedCornerShape(16.dp)).background(RetirementColors.Gold).padding(vertical = 8.dp), contentAlignment = Alignment.Center) {
                Text("WORK", style = TextStyle(fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFF111315)))
            }
            Box(modifier = Modifier.weight(1f).padding(vertical = 8.dp), contentAlignment = Alignment.Center) {
                Text("VACATION", style = TextStyle(fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.White.copy(alpha = 0.6f)))
            }
            Box(modifier = Modifier.weight(1f).padding(vertical = 8.dp), contentAlignment = Alignment.Center) {
                Text("HOLIDAYS", style = TextStyle(fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.White.copy(alpha = 0.6f)))
            }
        }
        SampleWorkDaysRow()
        SampleTimeRow("Start Time", "09:00 AM")
        SampleTimeRow("End Time", "06:00 PM")
    }
}

// FULL PAGE 11: Hero Stats Header with Progress Ring
@Composable
fun FullPageVariant11() {
    Column(modifier = Modifier.fillMaxSize().background(RetirementColors.Background).verticalScroll(rememberScrollState()).padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        SampleTopBar()
        Box(modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(14.dp)).background(Brush.verticalGradient(listOf(Color(0xFF2B2830), Color(0xFF1C1A20)))).border(1.dp, RetirementColors.Gold, RoundedCornerShape(14.dp)).padding(16.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Column {
                    Text("PENSION COUNTDOWN", style = TextStyle(fontSize = 10.sp, fontWeight = FontWeight.Bold, color = RetirementColors.Gold))
                    Text("2,080 Hours / Year", style = TextStyle(fontFamily = PlayfairDisplay, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = RetirementColors.Cream))
                }
                Box(modifier = Modifier.size(46.dp).clip(CircleShape).border(3.dp, RetirementColors.Gold, CircleShape), contentAlignment = Alignment.Center) {
                    Text("84%", style = TextStyle(fontSize = 12.sp, fontWeight = FontWeight.Bold, color = RetirementColors.Gold))
                }
            }
        }
        SampleWorkDaysRow()
    }
}

// FULL PAGE 12: Premium Gold Gradient Borders
@Composable
fun FullPageVariant12() {
    Column(modifier = Modifier.fillMaxSize().background(RetirementColors.Background).verticalScroll(rememberScrollState()).padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        SampleTopBar()
        Box(modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(12.dp)).background(Color.White.copy(alpha = 0.05f)).border(1.5.dp, Brush.horizontalGradient(listOf(RetirementColors.Gold, RetirementColors.Cream, RetirementColors.Gold)), RoundedCornerShape(12.dp)).padding(14.dp)) {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text("WORK SCHEDULE (GRADIENT FRAME)", style = TextStyle(fontFamily = PlayfairDisplay, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = RetirementColors.Gold))
                SampleWorkDaysRow()
            }
        }
        SampleVacationCard("09.02.2027 - 19.02.2027", "9 d", "FEB")
    }
}

// FULL PAGE 13: Floating Action Badges
@Composable
fun FullPageVariant13() {
    Column(modifier = Modifier.fillMaxSize().background(RetirementColors.Background).verticalScroll(rememberScrollState()).padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        SampleTopBar()
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Box(modifier = Modifier.clip(RoundedCornerShape(16.dp)).background(RetirementColors.Gold).padding(horizontal = 12.dp, vertical = 6.dp)) {
                Text("✔ 5 Work Days", style = TextStyle(fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFF111315)))
            }
            Box(modifier = Modifier.clip(RoundedCornerShape(16.dp)).background(Color.White.copy(alpha = 0.08f)).border(1.dp, RetirementColors.Gold.copy(alpha = 0.4f), RoundedCornerShape(16.dp)).padding(horizontal = 12.dp, vertical = 6.dp)) {
                Text("31 Vacation Days", style = TextStyle(fontSize = 12.sp, fontWeight = FontWeight.Bold, color = RetirementColors.Gold))
            }
        }
        SampleWorkDaysRow()
        SampleTimeRow("Shift Hours", "09:00 AM - 06:00 PM")
    }
}

// FULL PAGE 14: Roman Serif Monumental Theme
@Composable
fun FullPageVariant14() {
    Column(modifier = Modifier.fillMaxSize().background(RetirementColors.Background).verticalScroll(rememberScrollState()).padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        SampleTopBar(title = "CONFIGURATIONS")
        Text("WORKTIME CONFIGURATION", style = TextStyle(fontFamily = CormorantGaramond, fontSize = 17.sp, fontWeight = FontWeight.Bold, color = RetirementColors.Gold, letterSpacing = 2.sp))
        SampleWorkDaysRow()
        HorizontalDivider(color = RetirementColors.Gold.copy(alpha = 0.3f))
        Text("VACATION PERIODS", style = TextStyle(fontFamily = CormorantGaramond, fontSize = 17.sp, fontWeight = FontWeight.Bold, color = RetirementColors.Gold, letterSpacing = 2.sp))
        SampleVacationCard("09.02.2027 - 19.02.2027", "9 d", "FEB")
    }
}

// FULL PAGE 15: Streamlined Master Sheet
@Composable
fun FullPageVariant15() {
    Column(modifier = Modifier.fillMaxSize().background(RetirementColors.Background).verticalScroll(rememberScrollState()).padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        SampleTopBar()
        Column(modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(18.dp)).background(Color(0xFF1A191F)).border(1.5.dp, RetirementColors.Gold.copy(alpha = 0.4f), RoundedCornerShape(18.dp)).padding(16.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
            Text("ALL SETTINGS MASTER SHEET", style = TextStyle(fontFamily = PlayfairDisplay, fontSize = 15.sp, fontWeight = FontWeight.Bold, color = RetirementColors.Gold))
            SampleWorkDaysRow()
            HorizontalDivider(color = Color.White.copy(alpha = 0.08f))
            SampleTimeRow("Start Time", "09:00 AM")
            SampleTimeRow("End Time", "06:00 PM")
            HorizontalDivider(color = Color.White.copy(alpha = 0.08f))
            SampleVacationCard("09.02.2027 - 19.02.2027", "9 d", "FEB")
        }
    }
}

// INDIVIDUAL FULL-PAGE PREVIEWS (380dp x 800dp)
@Preview(showBackground = true, widthDp = 380, heightDp = 800, name = "Full Page 1: Classic Gold Cards")
@Composable
fun PreviewFullSettings1() { RetirementTimerAppTheme { FullPageVariant1() } }

@Preview(showBackground = true, widthDp = 380, heightDp = 800, name = "Full Page 2: Top Hero Dashboard")
@Composable
fun PreviewFullSettings2() { RetirementTimerAppTheme { FullPageVariant2() } }

@Preview(showBackground = true, widthDp = 380, heightDp = 800, name = "Full Page 3: Glassmorphic Glow")
@Composable
fun PreviewFullSettings3() { RetirementTimerAppTheme { FullPageVariant3() } }

@Preview(showBackground = true, widthDp = 380, heightDp = 800, name = "Full Page 4: Two-Column Grid")
@Composable
fun PreviewFullSettings4() { RetirementTimerAppTheme { FullPageVariant4() } }

@Preview(showBackground = true, widthDp = 380, heightDp = 800, name = "Full Page 5: Left Accent Strip")
@Composable
fun PreviewFullSettings5() { RetirementTimerAppTheme { FullPageVariant5() } }

@Preview(showBackground = true, widthDp = 380, heightDp = 800, name = "Full Page 6: Pill Capsule Cards 24dp")
@Composable
fun PreviewFullSettings6() { RetirementTimerAppTheme { FullPageVariant6() } }

@Preview(showBackground = true, widthDp = 380, heightDp = 800, name = "Full Page 7: Ultra-Minimalist Lines")
@Composable
fun PreviewFullSettings7() { RetirementTimerAppTheme { FullPageVariant7() } }

@Preview(showBackground = true, widthDp = 380, heightDp = 800, name = "Full Page 8: Contrast Dark Charcoal")
@Composable
fun PreviewFullSettings8() { RetirementTimerAppTheme { FullPageVariant8() } }

@Preview(showBackground = true, widthDp = 380, heightDp = 800, name = "Full Page 9: Accordion Collapsible")
@Composable
fun PreviewFullSettings9() { RetirementTimerAppTheme { FullPageVariant9() } }

@Preview(showBackground = true, widthDp = 380, heightDp = 800, name = "Full Page 10: Category Tabs View")
@Composable
fun PreviewFullSettings10() { RetirementTimerAppTheme { FullPageVariant10() } }

@Preview(showBackground = true, widthDp = 380, heightDp = 800, name = "Full Page 11: Hero Progress Ring")
@Composable
fun PreviewFullSettings11() { RetirementTimerAppTheme { FullPageVariant11() } }

@Preview(showBackground = true, widthDp = 380, heightDp = 800, name = "Full Page 12: Gold Gradient Borders")
@Composable
fun PreviewFullSettings12() { RetirementTimerAppTheme { FullPageVariant12() } }

@Preview(showBackground = true, widthDp = 380, heightDp = 800, name = "Full Page 13: Floating Action Badges")
@Composable
fun PreviewFullSettings13() { RetirementTimerAppTheme { FullPageVariant13() } }

@Preview(showBackground = true, widthDp = 380, heightDp = 800, name = "Full Page 14: Roman Serif Monumental")
@Composable
fun PreviewFullSettings14() { RetirementTimerAppTheme { FullPageVariant14() } }

@Preview(showBackground = true, widthDp = 380, heightDp = 800, name = "Full Page 15: Streamlined Master Sheet")
@Composable
fun PreviewFullSettings15() { RetirementTimerAppTheme { FullPageVariant15() } }
