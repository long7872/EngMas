package com.example.engmas.ui.utils

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.example.engmas.R

@Composable
fun TaskProgressBar(
    progress: Float, // từ 0f đến 1f
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
    ) {
        CustomProgressBar(
            progress = progress.coerceIn(0f, 1f),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = dimensionResource(R.dimen.button_horizontal_padding))
        )
    }
}
