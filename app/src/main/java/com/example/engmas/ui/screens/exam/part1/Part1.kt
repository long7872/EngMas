package com.example.engmas.ui.screens.exam.part1

import android.media.MediaPlayer
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.engmas.R
import com.example.engmas.ui.screens.exam.data.Question
import com.example.engmas.ui.theme.EngMasTheme
import com.example.engmas.ui.utils.AnswerButtons
import com.example.engmas.ui.utils.Previous_Next_Button
import com.example.engmas.ui.utils.QuizHeader
import com.example.engmas.ui.utils.ZoomableImageCard
import kotlinx.coroutines.launch
import java.io.File

@Composable
fun Part1(
    currentQuestion: String,
    audioFile: File?,
    imageFiles: List<File>,
    question: Question,
    onAnswerSelected: (String) -> Unit,
    onCompleted: () -> Unit,
    isDebug: Boolean = false,
    onPreviousClicked: () -> Unit,
    onNextClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedAnswer by remember { mutableStateOf("") }
    val fullOptions = question.options
    val displayOptions = fullOptions.map { it.first().toString() }
    val mediaPlayer = remember { MediaPlayer() }
    LaunchedEffect(currentQuestion) {
        selectedAnswer = ""
        // Kiểm tra nếu audioFile không null và là một tệp hợp lệ
        if (audioFile != null && audioFile.exists()) {
            // Reset MediaPlayer và chuẩn bị phát tệp mới
            mediaPlayer.setDataSource(audioFile.absolutePath)  // Sử dụng đường dẫn tuyệt đối của tệp
            Log.d("Part 1 Screen", "audio FilePath: ${audioFile.absolutePath}")
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
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                QuizHeader(
                    currentQuestion = currentQuestion,
                    part = "1",
                    timeLeft = 7200
                )

                imageFiles.forEach { imageFile ->
                    imageFile.let {
                        Log.d("Part1", "Loading image: ${it.toURI()}")
                        ZoomableImageCard(imageUrl = it.toURI().toString())
                    }
                }
                Spacer(modifier = Modifier.height(50.dp))
                AnswerButtons(
                    options = displayOptions,
                    selectedAnswer = selectedAnswer,
                    onAnswerSelected = { shortAnswer ->
                        selectedAnswer = shortAnswer

                        val fullAnswer = fullOptions.find { it.startsWith(shortAnswer) }
                        if (fullAnswer != null) {
                            // Trả về đáp án đầy đủ khi người dùng chọn
                            onAnswerSelected(fullAnswer)
                        }
                    },
                    modifier = Modifier.padding(start = 20.dp, end = 20.dp)
                )
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

@Preview(showBackground = true)
@Composable
private fun Part1Preview() {
    EngMasTheme {
//        Part1()
    }
}