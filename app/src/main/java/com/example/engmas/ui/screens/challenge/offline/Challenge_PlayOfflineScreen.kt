package com.example.engmas.ui.screens.challenge.offline

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
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
import com.example.engmas.ui.screens.challenge.online.ResultState
import com.example.engmas.ui.theme.KufamFont
import com.example.engmas.ui.utils.UnscrambleGameContent

@Composable
fun Challenge_PlayOfflineScreen(
    uiState: Challenge_OfflineUiState,
    onFinishTimeBar: () -> Unit,
    onSkipButton: () -> Unit,
    onSubmitButton: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val resultState = ResultState.None
    Card(
        elevation = CardDefaults.cardElevation(4.dp),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE3F2FD)),
        modifier = modifier
            .fillMaxSize()
            .padding(dimensionResource(R.dimen.padding_medium)),
    ) {
//        UnscrambleGameContent(
//            unscrambleWord = "Comfortable",
//            resultState = resultState,
//            onFinishTimeBar = {},
//            onSkipButton = {},
//            onSubmitButton = {},
//            modifier = Modifier.fillMaxSize()
//        )
        if (uiState.scrambledList.isNotEmpty()) {
            if ((uiState.thisUserCurrentQuestion < uiState.scrambledList.size-1)) {
                UnscrambleGameContent(
                    unscrambleWord = uiState.scrambledList[uiState.thisUserCurrentQuestion],
                    resultState = resultState,
                    onFinishTimeBar = onFinishTimeBar,
                    onSkipButton = onSkipButton,
                    onSubmitButton = onSubmitButton,
                    modifier = Modifier.fillMaxSize()
                )
            } else {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Wait",
                        fontFamily = KufamFont,
                        fontWeight = FontWeight.Bold,
                        fontSize = 30.sp,
                        color = Color(0xFF757575),
                        textAlign = TextAlign.Center
                    )
                }
            }
        } else {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Challenge_PlayOfflineScreenPreview() {
//    Challenge_PlayOfflineScreen()
}