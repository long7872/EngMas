package com.example.engmas.ui.screens.exam.part1

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.engmas.R
import com.example.engmas.ui.theme.EngMasTheme
import com.example.engmas.ui.theme.KufamFont
import com.example.engmas.ui.utils.AnswerButtons
import com.example.engmas.ui.utils.QuizHeader
import com.example.engmas.ui.utils.ZoomableImageCard

@Composable
fun Part1(
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
                    currentQuestion = 6,
                    totalQuestions = 200,
                    part = "1",
                    timeLeft = 7200
                )

                ZoomableImageCard("https://thptlqc-my.sharepoint.com/:i:/g/personal/trinhxuanhoa_thptlequangchi_edu_vn/EVekwTAe7UJPmTQJK3T9MOMBw0qPBWLDDHpgFv99OpLvhA?e=cKWblx")
                AnswerButtons(modifier = Modifier.padding(start = 20.dp, end = 20.dp))
            }
            Button(
                elevation = ButtonDefaults.buttonElevation(4.dp),
                onClick = {},
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF24D3E3)) ,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(50.dp)
            ) {
                Text(
                    text = "Start",
                    color = Color(0xFFFFFFFF),
                    fontFamily = KufamFont,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Part1Preview() {
    EngMasTheme {
        Part1()
    }
}