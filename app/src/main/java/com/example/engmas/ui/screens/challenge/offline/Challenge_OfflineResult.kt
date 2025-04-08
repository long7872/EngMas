package com.example.engmas.ui.screens.challenge.offline

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import com.example.engmas.ui.theme.KufamFont
import com.example.engmas.ui.utils.AnswerInputField
import com.example.engmas.ui.utils.CircleFrame
import com.example.engmas.ui.utils.CustomButton
import com.example.engmas.ui.utils.TimeBar
import com.example.engmas.ui.utils.UnscrambleGameContent

@Composable
fun Challenge_OfflineResult(
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
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            ScoreCard(
                questionCorrected = 10
            )

            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.Bottom,
                modifier = Modifier.fillMaxSize()
            ) {
                CustomButton(
                    text = "Exit",
                    fontSize = 14.sp,
                    color = Color(0xFFDD0909),
                    onClick = {  },
                    modifier = Modifier.weight(0.5f)
                        .padding(
                            start = dimensionResource(R.dimen.button_horizontal_padding),
                            top = dimensionResource(R.dimen.button_horizontal_padding),
                            bottom = dimensionResource(R.dimen.button_horizontal_padding),
                            end = dimensionResource(R.dimen.button_horizontal_padding) / 2
                        )
                        .height(dimensionResource(R.dimen.button_size))
                )
                CustomButton(
                    text = "Play Again",
                    fontSize = 14.sp,
                    color = Color(0xFF09DD30),
                    onClick = {  },
                    modifier = Modifier.weight(0.5f)
                        .padding(
                            start = dimensionResource(R.dimen.button_horizontal_padding) / 2,
                            top = dimensionResource(R.dimen.button_horizontal_padding),
                            bottom = dimensionResource(R.dimen.button_horizontal_padding),
                            end = dimensionResource(R.dimen.button_horizontal_padding)
                        )
                        .height(dimensionResource(R.dimen.button_size))
                )
            }
        }
    }
}

@Composable
private fun ScoreCard(
    questionCorrected: Int,
    modifier: Modifier = Modifier
) {
    Card(
        elevation = CardDefaults.cardElevation(4.dp),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5)),
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight(0.65f)
            .padding(dimensionResource(R.dimen.button_horizontal_padding)),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .padding(vertical = dimensionResource(R.dimen.offline_result_vertical_padding))
            ) {
                CircleFrame(
                    avatarRes = R.drawable.avatar1_test,
                    frameSize = dimensionResource(R.dimen.avatar_frame_size)
                )
                Text(
                    text = "User",
                    fontFamily = KufamFont,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    color = Color(0xFF757575),
                    modifier = Modifier.padding(top = dimensionResource(R.dimen.padding_medium))
                )
            }
            Text(
                text = stringResource(R.string.offline_score),
                fontFamily = KufamFont,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = Color(0xFF757575),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(vertical = dimensionResource(R.dimen.offline_result_vertical_padding) / 2)
            )
            Text(
                text = "$questionCorrected/10",
                fontFamily = KufamFont,
                fontWeight = FontWeight.Bold,
                fontSize = 32.sp,
                color = if (questionCorrected == 10) Color(0xFF09DD30)
                else Color(0xFFDD0909),
                textAlign = TextAlign.Center,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Challenge_OfflineResultPreview() {
    Challenge_OfflineResult()
}