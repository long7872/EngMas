package com.example.engmas.ui.screens.practice.courses

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.example.engmas.R
import com.example.engmas.ui.screens.practice.courses.data.QuestionGrammarItem
import com.example.engmas.ui.screens.practice.courses.data.QuestionState
import com.example.engmas.ui.theme.KufamFont
import com.example.engmas.ui.utils.CustomButton
import com.example.engmas.ui.utils.TitleRow

@Composable
fun CourseGrammar(
    title: String,
    item: QuestionGrammarItem,
    onCorrect: (QuestionGrammarItem ,Boolean) -> Unit,
    onBackClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val sentence = item.question
    val modifiedSentence = sentence.replace("[answer]", "...")
    var questionState by remember { mutableStateOf(QuestionState.Normal) }
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
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TitleRow(
                    containerColor = Color(0xFFE3F2FD),
                    itemColor = Color(0xFF757575),
                    text = title,
                    onClick = onBackClicked
                )

                Spacer(modifier = Modifier.padding(20.dp))

                Text(
                    text = "Fill in the blank to answer the question",
                    fontFamily = KufamFont,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF757575),
                    fontSize = 16.sp
                )
            }

            GrammarCard(
                question = modifiedSentence,
                questionState = questionState,
                onSubmitButton = { answer ->
                    if (answer == item.answer) {
                        questionState = QuestionState.Correct
                        onCorrect(item, true)
                    } else {
                        questionState = QuestionState.Wrong
                        onCorrect(item, false)
                    }
                },
            )

            Spacer(modifier = Modifier.padding(12.dp))
        }
    }
}

@Composable
private fun GrammarCard(
    question: String,
    questionState: QuestionState,
    onSubmitButton: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var answer by remember { mutableStateOf("") }
    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .imePadding()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = question,
                fontFamily = KufamFont,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color(0xFF4DC5DD),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(top = dimensionResource(R.dimen.button_horizontal_padding))
            )

            Spacer(Modifier.height(dimensionResource(R.dimen.padding_small)))

            OutlinedTextField(
                value = answer,
                onValueChange = { answer = it },
                placeholder = {
                    Text(
                        text = stringResource(R.string.placeholder_answer_unscramble),
                        fontFamily = KufamFont,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp,
                        color = Color(0x80757575),
                        textAlign = TextAlign.Start,
                    )
                },
                textStyle = TextStyle(
                    fontFamily = KufamFont,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp,
                    color = Color(0xFF757575),
                    textAlign = TextAlign.Start,
                ),
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = dimensionResource(R.dimen.padding_large)),
                shape = MaterialTheme.shapes.medium,
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White,
                    unfocusedIndicatorColor = when(questionState) {
                        QuestionState.Normal -> Color(0xFF96BCFF)
                        QuestionState.Correct -> Color(0xFF09DD30)
                        QuestionState.Wrong -> Color(0xFFDD0909)
                    },
                    focusedIndicatorColor = when(questionState) {
                        QuestionState.Normal -> Color(0xFF96BCFF)
                        QuestionState.Correct -> Color(0xFF09DD30)
                        QuestionState.Wrong -> Color(0xFFDD0909)
                    },
                ),
                singleLine = true,
                trailingIcon = {
                    when(questionState) {
                        QuestionState.Normal -> { }
                        QuestionState.Correct -> {
                            Icon(
                                painter = painterResource(R.drawable.correct_icon),
                                tint = Color(0xFF09DD30),
                                contentDescription = stringResource(R.string.answer_correct),
                                modifier = Modifier.size(dimensionResource(R.dimen.answer_state_icon))
                            )
                        }
                        QuestionState.Wrong -> {
                            Icon(
                                painter = painterResource(R.drawable.wrong_icon),
                                tint = Color(0xFFDD0909),
                                contentDescription = stringResource(R.string.answer_wrong),
                                modifier = Modifier.size(dimensionResource(R.dimen.answer_state_icon))
                            )
                        }
                    }
                }
            )
        }

        Row(
            horizontalArrangement = Arrangement.Center
        ) {
            CustomButton(
                text = "Submit",
                fontSize = 14.sp,
                color = Color(0xFF09DD30),
                onClick = { onSubmitButton(answer) },
                modifier = Modifier.weight(0.5f)
                    .padding(
                        start = dimensionResource(R.dimen.button_horizontal_padding) / 2,
                        top = dimensionResource(R.dimen.button_horizontal_padding),
                        bottom = dimensionResource(R.dimen.button_horizontal_padding),
                        end = dimensionResource(R.dimen.button_horizontal_padding) /2
                    )
                    .height(dimensionResource(R.dimen.button_size))
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CourseGrammarPreview() {
    val sentence = "We [answer] (fly) to Sydney."
    CourseGrammar(
        title = sentence,
        item = QuestionGrammarItem(question = sentence),
        onCorrect = {_,_ ->},
        onBackClicked = {}
    )
}