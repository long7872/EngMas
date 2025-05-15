package com.example.engmas.ui.utils

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.engmas.ui.theme.EngMasTheme

@Composable
fun ZoomableImageCard(imageUrl: String) {
    var scale by remember { mutableStateOf(1f) } // To hold the zoom level
    var offsetX by remember { mutableStateOf(0f) } // To hold the horizontal offset
    var offsetY by remember { mutableStateOf(0f) } // To hold the vertical offset

    // Function to handle pinch zoom and dragging (pan)
    val scaleGesture = Modifier.pointerInput(Unit) {
        detectTransformGestures { _, pan, zoom, _ ->
            scale *= zoom // Apply zoom scale from gestures
            scale = scale.coerceIn(0.5f, 3f) // Set minimum and maximum zoom level

            // Apply panning
            offsetX += pan.x
            offsetY += pan.y
        }
    }

    // Card Layout with fixed size
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(350.dp) // Fixed height for the card
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(Color(0xFFFFFFFF))
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Image(
                painter = rememberAsyncImagePainter(imageUrl),
                contentDescription = "Zoomable Image",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .fillMaxSize()
                    .then(scaleGesture) // Apply zooming gesture modifier only on image
                    .graphicsLayer(
                        scaleX = scale,
                        scaleY = scale,
                        translationX = offsetX,
                        translationY = offsetY
                    )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ZoomableImageCardPreview() {
    EngMasTheme {
        ZoomableImageCard("https://drive.google.com/uc?export=view&id=1NP1x7xsto8TQpV524W3Dtrc3_v6jjaKK")
    }
}