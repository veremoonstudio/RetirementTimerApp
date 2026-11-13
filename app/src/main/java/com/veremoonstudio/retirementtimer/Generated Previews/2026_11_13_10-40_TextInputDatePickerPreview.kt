package com.veremoonstudio.retirementtimer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.veremoonstudio.retirementtimer.ui.theme.PlayfairDisplay
import com.veremoonstudio.retirementtimer.ui.theme.RetirementColors
import com.veremoonstudio.retirementtimer.ui.theme.RetirementTimerAppTheme
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun TextInputDatePickerPreviewScreen() {
    var isTextInputMode by remember { mutableStateOf(true) }
    var textInputDate by remember { mutableStateOf("11.11.2026") }
    val sampleDate = LocalDate.of(2026, 11, 11)
    val localeRu = remember { Locale.forLanguageTag("ru") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(RetirementColors.Background)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "ПРЕВЬЮ ТЕКСТОВОГО ВВОДА ДАТЫ",
            style = TextStyle(
                fontFamily = PlayfairDisplay,
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                color = RetirementColors.Gold,
                textAlign = TextAlign.Center,
                letterSpacing = 1.sp
            )
        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(28.dp)),
            colors = CardDefaults.cardColors(containerColor = RetirementColors.Background)
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                // Top Header Section
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Black.copy(alpha = 0.25f))
                        .padding(start = 24.dp, end = 24.dp, top = 20.dp, bottom = 20.dp)
                ) {
                    Text(
                        text = "Выберите дату",
                        style = TextStyle(
                            fontFamily = PlayfairDisplay,
                            fontSize = 14.sp,
                            color = RetirementColors.Gold,
                            fontWeight = FontWeight.Medium
                        )
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = if (isTextInputMode) "Введенная дата" else sampleDate.format(DateTimeFormatter.ofPattern("d MMM yyyy 'г.'", localeRu)),
                            style = TextStyle(
                                fontFamily = FontFamily.SansSerif,
                                fontSize = if (isTextInputMode) 26.sp else 28.sp,
                                fontWeight = FontWeight.Normal,
                                color = RetirementColors.Gold
                            )
                        )

                        IconButton(
                            onClick = { isTextInputMode = !isTextInputMode },
                            modifier = Modifier.size(28.dp)
                        ) {
                            Icon(
                                imageVector = if (isTextInputMode) Icons.Default.DateRange else Icons.Default.Edit,
                                contentDescription = if (isTextInputMode) "Calendar Mode" else "Text Input Mode",
                                tint = RetirementColors.Gold,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                }

                HorizontalDivider(color = Color.White.copy(alpha = 0.08f), thickness = 1.dp)

                // Content Section (Text Input Field)
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(RetirementColors.Background)
                        .padding(start = 24.dp, end = 24.dp, top = 24.dp, bottom = 32.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    OutlinedTextField(
                        value = textInputDate,
                        onValueChange = { textInputDate = it },
                        label = { Text("Дата", color = RetirementColors.Gold) },
                        placeholder = { Text("DD.MM.YYYY", color = Color.White.copy(alpha = 0.4f)) },
                        singleLine = true,
                        textStyle = TextStyle(
                            fontFamily = FontFamily.SansSerif,
                            fontSize = 18.sp,
                            color = Color.White
                        ),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = RetirementColors.Gold,
                            unfocusedBorderColor = RetirementColors.Gold.copy(alpha = 0.6f),
                            cursorColor = RetirementColors.Gold,
                            focusedLabelColor = RetirementColors.Gold,
                            unfocusedLabelColor = RetirementColors.Gold.copy(alpha = 0.8f)
                        ),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Done
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                // Footer Action Buttons
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFF2B2930))
                        .padding(horizontal = 24.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Cancel",
                        style = TextStyle(
                            fontFamily = FontFamily.SansSerif,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.White.copy(alpha = 0.9f)
                        )
                    )
                    Spacer(modifier = Modifier.width(24.dp))
                    Text(
                        text = "OK",
                        style = TextStyle(
                            fontFamily = FontFamily.SansSerif,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = RetirementColors.Gold
                        )
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 410, heightDp = 400, name = "Text Input Date Picker Preview")
@Composable
fun TextInputDatePickerPreview() {
    RetirementTimerAppTheme {
        TextInputDatePickerPreviewScreen()
    }
}
