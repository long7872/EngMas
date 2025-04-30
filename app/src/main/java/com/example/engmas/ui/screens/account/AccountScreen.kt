package com.example.engmas.ui.screens.account

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.engmas.R
import com.example.engmas.ui.navigation.NavigationDestination
import com.example.engmas.ui.theme.KufamFont
import com.example.engmas.ui.utils.CircleFrame

object AccountDestination: NavigationDestination {
    override val route = "account"
    override val titleRes = R.string.tab_account
}

@Composable
fun AccountScreen(
    modifier: Modifier = Modifier
) {
    Card(
        elevation = CardDefaults.cardElevation(4.dp),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFFFF)),
        modifier = modifier
            .fillMaxWidth()
            .padding(dimensionResource(R.dimen.padding_medium)),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(dimensionResource(R.dimen.bio_card_size))
                    .background(Color(0xFF49C1D9), MaterialTheme.shapes.medium)
                    .clip(MaterialTheme.shapes.medium)
            ) {
                BioCard(
                    userTag = "kierantrinh",
                    userEmail = "kierantrinhvn@gmail.com",
                    userId = "0001",
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = dimensionResource(R.dimen.padding_small)),
                )
            }

            val scrollState = rememberScrollState()
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .verticalScroll(scrollState)
            ) {
                GeneralOption(
                    notifySettingClicked = {},
                    infoClicked = {},
                    achievementClicked = {},
                    friendClicked = {},
                    modifier = Modifier
                        .padding(top = dimensionResource(R.dimen.padding_medium)),
                )

                DangerOption(
                    signOutClicked = {},
                    deleteClicked = {},
                    modifier = Modifier
                        .padding(bottom = dimensionResource(R.dimen.padding_medium)),
                )
            }
        }
    }
}

@Composable
private fun BioCard(
    userTag: String,
    userEmail: String,
    userId: String,
    modifier: Modifier = Modifier
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFFFF)),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(4.dp),
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxSize()
                .padding(start = dimensionResource(R.dimen.padding_large))
        ) {
            CircleFrame(
                avatarRes = R.drawable.avatar1_test,
                frameSize = dimensionResource(R.dimen.user_bio_avatar)
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = dimensionResource(R.dimen.padding_larger))
            ) {
                Text(
                    text = stringResource(R.string.user_tag, userTag),
                    fontFamily = KufamFont,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color(0xFFF9CC17)
                )
                Text(
                    text = stringResource(R.string.user_email, userEmail),
                    fontFamily = KufamFont,
                    fontSize = 10.sp,
                    color = Color(0xFF757575),
                    modifier = Modifier
                        .padding(vertical = dimensionResource(R.dimen.padding_smaller))
                )
                Text(
                    text = stringResource(R.string.user_id, userId),
                    fontFamily = KufamFont,
                    fontSize = 10.sp,
                    color = Color(0xFF757575)
                )
            }
        }
    }
}

@Composable
private fun GeneralOption(
    notifySettingClicked: () -> Unit,
    infoClicked: () -> Unit,
    achievementClicked: () -> Unit,
    friendClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFFFF)),
        border = CardDefaults.outlinedCardBorder(true),
        modifier = modifier
            .fillMaxWidth()
            .padding(dimensionResource(R.dimen.padding_medium)),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
                .padding(dimensionResource(R.dimen.padding_medium))
        ) {
            Text(
                text = stringResource(R.string.general_option),
                fontFamily = KufamFont,
                fontWeight = FontWeight.SemiBold,
                fontSize = 13.sp,
                textAlign = TextAlign.Center,
                color = Color(0xFF757575),
            )
            OptionRow(
                containerColor = Color(0xFFE3F2FD),
                itemColor = Color(0xFF757575),
                iconRes = R.drawable.bell_notif,
                iconDes = R.string.notification_icon,
                text = R.string.notification_text,
                onClick = notifySettingClicked
            )
            OptionRow(
                containerColor = Color(0xFFE3F2FD),
                itemColor = Color(0xFF757575),
                iconRes = R.drawable.person_icon,
                iconDes = R.string.info_icon,
                text = R.string.info_text,
                onClick = infoClicked
            )
            OptionRow(
                containerColor = Color(0xFFE3F2FD),
                itemColor = Color(0xFF757575),
                iconRes = R.drawable.medal_icon,
                iconDes = R.string.achievement_icon,
                text = R.string.achievement_text,
                onClick = achievementClicked
            )
            OptionRow(
                containerColor = Color(0xFFE3F2FD),
                itemColor = Color(0xFF757575),
                iconRes = R.drawable.friend_icon,
                iconDes = R.string.friend_icon,
                text = R.string.friend_text,
                onClick = friendClicked
            )
        }
    }
}

@Composable
private fun DangerOption(
    signOutClicked: () -> Unit,
    deleteClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFFFF)),
        border = CardDefaults.outlinedCardBorder(true),
        modifier = modifier
            .fillMaxWidth()
            .padding(dimensionResource(R.dimen.padding_medium)),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
                .padding(dimensionResource(R.dimen.padding_medium))
        ) {
            Text(
                text = stringResource(R.string.danger_option),
                fontFamily = KufamFont,
                fontWeight = FontWeight.SemiBold,
                fontSize = 13.sp,
                textAlign = TextAlign.Center,
                color = Color(0xFF757575),
            )
            OptionRow(
                containerColor = Color(0xFFFFFDD3),
                itemColor = Color(0xFFE4B706),
                iconRes = R.drawable.sign_out_icon,
                iconDes = R.string.sign_out_icon,
                text = R.string.sign_out_text,
                onClick = signOutClicked
            )
            OptionRow(
                containerColor = Color(0xFFFDE8E3),
                itemColor = Color(0xFFEB1B1B),
                iconRes = R.drawable.delete_icon,
                iconDes = R.string.delete_icon,
                text = R.string.delete_text,
                onClick = deleteClicked
            )
        }
    }
}

@Composable
private fun OptionRow(
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
            .padding(vertical = dimensionResource(R.dimen.padding_small))
            .clickable { onClick() },
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxSize()
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
                    fontWeight = FontWeight.Medium,
                    fontSize = 13.sp,
                    textAlign = TextAlign.Center,
                    color = itemColor,
                )
            }
            Icon(
                painter = painterResource(R.drawable.goto_icon),
                tint = itemColor,
                contentDescription = stringResource(R.string.notification_icon),
                modifier = Modifier.width(dimensionResource(R.dimen.goto_icon_size))
                    .aspectRatio(1/2f)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AccountScreenPreview() {
    AccountScreen()
}