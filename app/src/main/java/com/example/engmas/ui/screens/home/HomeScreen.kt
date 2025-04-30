package com.example.engmas.ui.screens.home

import android.graphics.BitmapFactory
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.engmas.R
import com.example.engmas.data.model.Stats
import com.example.engmas.data.model.Today
import com.example.engmas.ui.GeneralViewModel
import com.example.engmas.ui.navigation.NavigationDestination
import com.example.engmas.ui.theme.KufamFont
import com.example.engmas.ui.utils.CustomSearchBar

object HomeDestination: NavigationDestination {
    override val route = "home"
    override val titleRes = R.string.tab_home
}

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = viewModel(),
    timerViewModel: GeneralViewModel,
    modifier: Modifier = Modifier
) {

    val context = LocalContext.current
    val hasStartedTracking = rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(hasStartedTracking.value) {
        // Chỉ gọi startSessionTracking khi chưa bắt đầu
        if (!hasStartedTracking.value) {
            // Bắt đầu session tracking
            timerViewModel.startSessionTracking(context) {
                viewModel.markTodayCompleted() // Khi xong tracking
            }
            hasStartedTracking.value = true // Đánh dấu là đã bắt đầu
        }
    }

    val uiState by viewModel.uiState.collectAsState()

    val query = uiState.query
    var isActive by rememberSaveable { mutableStateOf(false) }
    val searchResult = uiState.searchResults
    val streak = uiState.userScore.streak
    viewModel.checkAndUpdateStreak(streak)

    LaunchedEffect(streak) {
        Log.d("HomeScreen", "🎯 Dữ liệu streak thay đổi: ${streak.completedDays}")

        viewModel.checkAndUpdateStreak(streak)
    }

    val currentWeek = uiState.userScore.currentWeekStats
    val previousWeek = uiState.userScore.previousWeekStats


    Column(
        modifier = modifier.fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            CustomSearchBar(
                query = query,
                onQueryChange = {
                    viewModel.searchVocab(it)
                },
                isActive = isActive,
                onActiveChange = { isActive = it },
                onClearButton = { viewModel.searchVocab("") },
                queryItems = searchResult,
                onItemClicked = {},
                modifier = Modifier.fillMaxWidth()
                    .align(Alignment.TopCenter)
            )
        }
        LearningStreakCard(
            completedDays = streak.completedDays,
            today = streak.today,
            highestStreak = streak.highestStreak,
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.padding_medium))
        )
        WeeklyAnalysisCard(
            currentWeek = currentWeek,
            previousWeek = previousWeek,
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.padding_medium))
        )
    }
}

@Composable
private fun LearningStreakCard(
    completedDays: List<String>,
    today: Today,
    highestStreak: Int,
    modifier: Modifier = Modifier
) {
    val days = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")

    val isStreakBroken = !today.completed

    Card(
        elevation = CardDefaults.cardElevation(4.dp),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFFFF)),
        modifier = modifier
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TitleStreakCard(
                isStreakBroken = isStreakBroken,
                highestStreak = highestStreak,
                modifier = Modifier
                    .padding(dimensionResource(R.dimen.padding_large))
            )
            StreakRow(
                days = days,
                today = today,
                completedDays = completedDays,
                isStreakBroken = isStreakBroken,
                modifier = Modifier
                    .padding(
                        bottom = dimensionResource(R.dimen.padding_medium),
                        start = dimensionResource(R.dimen.padding_medium),
                        end = dimensionResource(R.dimen.padding_medium)
                    )
            )
        }
    }
}

@Composable
private fun TitleStreakCard(
    isStreakBroken: Boolean,
    highestStreak: Int,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val imageBitmap =
        BitmapFactory.decodeResource(context.resources,
            if (isStreakBroken) R.drawable.streak_inactive
            else R.drawable.streak_active
        ).asImageBitmap()

    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier,
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .aspectRatio(1f)
                .padding(dimensionResource(R.dimen.padding_small)),
        ) {
            Image(
                bitmap = imageBitmap,
                contentDescription = "Flame",
                modifier = Modifier.fillMaxSize()
            )
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = stringResource(R.string.streak_title),
                fontFamily = KufamFont,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = Color(0xFF757575)
            )
            Text(
                text = highestStreak.toString(),
                fontFamily = KufamFont,
                fontWeight = FontWeight.Bold,
                fontSize = 64.sp,
                textAlign = TextAlign.Center,
                color = Color(
                    color = if (isStreakBroken) 0xFF757575
                    else 0xFFFFCD2A
                ),
                modifier = Modifier
                    .height(70.dp)
                    .wrapContentHeight(align = Alignment.Bottom)
            )
            Text(
                text = stringResource(R.string.streak_guide),
                fontFamily = KufamFont,
                fontWeight = FontWeight.SemiBold,
                fontSize = 8.sp,
                textAlign = TextAlign.Center,
                color = Color(
                    color = if (isStreakBroken) 0xFF2B4EA2
                    else 0xFF757575
                )
            )
        }
    }
}

@Composable
private fun StreakRow(
    days: List<String>,
    today: Today,
    completedDays: List<String>,
    isStreakBroken: Boolean,
    modifier: Modifier = Modifier
) {
    val todayIndex = days.indexOf(today.day)
    val completedDayIndicesUpdated = completedDays.map { days.indexOf(it) }.sorted()
    val latestCompletedIndex = completedDayIndicesUpdated.lastOrNull()

    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = modifier.fillMaxWidth()
    ) {
        days.forEachIndexed { index, day ->
            val status = if (isStreakBroken) {
                 when {
                     latestCompletedIndex == null && index == todayIndex -> 2
                     latestCompletedIndex != null && index == latestCompletedIndex+1 -> 2
                     index in completedDayIndicesUpdated -> 1
                     else -> 0
                }
            } else {
                when {
                    latestCompletedIndex != null && index == latestCompletedIndex -> 2
                    index in completedDayIndicesUpdated -> 1
                    else -> 0
                }
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = day,
                    fontFamily = KufamFont,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 12.sp,
                    color = Color(0xFF757575).copy(alpha = 0.58f)
                )
                when (status) {
                    2 -> {
                        Icon(
                            painter = painterResource(
                                if (isStreakBroken) R.drawable.today_streak_inactive
                                else R.drawable.today_streak
                            ),
                            tint = Color.Unspecified,
                            contentDescription = stringResource(R.string.tomorrow_streak),
                            modifier = Modifier
                                .size(dimensionResource(R.dimen.streak_icon_size))
                        )
                    }
                    1 -> {
                        Icon(
                            painter = painterResource(
                                if (isStreakBroken) R.drawable.past_streak_inactive
                                else R.drawable.past_streak
                            ),
                            tint = Color.Unspecified,
                            contentDescription = stringResource(R.string.tomorrow_streak),
                            modifier = Modifier
                                .size(dimensionResource(R.dimen.streak_icon_size))
                                .padding(dimensionResource(R.dimen.padding_smaller))
                        )
                    }
                    else -> {
                        Icon(
                            painter = painterResource(
                                if (isStreakBroken) R.drawable.tomorrow_streak_inactive
                                else R.drawable.tomorrow_streak
                            ),
                            tint = Color.Unspecified,
                            contentDescription = stringResource(R.string.tomorrow_streak),
                            modifier = Modifier
                                .size(dimensionResource(R.dimen.streak_icon_size))
                                .padding(dimensionResource(R.dimen.padding_smaller))
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun WeeklyAnalysisCard(
    currentWeek: Stats,
    previousWeek: Stats,
    modifier: Modifier = Modifier
) {
    val isLearnedOkay = currentWeek.wordsLearned >= previousWeek.wordsLearned
    val isReviewOkay = currentWeek.wordsToReview <= previousWeek.wordsToReview
    val okayColorContainer = Color(0xFF63F632).copy(alpha = 0.42f)
    val okayColorItem = Color(0xFF07C500)
    val notOkayColorContainer = Color(0xFFF63232).copy(alpha = 0.58f)
    val notOkayColorItem = Color(0xFFC50000)
    val okayIcon = painterResource(R.drawable.okay)
    val notOkayIcon = painterResource(R.drawable.not_okay)
    Card(
        elevation = CardDefaults.cardElevation(4.dp),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE3F2FD)),
        modifier = modifier
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(dimensionResource(R.dimen.padding_small))
        ) {
            Text(
                text = stringResource(R.string.analysis),
                fontFamily = KufamFont,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp,
                color = Color(0xFF757575)
            )
            HorizontalDivider(
                thickness = 1.dp,
                color = Color(0xFFD3D3D3),
                modifier = Modifier
                    .padding(horizontal = dimensionResource(R.dimen.padding_large))
                    .padding(vertical = dimensionResource(R.dimen.padding_small))
            )
            Card(
                shape = MaterialTheme.shapes.medium,
                colors = CardDefaults.cardColors(
                    containerColor = if (isLearnedOkay) okayColorContainer
                    else notOkayColorContainer
                ),
                modifier = Modifier.padding(dimensionResource(R.dimen.padding_small))
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                        .height(dimensionResource(R.dimen.analysis_row))
                        .padding(dimensionResource(R.dimen.padding_smaller))
                ) {
                    Icon(
                        painter = if (isLearnedOkay) okayIcon else notOkayIcon,
                        tint = if (isLearnedOkay) okayColorItem else notOkayColorItem,
                        contentDescription = stringResource(R.string.analysis_up),
                        modifier = Modifier.height(dimensionResource(R.dimen.streak_icon_size))
                            .padding(horizontal = dimensionResource(R.dimen.padding_smaller))
                            .padding(start = dimensionResource(R.dimen.padding_medium))
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxHeight()
                    ) {
                        Text(
                            text = currentWeek.wordsLearned.toString(),
                            fontFamily = KufamFont,
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            color = if (isLearnedOkay) okayColorItem else notOkayColorItem,
                            modifier = Modifier.padding(horizontal = dimensionResource(R.dimen.padding_small))
                        )
                        Text(
                            text = " ${stringResource(R.string.analysis_learned)} ",
                            fontFamily = KufamFont,
                            fontWeight = FontWeight.Medium,
                            fontSize = 13.sp,
                            color = if (isLearnedOkay) okayColorItem else notOkayColorItem,
                            modifier = Modifier.padding(end = dimensionResource(R.dimen.padding_small))
                        )
                    }
                    Icon(
                        painter = painterResource(R.drawable.drop),
                        tint = if (isLearnedOkay) okayColorItem else notOkayColorItem,
                        contentDescription = stringResource(R.string.analysis_up),
                        modifier = Modifier.height(dimensionResource(R.dimen.drop_icon_size))
                            .padding(horizontal = dimensionResource(R.dimen.padding_smaller))
                            .padding(end = dimensionResource(R.dimen.padding_medium))
                    )
                }
            }
            Card(
                shape = MaterialTheme.shapes.medium,
                colors = CardDefaults.cardColors(
                    containerColor = if (isReviewOkay) okayColorContainer
                    else notOkayColorContainer
                ),
                modifier = Modifier.padding(dimensionResource(R.dimen.padding_small))
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                        .height(dimensionResource(R.dimen.analysis_row))
                        .padding(dimensionResource(R.dimen.padding_smaller))
                ) {
                    Icon(
                        painter = if (isReviewOkay) okayIcon else notOkayIcon,
                        tint = if (isReviewOkay) okayColorItem else notOkayColorItem,
                        contentDescription = stringResource(R.string.analysis_up),
                        modifier = Modifier.height(dimensionResource(R.dimen.streak_icon_size))
                            .padding(horizontal = dimensionResource(R.dimen.padding_smaller))
                            .padding(start = dimensionResource(R.dimen.padding_medium))
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxHeight()
                    ) {
                        Text(
                            text = currentWeek.wordsToReview.toString(),
                            fontFamily = KufamFont,
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            color = if (isReviewOkay) okayColorItem else notOkayColorItem,
                            modifier = Modifier.padding(horizontal = dimensionResource(R.dimen.padding_small))
                        )
                        Text(
                            text = stringResource(R.string.analysis_review),
                            fontFamily = KufamFont,
                            fontWeight = FontWeight.Medium,
                            fontSize = 13.sp,
                            color = if (isReviewOkay) okayColorItem else notOkayColorItem,
                            modifier = Modifier.padding(end = dimensionResource(R.dimen.padding_small))
                        )
                    }
                    Icon(
                        painter = painterResource(R.drawable.drop),
                        tint = if (isReviewOkay) okayColorItem else notOkayColorItem,
                        contentDescription = stringResource(R.string.analysis_up),
                        modifier = Modifier.height(dimensionResource(R.dimen.drop_icon_size))
                            .padding(horizontal = dimensionResource(R.dimen.padding_smaller))
                            .padding(end = dimensionResource(R.dimen.padding_medium))
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {
//    HomeScreen(
//
//    )
    val currentWeek = Stats(
        wordsLearned = 24,
        wordsToReview = 16
    )
    val previousWeek = Stats(
        wordsLearned = 24,
        wordsToReview = 16
    )
    WeeklyAnalysisCard(
        currentWeek = currentWeek,
        previousWeek = previousWeek
    )
}