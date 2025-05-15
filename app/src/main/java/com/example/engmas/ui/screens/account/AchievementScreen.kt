package com.example.engmas.ui.screens.account

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.engmas.R
import com.example.engmas.ui.navigation.NavigationDestination
import com.example.engmas.ui.screens.account.data.LearningBadge
import com.example.engmas.ui.theme.KufamFont
import com.example.engmas.ui.utils.TitleRow

object AccountAchievementDestination: NavigationDestination {
    override val route = "account/achievement"
    override val titleRes = R.string.tab_account_achievement
}

@Composable
fun AchievementScreen(
    viewModel: AccountViewModel = viewModel(),
    onBackClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    val badges = uiState.badges
    val (favouriteBadges, otherBadges) = badges.partition { it.favourite == 1 }
    LaunchedEffect(Unit) {
        viewModel.getUserBadges()
    }
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
                text = "Achievements",
                onClick = onBackClicked
            )

            Spacer(modifier = Modifier.padding(12.dp))

            FavoriteBadges(
                items = favouriteBadges,
                onOptionClicked = { viewModel.updateFavourite(it, 0) }
            )

            AllBadges(
                items = otherBadges,
                onOptionClicked = { viewModel.updateFavourite(it, 1) }
            )

        }
    }
}

@Composable
fun FavoriteBadges(
    items: List<LearningBadge>,
    onOptionClicked: (LearningBadge) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        elevation = CardDefaults.cardElevation(4.dp),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE3FDED)),
        modifier = modifier
            .padding(
                top = dimensionResource(R.dimen.padding_medium),
                start = dimensionResource(R.dimen.padding_medium),
                end = dimensionResource(R.dimen.padding_medium)
            )
        ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_larger)))

            Text(
                text = "Favorite badges",
                fontFamily = KufamFont,
                fontWeight = FontWeight.SemiBold,
                fontSize = 15.sp,
                color = Color.Gray,
                textAlign = TextAlign.Center
            )

            LazyVerticalGrid(
                columns = GridCells.Fixed(2), // 2 cột
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(16.dp) // Padding cho lưới
            ) {
                items(items) { // Số lượng thẻ trong lưới, ở đây là 6 thẻ
                    BadgeCard(
                        isFavourite = true,
                        title = it.badgeType,
                        imagePath = it.imagePath,
                        onOptionClicked = { onOptionClicked(it) }
                    )
                }
            }
        }

    }
}

@Composable
fun AllBadges(
    items: List<LearningBadge>,
    onOptionClicked: (LearningBadge) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        elevation = CardDefaults.cardElevation(4.dp),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE3F2FD)),
        modifier = modifier
            .padding(
                top = dimensionResource(R.dimen.padding_large),
                start = dimensionResource(R.dimen.padding_medium),
                end = dimensionResource(R.dimen.padding_medium),
                bottom = dimensionResource(R.dimen.padding_medium)

            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_larger)))

            Text(
                text = "All badges",
                fontFamily = KufamFont,
                fontWeight = FontWeight.SemiBold,
                fontSize = 15.sp,
                color = Color.Gray,
                textAlign = TextAlign.Center
            )

            LazyVerticalGrid(
                columns = GridCells.Fixed(2), // 2 cột
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(16.dp) // Padding cho lưới
            ) {
                items(items) { // Số lượng thẻ trong lưới, ở đây là 6 thẻ
                    BadgeCard(
                        isFavourite = false,
                        title = it.badgeType,
                        imagePath = it.imagePath,
                        onOptionClicked = { onOptionClicked(it) }
                    )
                }
            }
        }

    }
}

@Composable
fun BadgeCard(
    isFavourite: Boolean,
    title: String,
    imagePath: String,
    onOptionClicked: () -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }
    Card(
        modifier = Modifier
            .padding(8.dp) // Khoảng cách giữa các thẻ
            .fillMaxWidth() // Đảm bảo thẻ chiếm toàn bộ chiều rộng trong mỗi ô
            .border(1.dp, Color(0xFFD3D3D3), RoundedCornerShape(8.dp)), // Viền màu D3D3D3 (màu xám nhạt)
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFFFF)) // Màu nền thẻ
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentHeight()
                .padding(dimensionResource(R.dimen.padding_medium)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End // Căn icon sang bên phải
            ) {
                Column {
                    IconButton(onClick = { expanded = !expanded }, modifier = Modifier.size(14.dp)) {
                        Icon(
                            painter = painterResource(id = R.drawable.more_options_icon),
                            contentDescription = "More options",
                            modifier = Modifier.size(12.dp)
                        )
                    }
                    DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                        DropdownMenuItem(
                            onClick = onOptionClicked,
                            text = {
                                if (!isFavourite) {
                                    Text("Add favourite")
                                } else {
                                    Text("Remove")
                                }
                            }
                        )
                    }
                }
            }


            AsyncImage(
                model = imagePath,
                contentDescription = title,
                modifier = Modifier
                    .size(dimensionResource(R.dimen.image_size))
                    .clip(CircleShape) // Cắt ảnh thành hình tròn
                    .background(Color.Gray) // Màu nền mặc định nếu không có ảnh
            )

            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_medium)))

            // Tiêu đề của thẻ
            Text(
                text = title,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp,
                color = Color.Gray,
                textAlign = TextAlign.Center // Căn giữa văn bản
            )

            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_small)))

        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AchievementScreenPreview() {
//    AchievementScreen(
//
//    )
//    BadgeCard(
//        isFavourite = false,
//        title = "First Course",
//        imagePath = "https://www.dropbox.com/scl/fi/k6nnyqp1yi3sb0tmrs1mf/Course.png?rlkey=t8b54ym8wkwugmj5icarigr70&e=2&st=rqvmjapu&raw=1"
//    )
//    AsyncImage(
//        model = "https://www.dropbox.com/scl/fi/ppmwt7pgj7o2xvkx2vwve/TopicFlashcard.png?rlkey=lwuf6sya5ypcnhnohaydf2hzx&st=mofy2ztb&raw=1",
//        contentDescription = "Dropbox Image",
//        modifier = Modifier.fillMaxWidth(),
////        contentScale = ContentScale.Crop
//    )
}