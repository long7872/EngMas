package com.example.engmas.ui.screens.practice

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.example.engmas.R
import com.example.engmas.ui.navigation.NavigationDestination
import com.example.engmas.ui.screens.practice.courses.fakedata.CourseItem
import com.example.engmas.ui.theme.EngMasTheme
import com.example.engmas.ui.theme.KufamFont
import com.example.engmas.ui.utils.CourseFrame

object PracticeDestination: NavigationDestination {
    override val route = "practice"
    override val titleRes = R.string.tab_practice
}

@Composable
fun PracticeScreen(
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
            modifier = Modifier.fillMaxWidth().padding(top = 20.dp)
        ) {
            Courses(
                items = listOf(
                    CourseItem("Course 1"),
                    CourseItem("Course 2"),
                    CourseItem("Course 3"),
                    CourseItem("Course 4"),
                    CourseItem("Course 3"),
                    CourseItem("Course 3"),
                    CourseItem("Course 3"),
                )
            )

            Selection()
            Pronunciation()
        }
    }
}



@Composable
private fun Courses(
    items: List<CourseItem>,
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
                    Course(courseItem = item)
                }
            }
        }
    }
}

@Composable
private fun Course(
    courseItem: CourseItem,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.padding(
            start = 8.dp,
            end = 8.dp
        )
    ) {
        CourseFrame(
            avatarRes = courseItem.avatarRes,
            frameSize = 80.dp
        )
        Text(
            text = courseItem.name,
            fontFamily = KufamFont,
            fontWeight = FontWeight.Medium,
            color = Color(0xFF757575),
            fontSize = 10.sp
        )
    }
}



@Composable
private fun Selection(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(start = 12.dp, end = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        ItemCard(cardName = "Vocabulary", itemRes = R.drawable.vocabulary_icon, modifier = Modifier.weight(1f))
        ItemCard(cardName = "Grammar", itemRes = R.drawable.grammar_icon, modifier = Modifier.weight(1f))
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        ItemCard(cardName = "Flashcard", itemRes = R.drawable.flashcard_icon, modifier = Modifier.weight(1f))
        ItemCard(cardName = "Review", itemRes = R.drawable.review_icon, modifier = Modifier.weight(1f))
    }
}

@Composable
private fun ItemCard(
    cardName: String,
    itemRes: Int,
    modifier: Modifier = Modifier
) {
    Card(
        elevation = CardDefaults.cardElevation(4.dp),
        shape = MaterialTheme.shapes.small,
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = modifier
            .size(150.dp)
            .padding(4.dp)
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
            ),
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
                    onClick = {},
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
        PracticeScreen()
    }
}