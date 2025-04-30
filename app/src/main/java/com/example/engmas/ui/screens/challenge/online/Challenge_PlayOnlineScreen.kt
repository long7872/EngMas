package com.example.engmas.ui.screens.challenge.online

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.withFrameNanos
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.Popup
import com.example.engmas.R
import com.example.engmas.ui.theme.KufamFont
import com.example.engmas.ui.utils.CustomButton
import com.example.engmas.ui.utils.CustomProgressBar
import com.example.engmas.ui.utils.PlayerDuelCard
import com.example.engmas.ui.utils.UnscrambleGameContent

@Composable
fun Challenge_PlayOnlineScreen(
    uiState: Challenge_OnlineUiState,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    onFinishTimeBar: () -> Unit,
    onSkipButton: () -> Unit,
    onSubmitButton: (String) -> Unit,
    onExitButton: () -> Unit,
    onPlayAgainButton: () -> Unit,
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
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(dimensionResource(R.dimen.player_duel_card_size))
                .background(Color(0xFF49C1D9), MaterialTheme.shapes.medium)
                .clip(MaterialTheme.shapes.medium)
        ) {
            PlayerDuelCard(
                thisUserName = uiState.thisUser.username,
                thisUserQuestionCompleted = uiState.thisUserCurrentQuestion + 1,
                otherUserName = uiState.matchedUser.username,
                otherUserQuestionCompleted = uiState.otherUserCurrentQuestion + 1,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = dimensionResource(R.dimen.padding_small)),
            )
        }

        if (uiState.unscrambleList.isNotEmpty()) {
            if ((uiState.thisUserCurrentQuestion < uiState.unscrambleList.size-1)) {
                UnscrambleGameContent(
                    unscrambleWord = uiState.unscrambleList[uiState.thisUserCurrentQuestion],
                    resultState = uiState.resultState,
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
                        text = stringResource(R.string.waiting),
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
    if (uiState.resultState != ResultState.None) {
        WinnerOverlay(
            thisUserName = uiState.thisUser.username,
            thisUserQuestionCompleted = uiState.thisUserScore,
            otherUserName = uiState.matchedUser.username,
            otherUserQuestionCompleted = uiState.otherUserScore,
            onExit = onExitButton,
            onPlayAgain = onPlayAgainButton,
            isWinner = uiState.resultState == ResultState.Win,
            paddingValues = contentPadding
        )
    }
}

@Composable
fun WinnerOverlay(
    thisUserName: String,
    thisUserQuestionCompleted: Int,
    otherUserName: String,
    otherUserQuestionCompleted: Int,
    onExit: () -> Unit,
    onPlayAgain: () -> Unit,
    isWinner: Boolean,
    paddingValues: PaddingValues = PaddingValues(0.dp)
) {
    Dialog(
        onDismissRequest = { /* Disable dismiss by outside touch */ },
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Box(
            modifier = Modifier
                .background(Color.Black.copy(alpha = 0.6f)) // mờ nền
                .fillMaxSize()
                .padding(paddingValues)
                .padding(
                    start = dimensionResource(R.dimen.padding_medium),
                    end = dimensionResource(R.dimen.padding_medium)
                ),
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxSize()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(dimensionResource(R.dimen.player_duel_card_size))
                        .background(Color(0xFF49C1D9), MaterialTheme.shapes.medium)
                        .clip(MaterialTheme.shapes.medium)
                ) {
                    PlayerDuelCard(
                        thisUserName = thisUserName,
                        thisUserQuestionCompleted = thisUserQuestionCompleted + 1,
                        otherUserName = otherUserName,
                        otherUserQuestionCompleted = otherUserQuestionCompleted + 1,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(bottom = dimensionResource(R.dimen.padding_small)),
                    )
                }
                if (isWinner) {
                    Image(
                        painter = painterResource(R.drawable.winner_trophy),
                        contentDescription = stringResource(R.string.online_win_result),
                        modifier = Modifier
                            .size(dimensionResource(R.dimen.result_trophy_size))
                    )
                } else {
                    Image(
                        painter = painterResource(R.drawable.loser_trophy),
                        contentDescription = stringResource(R.string.online_lose_result),
                        modifier = Modifier
                            .size(dimensionResource(R.dimen.result_trophy_size))
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.Center
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
}


@Preview(showBackground = true)
@Composable
private fun Challenge_PlayOnlineScreenPreview() {
    Challenge_PlayOnlineScreen(
        uiState = Challenge_OnlineUiState(
            resultState = ResultState.Lose
        ),
        onFinishTimeBar = {},
        onSkipButton = {},
        onSubmitButton = {},
        onExitButton = {},
        onPlayAgainButton = {}
    )
}