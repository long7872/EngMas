package com.example.engmas.ui.utils

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.engmas.R
import com.example.engmas.ui.screens.challenge.online.ResultState
import com.example.engmas.ui.theme.KufamFont

@Composable
fun UnscrambleGameContent(
    unscrambleWord: String,
    resultState: ResultState,
    onSkipButton: () -> Unit,
    onSubmitButton: () -> Unit,
    modifier: Modifier = Modifier
) {

    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .imePadding()
    ) {
        TimeBar(
            onFinish = {  },
            durationMillis = 30_000,
            modifier = Modifier.fillMaxWidth()
                .padding(top = dimensionResource(R.dimen.button_horizontal_padding))
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = stringResource(R.string.play_guide),
                fontFamily = KufamFont,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = Color(0xFF757575),
                textAlign = TextAlign.Center,
            )
            Text(
                text = unscrambleWord.toCharArray().joinToString(" "),
                fontFamily = KufamFont,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                color = Color(0xFFFF0000),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(top = dimensionResource(R.dimen.button_horizontal_padding))
            )
            AnswerInputField(
                value = "",
                onValueChange =  {  },
                resultState = resultState,
                modifier = Modifier
                    .padding(top = dimensionResource(R.dimen.text_field_vertical_padding))
            )
        }

        Row(
            horizontalArrangement = Arrangement.Center
        ) {
            CustomButton(
                text = "Skip",
                fontSize = 14.sp,
                color = Color(0xFFDD0909),
                onClick = onSkipButton,
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
                text = "Submit",
                fontSize = 14.sp,
                color = Color(0xFF09DD30),
                onClick = onSubmitButton,
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