package com.veremoonstudio.retirementtimer

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.veremoonstudio.retirementtimer.ui.theme.*
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun CalendarPreviewScreen() {
    val sampleDate = LocalDate.of(2026, 11, 18)
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF111315))
            .verticalScroll(scrollState)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Text(
            text = "CALENDAR DESIGN VARIANTS",
            style = TextStyle(
                fontFamily = PlayfairDisplay,
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp,
                color = RetirementColors.Gold,
                letterSpacing = 2.sp
            ),
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // Variant 1
        CalendarCardWrapper(
            title = "Variant 1: Classic Luxury Gold Circle (Current)",
            subtitle = "Circle selector, Playfair headers & Garamond numbers"
        ) {
            CalendarVariant1(selectedDate = sampleDate)
        }

        // Variant 2
        CalendarCardWrapper(
            title = "Variant 2: Modern Soft Rounded Card",
            subtitle = "Rounded square selector, clean sans-serif headers"
        ) {
            CalendarVariant2(selectedDate = sampleDate)
        }

        // Variant 3
        CalendarCardWrapper(
            title = "Variant 3: Editorial Gold Ring & Dot",
            subtitle = "Garamond Italic headers, gold ring selector with cream text"
        ) {
            CalendarVariant3(selectedDate = sampleDate)
        }

        // Variant 4
        CalendarCardWrapper(
            title = "Variant 4: Royal Pill Accent & Header Badge",
            subtitle = "Pill shape selector, badge header for month title"
        ) {
            CalendarVariant4(selectedDate = sampleDate)
        }

        // Variant 5
        CalendarCardWrapper(
            title = "Variant 5: Roman Emperor (Cinzel Classical Serif)",
            subtitle = "Cinzel serif font, classical Roman architecture aesthetic"
        ) {
            CalendarVariant5(selectedDate = sampleDate)
        }
    }
}

@Composable
fun CalendarCardWrapper(
    title: String,
    subtitle: String,
    content: @Composable () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, RetirementColors.Gold.copy(alpha = 0.3f), RoundedCornerShape(16.dp)),
        colors = CardDefaults.cardColors(containerColor = RetirementColors.Background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = title,
                style = TextStyle(
                    fontFamily = PlayfairDisplay,
                    fontWeight = FontWeight.Bold,
                    fontSize = 17.sp,
                    color = RetirementColors.Gold
                )
            )
            Text(
                text = subtitle,
                style = TextStyle(
                    fontFamily = CormorantGaramond,
                    fontSize = 13.sp,
                    color = RetirementColors.Cream.copy(alpha = 0.7f)
                )
            )
            HorizontalDivider(color = RetirementColors.Gold.copy(alpha = 0.2f), thickness = 1.dp)
            content()
        }
    }
}

// -------------------------------------------------------------
// VARIANT 1: Classic Luxury Gold Circle
// -------------------------------------------------------------
@Composable
fun CalendarVariant1(selectedDate: LocalDate) {
    val month = YearMonth.of(2026, 11)
    val today = LocalDate.of(2026, 11, 10)

    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        // Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null, tint = RetirementColors.Gold)
            Text(
                text = "November 2026",
                style = TextStyle(fontFamily = PlayfairDisplay, fontWeight = FontWeight.SemiBold, fontSize = 18.sp, color = RetirementColors.Gold)
            )
            Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, tint = RetirementColors.Gold)
        }

        // Days Header
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            listOf("Mo", "Tu", "We", "Th", "Fr", "Sa", "Su").forEach { day ->
                Text(text = day, style = TextStyle(fontFamily = PlayfairDisplay, fontWeight = FontWeight.Bold, fontSize = 14.sp, color = RetirementColors.Gold), modifier = Modifier.width(38.dp), textAlign = TextAlign.Center)
            }
        }

        // Days Grid
        CalendarGridHelper(
            yearMonth = month,
            selectedDate = selectedDate,
            today = today,
            dayShape = CircleShape,
            selectedContainer = RetirementColors.Gold,
            selectedContent = Color(0xFF111315),
            fontFamily = CormorantGaramond
        )
    }
}

// -------------------------------------------------------------
// VARIANT 2: Modern Soft Rounded Card
// -------------------------------------------------------------
@Composable
fun CalendarVariant2(selectedDate: LocalDate) {
    val month = YearMonth.of(2026, 11)
    val today = LocalDate.of(2026, 11, 10)

    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null, tint = RetirementColors.Cream)
            Text(
                text = "November 2026",
                style = TextStyle(fontFamily = PlayfairDisplay, fontWeight = FontWeight.Bold, fontSize = 18.sp, color = RetirementColors.Cream)
            )
            Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, tint = RetirementColors.Cream)
        }

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            listOf("MON", "TUE", "WED", "THU", "FRI", "SAT", "SUN").forEach { day ->
                Text(text = day, style = TextStyle(fontFamily = InterFontFamily, fontWeight = FontWeight.Bold, fontSize = 11.sp, color = RetirementColors.Gold), modifier = Modifier.width(38.dp), textAlign = TextAlign.Center)
            }
        }

        CalendarGridHelper(
            yearMonth = month,
            selectedDate = selectedDate,
            today = today,
            dayShape = RoundedCornerShape(10.dp),
            selectedContainer = RetirementColors.Gold,
            selectedContent = Color(0xFF111315),
            fontFamily = PlayfairDisplay
        )
    }
}

// -------------------------------------------------------------
// VARIANT 3: Editorial Gold Ring & Dot
// -------------------------------------------------------------
@Composable
fun CalendarVariant3(selectedDate: LocalDate) {
    val month = YearMonth.of(2026, 11)
    val today = LocalDate.of(2026, 11, 10)

    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("<", style = TextStyle(fontFamily = PlayfairDisplay, fontSize = 20.sp, color = RetirementColors.Gold))
            Text(
                text = "November 2026",
                style = TextStyle(fontFamily = CormorantGaramond, fontWeight = FontWeight.Medium, fontStyle = FontStyle.Italic, fontSize = 22.sp, color = RetirementColors.Cream)
            )
            Text(">", style = TextStyle(fontFamily = PlayfairDisplay, fontSize = 20.sp, color = RetirementColors.Gold))
        }

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            listOf("M", "T", "W", "T", "F", "S", "S").forEach { day ->
                Text(text = day, style = TextStyle(fontFamily = CormorantGaramond, fontWeight = FontWeight.Bold, fontSize = 15.sp, color = RetirementColors.Gold), modifier = Modifier.width(38.dp), textAlign = TextAlign.Center)
            }
        }

        CalendarGridHelper(
            yearMonth = month,
            selectedDate = selectedDate,
            today = today,
            dayShape = CircleShape,
            selectedContainer = RetirementColors.Gold.copy(alpha = 0.25f),
            selectedContent = RetirementColors.Gold,
            isRingStyle = true,
            fontFamily = CormorantGaramond
        )
    }
}

// -------------------------------------------------------------
// VARIANT 4: Royal Pill Accent & Header Badge
// -------------------------------------------------------------
@Composable
fun CalendarVariant4(selectedDate: LocalDate) {
    val month = YearMonth.of(2026, 11)
    val today = LocalDate.of(2026, 11, 10)

    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null, tint = RetirementColors.Gold)
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .background(RetirementColors.Gold.copy(alpha = 0.15f))
                    .padding(horizontal = 16.dp, vertical = 6.dp)
            ) {
                Text(
                    text = "NOVEMBER 2026",
                    style = TextStyle(fontFamily = PlayfairDisplay, fontWeight = FontWeight.Bold, fontSize = 15.sp, color = RetirementColors.Gold, letterSpacing = 1.sp)
                )
            }
            Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, tint = RetirementColors.Gold)
        }

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            listOf("Mo", "Tu", "We", "Th", "Fr", "Sa", "Su").forEach { day ->
                Text(text = day, style = TextStyle(fontFamily = PlayfairDisplay, fontWeight = FontWeight.Bold, fontSize = 13.sp, color = RetirementColors.Gold), modifier = Modifier.width(38.dp), textAlign = TextAlign.Center)
            }
        }

        CalendarGridHelper(
            yearMonth = month,
            selectedDate = selectedDate,
            today = today,
            dayShape = RoundedCornerShape(16.dp),
            selectedContainer = RetirementColors.Gold,
            selectedContent = Color(0xFF111315),
            fontFamily = CormorantGaramond
        )
    }
}

// -------------------------------------------------------------
// VARIANT 5: Roman Emperor (Cinzel Classical Serif)
// -------------------------------------------------------------
@Composable
fun CalendarVariant5(selectedDate: LocalDate) {
    val month = YearMonth.of(2026, 11)
    val today = LocalDate.of(2026, 11, 10)

    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null, tint = RetirementColors.Gold)
            Text(
                text = "NOVEMBER 2026",
                style = TextStyle(fontFamily = CinzelFontFamily, fontWeight = FontWeight.Bold, fontSize = 17.sp, color = RetirementColors.Gold, letterSpacing = 2.sp)
            )
            Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, tint = RetirementColors.Gold)
        }

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            listOf("M", "T", "W", "T", "F", "S", "S").forEach { day ->
                Text(text = day, style = TextStyle(fontFamily = CinzelFontFamily, fontWeight = FontWeight.Bold, fontSize = 13.sp, color = RetirementColors.Gold), modifier = Modifier.width(38.dp), textAlign = TextAlign.Center)
            }
        }

        CalendarGridHelper(
            yearMonth = month,
            selectedDate = selectedDate,
            today = today,
            dayShape = RoundedCornerShape(8.dp),
            selectedContainer = RetirementColors.Gold,
            selectedContent = Color(0xFF111315),
            fontFamily = CinzelFontFamily
        )
    }
}

// Helper for rendering preview grids
@Composable
fun CalendarGridHelper(
    yearMonth: YearMonth,
    selectedDate: LocalDate,
    today: LocalDate,
    dayShape: Shape,
    selectedContainer: Color,
    selectedContent: Color,
    isRingStyle: Boolean = false,
    fontFamily: FontFamily
) {
    val firstDayOfWeek = yearMonth.atDay(1).dayOfWeek.value
    val daysInMonth = yearMonth.lengthOfMonth()
    val totalCells = (firstDayOfWeek - 1) + daysInMonth
    val rows = (totalCells + 6) / 7

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        for (row in 0 until rows) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                for (col in 0 until 7) {
                    val dayNumber = row * 7 + col - (firstDayOfWeek - 2)
                    if (dayNumber in 1..daysInMonth) {
                        val date = yearMonth.atDay(dayNumber)
                        val isSelected = date == selectedDate
                        val isToday = date == today

                        Box(
                            modifier = Modifier
                                .size(38.dp)
                                .clip(dayShape)
                                .then(
                                    when {
                                        isSelected && !isRingStyle -> Modifier.background(selectedContainer)
                                        isSelected && isRingStyle -> Modifier.border(2.dp, RetirementColors.Gold, dayShape).background(selectedContainer)
                                        isToday -> Modifier.border(1.5.dp, RetirementColors.Gold, dayShape)
                                        else -> Modifier
                                    }
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = dayNumber.toString(),
                                style = TextStyle(
                                    fontFamily = fontFamily,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                    fontSize = 17.sp,
                                    color = if (isSelected) selectedContent else RetirementColors.Cream
                                )
                            )
                        }
                    } else {
                        Spacer(modifier = Modifier.size(38.dp))
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 380, name = "Variant 1 - Classic Luxury Gold Circle")
@Composable
fun PreviewVariant1() {
    RetirementTimerAppTheme {
        CalendarCardWrapper("Variant 1: Classic Luxury Gold Circle (Current)", "Circle selector, Playfair headers & Garamond numbers") {
            CalendarVariant1(selectedDate = LocalDate.of(2026, 11, 18))
        }
    }
}

@Preview(showBackground = true, widthDp = 380, name = "Variant 2 - Modern Soft Rounded Card")
@Composable
fun PreviewVariant2() {
    RetirementTimerAppTheme {
        CalendarCardWrapper("Variant 2: Modern Soft Rounded Card", "Rounded square selector, clean sans-serif headers") {
            CalendarVariant2(selectedDate = LocalDate.of(2026, 11, 18))
        }
    }
}

@Preview(showBackground = true, widthDp = 380, name = "Variant 3 - Editorial Gold Ring & Dot")
@Composable
fun PreviewVariant3() {
    RetirementTimerAppTheme {
        CalendarCardWrapper("Variant 3: Editorial Gold Ring & Dot", "Garamond Italic headers, gold ring selector with cream text") {
            CalendarVariant3(selectedDate = LocalDate.of(2026, 11, 18))
        }
    }
}

@Preview(showBackground = true, widthDp = 380, name = "Variant 4 - Royal Pill Accent")
@Composable
fun PreviewVariant4() {
    RetirementTimerAppTheme {
        CalendarCardWrapper("Variant 4: Royal Pill Accent & Header Badge", "Pill shape selector, badge header for month title") {
            CalendarVariant4(selectedDate = LocalDate.of(2026, 11, 18))
        }
    }
}

@Preview(showBackground = true, widthDp = 380, name = "Variant 5 - Roman Emperor Cinzel Serif")
@Composable
fun PreviewVariant5() {
    RetirementTimerAppTheme {
        CalendarCardWrapper("Variant 5: Roman Emperor (Cinzel Classical Serif)", "Cinzel serif font, classical Roman architecture aesthetic") {
            CalendarVariant5(selectedDate = LocalDate.of(2026, 11, 18))
        }
    }
}
