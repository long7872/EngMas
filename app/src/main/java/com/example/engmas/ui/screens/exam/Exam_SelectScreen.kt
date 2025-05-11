package com.example.engmas.ui.screens.exam

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.engmas.R
import com.example.engmas.ui.theme.KufamFont
import com.example.engmas.ui.utils.CustomButton

@Composable
fun Exam_SelectScreen(
    title: String,
    onStartClicked: (List<Boolean>) -> Unit,
    modifier: Modifier = Modifier
) {
    val partStates = remember { mutableStateOf(List(7) { true }) }
    val updatePartState: (Int, Boolean) -> Unit = { index, isChecked ->
        partStates.value = partStates.value.toMutableList().apply { this[index] = isChecked }
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
            verticalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxSize()
        ) {
            Title_Subtitle(
                title = title,
                modifier = Modifier.fillMaxWidth()
            )

            ChoosePart(
                partStates = partStates.value,
                onPartStateChange = updatePartState
            )

            StartButton(
                onClick = { onStartClicked(partStates.value) },

            )
        }
    }
}

@Composable
private fun Title_Subtitle(
    title: String,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Text(
            text = title,
            fontFamily = KufamFont,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            color = Color(0xFF757575),
//                modifier = Modifier.padding(vertical = dimensionResource(R.dimen.padding_smaller))
        )
//        Text(
//            text = stringResource(R.string.completed_count, 1000),
//            fontFamily = KufamFont,
//            fontWeight = FontWeight.Medium,
//            fontSize = 14.sp,
//            color = Color(0xFF757575),
////                modifier = Modifier.padding(vertical = dimensionResource(R.dimen.padding_smaller))
//        )
    }
}

@Composable
private fun ChoosePart(
    partStates: List<Boolean>,
    onPartStateChange: (Int, Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Text(
            text = stringResource(R.string.exam_guide, 1000),
            fontFamily = KufamFont,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            color = Color(0xFF757575),
//                modifier = Modifier.padding(vertical = dimensionResource(R.dimen.padding_smaller))
        )

        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_larger)))

        Text(
            text = stringResource(R.string.listening_part),
            style = TextStyle(
                color = Color(0xFF757575),
                fontFamily = KufamFont,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            ),
            modifier = Modifier.padding(start = dimensionResource(R.dimen.padding_medium))
                .align(Alignment.Start)
        )
        PartRow(partNumber = 1, numOfQuestion = 6, checked = partStates[0]) { isChecked ->
            onPartStateChange(0, isChecked)
        }
        PartRow(partNumber = 2, numOfQuestion = 25, checked = partStates[1]) { isChecked ->
            onPartStateChange(1, isChecked)
        }
        PartRow(partNumber = 3, numOfQuestion = 39, checked = partStates[2]) { isChecked ->
            onPartStateChange(2, isChecked)
        }
        PartRow(partNumber = 4, numOfQuestion = 30, checked = partStates[3]) { isChecked ->
            onPartStateChange(3, isChecked)
        }

        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_larger)))

        Text(
            text = stringResource(R.string.reading_part),
            style = TextStyle(
                color = Color(0xFF757575),
                fontFamily = KufamFont,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            ),
            modifier = Modifier.padding(start = dimensionResource(R.dimen.padding_medium))
                .align(Alignment.Start)
        )
        PartRow(partNumber = 5, numOfQuestion = 30, checked = partStates[4]) { isChecked ->
            onPartStateChange(4, isChecked)
        }
        PartRow(partNumber = 6, numOfQuestion = 16, checked = partStates[5]) { isChecked ->
            onPartStateChange(5, isChecked)
        }
        PartRow(partNumber = 7, numOfQuestion = 54, checked = partStates[6]) { isChecked ->
            onPartStateChange(6, isChecked)
        }
    }
}

@Composable
private fun PartRow(
    partNumber: Int,
    numOfQuestion: Int,
    checked: Boolean,
    modifier: Modifier = Modifier,
    onCheckedChange: (Boolean) -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        IconToggleButton(
            checked = checked,
            onCheckedChange = onCheckedChange
        ) {
            Icon(
                imageVector = if (checked) Icons.Default.Check else Icons.Default.Close,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier
                    .background(
                        color = if (checked) Color(0xFF24D3E3) else Color.Gray,
                        shape = MaterialTheme.shapes.extraSmall
                    ).size(dimensionResource(R.dimen.checkbox_button_size))
            )
        }

//        Spacer(modifier = Modifier.width(2.dp))

        Text(
            text = stringResource(R.string.part_text_choose, partNumber, numOfQuestion),
            style = TextStyle(
                color = Color(0xFF757575),
                fontFamily = KufamFont,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            ),
            modifier = Modifier.padding(top = dimensionResource(R.dimen.padding_smaller))
        )
    }
}

@Composable
private fun StartButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
    ) {
        Spacer(modifier = Modifier.weight(1/5f))
        CustomButton(
            text = "Start",
            fontSize = 11.sp,
            color = Color(0xFF24D3E3),
            onClick = onClick,
            modifier = Modifier.weight(4/5f)
                .height(dimensionResource(R.dimen.button_size))
        )
        Spacer(modifier = Modifier.weight(1/5f))
    }
}

@Preview(showBackground = true)
@Composable
private fun Exam_SelectScreenPreview() {
//    Exam_SelectScreen()
}