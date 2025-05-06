package com.example.engmas.ui.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.engmas.R
import com.example.engmas.ui.theme.KufamFont

@Composable
fun TitleRow(
    containerColor: Color,
    itemColor: Color,
    text: String,
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
                            text = text,
                            fontFamily = KufamFont,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 17.sp,
                            textAlign = TextAlign.Start,
                            color = itemColor,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
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
            }
        }
    }
}