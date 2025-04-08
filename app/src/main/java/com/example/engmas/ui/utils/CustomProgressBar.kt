package com.example.engmas.ui.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun CustomProgressBar(
    progress: Float,
    modifier: Modifier = Modifier
) {
    val backgroundColor = Color(0xFFE0E0E0) // Màu nền
    val progressColor = Color(0xFF7ED321)   // Màu xanh lá

    Box(
        modifier = modifier
            .height(14.dp)
            .clip(RoundedCornerShape(50))
            .background(backgroundColor)
            .shadow(
                elevation = 2.dp,
                shape = RoundedCornerShape(50),
                clip = false, // đổ bóng ra ngoài
                ambientColor = Color.Black.copy(alpha = 0.1f),
                spotColor = Color.Black.copy(alpha = 0.1f)
            )
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(progress.coerceIn(0f, 1f))
                .clip(RoundedCornerShape(50))
                .background(progressColor)
        )
    }
}