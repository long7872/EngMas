package com.example.engmas.ui.utils

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.example.engmas.ui.theme.EngMasTheme

@Composable
fun ZoomableImageCard(imageUrl: String) {
    var scale by remember { mutableStateOf(1f) } // To hold the zoom level

    // Function to handle pinch zoom (you can add more logic for touch-based zooming)
    val scaleGesture = Modifier.pointerInput(Unit) {
        detectTransformGestures { _, pan, zoom, _ ->
            scale *= zoom // Apply zoom scale from gestures
            scale = scale.coerceIn(0.5f, 3f) // Set minimum and maximum zoom level
        }
    }

    // Card Layout with Image inside
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .graphicsLayer(
                scaleX = scale,
                scaleY = scale,
                // You can apply additional transformations if needed
            ),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .then(scaleGesture) // Apply zooming gesture modifier
        ) {
            Image(
                painter = rememberAsyncImagePainter(imageUrl),
                contentDescription = "Zoomable Image",
                contentScale = ContentScale.Fit,
                modifier = Modifier.fillMaxSize()
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
