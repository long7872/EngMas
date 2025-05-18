package com.example.engmas.ui.screens.practice

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.engmas.R
import com.example.engmas.data.model.Course
import com.example.engmas.ui.navigation.NavigationDestination
import com.example.engmas.ui.theme.EngMasTheme
import com.example.engmas.ui.theme.KufamFont
import com.example.engmas.ui.utils.CourseFrame

object PracticeDestination: NavigationDestination {
    override val route = "practice"
    override val titleRes = R.string.tab_practice
}

@Composable
fun PracticeScreen(
    onVocabularyClicked: () -> Unit,
    onGrammarClicked: () -> Unit,
    onFlashCardClicked: () -> Unit,
    onReviewClicked: () -> Unit,
    onCourseClicked: (Int) -> Unit,
    onVoiceClicked: () -> Unit,
    viewModel: PracticeViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.getCourses()
    }
    val courseList = uiState.courseList

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
            modifier = Modifier.fillMaxWidth()
                .padding(top = 20.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Courses(
                items = courseList,
                onCourseClicked = onCourseClicked
            )

            Selection(
                onVocabularyClicked = onVocabularyClicked,
                onGrammarClicked = onGrammarClicked,
                onFlashCardClicked = onFlashCardClicked,
                onReviewClicked = onReviewClicked
            )
            Pronunciation(
                onClicked = onVoiceClicked
            )
        }
    }
}



@Composable
private fun Courses(
    items: List<Course>,
    onCourseClicked: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        elevation = CardDefaults.cardElevation(4.dp),
        shape = MaterialTheme.shapes.small,
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFFFF)),
        modifier = modifier
            .fillMaxWidth()
            .height(210.dp)
            .padding(dimensionResource(R.dimen.padding_medium)),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 20.dp,
                    top = 20.dp,
                    bottom = 20.dp
                )
        ) {
            Text(
                text = "Courses",
                fontFamily = KufamFont,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF757575),
                fontSize = 17.sp
            )
            LazyHorizontalGrid(
                rows = GridCells.Fixed(1),
                modifier = Modifier.padding(top = 8.dp, end = 28.dp)
            ) {
                items(items) { item ->
                    CourseButton(
                        courseItem = item,
                        onCourseClicked = onCourseClicked
                    )
                }
            }
        }
    }
}

@Composable
private fun CourseButton(
    courseItem: Course,
    onCourseClicked: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.padding(
            start = 8.dp,
            end = 8.dp
        ).size(80.dp, 100.dp)
            .clickable { onCourseClicked(courseItem.id) }
    ) {
        CourseFrame(
            avatarRes = R.drawable.course_pic,
            frameSize = 80.dp
        )
        Text(
            text = courseItem.name,
            fontFamily = KufamFont,
            fontWeight = FontWeight.Medium,
            color = Color(0xFF757575),
            fontSize = 10.sp,
            modifier = Modifier
                .fillMaxWidth() // hoặc dùng weight nếu có sibling
                .basicMarquee(
                    iterations = Int.MAX_VALUE,
                    repeatDelayMillis = 3000,
                    initialDelayMillis = 0,
                    velocity = 30.dp
                )
        )
    }
}



@Composable
private fun Selection(
    onVocabularyClicked: () -> Unit,
    onGrammarClicked: () -> Unit,
    onFlashCardClicked: () -> Unit,
    onReviewClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(start = 12.dp, end = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        ItemCard(
            cardName = "Vocabulary",
            itemRes = R.drawable.vocabulary_icon,
            onClick = onVocabularyClicked,
            modifier = Modifier.weight(1f))
        ItemCard(
            cardName = "Grammar",
            itemRes = R.drawable.grammar_icon,
            onClick = onGrammarClicked,
            modifier = Modifier.weight(1f))
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        ItemCard(
            cardName = "Flashcard",
            itemRes = R.drawable.flashcard_icon,
            onClick = onFlashCardClicked,
            modifier = Modifier.weight(1f))
        ItemCard(
            cardName = "Review",
            itemRes = R.drawable.review_icon,
            onClick = onReviewClicked,
            modifier = Modifier.weight(1f))
    }
}

@Composable
private fun ItemCard(
    cardName: String,
    itemRes: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        elevation = CardDefaults.cardElevation(4.dp),
        shape = MaterialTheme.shapes.small,
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = modifier
            .size(150.dp)
            .padding(4.dp)
            .clickable { onClick() }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = modifier.fillMaxSize()
        ) {
            Text(
                text = cardName,
                fontFamily = KufamFont,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF757575),
                fontSize = 18.sp
            )
            Spacer(modifier = Modifier.padding(4.dp))
            Icon(
                painter = painterResource(itemRes),
                contentDescription = null,
                modifier = Modifier.size(40.dp),
                tint = Color(0xFF81D7FF)
            )
            Spacer(modifier = Modifier.padding(8.dp))
        }
    }
}



@Composable
private fun Pronunciation(
    onClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        elevation = CardDefaults.cardElevation(4.dp),
        shape = MaterialTheme.shapes.small,
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFFFF)),
        modifier = modifier
            .fillMaxWidth()
            .height(180.dp)
            .padding(
                top = 8.dp,
                bottom = 16.dp,
                start = 16.dp,
                end = 16.dp
            ).clickable { onClicked() },
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.mic_icon),
                contentDescription = null,
                modifier = Modifier.size(110.dp).padding( top = 16.dp, bottom = 16.dp),
                tint = Color(0xFF81D7FF)
            )

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(end = 8.dp)
            ) {
                Text(
                    text = "Pronunciation Practice With EngMas AI",
                    fontFamily = KufamFont,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF757575),
                    fontSize = 15.sp,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.padding(4.dp))
                Button(
                    onClick = onClicked,
                    colors = ButtonDefaults.buttonColors(Color(0xAB24D3E3)),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.size(180.dp, 40.dp)
                ) {
                    Text(
                        text = "Explore",
                        color = Color.White,
                        fontFamily = KufamFont,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//private fun PracticeScreen1Preview() {
//    EngMasTheme {
//        Pronunciation()
//    }
//}

@Preview(showBackground = true)
@Composable
private fun PracticeScreenPreview() {
    EngMasTheme {
        PracticeScreen(
            onVocabularyClicked = {},
            onGrammarClicked = {},
            onFlashCardClicked = {},
            onReviewClicked = {},
            onCourseClicked = {},
            onVoiceClicked = {}
        )
    }
}