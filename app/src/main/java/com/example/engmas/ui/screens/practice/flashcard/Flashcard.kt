package com.example.engmas.ui.screens.practice.courses

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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
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
import com.example.engmas.ui.utils.CustomProgressBar

@Composable
fun VocabularyCard(
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
                text = R.string.vocabulary,
                onClick = {  }
            )

            Spacer(modifier = Modifier.padding(4.dp))

            // Phân loại các item theo tiến độ (progress)
            val inProgressItems = items.filter { it.progress > 0 }
            val exploreItems = items.filter { it.progress == 0f }

            // Hiển thị ContainerCard cho các item có tiến độ (InProgress)
            if (inProgressItems.isNotEmpty()) {
                ContainerCard(
                    title = "In Progress",
                    items = inProgressItems,
                    showProgress = true
                )
            }

            // Thêm khoảng cách để phần Explore không bị đè lên
            Spacer(modifier = Modifier.height(8.dp))

            // Hiển thị ContainerCard cho các item chưa bắt đầu (Explore)
            if (exploreItems.isNotEmpty()) {
                ContainerCard(
                    title = "Explore",
                    items = exploreItems,
                    showProgress = false,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                )
            }

            Spacer(modifier = Modifier.padding(12.dp))
        }
    }
}

@Composable
fun ContainerCard(
    title: String,
    items: List<VocabularyItem>,
    showProgress: Boolean,
    modifier: Modifier = Modifier
) {
    Card(
        border = BorderStroke(1.dp, color = Color(0xFFD3D3D3)),
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = modifier
            .fillMaxWidth()
            .padding(12.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Text(
                text = title,
                fontFamily = KufamFont,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                color = Color(0xFF757575),
                modifier = Modifier.padding(top = 10.dp, start = 10.dp, bottom = 10.dp)
            )

            // Hiển thị các item trong LazyColumn
            if (showProgress) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = 300.dp),  // Giới hạn chiều cao tối đa
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(items) { item ->
                        InProgressRow(
                            containerColor = item.containerColor,
                            itemColor = item.itemColor,
                            iconRes = item.iconRes,
                            iconDes = item.iconDes,
                            text = item.text,
                            onClick = item.onClick,
                            progress = item.progress
                        )
                    }
                }
            } else {
                // Hiển thị ExploreRow nếu không có tiến độ
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(items) { item ->
                        ExploreRow(
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
private fun InProgressRow(
    containerColor: Color,
    itemColor: Color,
    @DrawableRes iconRes: Int,
    @StringRes iconDes: Int,
    @StringRes text: Int,
    onClick: () -> Unit,
    progress: Float,
    modifier: Modifier = Modifier
) {
    Card(
        elevation = CardDefaults.cardElevation(4.dp),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = containerColor),
        modifier = modifier
            .height(90.dp)
            .padding(
                top = 8.dp,
                start = 12.dp,
                end = 12.dp
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
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        painter = painterResource(iconRes),
                        tint = itemColor,
                        contentDescription = stringResource(iconDes),
                        modifier = Modifier
                            .size(24.dp)
                    )
                    Text(
                        text = stringResource(text),
                        fontFamily = KufamFont,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp,
                        color = itemColor,
                    )
                    Button(
                        elevation = ButtonDefaults.buttonElevation(4.dp),
                        onClick = {},
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF24D3E3)) ,
                        modifier = Modifier
                            .size(120.dp, 30.dp)
                            .padding(start = 8.dp)
                    ) {
                        Text(
                            text = "Continue",
                            color = Color(0xFFFFFFFF),
                            fontFamily = KufamFont,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                        )
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
                CustomProgressBar(
                    progress = progress,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
private fun ExploreRow(
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
            .height(80.dp)
            .padding(
                top = 8.dp,
                start = 12.dp,
                end = 12.dp
            )
            .clickable { onClick() },
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(iconRes),
                    tint = itemColor,
                    contentDescription = stringResource(iconDes),
                    modifier = Modifier
                        .size(20.dp)
                )
                Spacer(Modifier.width(18.dp))
                Text(
                    text = stringResource(text),
                    fontFamily = KufamFont,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp,
                    color = itemColor,
                )
            }
            Button(
                elevation = ButtonDefaults.buttonElevation(4.dp),
                onClick = {},
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF24D3E3)) ,
                modifier = Modifier
                    .size(120.dp, 30.dp)
                    .padding(start = 8.dp)
            ) {
                Text(
                    text = "Start",
                    color = Color(0xFFFFFFFF),
                    fontFamily = KufamFont,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CoursesPreview() {
//    ExploreRow(
//        containerColor = Color(0xFFE3F2FD),
//        itemColor = Color(0xFF4DC5DD),
//        iconRes = R.drawable.food_icon,
//        iconDes = R.string.foodicon,
//        text = R.string.food,
//        onClick = {}
//    )
    VocabularyCard()
}