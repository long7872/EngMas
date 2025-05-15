package com.example.engmas.ui.screens.account

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.engmas.R
import com.example.engmas.data.model.UserFriend
import com.example.engmas.data.model.UserFriendStatus
import com.example.engmas.ui.navigation.NavigationDestination
import com.example.engmas.ui.screens.account.data.UserFriendResponse
import com.example.engmas.ui.theme.KufamFont
import com.example.engmas.ui.utils.CircleFrame
import com.example.engmas.ui.utils.CustomSearchBar
import com.example.engmas.ui.utils.TitleRow

object AccountFriendDestination: NavigationDestination {
    override val route = "account/friend"
    override val titleRes = R.string.tab_account_friend
}

@Composable
fun FriendScreen(
    viewModel: AccountViewModel = viewModel(),
    onBackClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    val friends = uiState.friends

    LaunchedEffect(Unit) {
        viewModel.loadFriend()
    }
    val userId = viewModel.getUserId()
    val groups = friends.groupBy { it.status }
    val pendingFriend = groups[UserFriendStatus.Pending] ?: emptyList()
    val requestFriend = pendingFriend.filter { it.sender != userId }
    val alreadyFriend = groups[UserFriendStatus.Accepted] ?: emptyList()

    val searchedFriend = uiState.searchedFriends

    var query by remember { mutableStateOf("") }

    LaunchedEffect(query) {
        if (query == "") {
            viewModel.changeSearchScreen(isSearch = false)
        }
    }
    val isSearchScreen = uiState.isSearchScreen

    LaunchedEffect(isSearchScreen) {
        if (!isSearchScreen) {
            viewModel.loadFriend()
        }
    }

    Column {
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
                    text = stringResource(R.string.friend_text),
                    onClick = onBackClicked
                )

                Spacer(modifier = Modifier.padding(12.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                ) {
                    OutlinedTextField(
                        value = query,
                        onValueChange = { query = it },
                        label = { Text("Search...") },
                        placeholder = { Text("Search...") },
                        leadingIcon = {
                            Icon(
                                painter = painterResource(R.drawable.find_icon),
                                contentDescription = stringResource(R.string.search),
                                tint = Color(0xFF1E88E5),
                                modifier = Modifier
                                    .padding(horizontal = dimensionResource(R.dimen.padding_smaller))
                                    .size(dimensionResource(R.dimen.search_icon_size))
                            )
                        },
                        singleLine = true,
                        trailingIcon = {
                            if (query.isEmpty()) {
                                Icon(Icons.Default.Mic, contentDescription = "Microphone")
                            } else {
                                IconButton(onClick = {
                                    query = ""
                                }) {
                                    Icon(Icons.Default.Clear, contentDescription = "Clear")
                                }
                            }
                        },
                        keyboardOptions = KeyboardOptions.Default.copy(
                            imeAction = ImeAction.Done // Đặt IME action là "Done" (nút OK trên bàn phím)
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = {
                                // Khi nhấn nút "OK" hoặc "Done"
                                if (query != "") {
                                    viewModel.changeSearchScreen(isSearch = true)
                                    viewModel.searchFriend(query)
                                }
                            }
                        ),
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(dimensionResource(R.dimen.padding_medium))
                    )

                }

                if (!isSearchScreen) {
                    FriendsRequest(
                        list = requestFriend,
                        onConfirmClick = { viewModel.acceptFriendRequest(it.friendId, UserFriendStatus.Accepted) },
                        onDeleteClick = { viewModel.deleteFriendRequest(it.friendId) }
                    )

                    YourFriends(
                        list = alreadyFriend,
                        onChatClick = {}
                    )
                } else {
                    Card(
                        shape = MaterialTheme.shapes.large,
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFFFF)),
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(dimensionResource(R.dimen.padding_medium))
                            .border(1.dp, Color(0xFFD3D3D3), MaterialTheme.shapes.medium) // Add a thin gray border,
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium))
                        ) {

                            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_smaller)))

                            Text(
                                text = "Results",
                                fontFamily = KufamFont,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 15.sp,
                                color = Color.Gray,
                                textAlign = TextAlign.Center
                            )

                            LazyColumn(
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                items(searchedFriend) {
                                    SearchFriendsCard(
                                        username = it.username,
                                        userImage = it.photoUrl,
                                        status = it.status,
                                        onAddButtonClick = {
                                            viewModel.addFriendRequest(it)
                                        },
                                        onCancelButtonClick = {
                                            val friendId = it.friendId
                                            viewModel.deleteFriendRequest(friendId)
                                        }
                                    )
                                }
                            }
                        }
                    }
                }

            }
        }
    }
}

@Composable
fun FriendsRequest(
    list: List<UserFriendResponse>,
    onConfirmClick: (UserFriendResponse) -> Unit,
    onDeleteClick: (UserFriendResponse) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFFFF)),
        modifier = modifier
            .padding(
                top = dimensionResource(R.dimen.padding_medium),
                start = dimensionResource(R.dimen.padding_medium),
                end = dimensionResource(R.dimen.padding_medium)
            )
            .border(1.dp, Color(0xFFD3D3D3), MaterialTheme.shapes.medium) // Add a thin gray border
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_medium)))

            Text(
                text = "Friends request",
                fontFamily = KufamFont,
                fontWeight = FontWeight.SemiBold,
                fontSize = 15.sp,
                color = Color.Gray,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_medium)))

            LazyColumn(
                modifier = Modifier.fillMaxWidth()

            ) {
                items(list) {
                    FriendRequestCard(
                        userName = it.friendName,
                        userImage = it.friendPhoto, // Sử dụng hình ảnh giả
                        onConfirmClick = { onConfirmClick(it) },
                        onDeleteClick = { onDeleteClick(it) }
                    )
                }
            }
        }

    }
}

@Composable
fun YourFriends(
    list: List<UserFriendResponse>,
    onChatClick: (UserFriendResponse) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFFFF)),
        modifier = modifier
            .padding(
                top = dimensionResource(R.dimen.padding_medium),
                start = dimensionResource(R.dimen.padding_medium),
                end = dimensionResource(R.dimen.padding_medium),
            )
            .border(1.dp, Color(0xFFD3D3D3), MaterialTheme.shapes.medium) // Add a thin gray border
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_medium)))

            Text(
                text = "Your friends",
                fontFamily = KufamFont,
                fontWeight = FontWeight.SemiBold,
                fontSize = 15.sp,
                color = Color.Gray,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_medium)))

            // LazyColumn để hiển thị danh sách yêu cầu kết bạn
            LazyColumn(
                modifier = Modifier.fillMaxWidth()
            ) {
                items(list) {
                    YourFriendsCard(
                        userName = it.friendName,
                        userImage = it.friendPhoto, // Sử dụng hình ảnh giả
                        onChatClick = { onChatClick(it) }  // Truyền hàm xử lý sự kiện nhấn
                    )
                }
            }
        }

    }
}

@Composable
fun FriendRequestCard(
    userName: String = "kierantrinh",
    userImage: String,
    onConfirmClick: () -> Unit,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    Card(
        elevation = CardDefaults.cardElevation(4.dp),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE3F2FD)),
        modifier = modifier
            .padding(
                bottom = dimensionResource(R.dimen.padding_smaller_medium),
                start = dimensionResource(R.dimen.padding_smaller_medium),
                end = dimensionResource(R.dimen.padding_smaller_medium)
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp) // Tạo khoảng cách giữa CircleFrame và Column
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .background(Color.Transparent)
                    .size(dimensionResource(R.dimen.row_card_size))
            ) {
                Image(
                    painter = painterResource(R.drawable.avatar_circle_frame),
                    contentDescription = null
                )
                Image(
                    painter = if (userImage == "") painterResource(R.drawable.avatardefault)
                        else rememberAsyncImagePainter(userImage),
                    contentDescription = stringResource(R.string.avatar),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(dimensionResource(R.dimen.avatar_frame_size))
                        .clip(CircleShape)
                        .padding(dimensionResource(R.dimen.frame_gap_size))
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f), // Column chiếm không gian còn lại
                verticalArrangement = Arrangement.SpaceBetween // Text trên cùng, Row dưới cùng
            ) {
                // Tên người dùng, sát lề trên
                Text(
                    text = userName,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color.Gray,
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Row, sát lề dưới
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp) // Khoảng cách giữa các nút
                ) {
                    Button(
                        onClick = onConfirmClick,
                        modifier = Modifier
                            .height(25.dp),
                        contentPadding = PaddingValues(start = 25.dp, end = 25.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF008CFF) // Sử dụng containerColor thay vì backgroundColor
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = "Confirm",
                            fontSize = 11.sp,
                            textAlign = TextAlign.Center,
                            fontFamily = KufamFont,
                        )
                    }

                    OutlinedButton(
                        onClick = onDeleteClick,
                        modifier = Modifier
                            .height(25.dp),
                        contentPadding = PaddingValues(start = 25.dp, end = 25.dp),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = "Delete",
                            fontSize = 11.sp,
                            textAlign = TextAlign.Start,
                            fontFamily = KufamFont,
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun YourFriendsCard(
    userName: String = "kierantrinh",
    userImage: String,
    onChatClick: () -> Unit, // Thêm tham số onChatClick để xử lý sự kiện nhấn
    modifier: Modifier = Modifier
) {

    Card(
        elevation = CardDefaults.cardElevation(4.dp),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE3F2FD)),
        modifier = modifier
            .padding(
                bottom = dimensionResource(R.dimen.padding_smaller_medium),
                start = dimensionResource(R.dimen.padding_smaller_medium),
                end = dimensionResource(R.dimen.padding_smaller_medium)
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp) // Tạo khoảng cách giữa CircleFrame và Column
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .background(Color.Transparent)
                    .size(dimensionResource(R.dimen.row_card_size))
            ) {
                Image(
                    painter = painterResource(R.drawable.avatar_circle_frame),
                    contentDescription = null
                )
                Image(
                    painter = if (userImage == "") painterResource(R.drawable.avatardefault)
                    else rememberAsyncImagePainter(userImage),
                    contentDescription = stringResource(R.string.avatar),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(dimensionResource(R.dimen.avatar_frame_size))
                        .clip(CircleShape)
                        .padding(dimensionResource(R.dimen.frame_gap_size))
                )
            }

            // Tên người dùng, sát lề trên
            Text(
                text = userName,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = Color.Gray,
            )

            // Căn lề phải cho icon
            Box(modifier = Modifier.fillMaxWidth()) {
                IconButton(
                    onClick = onChatClick, // Xử lý sự kiện khi nhấn vào icon
                    modifier = Modifier
                        .align(Alignment.CenterEnd) // Căn icon sát lề phải
                        .size(
                            dimensionResource(R.dimen.icon_size)
                                    + dimensionResource(R.dimen.padding_small)
                        )
                ) {
                    Icon(
                        painter = painterResource(R.drawable.chat_icon),
                        tint = Color(0xFF2B4EA2),
                        contentDescription = stringResource(R.string.chat),
                        modifier = Modifier.size(dimensionResource(R.dimen.nav_icon_size))
                    )
                }
            }

        }
    }
}

@Composable
fun SearchFriendsCard(
    username: String,
    userImage: String,
    status: UserFriendStatus,
    onAddButtonClick: () -> Unit,
    onCancelButtonClick: () -> Unit
) {
    Card(
        elevation = CardDefaults.cardElevation(4.dp),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE3F2FD)),
        modifier = Modifier
            .padding(
                bottom = dimensionResource(R.dimen.padding_smaller_medium),
                start = dimensionResource(R.dimen.padding_smaller_medium),
                end = dimensionResource(R.dimen.padding_smaller_medium)
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .background(Color.Transparent)
                    .size(dimensionResource(R.dimen.row_card_size))
            ) {
                Image(
                    painter = painterResource(R.drawable.avatar_circle_frame),
                    contentDescription = null
                )
                Image(
                    painter = if (userImage == "") painterResource(R.drawable.avatardefault)
                    else rememberAsyncImagePainter(userImage),
                    contentDescription = stringResource(R.string.avatar),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(dimensionResource(R.dimen.avatar_frame_size))
                        .clip(CircleShape)
                        .padding(dimensionResource(R.dimen.frame_gap_size))
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = username,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color.Gray,
                )

                Spacer(modifier = Modifier.height(13.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Button(
                            onClick = {
                                if (status != UserFriendStatus.Pending) {
                                    onAddButtonClick()
                                } else {
                                    onCancelButtonClick()
                                }
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (status == UserFriendStatus.Pending) Color(0xFFE14646) else Color(
                                    0xFF008CFF
                                )
                            ),
                            modifier = Modifier
                                .fillMaxWidth() // Thêm fillMaxWidth để Button chiếm toàn bộ chiều rộng
                                .height(25.dp),
                            contentPadding = PaddingValues(horizontal = 12.dp),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Icon(
                                    imageVector = if (status == UserFriendStatus.Pending) Icons.Default.Clear else Icons.Default.Add,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(16.dp)
                                )
                                Text(
                                    text = if (status == UserFriendStatus.Pending) "Cancel request" else "Add friend",
                                    color = Color.White,
                                    fontFamily = KufamFont,
                                    fontSize = 12.sp
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
private fun FriendScreenPreview() {
//    FriendScreen(
//
//    )
}