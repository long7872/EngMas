package com.example.engmas.ui.screens.practice.courses

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.engmas.R
import com.example.engmas.ui.navigation.NavigationDestination
import com.example.engmas.ui.screens.practice.courses.fakedata.Items
import com.example.engmas.ui.screens.practice.courses.fakedata.VocabularyItem
import com.example.engmas.ui.screens.practice.vocabulary.VocabularyViewModel
import com.example.engmas.ui.screens.practice.vocabulary.model.TopicProgress
import com.example.engmas.ui.theme.KufamFont
import com.example.engmas.ui.utils.CustomProgressBar
import com.example.engmas.ui.utils.TitleRow

object PracticeReviewDestination: NavigationDestination {
    override val route = "practice/review"
    override val titleRes = R.string.tab_review
}

@Composable
fun ReviewScreen(
    onVocabularyClick: (Int) -> Unit,
    onFlashcardClick: (Int) -> Unit,
    onBackClicked: () -> Unit,
    viewModel: VocabularyViewModel = viewModel(),
    modifier: Modifier = Modifier,
) {
    val uiState by viewModel.uiState.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.getTopic()
    }
    val listTopicProgress = uiState.listTopics

    Card(
        elevation = CardDefaults.cardElevation(4.dp),
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFFFF)),
        modifier = modifier
            .fillMaxWidth()
            .padding(dimensionResource(R.dimen.padding_medium)),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            TitleRow(
                containerColor = Color(0xFFE3F2FD),
                itemColor = Color(0xFF757575),
                text = stringResource(R.string.review),
                onClick = onBackClicked
            )

            Spacer(modifier = Modifier.padding(4.dp))

            // Phân loại các item theo tiến độ (progress)
            val inProgressItems = listTopicProgress.filter { it.progress > 0 }
            val exploreItems = listTopicProgress.filter { it.progress == 0f }

            // Hiển thị ContainerCard cho các item có tiến độ (InProgress)
            ContainerCard(
                title = "Vocabulary",
                items = inProgressItems,
                onClick = onVocabularyClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )

            Spacer(modifier = Modifier.height(8.dp))

            ContainerCard(
                title = "Flashcard",
                items = inProgressItems,
                onClick = onFlashcardClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )

            Spacer(modifier = Modifier.padding(12.dp))
        }
    }
}

@Composable
private fun ContainerCard(
    title: String,
    items: List<TopicProgress>,
    showProgress: Boolean = true,
    onClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        border = BorderStroke(1.dp, color = Color(0xFFD3D3D3)),
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = modifier
            .fillMaxWidth()
            .padding(12.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Text(
                text = title,
                fontFamily = KufamFont,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                color = Color(0xFF757575),
                modifier = Modifier.padding(top = 10.dp, start = 10.dp, bottom = 10.dp)
            )

            // Hiển thị các item trong LazyColumn
            if (showProgress) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = 300.dp),  // Giới hạn chiều cao tối đa
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(items) { item ->
                        InProgressRow(
                            text = "${item.topicName} - ${item.topicNameVi}",
                            progress = item.progress,
                            onClick = { onClick(item.id) },
                        )
                    }
                }
            } else {
                // Hiển thị ExploreRow nếu không có tiến độ
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(items) { item ->
                        ExploreRow(
                            text = "${item.topicName} - ${item.topicNameVi}",
                            onClick = { onClick(item.id) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun InProgressRow(
    text: String,
    onClick: () -> Unit,
    progress: Float,
    modifier: Modifier = Modifier
) {
    Card(
        elevation = CardDefaults.cardElevation(4.dp),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE3F2FD)),
        modifier = modifier
            .height(90.dp)
            .padding(
                top = 8.dp,
                start = 12.dp,
                end = 12.dp
            ),
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxSize()
                .padding(dimensionResource(R.dimen.padding_medium))
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = dimensionResource(R.dimen.padding_medium))
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.item_icon),
                            tint = Color(0xFF4DC5DD),
                            contentDescription = stringResource(R.string.item_icon),
                            modifier = Modifier
                                .size(20.dp)
                        )

                        Spacer(Modifier.width(16.dp))

                        Box(
                            contentAlignment = Alignment.BottomCenter,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(20.dp) // đảm bảo đủ để hiển thị marquee
                        ) {
                            Text(
                                text = text,
                                fontFamily = KufamFont,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 14.sp,
                                color = Color(0xFF4DC5DD),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier
                                    .basicMarquee(
                                        iterations = Int.MAX_VALUE, // lặp vô hạn
                                        repeatDelayMillis = 3000,         // nghỉ 3s trước mỗi lần lặp lại
                                        initialDelayMillis = 0,     // không delay lần đầu tiên
                                        velocity = 30.dp            // tốc độ cuộn, có thể tuỳ chỉnh
                                    )
                                    .fillMaxWidth()
                            )
                        }
                    }
                    Button(
                        elevation = ButtonDefaults.buttonElevation(4.dp),
                        onClick = onClick,
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF24D3E3)) ,
                        modifier = Modifier
                            .size(120.dp, 30.dp)
                            .padding(start = 8.dp)
                    ) {
                        Text(
                            text = "Continue",
                            color = Color(0xFFFFFFFF),
                            fontFamily = KufamFont,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                CustomProgressBar(
                    progress = progress,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
private fun ExploreRow(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        elevation = CardDefaults.cardElevation(4.dp),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE3F2FD)),
        modifier = modifier
            .height(80.dp)
            .padding(
                top = 8.dp,
                start = 12.dp,
                end = 12.dp
            ),
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .weight(1f)
                    .padding(end = dimensionResource(R.dimen.padding_medium))
            ) {
                Icon(
                    painter = painterResource(R.drawable.item_icon),
                    tint = Color(0xFF4DC5DD),
                    contentDescription = stringResource(R.string.item_icon),
                    modifier = Modifier
                        .size(20.dp)
                )

                Spacer(Modifier.width(16.dp))

                Box(
                    contentAlignment = Alignment.BottomCenter,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(20.dp) // đảm bảo đủ để hiển thị marquee
                ) {
                    Text(
                        text = text,
                        fontFamily = KufamFont,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp,
                        color = Color(0xFF4DC5DD),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier
                            .basicMarquee(
                                iterations = Int.MAX_VALUE, // lặp vô hạn
                                repeatDelayMillis = 3000,         // nghỉ 3s trước mỗi lần lặp lại
                                initialDelayMillis = 0,     // không delay lần đầu tiên
                                velocity = 30.dp            // tốc độ cuộn, có thể tuỳ chỉnh
                            )
                            .fillMaxWidth()
                    )
                }
            }
            Button(
                elevation = ButtonDefaults.buttonElevation(4.dp),
                onClick = onClick,
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF24D3E3)) ,
                modifier = Modifier
                    .size(120.dp, 30.dp)
                    .padding(start = 8.dp)
            ) {
                Text(
                    text = "Start",
                    color = Color(0xFFFFFFFF),
                    fontFamily = KufamFont,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CoursesPreview() {
//    ExploreRow(
//        containerColor = Color(0xFFE3F2FD),
//        itemColor = Color(0xFF4DC5DD),
//        iconRes = R.drawable.item_icon,
//        iconDes = R.string.foodicon,
//        text = R.string.food,
//        onClick = {}
//    )
//    ReviewScreen()
}