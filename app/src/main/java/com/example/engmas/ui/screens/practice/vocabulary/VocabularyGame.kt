package com.example.engmas.ui.screens.practice.vocabulary

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.engmas.R
import com.example.engmas.ui.screens.practice.courses.fakedata.Items
import com.example.engmas.ui.screens.practice.courses.fakedata.VocabularyItem
import com.example.engmas.ui.theme.KufamFont

@Composable
private fun Content(
    modifier: Modifier = Modifier,
    items: List<VocabularyItem> = Items
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
                text = R.string.course1,
                onClick = {  }
            )

            Spacer(modifier = Modifier.padding(20.dp))

            Text(
                text = "What is the meaning of this word?",
                fontFamily = KufamFont,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF757575),
                fontSize = 16.sp
            )

            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.weight(0.5f)
            ) {
                WordCard(
                    text = R.string.vocabulary,
                    onClick = {},
                    itemColor = Color(0xFF2B4EA2),
                    phonetic = R.string.vocabulary,
                    iconDes = R.string.notification_icon,
                    iconRes = R.drawable.vocabulary,
                    containerColor = Color(0xFFE3F2FD),
                    meaning = R.string.vocabulary
                )
            }

            Selection()

            Spacer(modifier = Modifier.padding(12.dp))
        }
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
        ItemCard(cardName = "Vocabulary", modifier = Modifier.weight(1f))
        ItemCard(cardName = "Grammar", modifier = Modifier.weight(1f))
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        ItemCard(cardName = "Flashcard", modifier = Modifier.weight(1f))
        ItemCard(cardName = "Review", modifier = Modifier.weight(1f))
    }
}

@Composable
private fun ItemCard(
    cardName: String,
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
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF757575),
                fontSize = 16.sp
            )
        }
    }
}


@Composable
private fun TitleRow(
    containerColor: Color,
    itemColor: Color,
    @StringRes text: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        elevation = CardDefaults.cardElevation(4.dp),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFFFF)),
        modifier = modifier
            .fillMaxWidth()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(dimensionResource(R.dimen.row_card_size))
                    .background(Color(0xFF49C1D9), MaterialTheme.shapes.medium)
                    .clip(MaterialTheme.shapes.medium)
            ) {
                Card(
                    elevation = CardDefaults.cardElevation(4.dp),
                    shape = MaterialTheme.shapes.medium,
                    colors = CardDefaults.cardColors(containerColor = containerColor),
                    modifier = modifier
                        .fillMaxSize()
                        .height(dimensionResource(R.dimen.option_row_size))
                        .padding(bottom = dimensionResource(R.dimen.padding_smaller))
                        .clickable { onClick() },
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(dimensionResource(R.dimen.padding_medium))
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.goback_icon),
                            tint = itemColor,
                            contentDescription = stringResource(R.string.goback_icon),
                            modifier = Modifier
                                .width(dimensionResource(R.dimen.goto_icon_size))
                                .aspectRatio(1 / 2f)
                        )
                        Spacer(Modifier.width(dimensionResource(R.dimen.option_icon_text_gap)))
                        Text(
                            text = stringResource(text),
                            fontFamily = KufamFont,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 17.sp,
                            textAlign = TextAlign.Center,
                            color = itemColor,
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun WordCard(
    containerColor: Color,
    itemColor: Color,
    @DrawableRes iconRes: Int,
    @StringRes iconDes: Int,
    @StringRes text: Int,
    @StringRes phonetic: Int,
    @StringRes meaning: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        elevation = CardDefaults.cardElevation(4.dp),
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(containerColor = containerColor),
        modifier = modifier
            .fillMaxWidth()
            .padding(dimensionResource(R.dimen.padding_medium))
            .clickable { onClick() },
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.padding_medium))
        ) {

            Spacer(modifier = Modifier.height(30.dp))

            // Tên và phiên âm
            Text(
                text = stringResource(text),
                fontFamily = KufamFont,
                fontWeight = FontWeight.Bold,
                fontSize = 28.sp,
                color = itemColor,
                textAlign = TextAlign.Center,
            )

            Text(
                text = stringResource(phonetic),
                fontFamily = KufamFont,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                color = itemColor,
                textAlign = TextAlign.Center,
            )

            Spacer(modifier = Modifier.height(16.dp))

            IconButton(
                onClick = { /* Play sound */ },
                modifier = Modifier
                    .size(70.dp)
                    .background(Color(0xFFE3F2FD), shape = RoundedCornerShape(50))
                    .padding(8.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.speaker),
                    contentDescription = "Play sound",
                    tint = itemColor
                )
            }

            Spacer(modifier = Modifier.height(22.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun VocabularyGame() {
    Content()
}