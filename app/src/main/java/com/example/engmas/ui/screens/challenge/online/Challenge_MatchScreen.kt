package com.example.engmas.ui.screens.challenge.online

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.engmas.R
import com.example.engmas.ui.theme.KufamFont
import com.example.engmas.ui.utils.CircleFrame

@Composable
fun Challenge_MatchScreen(
    uiState: Challenge_OnlineUiState,
    onSubmitButton: () -> Unit,
    modifier: Modifier = Modifier
) {
    if (uiState.matchingState == MatchingState.Play) {
        Challenge_PlayOnlineScreen(
            uiState = uiState,
            contentPadding = PaddingValues(0.dp),
            onSkipButton = {},
            onSubmitButton = onSubmitButton,
            onExitButton = {},
            onPlayAgainButton = {}
        )
    } else {
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
                verticalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxSize()
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Bottom,
                    modifier = Modifier.weight(0.33f)
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

                Image(
                    painter = painterResource(R.drawable.vs_logo),
                    contentDescription = stringResource(R.string.vs_logo),
                    modifier = Modifier.weight(0.33f)
                        .aspectRatio(1f)
                )

                if (uiState.matchingState == MatchingState.Matching) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.weight(0.33f)
                    ) {
                        Text(
                            text = stringResource(R.string.matching),
                            fontFamily = KufamFont,
                            fontWeight = FontWeight.Bold,
                            fontSize = 24.sp,
                            color = Color(0xFF757575),
                        )
                    }
                }
                if (uiState.matchingState == MatchingState.Matched) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Top,
                        modifier = Modifier.weight(0.33f)
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
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Challenge_MatchScreenPreview() {

}