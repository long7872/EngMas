package com.example.engmas.ui.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
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
    val backgroundColor = Color(0xFFFFFFFF)
    val progressColor = Color(0xFF7ED321)

    Box(
        modifier = modifier
            .height(5.dp)  // Chiều cao thanh tiến trình
            .clip(RoundedCornerShape(50))  // Bo tròn góc
            .background(backgroundColor)  // Màu nền (luôn trắng)
            .shadow(
                elevation = 2.dp,
                shape = RoundedCornerShape(50),
                clip = false, // Đổ bóng ra ngoài
                ambientColor = Color.Black.copy(alpha = 0.1f),
                spotColor = Color.Black.copy(alpha = 0.1f)
            )
    ) {
        // Phần màu xanh sẽ chiếm theo giá trị progress
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .width((progress.coerceIn(0f, 1f) * 100).dp)  // Chiều rộng thay đổi theo progress
                .clip(RoundedCornerShape(50))  // Bo tròn góc cho thanh tiến trình
                .background(progressColor)  // Màu xanh lá
        )
    }
}
