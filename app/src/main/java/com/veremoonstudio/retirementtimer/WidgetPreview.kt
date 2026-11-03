package com.veremoonstudio.retirementtimer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview(
    name = "Widget 3x1 Preview",
    widthDp = 300,
    heightDp = 60,
    showBackground = true
)
@Composable
fun Widget3x1Preview() {
    val goldColor = Color(0xFFC9A961)
    val creamColor = Color(0xFFF5F0E3)
    val bgColor = Color(0xFF1C1C1E)
    val cardBg = Color(0xFF262628)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 4.dp, horizontal = 2.dp),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(bgColor)
                .padding(horizontal = 4.dp, vertical = 2.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "RETIREMENT: Dec 31, 2030",
                    color = goldColor,
                    fontFamily = FontFamily.SansSerif,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    maxLines = 1
                )

                Spacer(modifier = Modifier.height(1.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(3.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    listOf("1234" to "DAYS", "08" to "HRS", "45" to "MIN", "12" to "SEC").forEach { (valStr, label) ->
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(8.dp))
                                .background(cardBg)
                                .padding(horizontal = 2.dp, vertical = 2.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = valStr,
                                    color = creamColor,
                                    fontFamily = FontFamily.SansSerif,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = label,
                                    color = goldColor,
                                    fontFamily = FontFamily.SansSerif,
                                    fontSize = 8.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
