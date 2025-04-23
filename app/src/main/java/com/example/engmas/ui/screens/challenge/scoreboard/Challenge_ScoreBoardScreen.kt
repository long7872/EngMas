package com.example.engmas.ui.screens.challenge.scoreboard

import android.graphics.BitmapFactory
import android.util.Log
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.engmas.R
import com.example.engmas.data.model.UserScore
import com.example.engmas.ui.theme.KufamFont
import com.example.engmas.ui.utils.BorderSide
import com.example.engmas.ui.utils.customBorder
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue


@Composable
fun Challenge_ScoreBoardScreen(
    modifier: Modifier = Modifier,
    viewModel: ScoreBoardViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
//    WinnerFrame(
//        avatarRes = R.drawable.avatar1_test,
//        frameSize = dimensionResource(R.dimen.avatar_frame_size),
//        modifier = Modifier.fillMaxSize()
//    )

    // Lấy dữ liệu người chơi từ ViewModel
    val players by viewModel.players.collectAsState()

    var topPlayers by remember { mutableStateOf<List<UserScore>>(emptyList()) }

    // Khi players thay đổi, tự động tính lại topPlayers
    LaunchedEffect(players) {
        if (players.isNotEmpty()) {
            topPlayers = viewModel.getTopPlayers()
        }
    }


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
            modifier = Modifier
                .fillMaxSize()
        ) {
            Text(
                text = stringResource(R.string.scoreboard),
                fontFamily = KufamFont,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                color = Color(0xFF009DFF),
                modifier = Modifier
                    .padding(top = dimensionResource(R.dimen.button_horizontal_padding))
            )

//            Log.e(null, "${topPlayers.}")
            ScoreCard(players = topPlayers, viewModel = viewModel)
        }
    }
}

@Composable
private fun ScoreCard(
    players: List<UserScore>,
    modifier: Modifier = Modifier,
    viewModel: ScoreBoardViewModel
) {

    val topPlayer = players.firstOrNull() // Người đứng đầu
    val otherPlayers = players.drop(1) // 4 người còn lại (trừ người đứng đầu)


//    val players = listOf(
//        Triple(2, "longquynh", 126),
//        Triple(3, "hahahaha", 89),
//        Triple(4, "blababbabaab", 63),
//        Triple(5, "thisnamelord", 32),
//        Triple(321, "yourname", 7)
//    )

    Card(
        elevation = CardDefaults.cardElevation(1.dp),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE0F3DE)),
        modifier = modifier
            .fillMaxSize()
            .padding(dimensionResource(R.dimen.button_horizontal_padding)),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                topPlayer?.let {
                    WinnerFrame(
                        avatarRes = R.drawable.avatar1_test,
                        frameSize = dimensionResource(R.dimen.avatar_frame_size)
                    )
                    Text(
                        text = it.name,
                        fontFamily = KufamFont,
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        color = Color(0xFFF9CC17),
                        modifier = Modifier
                            .padding(top = dimensionResource(R.dimen.frame_winner_vertical_padding))
                    )
                    Text(
                        text = it.score.toString(),
                        fontFamily = KufamFont,
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        color = Color(0xFFF9CC17),
                        modifier = Modifier
                    )
                }
            }
            ScoreTable(
                players = otherPlayers,
                modifier = Modifier
                    .padding(dimensionResource(R.dimen.padding_medium))
                    .fillMaxHeight(0.5f),
                viewModel = viewModel
            )
        }
    }
}

@Composable
private fun WinnerFrame(
    @DrawableRes avatarRes: Int,
    frameSize: Dp,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val imageBitmap = remember {
        BitmapFactory.decodeResource(context.resources, R.drawable.winner_circle)
            .asImageBitmap()
    }
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .background(Color.Transparent)
            .size(frameSize)
            .aspectRatio(3/4f)
    ) {
        Image(
            bitmap = imageBitmap,
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
        )
        Column(
            verticalArrangement = Arrangement.Bottom,
            modifier = Modifier.fillMaxSize()
                .background(Color.Transparent)
        ) {
            Image(
                painter = painterResource(avatarRes),
                contentDescription = stringResource(R.string.avatar),
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .padding(dimensionResource(R.dimen.frame_gap_size))
            )
        }
    }
}

@Composable
private fun ScoreTable(
    players: List<UserScore>,
    modifier: Modifier = Modifier,
    viewModel: ScoreBoardViewModel // Nhận viewModel làm tham số
) {
    // Q:
    // Giả sử currentUserId đã có
    val currentUsername = "UserGPT298"
    var currentUser: UserScore? by remember { mutableStateOf(UserScore()) }
    var isOutBoard by remember { mutableStateOf(false) }
    LaunchedEffect(players) {
        if (players.isNotEmpty()) {
            currentUser = viewModel.getUserByName(currentUsername)
            isOutBoard = (currentUser?.rank ?: 0) > 10
            Log.e("TAG", "ID: $currentUser Out: $isOutBoard")
        }
//        val currentUserId = viewModel.getUserByName(currentUser)?:""
//        val currentUserRank = viewModel.getUserRank(currentUserId)?:0
//        val isOutBoard = currentUserRank > 10
    }
    LazyColumn(
        modifier = modifier
    ) {
        var isFirst = true
        items(players) { player ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min)
            ) {
                if (isFirst) {
                    TableCell(
                        text = player.rank.toString(),
                        modifier = Modifier.weight(1f)
                            .customBorder(
                                width = 1.dp,
                                color = Color(0xFF757575),
                                sides = setOf(BorderSide.Top, BorderSide.Bottom)
                            ).padding(dimensionResource(R.dimen.padding_medium))
                    )
                    TableCell(
                        text = player.name,
                        modifier = Modifier.weight(3f)
                            .customBorder(
                                width = 1.dp,
                                color = Color(0xFF757575),
                                sides = setOf(
                                    BorderSide.Top,
                                    BorderSide.Bottom,
                                    BorderSide.Left,
                                    BorderSide.Right
                                )
                            )
                            .padding(dimensionResource(R.dimen.padding_medium))
                    )
                    TableCell(
                        text = player.score.toString(),
                        modifier = Modifier.weight(1f)
                            .customBorder(
                                width = 1.dp,
                                color = Color(0xFF757575),
                                sides = setOf(BorderSide.Top, BorderSide.Bottom)
                            ).padding(dimensionResource(R.dimen.padding_medium))
                    )
                    isFirst = false
                } else {
                    TableCell(
                        text = player.rank.toString(),
                        modifier = Modifier.weight(1f)
                            .customBorder(
                                width = 1.dp,
                                color = Color(0xFF757575),
                                sides = setOf(BorderSide.Bottom)
                            ).padding(dimensionResource(R.dimen.padding_medium))
                    )
                    TableCell(
                        text = player.name,
                        modifier = Modifier.weight(3f)
                            .customBorder(
                                width = 1.dp,
                                color = Color(0xFF757575),
                                sides = setOf(BorderSide.Bottom, BorderSide.Left, BorderSide.Right)
                            )
                            .padding(dimensionResource(R.dimen.padding_medium))
                    )
                    TableCell(
                        text = player.score.toString(),
                        modifier = Modifier.weight(1f)
                            .customBorder(
                                width = 1.dp,
                                color = Color(0xFF757575),
                                sides = setOf(BorderSide.Bottom)
                            ).padding(dimensionResource(R.dimen.padding_medium))
                    )
                }

            }
        }
        Log.e("TAG", "ID: $currentUser Out: $isOutBoard")
        if (isOutBoard) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(IntrinsicSize.Min)
                ) {
                    TableCell(
                        text = "...",
                        modifier = Modifier.weight(1f)
                            .customBorder(
                                width = 1.dp,
                                color = Color(0xFF757575),
                                sides = setOf(BorderSide.Bottom)
                            ).padding(dimensionResource(R.dimen.padding_medium))
                    )
                    TableCell(
                        text = "...",
                        modifier = Modifier.weight(3f)
                            .customBorder(
                                width = 1.dp,
                                color = Color(0xFF757575),
                                sides = setOf(
                                    BorderSide.Bottom,
                                    BorderSide.Left,
                                    BorderSide.Right
                                )
                            )
                            .padding(dimensionResource(R.dimen.padding_medium))
                    )
                    TableCell(
                        text = "...",
                        modifier = Modifier.weight(1f)
                            .customBorder(
                                width = 1.dp,
                                color = Color(0xFF757575),
                                sides = setOf(BorderSide.Bottom)
                            ).padding(dimensionResource(R.dimen.padding_medium))
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(IntrinsicSize.Min)
                ) {
                    TableCell(
                        text = currentUser?.rank.toString(),
                        modifier = Modifier.weight(1f)
                            .customBorder(
                                width = 1.dp,
                                color = Color(0xFF757575),
                                sides = setOf(BorderSide.Bottom)
                            ).padding(dimensionResource(R.dimen.padding_medium))
                    )
                    TableCell(
                        text = currentUser?.name.toString(),
                        modifier = Modifier.weight(3f)
                            .customBorder(
                                width = 1.dp,
                                color = Color(0xFF757575),
                                sides = setOf(
                                    BorderSide.Bottom,
                                    BorderSide.Left,
                                    BorderSide.Right
                                )
                            )
                            .padding(dimensionResource(R.dimen.padding_medium))
                    )
                    TableCell(
                        text = currentUser?.score.toString(),
                        modifier = Modifier.weight(1f)
                            .customBorder(
                                width = 1.dp,
                                color = Color(0xFF757575),
                                sides = setOf(BorderSide.Bottom)
                            ).padding(dimensionResource(R.dimen.padding_medium))
                    )
                }
            }
        }
    }
}

@Composable
private fun TableCell(
    text: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            fontFamily = KufamFont,
            fontWeight = FontWeight.SemiBold,
            fontSize = 12.sp,
            textAlign = TextAlign.Center,
            color = Color(0xFF757575)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun Challenge_ScoreBoardScreenPreview() {
    Challenge_ScoreBoardScreen()
}