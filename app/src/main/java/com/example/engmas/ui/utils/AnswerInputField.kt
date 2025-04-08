package com.example.engmas.ui.utils

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.engmas.R
import com.example.engmas.ui.screens.challenge.online.ResultState
import com.example.engmas.ui.theme.KufamFont

@Composable
fun AnswerInputField(
    value: String,
    onValueChange: (String) -> Unit,
    resultState: ResultState,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
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
            unfocusedIndicatorColor = when(resultState) {
                ResultState.None -> Color(0xFF96BCFF)
                ResultState.Win -> Color(0xFF09DD30)
                ResultState.Lose -> Color(0xFFDD0909)
            },
            focusedIndicatorColor = when(resultState) {
                ResultState.None -> Color(0xFF96BCFF)
                ResultState.Win -> Color(0xFF09DD30)
                ResultState.Lose -> Color(0xFFDD0909)
            },
        ),
        singleLine = true,
        trailingIcon = {
            when(resultState) {
                ResultState.None -> { }
                ResultState.Win -> {
                    Icon(
                        painter = painterResource(R.drawable.correct_icon),
                        tint = Color(0xFF09DD30),
                        contentDescription = stringResource(R.string.answer_correct),
                        modifier = Modifier.size(dimensionResource(R.dimen.answer_state_icon))
                    )
                }
                ResultState.Lose -> {
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