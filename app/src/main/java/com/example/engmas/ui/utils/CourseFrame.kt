package com.example.engmas.ui.utils

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import com.example.engmas.R

@Composable
fun CourseFrame(
    @DrawableRes avatarRes: Int,
    frameSize: Dp,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .background(Color.Transparent)
            .size(frameSize)
    ) {
        Image(
            painter = painterResource(avatarRes),
            contentDescription = stringResource(R.string.avatar),
            modifier = Modifier
                .size(dimensionResource(R.dimen.avatar_frame_size))
                .padding(dimensionResource(R.dimen.frame_gap_size))
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun CourseFramePreview() {
    CourseFrame(
        R.drawable.avatar1_test,
        dimensionResource(R.dimen.avatar_frame_size)
    )
}