package com.example.retirementtimerapp

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.MilitaryTech
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.retirementtimerapp.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PremiumScreen(onClose: () -> Unit) {
    var selectedOption by remember { mutableStateOf("Yearly") }

    MarbleBackground {
        Scaffold(
            containerColor = Color.Transparent,
            topBar = {
                TopAppBar(
                    title = {},
                    navigationIcon = {
                        IconButton(onClick = onClose) {
                            Icon(Icons.Default.Close, contentDescription = "Close", tint = RetirementColors.Gold)
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
                )
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                // Header
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.Default.MilitaryTech,
                        contentDescription = null,
                        tint = RetirementColors.Gold,
                        modifier = Modifier.size(80.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "GET PREMIUM TODAY!",
                        style = TextStyle(
                            fontFamily = PlayfairDisplay,
                            fontWeight = FontWeight.Bold,
                            fontSize = 32.sp,
                            color = RetirementColors.Cream,
                            textAlign = TextAlign.Center
                        )
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "Unlock all Backgrounds, and say goodbye to ads!",
                        style = TextStyle(
                            fontFamily = CormorantGaramond,
                            fontSize = 18.sp,
                            color = RetirementColors.Gold.copy(alpha = 0.7f),
                            textAlign = TextAlign.Center
                        )
                    )
                }

                // Options
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    PremiumOption(
                        title = "Lifetime",
                        price = "119,99 грн.",
                        isSelected = selectedOption == "Lifetime",
                        onClick = { selectedOption = "Lifetime" }
                    )
                    PremiumOption(
                        title = "Yearly",
                        price = "79,99 грн.",
                        isSelected = selectedOption == "Yearly",
                        onClick = { selectedOption = "Yearly" }
                    )
                    PremiumOption(
                        title = "Monthly",
                        price = "9,19 грн.",
                        isSelected = selectedOption == "Monthly",
                        onClick = { selectedOption = "Monthly" }
                    )
                }

                // Footer
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Button(
                        onClick = { /* Process */ },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .background(
                                brush = Brush.linearGradient(listOf(GoldPrimary, GoldDeep)),
                                shape = RoundedCornerShape(28.dp)
                            ),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                        shape = RoundedCornerShape(28.dp)
                    ) {
                        Text(
                            "CONTINUE",
                            style = TextStyle(
                                fontFamily = PlayfairDisplay,
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp,
                                color = RetirementColors.Background
                            )
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(24.dp))
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        FooterLink("Restore")
                        Text("•", color = RetirementColors.AdTextColor)
                        FooterLink("Terms")
                        Text("•", color = RetirementColors.AdTextColor)
                        FooterLink("Privacy policy")
                    }
                }
            }
        }
    }
}

@Composable
fun PremiumOption(title: String, price: String, isSelected: Boolean, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(if (isSelected) Color.White.copy(alpha = 0.08f) else Color.White.copy(alpha = 0.03f))
            .border(
                width = 1.dp,
                color = if (isSelected) RetirementColors.Gold else RetirementColors.Gold.copy(alpha = 0.2f),
                shape = RoundedCornerShape(12.dp)
            )
            .clickable { onClick() }
            .padding(20.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = title,
                    style = TextStyle(
                        fontFamily = PlayfairDisplay,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = if (isSelected) RetirementColors.Gold else RetirementColors.Cream
                    )
                )
                Text(
                    text = price,
                    style = TextStyle(
                        fontFamily = CormorantGaramond,
                        fontSize = 14.sp,
                        color = RetirementColors.Gold.copy(alpha = 0.6f)
                    )
                )
            }
            RadioButton(
                selected = isSelected,
                onClick = onClick,
                colors = RadioButtonDefaults.colors(
                    selectedColor = RetirementColors.Gold,
                    unselectedColor = RetirementColors.Gold.copy(alpha = 0.4f)
                )
            )
        }
    }
}

@Composable
fun FooterLink(text: String) {
    Text(
        text = text,
        style = TextStyle(
            fontFamily = CormorantGaramond,
            fontSize = 12.sp,
            color = RetirementColors.AdTextColor
        ),
        modifier = Modifier.clickable { }
    )
}
