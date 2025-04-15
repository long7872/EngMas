package com.example.engmas.ui.utils

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.engmas.R

@Composable
fun SocialIcon(
    @DrawableRes iconRes: Int
) {
    Image(
        painter = painterResource(id = iconRes),
        contentDescription = null,
        contentScale = ContentScale.Fit,
        modifier = Modifier.size(dimensionResource(R.dimen.social_icon_size))
    )
}