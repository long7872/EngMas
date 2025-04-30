package com.example.engmas.ui.utils

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.withFrameNanos
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.example.engmas.R

@Composable
fun TimeBar(
    durationMillis: Int = 30_000, // 30s
    onFinish: () -> Unit,
    modifier: Modifier = Modifier
) {
    var progress by remember { mutableFloatStateOf(value = 0f) }

    LaunchedEffect(Unit) {
        val startTime = withFrameNanos { it }
        while (progress < 1f) {
            val currentTime = withFrameNanos { it }
            val elapsedTime = (currentTime - startTime) / 1_000_000 // ns → ms
            progress = (elapsedTime.toFloat() / durationMillis).coerceIn(0f, 1f)
        }
        onFinish()
        progress = 0f
    }

    Row(
        modifier = modifier
    ) {
        CustomProgressBar(
            progress = progress,
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = dimensionResource(R.dimen.button_horizontal_padding))
        )
    }
}