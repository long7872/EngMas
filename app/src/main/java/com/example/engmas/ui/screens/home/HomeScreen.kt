package com.example.engmas.ui.screens.home

import android.graphics.BitmapFactory
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import com.example.engmas.data.Today
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
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()

//    val userId = "-00YTYZBzmyAU5cvRHY"

    val streak by viewModel.streak.collectAsState()


    // Gọi hàm để lấy UID đầu tiên và tải streak khi Composable được tạo
    LaunchedEffect(Unit) {
        viewModel.fetchFirstUserIdAndLoadStreak()
    }


    // Log dữ liệu streak khi nó thay đổi
    LaunchedEffect(streak) {
        streak?.let {
            Log.d("HomeScreen", "Dữ liệu streak: ${it.completedDays}")
        } ?: Log.e("HomeScreen", "Dữ liệu streak không có!")
    }

    val query = uiState.query
    var isActive by rememberSaveable { mutableStateOf(false) }
    val searchResult = uiState.searchResults

    Column(
        modifier = modifier.fillMaxSize()

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
            completedDays = streak?.completedDays ?: listOf(),
            today = streak?.today ?: Today("", false),
            highestStreak = streak?.highestStreak ?: 0,
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.padding_medium)),
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
    // val completedDays = mutableListOf("Tue", "Wed")

    val lastCompletedIndex = completedDays.lastOrNull()?.let { days.indexOf(it) } ?: -1
    val todayIndex = days.indexOf(today.day)

    if (lastCompletedIndex != -1 && todayIndex - lastCompletedIndex > 1) {
//        completedDays.clear()
    }

    if (today.isCompleted && !completedDays.contains(today.day)) {
//        completedDays.add(today.first)
    }

    val isStreakBroken = !today.isCompleted
    Log.d(null, "$isStreakBroken")


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
    highestStreak: Int,
    isStreakBroken: Boolean,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val imageBitmap =
        BitmapFactory.decodeResource(context.resources,
            if (isStreakBroken) R.drawable.streak_inactive
            else R.drawable.streak_active
        ).asImageBitmap()

    Log.d(null, "$isStreakBroken")

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
                // HD
                text = "$highestStreak",
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
    completedDays: List<String>,
    isStreakBroken: Boolean,
    modifier: Modifier = Modifier
) {
    val completedDayIndicesUpdated = completedDays.map { days.indexOf(it) }.sorted()
    val latestCompletedIndex = completedDayIndicesUpdated.lastOrNull()

    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = modifier.fillMaxWidth()
    ) {
        days.forEachIndexed { index, day ->
            val status = if (isStreakBroken) {
                 when {
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

@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {
    HomeScreen(

    )
}