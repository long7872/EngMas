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
import androidx.compose.runtime.remember
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
import com.example.engmas.ui.theme.EngMasTheme
import com.example.engmas.ui.utils.AnswerButtons
import com.example.engmas.ui.utils.QuizHeader
import com.example.engmas.ui.utils.ZoomableImageCard
import kotlinx.coroutines.launch

@Composable
fun Part1(
    part1ViewModel: Part1ViewModel = viewModel(),
//    navController: NavController,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    // Tải câu hỏi khi màn hình được tạo

    val uiState by part1ViewModel.uiState.collectAsState()

    // Kiểm tra nếu câu hỏi đã hết, chuyển sang Part1_Result
//    if (uiState.currentQuestionIndex >= uiState.questionsPart1.size) {
//        LaunchedEffect(Unit) {
//            navController.navigate("part1_result") {
//                popUpTo("part1") { inclusive = true } // Pop up màn hình Part1
//            }
//        }
//    }

    LaunchedEffect(Unit) {

        val fileId = "1SG8sYd_Xg6aYTf4rEYRan_na14qVgomU"
        val url = "https://drive.google.com/uc?export=download&id=$fileId"

        Log.d("Part1", "Starting to download and unzip from: $url")

        part1ViewModel.loadQuestionsFromResources(context = context, url = url)  // Tải và giải nén dữ liệu

        Log.d("Part1", "Finished downloading and unzipping.")
    }
    // Lấy câu hỏi hiện tại từ ViewModel
    val currentQuestion = uiState.questionsPart1.getOrNull(uiState.currentQuestionIndex)

    Log.d("Part1", "Current question index: ${uiState.currentQuestionIndex}, Question: ${currentQuestion?.question}")

    val mediaPlayer = remember { MediaPlayer() }

    val audioFile = currentQuestion?.audioFile

    LaunchedEffect(currentQuestion) {
        // Kiểm tra nếu audioFile không null và là một tệp hợp lệ
        if (audioFile != null && audioFile.exists()) {
            // Reset MediaPlayer và chuẩn bị phát tệp mới
            mediaPlayer.reset()
            mediaPlayer.setDataSource(audioFile.absolutePath)  // Sử dụng đường dẫn tuyệt đối của tệp
            mediaPlayer.prepare()  // Chuẩn bị phát tệp âm thanh
            mediaPlayer.setVolume(1.0f, 1.0f)  // Đặt âm lượng

            // Phát âm thanh
            mediaPlayer.start()

            // Đặt OnCompletionListener để thực hiện hành động khi âm thanh kết thúc
            mediaPlayer.setOnCompletionListener {
                // Sau khi âm thanh kết thúc, đợi thêm 3 giây
                kotlinx.coroutines.GlobalScope.launch {
                    kotlinx.coroutines.delay(3000)

                    // Sau 3 giây, chuyển sang câu hỏi tiếp theo
                    part1ViewModel.nextQuestion()
                }
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
                .verticalScroll(rememberScrollState())
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                QuizHeader(
                    currentQuestion = (uiState.currentQuestionIndex + 1).toString(),
                    totalQuestions = uiState.questionsPart1.size.toString(),
                    part = "1",
                    timeLeft = 7200
                )

                currentQuestion?.imageFile?.let {
                    Log.d("Part1", "Loading image: ${it.toURI()}")
                    ZoomableImageCard(imageUrl = it.toURI().toString())
                }
                Spacer(modifier = Modifier.height(50.dp))
                currentQuestion?.let {
                    AnswerButtons(
                        options = it.options.map { it.first().toString() },
                        selectedAnswer = uiState.currentAnswer,
                        onAnswerSelected = { answer ->
                            Log.d("Part1", "Answer selected: $answer")
                            part1ViewModel.selectAnswer(answer)
                        },
                        modifier = Modifier.padding(start = 20.dp, end = 20.dp)
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