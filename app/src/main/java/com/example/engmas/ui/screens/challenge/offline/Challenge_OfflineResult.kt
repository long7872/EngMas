package com.example.engmas.ui.screens.challenge.offline

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.engmas.R
import com.example.engmas.ui.theme.KufamFont
import com.example.engmas.ui.utils.CircleFrame
import com.example.engmas.ui.utils.CustomButton

@Composable
fun Challenge_OfflineResult(
    userImage: String,
    questionCorrected: Int,
    onExit: () -> Unit,
    onPlayAgain: () -> Unit,
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
                userImage = userImage,
                questionCorrected = questionCorrected
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
                    onClick = onExit,
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
                    onClick = onPlayAgain,
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
    userImage: String,
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
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .background(Color.Transparent)
                        .size(dimensionResource(R.dimen.avatar_frame_size))
                ) {
                    Image(
                        painter = painterResource(R.drawable.avatar_circle_frame),
                        contentDescription = null
                    )
                    Image(
                        painter = if (userImage == "") painterResource(R.drawable.avatardefault)
                        else rememberAsyncImagePainter(userImage),
                        contentDescription = stringResource(R.string.avatar),
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(dimensionResource(R.dimen.avatar_frame_size))
                            .clip(CircleShape)
                            .padding(dimensionResource(R.dimen.frame_gap_size))
                    )
                }
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
//    Challenge_OfflineResult()
}