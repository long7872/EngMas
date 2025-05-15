package com.example.engmas.ui.utils

import android.media.MediaPlayer
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.VolumeOff
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive

@Composable
fun AudioPlayer(url: String, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val mediaPlayer = remember { MediaPlayer() }
    var isPrepared by remember { mutableStateOf(false) }
    var isPlaying by remember { mutableStateOf(false) }
    var isMuted by remember { mutableStateOf(false) }
    var currentPosition by remember { mutableStateOf(0) }
    var duration by remember { mutableStateOf(1) }

    // Prepare media
    LaunchedEffect(url) {
        try {
            mediaPlayer.reset()
            mediaPlayer.setDataSource(url)
            mediaPlayer.prepare()
            duration = mediaPlayer.duration
            isPrepared = true
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    // Track progress
    LaunchedEffect(isPlaying) {
        while (isPlaying && isActive) {
            if (mediaPlayer.isPlaying) {
                currentPosition = mediaPlayer.currentPosition
            }
            delay(500)
        }
    }

    // Toggle mute
    fun updateVolume(muted: Boolean) {
        val volume = if (muted) 0f else 1f
        mediaPlayer.setVolume(volume, volume)
    }

    DisposableEffect(Unit) {
        onDispose {
            mediaPlayer.release()
        }
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        if (isPrepared) {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Play / Pause
                IconButton(onClick = {
                    if (mediaPlayer.isPlaying) {
                        mediaPlayer.pause()
                        isPlaying = false
                    } else {
                        mediaPlayer.start()
                        isPlaying = true
                    }
                }) {
                    Icon(
                        imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                        contentDescription = "Play/Pause",
                        tint = Color(0xFF757575)
                    )
                }

                // Time
                Text(
                    text = "${formatTime(currentPosition)} / ${formatTime(duration)}",
                    fontSize = 14.sp,
                    color = Color(0xFF757575),
                    modifier = Modifier.width(70.dp)
                )

                // Progress bar
                Slider(
                    value = currentPosition.toFloat(),
                    onValueChange = {
                        currentPosition = it.toInt()
                        mediaPlayer.seekTo(currentPosition)
                    },
                    valueRange = 0f..duration.toFloat(),
                    modifier = Modifier.weight(1f),
                    colors = SliderColors(
                        Color(0xFF757575),
                        Color(0xFF757575),
                        Color(0xFF757575),
                        Color(0xFFD9D9D9),
                        Color(0xFFD9D9D9),
                        Color(0xFF757575),
                        Color(0xFF757575),
                        Color(0xFFD9D9D9),
                        Color(0xFF757575),
                        Color(0xFF757575)
                    )
                )

                // Mute / Unmute
                IconButton(onClick = {
                    isMuted = !isMuted
                    updateVolume(isMuted)
                }) {
                    Icon(
                        imageVector = if (isMuted) Icons.AutoMirrored.Filled.VolumeOff else Icons.AutoMirrored.Filled.VolumeUp,
                        contentDescription = if (isMuted) "Unmute" else "Mute",
                        tint = Color(0xFF757575)
                    )
                }
            }
        } else {
            Text(
                text = "Loading audio...",
                fontSize = 14.sp,
                color = Color.Gray,
                modifier = modifier.padding(12.dp)
            )
        }
    }
}

private fun formatTime(milliseconds: Int): String {
    val minutes = (milliseconds / 1000) / 60
    val seconds = (milliseconds / 1000) % 60
    return String.format("%d:%02d", minutes, seconds)
}

@Preview(showBackground = true)
@Composable
fun AudioPlayerPreview() {
    AudioPlayer(url = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3")
}
