package com.example.engmas.ui.utils

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.engmas.R
import com.example.engmas.ui.theme.KufamFont

@Composable
fun PlayerDuelCard(
    thisUserImage: String,
    thisUserName: String,
    thisUserQuestionCompleted: Int,
    otherUserImage: String,
    otherUserName: String,
    otherUserQuestionCompleted: Int,
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
        ) {
            PlayerInfo(
                userImage = thisUserImage,
                playerName = thisUserName,
                questionCompleted = thisUserQuestionCompleted,
                modifier = Modifier.weight(0.33f)
            )
            Image(
                painter = painterResource(R.drawable.vs_logo),
                contentDescription = stringResource(R.string.vs_logo),
                modifier = Modifier
                    .height(dimensionResource(R.dimen.avatar_dual_frame_size))
                    .aspectRatio(4/3f)
                    .offset(y = -dimensionResource(R.dimen.padding_medium))
                    .weight(0.33f)
            )
            PlayerInfo(
                userImage = otherUserImage,
                playerName = otherUserName,
                questionCompleted = otherUserQuestionCompleted,
                modifier = Modifier.weight(0.33f)
            )
        }
    }
}

@Composable
private fun PlayerInfo(
    userImage: String,
    playerName: String,
    questionCompleted: Int,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
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
        Text(
            text = playerName,
            fontFamily = KufamFont,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            color = Color(0xFF757575),
            textAlign = TextAlign.Center,
        )
        Text(
            text = "$questionCompleted/10",
            fontFamily = KufamFont,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            color = if (questionCompleted == 10) Color(0xFF09DD30)
                else Color(0xFFDD0909),
            textAlign = TextAlign.Center,
        )
    }
}

@Preview
@Composable
fun PlayerDuelCardPreview() {
//    PlayerDuelCard()
}