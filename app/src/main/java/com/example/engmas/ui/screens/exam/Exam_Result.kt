package com.example.engmas.ui.screens.exam

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.engmas.R
import com.example.engmas.ui.theme.KufamFont

@Composable
fun TestResultCard(
    testName: String = "Test 1 - ETS 2024",
    score: String = "990",
    listeningScores: List<Pair<String, String>> = listOf(
        "Part 1" to "06/06",
        "Part 2" to "25/25",
        "Part 3" to "39/39",
        "Part 4" to "30/30"
    ),
    readingScores: List<Pair<String, String>> = listOf(
        "Part 5" to "30/30",
        "Part 6" to "16/16",
        "Part 7" to "54/54"
    ),
    modifier: Modifier = Modifier
) {
    Card(
        elevation = CardDefaults.cardElevation(8.dp),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE3F2FD)),
        modifier = modifier
            .fillMaxWidth()
            .padding(dimensionResource(R.dimen.padding_medium)),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            // Test Name
            Text(
                text = testName,
                fontFamily = KufamFont,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                color = Color(0xFF757575),
            )

            Text(
                text = "Your Score",
                fontFamily = KufamFont,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color(0xFF757575),
            )

            // White Card for Score and Listening/Reading Results
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = 50.dp,
                        end = 50.dp
                    ),
                elevation = CardDefaults.cardElevation(4.dp),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Score
                    Text(
                        text = score,
                        fontFamily = KufamFont,
                        fontWeight = FontWeight.Bold,
                        fontSize = 40.sp,
                        color = Color(0xFF757575),
                    )
                }
            }

            Text(
                text = "LISTENING",
                fontFamily = KufamFont,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = Color(0xFF757575),
            )

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = 50.dp,
                        end = 50.dp
                    ),
                elevation = CardDefaults.cardElevation(4.dp),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    listeningScores.forEach { (part, result) ->
                        Text(
                            text = "$part: $result",
                            fontFamily = KufamFont,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 14.sp,
                            color = Color(0xFF757575)
                        )
                    }
                }
            }

            Text(
                text = "READING",
                fontFamily = KufamFont,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = Color(0xFF757575),
            )

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = 50.dp,
                        end = 50.dp
                    ),
                elevation = CardDefaults.cardElevation(4.dp),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    readingScores.forEach { (part, result) ->
                        Text(
                            text = "$part: $result",
                            fontFamily = KufamFont,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 14.sp,
                            color = Color(0xFF757575)
                        )
                    }
                }
            }

            // Buttons (Details and Retake)
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth().padding(top = 20.dp)
            ) {
                OutlinedButton(
                    onClick = {},
                    border = BorderStroke(1.dp, Color(0xFFD3D3D3)),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .size(130.dp, 40.dp)
                ) {
                    Text(
                        text = "Details",
                        color = Color(0xFF757575),
                        fontFamily = KufamFont,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                    )
                }

                Button(
                    onClick = {},
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF24D3E3)),
                    modifier = Modifier
                        .size(130.dp, 40.dp)
                ) {
                    Text(
                        text = "Retake",
                        color = Color(0xFFFFFFFF),
                        fontFamily = KufamFont,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TestResultCardPreview() {
    TestResultCard()
}
