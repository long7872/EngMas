package com.example.engmas.ui.screens.account

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.engmas.R
import com.example.engmas.data.model.User
import com.example.engmas.ui.navigation.NavigationDestination
import com.example.engmas.ui.theme.KufamFont
import com.example.engmas.ui.utils.CircleFrame
import com.example.engmas.ui.utils.TitleRow

object AccountInformationDestination: NavigationDestination {
    override val route = "account/information"
    override val titleRes = R.string.tab_account_information
}

@Composable
fun InformationScreen(
    onBackClicked: () -> Unit,
    viewModel: AccountViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()
    val user = uiState.user
    LaunchedEffect(Unit) {
        viewModel.getUser()
    }

    var imageUri by remember { mutableStateOf<Uri?>(null) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
        uri?.let {
            viewModel.uploadImage(context, it)
        }
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
                text = "Personal Information",
                onClick = onBackClicked
            )

            Spacer(modifier = Modifier.padding(12.dp))

            PersonalContent(
                user = user,
                onAvatarClicked = { launcher.launch("image/*") },
                onCancelClicked = onBackClicked,
                onSaveClicked = {
                    viewModel.updateUser(it)
                    onBackClicked()
                },
            )

        }
    }
}

@Composable
fun PersonalContent(
    user: User,
    onAvatarClicked: () -> Unit,
    onCancelClicked: () -> Unit,
    onSaveClicked: (User) -> Unit,
    modifier: Modifier = Modifier
) {
    var name by remember { mutableStateOf("") }
    var dob by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }

    LaunchedEffect(user) {
        name = user.name
        dob = user.doB
        email = user.email
        phone = user.phoneNumber
    }

    Column(
        modifier = Modifier
            .padding(dimensionResource(R.dimen.padding_medium)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_large))

    ) {
        AvatarCard(
            userImage = user.photoUrl,
            username = user.username,
            onAvatarClicked = onAvatarClicked
        )

        Spacer(modifier = Modifier.height(1.dp))

        UserInfoCardList(
            name = name,
            onNameChange = { name = it },
            dateOfBirth = dob,
            onDateChange = { dob = it },
            email = email,
            onEmailChange = { email = it },
            phoneNumber = phone,
            onPhoneChange = { phone = it }
        )

        Spacer(modifier = Modifier.height(1.dp))

        Row {
            OutlinedButton(
                onClick = onCancelClicked,
                border = BorderStroke(1.dp, Color(0xFFD3D3D3)),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .padding(
                        start = dimensionResource(R.dimen.padding_smaller_medium),
                        end = dimensionResource(R.dimen.padding_smaller_medium),
                        bottom = 2.dp
                    )
                    .weight(4f)
            ) {
                Text(
                    text = "Cancel",
                    color = Color(0xFF757575),
                    fontFamily = KufamFont,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    val updatedUser = user.copy(
                        name = name,
                        doB = dob,
                        email = email,
                        phoneNumber = phone
                    )
                    onSaveClicked(updatedUser)
                },
                elevation = ButtonDefaults.buttonElevation(4.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF24D3E3)) ,
                modifier = Modifier
                    .padding(
                        start = dimensionResource(R.dimen.padding_smaller_medium),
                        end = dimensionResource(R.dimen.padding_smaller_medium)
                    )
                    .weight(4f)
            ) {
                Text(
                    text = "Save",
                    color = Color(0xFFFFFFFF),
                    fontFamily = KufamFont,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                )
            }
        }

    }
}

@Composable
fun AvatarCard(
    userImage: String,
    username: String,     // Tên người dùng
    onAvatarClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {

        Card(
            elevation = CardDefaults.cardElevation(4.dp),
            shape = MaterialTheme.shapes.medium,
            colors = CardDefaults.cardColors(containerColor = Color(0xFFE3F2FD)),

            ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally // Canh giữa nội dung
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .background(Color.Transparent)
                        .size(dimensionResource(R.dimen.row_card_size))
                        .clickable { onAvatarClicked() }
                ) {
                    Image(
                        painter = painterResource(R.drawable.avatar_circle_frame),
                        contentDescription = null
                    )
                    Image(
                        painter = if (userImage == "") painterResource(R.drawable.avatardefault)
                        else rememberAsyncImagePainter(userImage),
                        contentScale = ContentScale.Crop,
                        contentDescription = stringResource(R.string.avatar),
                        modifier = Modifier
                            .size(dimensionResource(R.dimen.avatar_frame_size))
                            .clip(CircleShape)
                            .padding(dimensionResource(R.dimen.frame_gap_size))
                    )
                }

                Spacer(modifier = Modifier.height(8.dp)) // Khoảng cách giữa avatar và tên

                Text(
                    text = username,
                    fontFamily = KufamFont, // Nếu bạn có font, nếu không thì bỏ qua
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp,
                    color = Color(0xFFFED017), // Màu vàng cho tên
                    textAlign = TextAlign.Center
                )

            }
        }



}

@Composable
fun UserInfoCardList(
    name: String,
    onNameChange: (String) -> Unit,
    dateOfBirth: String,
    onDateChange: (String) -> Unit,
    email: String,
    onEmailChange: (String) -> Unit,
    phoneNumber: String,
    onPhoneChange: (String) -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium))
    ) {
        InfoCard(
            label = "Name",
            value = name,
            onValueChange = onNameChange
        )
        InfoCard(
            label = "Date of birth",
            value = dateOfBirth,
            onValueChange = onDateChange
        )
        InfoCard(
            label = "Email",
            value = email,
            onValueChange = onEmailChange
        )
        InfoCard(
            label = "Phone number",
            value = phoneNumber,
            onValueChange = onPhoneChange
        )
    }
}


@Composable
fun InfoCard(
    label: String,  // Tiêu đề thông tin
    value: String,  // Giá trị thông tin
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    var isEditing by remember { mutableStateOf(false) }
    Card(
        elevation = CardDefaults.cardElevation(4.dp),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE3F2FD)),
        modifier = modifier
//            .height(60.dp)
            .clickable(onClick = {
                isEditing = !isEditing
            })  // Thêm khả năng bấm vào
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Text label (Tên thông tin)
            Text(
                text = label,
                fontFamily = KufamFont,
                fontWeight = FontWeight.SemiBold,
                fontSize = 15.sp,
                color = Color(0xFF757575),
                modifier = Modifier.padding(top = dimensionResource(R.dimen.padding_smaller))
            )

            Spacer(modifier = Modifier.width(dimensionResource(R.dimen.padding_medium)))

            // Text value (Giá trị thông tin)
            OutlinedTextField(
                value = value,
                onValueChange = onValueChange,
                textStyle = TextStyle(
                    fontFamily = KufamFont,
                    fontWeight = FontWeight.Medium,
                    fontSize = 15.sp,
                    color = Color(0xFF757575),
                    textAlign = TextAlign.End
                ),
                enabled = isEditing,
                maxLines = 1,
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
                modifier = Modifier
//                    .fillMaxWidth(3/4f)
                    .weight(1f)
                    .padding(top = dimensionResource(R.dimen.padding_smaller))  // Làm cho TextField chiếm toàn bộ chiều rộng nếu cần
                    .then(
                        if (!isEditing) {
                            Modifier.basicMarquee(
                                iterations = Int.MAX_VALUE,  // Lặp lại vô hạn
                                repeatDelayMillis = 3000,  // Thời gian delay giữa các lần lặp lại
                                initialDelayMillis = 0,  // Thời gian delay ban đầu
                                velocity = 30.dp  // Tốc độ di chuyển
                            )
                        } else Modifier
                    )
            )

            Spacer(modifier = Modifier.width(dimensionResource(R.dimen.padding_medium)))

            Icon(
                painter = painterResource(R.drawable.goto_icon),
                contentDescription = stringResource(R.string.notification_icon),
                modifier = Modifier
                    .width(dimensionResource(R.dimen.goto_icon_size))
                    .height(dimensionResource(R.dimen.goto_icon_size)*2)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun InformationScreenPreview() {
//    InformationScreen(
//        onBackClicked = {}
//    )
//    InfoCard(label = "Date of birth", value = "kkkkkkkkkkkkkk", onValueChange = {},
//        Modifier.padding(top = 40.dp))
//    UserInfoCardList(
//        name = "vl",
//        email = "vlvl",
//        dateOfBirth = "vlvlvl",
//        phoneNumber = "vlvlvlvl"
//    )
}