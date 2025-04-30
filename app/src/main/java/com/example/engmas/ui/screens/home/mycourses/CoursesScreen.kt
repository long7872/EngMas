package com.example.engmas.ui.screens.home.mycourses

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
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
fun Content(
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

            Spacer(modifier = Modifier.padding(4.dp))

            ItemGrid(
                items = items,
                modifier = Modifier.weight(1f)
            )

            OutlinedButton(
                onClick = {},
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
                onClick = {},
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
fun ItemGrid(
    items: List<VocabularyItem>,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(1),
        modifier = modifier.fillMaxSize()
    ) {
        items(items) { item ->
            ContentRow(
                containerColor = item.containerColor,
                itemColor = item.itemColor,
                iconRes = item.iconRes,
                iconDes = item.iconDes,
                text = item.text,
                onClick = item.onClick
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
                            contentDescription = stringResource(R.string.notification_icon),
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
private fun ContentRow(
    containerColor: Color,
    itemColor: Color,
    @DrawableRes iconRes: Int,
    @StringRes iconDes: Int,
    @StringRes text: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        elevation = CardDefaults.cardElevation(4.dp),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = containerColor),
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
                    painter = painterResource(iconRes),
                    tint = itemColor,
                    contentDescription = stringResource(iconDes),
                    modifier = Modifier
                        .fillMaxHeight()
                )
                Spacer(Modifier.width(dimensionResource(R.dimen.option_icon_text_gap)))
                Text(
                    text = stringResource(text),
                    fontFamily = KufamFont,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 15.sp,
                    textAlign = TextAlign.Center,
                    color = itemColor,
                )
            }
            Icon(
                painter = painterResource(R.drawable.more_options_icon),
                tint = itemColor,
                contentDescription = stringResource(R.string.moreoptions),
                modifier = Modifier
                    .width(dimensionResource(R.dimen.goto_icon_size))
                    .aspectRatio(1 / 2f)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CoursesPreview() {
    Content()
}