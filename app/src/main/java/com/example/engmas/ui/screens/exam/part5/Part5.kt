package com.example.engmas.ui.screens.exam.part5

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
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
import com.example.engmas.ui.theme.EngMasTheme
import com.example.engmas.ui.theme.KufamFont
import com.example.engmas.ui.utils.QuestionWithAnswers
import com.example.engmas.ui.utils.QuizHeader

@Composable
fun Part5(
    modifier: Modifier = Modifier
) {
    Card(
        elevation = CardDefaults.cardElevation(4.dp),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE3F2FD)),
        modifier = modifier
            .fillMaxSize()
            .padding(dimensionResource(R.dimen.padding_medium)),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                verticalArrangement = Arrangement.Center
            ) {
                QuizHeader(
                    currentQuestion = "130",
                    totalQuestions = "200",
                    part = "5",
                    timeLeft = 7200
                )

                val question1 = "130. The_______information provided by Uniss Bank’s brochure helps applicants understand the terms of their loans."
                val options1 = listOf("A. arbitrary", "B. supplemental", "C. superfluous", "D. potential")
                QuestionWithAnswers(question = question1, options = options1)
            }
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth().padding(bottom = 20.dp)
            ) {
                OutlinedButton(
                    onClick = {},
                    border = BorderStroke(1.dp, Color(0xFFD3D3D3)),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .padding(20.dp)
                        .size(130.dp, 40.dp)
                ) {
                    Text(
                        text = "Previous",
                        color = Color(0xFF757575),
                        fontFamily = KufamFont,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                    )
                }

                Button(
                    onClick = {},
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF24D3E3)) ,
                    modifier = Modifier
                        .padding(20.dp)
                        .size(130.dp, 40.dp)
                ) {
                    Text(
                        text = "Next",
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
private fun Part5Preview() {
    EngMasTheme {
        Part5()
    }
}