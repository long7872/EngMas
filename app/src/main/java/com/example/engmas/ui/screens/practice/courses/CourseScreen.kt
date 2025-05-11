package com.example.engmas.ui.screens.practice.courses

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import androidx.compose.ui.graphics.Color
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
import com.example.engmas.ui.navigation.NavigationDestination
import com.example.engmas.ui.screens.practice.courses.data.QuestionGrammarItem
import com.example.engmas.ui.screens.practice.courses.data.QuestionVocabItem
import com.example.engmas.ui.screens.practice.vocabulary.model.VocabLearningInTopic
import com.example.engmas.ui.theme.KufamFont
import com.example.engmas.ui.utils.TitleRow

@Composable
fun CourseScreen(
    courseName: String,
    listVocab: List<QuestionVocabItem>,
    listGrammar: List<QuestionGrammarItem>,
    onVocabClicked: (QuestionVocabItem) -> Unit,
    onGrammarClicked: (QuestionGrammarItem) -> Unit,
    onMarkAllClicked: () -> Unit,
    onStartLearningClicked: () -> Unit,
    onBackClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {

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
                text = courseName,
                onClick = onBackClicked
            )

            Spacer(modifier = Modifier.padding(4.dp))

            ItemGrid(
                vocabItems = listVocab,
                grammarItems = listGrammar,
                onVocabClicked = onVocabClicked,
                onGrammarClicked = onGrammarClicked
            )

            OutlinedButton(
                onClick = onMarkAllClicked,
                border = BorderStroke(1.dp, Color(0xFFD3D3D3)),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = dimensionResource(R.dimen.padding_smaller_medium),
                        start = dimensionResource(R.dimen.padding_smaller_medium),
                        end = dimensionResource(R.dimen.padding_smaller_medium),
                        bottom = 2.dp
                    )
            ) {
                Text(
                    text = "Mark all as known",
                    color = Color(0xFF757575),
                    fontFamily = KufamFont,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                )
            }

            Button(
                elevation = ButtonDefaults.buttonElevation(4.dp),
                onClick = onStartLearningClicked,
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF24D3E3)) ,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = dimensionResource(R.dimen.padding_smaller_medium),
                        end = dimensionResource(R.dimen.padding_smaller_medium)
                    )
            ) {
                Text(
                    text = "Start Learning",
                    color = Color(0xFFFFFFFF),
                    fontFamily = KufamFont,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                )
            }
            Spacer(modifier = Modifier.padding(12.dp))
        }
    }
}

@Composable
private fun ItemGrid(
    vocabItems: List<QuestionVocabItem>,
    grammarItems: List<QuestionGrammarItem>,
    onVocabClicked: (QuestionVocabItem) -> Unit,
    onGrammarClicked: (QuestionGrammarItem) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(1),
        modifier = modifier.fillMaxSize()
    ) {
        items(vocabItems) { item ->
            val formattedWord = item.word.replaceFirstChar { it.uppercase() }
            ContentRow(
                text = formattedWord,
                onClick = { onVocabClicked(item) }
            )
        }
        items(grammarItems) { item ->
            val formattedWord = item.grammarName.replaceFirstChar { it.uppercase() }
            ContentRow(
                text = formattedWord,
                onClick = { onGrammarClicked(item) }
            )
        }
    }
}

@Composable
private fun ContentRow(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        elevation = CardDefaults.cardElevation(4.dp),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE3F2FD)),
        modifier = modifier
            .height(dimensionResource(R.dimen.option_row_size))
            .padding(
                top = dimensionResource(R.dimen.padding_smaller_medium),
                start = dimensionResource(R.dimen.padding_smaller_medium),
                end = dimensionResource(R.dimen.padding_smaller_medium)
            )
            .clickable { onClick() },
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxSize()
                .padding(dimensionResource(R.dimen.padding_medium))
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(R.drawable.item_icon),
                    tint = Color(0xFF4DC5DD),
                    contentDescription = stringResource(R.string.item_icon),
                    modifier = Modifier
                        .fillMaxHeight()
                )
                Spacer(Modifier.width(dimensionResource(R.dimen.option_icon_text_gap)))
                Text(
                    text = text,
                    fontFamily = KufamFont,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 15.sp,
                    textAlign = TextAlign.Center,
                    color = Color(0xFF4DC5DD),
                )
            }
//            Icon(
//                painter = painterResource(R.drawable.more_options_icon),
//                tint = Color(0xFF4DC5DD),
//                contentDescription = stringResource(R.string.moreoptions),
//                modifier = Modifier
//                    .width(dimensionResource(R.dimen.goto_icon_size))
//                    .aspectRatio(1 / 2f)
//            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CoursesPreview() {
//    Content()
}