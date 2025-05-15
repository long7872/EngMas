package com.example.engmas.ui.screens.exam.part3

import android.media.MediaPlayer
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.engmas.R
import com.example.engmas.ui.screens.exam.data.Question
import com.example.engmas.ui.theme.EngMasTheme
import com.example.engmas.ui.utils.Previous_Next_Button
import com.example.engmas.ui.utils.QuestionWithAnswers
import com.example.engmas.ui.utils.QuizHeader
import com.example.engmas.ui.utils.ZoomableImageCard
import java.io.File

@Composable
fun Part3(
    timeLeft: Long,
    currentQuestion: String,
    audioFile: File?,
    imageFiles: List<File>,
    questions: List<Question>,
    onAnswerSelected: (List<String>) -> Unit,
    onCompleted: () -> Unit,
    isDebug: Boolean = false,
    onPreviousClicked: () -> Unit,
    onNextClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    val selectedAnswer = remember(questions.size) { mutableStateListOf(*Array(questions.size) { "" }) }
    val mediaPlayer = remember { MediaPlayer() }

    DisposableEffect(Unit) {
        onDispose {
            try {
                mediaPlayer.setOnCompletionListener(null) // <- Ngắt listener trước khi release
                if (mediaPlayer.isPlaying) {
                    mediaPlayer.stop()
                }
                mediaPlayer.release()
                Log.d("Part 1 Screen", "MediaPlayer released")
            } catch (e: Exception) {
                Log.e("Part 1 Screen", "Error releasing MediaPlayer: ${e.message}")
            }
        }
    }

    LaunchedEffect(currentQuestion) {
        selectedAnswer.forEachIndexed { index, _ ->
            selectedAnswer[index] = ""  // Thiết lập lại giá trị của mỗi phần tử trong danh sách
        }
        // Kiểm tra nếu audioFile không null và là một tệp hợp lệ
        if (audioFile != null && audioFile.exists()) {
            // Reset MediaPlayer và chuẩn bị phát tệp mới
            mediaPlayer.setDataSource(audioFile.absolutePath)  // Sử dụng đường dẫn tuyệt đối của tệp
            Log.d("Part 3 Screen", "audio FilePath: ${audioFile.absolutePath}")
            mediaPlayer.prepare()  // Chuẩn bị phát tệp âm thanh
            mediaPlayer.setVolume(1.0f, 1.0f)  // Đặt âm lượng

            // Phát âm thanh
            mediaPlayer.start()

            // Đặt OnCompletionListener để thực hiện hành động khi âm thanh kết thúc
            mediaPlayer.setOnCompletionListener {
                mediaPlayer.stop()
                mediaPlayer.reset()
                onCompleted()
            }
        }
    }
    Card(
        elevation = CardDefaults.cardElevation(4.dp),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE3F2FD)),
        modifier = modifier
            .fillMaxSize()
            .padding(dimensionResource(R.dimen.padding_medium)),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                verticalArrangement = Arrangement.Center
            ) {
                QuizHeader(
                    currentQuestion = currentQuestion,
                    part = "3",
                    timeLeft = timeLeft
                )

                // LazyColumn for scrolling the questions
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxSize().padding(16.dp)
                ) {
                    items(imageFiles) { file ->
                        file.let {
                            Log.d("Part3", "Loading image: ${it.toURI()}")
                            ZoomableImageCard(imageUrl = it.toURI().toString())
                        }
                    }
                    itemsIndexed(questions) { index, item ->
                        QuestionWithAnswers(
                            question = item.question.toString(),
                            options = item.options,
                            selected = selectedAnswer[index],
                            onClicked = {
                                selectedAnswer[index] = it
                                onAnswerSelected(selectedAnswer.toList())
                            }
                        )
                    }
                    item {
                        if (isDebug) {
                            Previous_Next_Button(
                                onPreviousClicked = onPreviousClicked,
                                onNextClicked = {
                                    mediaPlayer.stop()
                                    mediaPlayer.reset()
                                    onNextClicked()
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Part3WithImagePreview() {
    EngMasTheme {
//        Part3()
    }
}