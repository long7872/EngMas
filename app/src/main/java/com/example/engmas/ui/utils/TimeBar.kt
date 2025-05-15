package com.example.engmas.ui.utils

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.withFrameNanos
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.engmas.R

@Composable
fun TimeBar(
    question: String,
    durationMillis: Int = 20_000, // 30s
    onFinish: () -> Unit,
    modifier: Modifier = Modifier
) {
    var progress by remember { mutableFloatStateOf(value = 0f) }

    LaunchedEffect(question) {
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
        val backgroundColor = Color(0xFFFFFFFF)
        val progressColor = Color(0xFF7ED321)

        Box(
            modifier = modifier.fillMaxWidth()
                .padding(horizontal = dimensionResource(R.dimen.button_horizontal_padding))
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
                    .fillMaxWidth(fraction = (progress.coerceIn(0f, 1f))) // Đặt tỷ lệ chiều rộng theo progress
                    .clip(RoundedCornerShape(50))  // Bo tròn góc cho thanh tiến trình
                    .background(progressColor)  // Màu xanh lá
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TimeBarPreview() {
//    TimeBar(onFinish = {
//        Log.d("dawadawdawda", "dawdadwadwawdawdawdwd")
//    })
}