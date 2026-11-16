@file:JvmName("SpringyGestureDragPreviewDotFormat")
package com.veremoonstudio.retirementtimer

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.veremoonstudio.retirementtimer.ui.theme.PlayfairDisplay
import com.veremoonstudio.retirementtimer.ui.theme.RetirementColors
import com.veremoonstudio.retirementtimer.ui.theme.RetirementTimerAppTheme

@Composable
fun SpringyGestureDragPreviewScreen() {
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

    val draggedIndex = 1 // Simulating 2nd item being dragged
    val dragOffsetY = 24f // Simulated touch drag Y offset

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(RetirementColors.Background)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "ПРЕВЬЮ: ЖЕСТОВОЕ ПЕРЕМЕЩЕНИЕ С ПРУЖИННОЙ АНИМАЦИЕЙ",
            style = TextStyle(
                fontFamily = PlayfairDisplay,
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp,
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

            Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                holidaysList.forEachIndexed { index, holiday ->
                    val isBeingDragged = index == draggedIndex

                    // Animated Bouncy Spring Displacement for neighboring items
                    val springOffsetY by animateFloatAsState(
                        targetValue = when {
                            !isBeingDragged && index == 2 -> -28f // Neighboring card parts with bouncy spring
                            else -> 0f
                        },
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioMediumBouncy,
                            stiffness = Spring.StiffnessLow
                        ),
                        label = "SpringDisplacement"
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .zIndex(if (isBeingDragged) 10f else 0f)
                            .graphicsLayer {
                                translationY = if (isBeingDragged) dragOffsetY else springOffsetY
                            }
                            .clip(RoundedCornerShape(8.dp))
                            .background(
                                if (isBeingDragged) Color.White.copy(alpha = 0.08f)
                                else Color.Transparent
                            )
                            .padding(horizontal = 8.dp, vertical = 3.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(modifier = Modifier.weight(1f), verticalAlignment = Alignment.CenterVertically) {
                            if (isBeingDragged) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(end = 6.dp)) {
                                    Icon(imageVector = Icons.Default.ArrowDropUp, contentDescription = null, tint = RetirementColors.Gold, modifier = Modifier.size(18.dp))
                                    Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = null, tint = RetirementColors.Gold, modifier = Modifier.size(18.dp))
                                }
                            }
                            Text(
                                text = "${holiday.name} (${holiday.month}/${holiday.day})",
                                style = TextStyle(
                                    fontFamily = PlayfairDisplay,
                                    fontSize = 17.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = if (isBeingDragged) RetirementColors.Gold else RetirementColors.Cream
                                ),
                                modifier = Modifier.weight(1f)
                            )
                        }
                        Checkbox(
                            checked = holiday.isEnabled,
                            onCheckedChange = {},
                            colors = CheckboxDefaults.colors(checkedColor = RetirementColors.Gold, uncheckedColor = RetirementColors.Gold.copy(alpha = 0.5f))
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 380, heightDp = 420, name = "Springy Gesture Drag Preview")
@Composable
fun SpringyGestureDragPreview() {
    RetirementTimerAppTheme {
        SpringyGestureDragPreviewScreen()
    }
}
