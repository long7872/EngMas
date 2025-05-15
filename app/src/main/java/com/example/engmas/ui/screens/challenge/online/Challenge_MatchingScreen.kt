package com.example.engmas.ui.screens.challenge.online

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.engmas.R
import com.example.engmas.ui.theme.KufamFont

@Composable
fun Challenge_MatchingScreen(
    userImage: String,
    userName: String,
    modifier: Modifier = Modifier
) {
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
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom,
                modifier = Modifier.weight(0.33f)
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .background(Color.Transparent)
                        .size(dimensionResource(R.dimen.avatar_frame_size))
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
                    text = userName,
                    fontFamily = KufamFont,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    color = Color(0xFF757575),
                    modifier = Modifier.padding(top = dimensionResource(R.dimen.padding_medium))
                )
            }

            Image(
                painter = painterResource(R.drawable.vs_logo),
                contentDescription = stringResource(R.string.vs_logo),
                modifier = Modifier.weight(0.33f)
                    .aspectRatio(1f)
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.weight(0.33f)
            ) {
                Text(
                    text = stringResource(R.string.matching),
                    fontFamily = KufamFont,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    color = Color(0xFF757575),
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Challenge_MatchingScreenPreview() {
    Challenge_MatchingScreen(
        userImage = "https://www.dropbox.com/scl/fi/foqhgsondmdm7ohgpaz6w/1747107278950_upload.jpg?rlkey=4fxgdavvztjgtm93rx95yzzuf&raw=1",
        userName = "longhehee",
    )
}