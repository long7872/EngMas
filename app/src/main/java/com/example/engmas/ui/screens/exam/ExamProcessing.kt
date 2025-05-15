package com.example.engmas.ui.screens.exam

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.engmas.R
import com.example.engmas.ui.EngMasTopAppBar
import com.example.engmas.ui.navigation.NavigationDestination
import com.example.engmas.ui.screens.exam.data.ToeicQuestion
import com.example.engmas.ui.screens.exam.part1.Part1
import com.example.engmas.ui.screens.exam.part1.Part1_Result
import com.example.engmas.ui.screens.exam.part2.Part2
import com.example.engmas.ui.screens.exam.part2.Part2_Result
import com.example.engmas.ui.screens.exam.part3.Part3
import com.example.engmas.ui.screens.exam.part3.Part3_Result
import com.example.engmas.ui.screens.exam.part4.Part4
import com.example.engmas.ui.screens.exam.part4.Part4_Result
import com.example.engmas.ui.screens.exam.part5.Part5
import com.example.engmas.ui.screens.exam.part5.Part5_Result
import com.example.engmas.ui.screens.exam.part6.Part6
import com.example.engmas.ui.screens.exam.part6.Part6_Result
import com.example.engmas.ui.screens.exam.part7.Part7
import com.example.engmas.ui.screens.exam.part7.Part7_Result

object ExamStartDestination: NavigationDestination {
    override val route = "exam/start"
    override val titleRes = R.string.tab_exam_start
    const val ITEM_ARGS = "exam_id"
    val routeWithArgs = "$route/{$ITEM_ARGS}"
}

@Composable
fun ExamProcessing(
    examId: Int,
    viewModel: ExamViewModel = viewModel(),
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()
    val isDebug by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        viewModel.getListNameToeic(context)
        viewModel.changeScreenState(ExamScreenState.Select)
    }

    var title by remember { mutableStateOf("") }
    if (uiState.listName.isNotEmpty()) {
        title = uiState.listName[examId]
        Log.d("Exam Processing", "exam id: $examId, title: $title")
        viewModel.setSelectedExam(title)
    }

    val timeLeft by viewModel.time.collectAsState()

    val screenState = uiState.screenState

    Log.d("Exam Processing Screen", "screen state: ${uiState.screenState}")

    val displaySubmit = if (screenState == ExamScreenState.Select
        || screenState == ExamScreenState.Result) false else !uiState.isDone
    Scaffold(
        topBar = { EngMasTopAppBar(
            isNotDisplay = false,
            canNavigateBack = true,
            navigateUp = navigateUp,
            onActionButtonClicked = {
                if (displaySubmit) {
                    viewModel.changeScreenState(ExamScreenState.Result)
                    viewModel.calculateScore()
                }
            },
            isSubmit = displaySubmit
        ) },
        containerColor = Color(0xFFF5F5F5)
    ) { contentPadding ->
        when (screenState) {
            ExamScreenState.Select -> {
                Exam_SelectScreen(
                    title = title,
                    onStartClicked = {
                        viewModel.updateCheckList(it)
                        viewModel.startCounting()
                    },
                    modifier = Modifier.padding(contentPadding)
                )
            }
            ExamScreenState.Part1 -> {
                val questionsPart1: List<ToeicQuestion> = uiState.questionParts[1] ?: emptyList()
                val currentQuestion = questionsPart1.getOrNull(uiState.questionIndexInPart)
                if (currentQuestion != null && !uiState.isDone) {
                    Part1(
                        timeLeft = timeLeft,
                        currentQuestion = currentQuestion.name, // Truyền câu hỏi hiện tại
                        audioFile = currentQuestion.audioFile,
                        imageFiles = currentQuestion.imageFiles, // Hình ảnh của câu hỏi
                        question = currentQuestion.questions.first(), // Câu hỏi hiện tại
                        onAnswerSelected = {
                            viewModel.updateSelectedAnswerParts(1, currentQuestion.name, listOf(it))
                        },
                        onCompleted = {
                            viewModel.nextQuestion(
                                size = questionsPart1.size - 1
                            )
                        },
                        isDebug = isDebug,
                        onPreviousClicked = { viewModel.previousQuestion() },
                        onNextClicked = { viewModel.nextQuestion(size = questionsPart1.size - 1) },
                        modifier = Modifier.padding(contentPadding)
                    )
                } else if (currentQuestion != null) {
                    val currentSelectedAnswerPart = uiState.selectedAnswerParts[1] ?: emptyMap()
                    val currentSelectedAnswerList = currentSelectedAnswerPart[currentQuestion.name] ?: emptyList()
                    var selectedAnswer = ""
                    if (currentSelectedAnswerList.isNotEmpty()) selectedAnswer = currentSelectedAnswerList.first()
                    Part1_Result(
                        timeLeft = timeLeft,
                        currentQuestion = currentQuestion.name, // Truyền câu hỏi hiện tại
                        audioFile = currentQuestion.audioFile,
                        imageFiles = currentQuestion.imageFiles, // Hình ảnh của câu hỏi
                        question = currentQuestion.questions.first(), // Câu hỏi hiện tại
                        selectedAnswer = selectedAnswer,
                        onPreviousClicked = { viewModel.previousQuestion() },
                        onNextClicked = { viewModel.nextQuestion(size = questionsPart1.size - 1) },
                        modifier = Modifier.padding(contentPadding)
                    )
                } else {
                    // Xử lý trường hợp không có câu hỏi nào
                    Text("Không có câu hỏi nào!")
                }
            }
            ExamScreenState.Part2 -> {
                val questionsPart2: List<ToeicQuestion> = uiState.questionParts[2] ?: emptyList()
                val currentQuestion = questionsPart2.getOrNull(uiState.questionIndexInPart)
                if (currentQuestion != null && !uiState.isDone) {
                    Part2(
                        timeLeft = timeLeft,
                        currentQuestion = currentQuestion.name, // Truyền câu hỏi hiện tại
                        audioFile = currentQuestion.audioFile,
                        question = currentQuestion.questions.first(), // Câu hỏi hiện tại
                        onAnswerSelected = {
                            viewModel.updateSelectedAnswerParts(2, currentQuestion.name, listOf(it))
                        },
                        onCompleted = {
                            viewModel.nextQuestion(
                                size = questionsPart2.size - 1
                            )
                        },
                        isDebug = isDebug,
                        onPreviousClicked = { viewModel.previousQuestion() },
                        onNextClicked = { viewModel.nextQuestion(size = questionsPart2.size - 1) },
                        modifier = Modifier.padding(contentPadding)
                    )
                } else if (currentQuestion != null) {
                    val currentSelectedAnswerPart = uiState.selectedAnswerParts[2] ?: emptyMap()
                    val currentSelectedAnswerList = currentSelectedAnswerPart[currentQuestion.name] ?: emptyList()
                    var selectedAnswer = ""
                    if (currentSelectedAnswerList.isNotEmpty()) selectedAnswer = currentSelectedAnswerList.first()
                    Part2_Result(
                        timeLeft = timeLeft,
                        currentQuestion = currentQuestion.name, // Truyền câu hỏi hiện tại
                        audioFile = currentQuestion.audioFile,
                        question = currentQuestion.questions.first(), // Câu hỏi hiện tại
                        selectedAnswer = selectedAnswer,
                        onPreviousClicked = { viewModel.previousQuestion() },
                        onNextClicked = { viewModel.nextQuestion(size = questionsPart2.size - 1) },
                        modifier = Modifier.padding(contentPadding)
                    )
                } else {
                    // Xử lý trường hợp không có câu hỏi nào
                    Text("Không có câu hỏi nào!")
                }
            }
            ExamScreenState.Part3 -> {
                val questionsPart3: List<ToeicQuestion> = uiState.questionParts[3] ?: emptyList()
                val currentQuestion = questionsPart3.getOrNull(uiState.questionIndexInPart)
                if (currentQuestion != null && !uiState.isDone) {
                    Part3(
                        timeLeft = timeLeft,
                        currentQuestion = currentQuestion.name, // Truyền câu hỏi hiện tại
                        audioFile = currentQuestion.audioFile,
                        imageFiles = currentQuestion.imageFiles,
                        questions = currentQuestion.questions, // Câu hỏi hiện tại
                        onAnswerSelected = {
                            viewModel.updateSelectedAnswerParts(3, currentQuestion.name, it)
                        },
                        onCompleted = {
                            viewModel.nextQuestion(
                                size = questionsPart3.size - 1
                            )
                        },
                        isDebug = isDebug,
                        onPreviousClicked = { viewModel.previousQuestion() },
                        onNextClicked = { viewModel.nextQuestion(size = questionsPart3.size - 1) },
                        modifier = Modifier.padding(contentPadding)
                    )
                } else if (currentQuestion != null) {
                    val currentSelectedAnswerPart = uiState.selectedAnswerParts[3] ?: emptyMap()
                    val selectedAnswer = currentSelectedAnswerPart[currentQuestion.name] ?: emptyList()
                    Part3_Result(
                        timeLeft = timeLeft,
                        currentQuestion = currentQuestion.name, // Truyền câu hỏi hiện tại
                        audioFile = currentQuestion.audioFile,
                        imageFiles = currentQuestion.imageFiles,
                        questions = currentQuestion.questions, // Câu hỏi hiện tại
                        selectedAnswer = selectedAnswer,
                        onPreviousClicked = { viewModel.previousQuestion() },
                        onNextClicked = { viewModel.nextQuestion(size = questionsPart3.size - 1) },
                        modifier = Modifier.padding(contentPadding)
                    )
                } else {
                    // Xử lý trường hợp không có câu hỏi nào
                    Text("Không có câu hỏi nào!")
                }
            }
            ExamScreenState.Part4 -> {
                val questionsPart4: List<ToeicQuestion> = uiState.questionParts[4] ?: emptyList()
                val currentQuestion = questionsPart4.getOrNull(uiState.questionIndexInPart)
                if (currentQuestion != null && !uiState.isDone) {
                    Part4(
                        timeLeft = timeLeft,
                        currentQuestion = currentQuestion.name, // Truyền câu hỏi hiện tại
                        audioFile = currentQuestion.audioFile,
                        imageFiles = currentQuestion.imageFiles,
                        questions = currentQuestion.questions, // Câu hỏi hiện tại
                        onAnswerSelected = {
                            viewModel.updateSelectedAnswerParts(4, currentQuestion.name, it)
                        },
                        onCompleted = {
                            viewModel.nextQuestion(
                                size = questionsPart4.size - 1
                            )
                        },
                        isDebug = isDebug,
                        onPreviousClicked = { viewModel.previousQuestion() },
                        onNextClicked = { viewModel.nextQuestion(size = questionsPart4.size - 1) },
                        modifier = Modifier.padding(contentPadding)
                    )
                } else if (currentQuestion != null) {
                    val currentSelectedAnswerPart = uiState.selectedAnswerParts[4] ?: emptyMap()
                    val selectedAnswer = currentSelectedAnswerPart[currentQuestion.name] ?: emptyList()
                    Part4_Result(
                        timeLeft = timeLeft,
                        currentQuestion = currentQuestion.name, // Truyền câu hỏi hiện tại
                        audioFile = currentQuestion.audioFile,
                        imageFiles = currentQuestion.imageFiles,
                        questions = currentQuestion.questions, // Câu hỏi hiện tại
                        selectedAnswer = selectedAnswer,
                        onPreviousClicked = { viewModel.previousQuestion() },
                        onNextClicked = { viewModel.nextQuestion(size = questionsPart4.size - 1) },
                        modifier = Modifier.padding(contentPadding)
                    )
                } else {
                    // Xử lý trường hợp không có câu hỏi nào
                    Text("Không có câu hỏi nào!")
                }
            }
            ExamScreenState.Part5 -> {
                val questionsPart5: List<ToeicQuestion> = uiState.questionParts[5] ?: emptyList()
                val currentQuestion = questionsPart5.getOrNull(uiState.questionIndexInPart)
                if (currentQuestion != null && !uiState.isDone) {
                    val currentSelectedAnswerPart = uiState.selectedAnswerParts[5] ?: emptyMap()
                    val currentSelectedAnswerList = currentSelectedAnswerPart[currentQuestion.name] ?: emptyList()
                    var selectedAnswer = ""
                    if (currentSelectedAnswerList.isNotEmpty()) selectedAnswer = currentSelectedAnswerList.first()
                    Log.d("Exam Processing Screen", "screen state Part 5: currentSelectedAnswerPart: $currentSelectedAnswerPart")
                    Log.d("Exam Processing Screen", "screen state Part 5: currentSelectedAnswerList: $currentSelectedAnswerList")
                    Log.d("Exam Processing Screen", "screen state Part 5: currentSelectedAnswer: $selectedAnswer")
                    Part5(
                        timeLeft = timeLeft,
                        currentQuestion = currentQuestion.name, // Truyền câu hỏi hiện tại
                        question = currentQuestion.questions.first(), // Câu hỏi hiện tại
                        selectedAnswer = selectedAnswer,
                        onAnswerSelected = {
                            viewModel.updateSelectedAnswerParts(5, currentQuestion.name, listOf(it))
                        },
                        onPreviousClicked = { viewModel.previousQuestion() },
                        onNextClicked = { viewModel.nextQuestion(size = questionsPart5.size - 1) },
                        modifier = Modifier.padding(contentPadding)
                    )
                } else if (currentQuestion != null) {
                    val currentSelectedAnswerPart = uiState.selectedAnswerParts[5] ?: emptyMap()
                    val currentSelectedAnswerList = currentSelectedAnswerPart[currentQuestion.name] ?: emptyList()
                    var selectedAnswer = ""
                    if (currentSelectedAnswerList.isNotEmpty()) selectedAnswer = currentSelectedAnswerList.first()
                    Part5_Result(
                        timeLeft = timeLeft,
                        currentQuestion = currentQuestion.name, // Truyền câu hỏi hiện tại
                        question = currentQuestion.questions.first(), // Câu hỏi hiện tại
                        selectedAnswer = selectedAnswer,
                        onPreviousClicked = { viewModel.previousQuestion() },
                        onNextClicked = { viewModel.nextQuestion(size = questionsPart5.size - 1) },
                        modifier = Modifier.padding(contentPadding)
                    )
                } else {
                    // Xử lý trường hợp không có câu hỏi nào
                    Text("Không có câu hỏi nào!")
                }
            }
            ExamScreenState.Part6 -> {
                val questionsPart6: List<ToeicQuestion> = uiState.questionParts[6] ?: emptyList()
                val currentQuestion = questionsPart6.getOrNull(uiState.questionIndexInPart)
                if (currentQuestion != null && !uiState.isDone) {
                    val currentSelectedAnswerPart = uiState.selectedAnswerParts[6] ?: emptyMap()
                    val selectedAnswer = currentSelectedAnswerPart[currentQuestion.name] ?: emptyList()
                    Log.d("Exam Processing Screen", "screen state Part 6: currentSelectedAnswerPart: $currentSelectedAnswerPart")
                    Log.d("Exam Processing Screen", "screen state Part 6: selectedAnswer: $selectedAnswer")
                    Part6(
                        timeLeft = timeLeft,
                        currentQuestion = currentQuestion.name, // Truyền câu hỏi hiện tại
                        imageFiles = currentQuestion.imageFiles,
                        questions = currentQuestion.questions, // Câu hỏi hiện tại
                        selectedAnswer = selectedAnswer,
                        onAnswerSelected = {
                            viewModel.updateSelectedAnswerParts(6, currentQuestion.name, it)
                        },
                        onPreviousClicked = { viewModel.previousQuestion() },
                        onNextClicked = { viewModel.nextQuestion(size = questionsPart6.size - 1) },
                        modifier = Modifier.padding(contentPadding)
                    )
                } else if (currentQuestion != null) {
                    val currentSelectedAnswerPart = uiState.selectedAnswerParts[6] ?: emptyMap()
                    val selectedAnswer = currentSelectedAnswerPart[currentQuestion.name] ?: emptyList()
                    Part6_Result(
                        timeLeft = timeLeft,
                        currentQuestion = currentQuestion.name, // Truyền câu hỏi hiện tại
                        imageFiles = currentQuestion.imageFiles,
                        questions = currentQuestion.questions, // Câu hỏi hiện tại
                        selectedAnswer = selectedAnswer,
                        onPreviousClicked = { viewModel.previousQuestion() },
                        onNextClicked = { viewModel.nextQuestion(size = questionsPart6.size - 1) },
                        modifier = Modifier.padding(contentPadding)
                    )
                } else {
                    // Xử lý trường hợp không có câu hỏi nào
                    Text("Không có câu hỏi nào!")
                }
            }
            ExamScreenState.Part7 -> {
                val questionsPart7: List<ToeicQuestion> = uiState.questionParts[7] ?: emptyList()
                val currentQuestion = questionsPart7.getOrNull(uiState.questionIndexInPart)
                if (currentQuestion != null && !uiState.isDone) {
                    val currentSelectedAnswerPart = uiState.selectedAnswerParts[7] ?: emptyMap()
                    val selectedAnswer = currentSelectedAnswerPart[currentQuestion.name] ?: emptyList()
                    Log.d("Exam Processing Screen", "screen state Part 7: currentSelectedAnswerPart: $currentSelectedAnswerPart")
                    Log.d("Exam Processing Screen", "screen state Part 7: selectedAnswer: $selectedAnswer")
                    Part7(
                        timeLeft = timeLeft,
                        currentQuestion = currentQuestion.name, // Truyền câu hỏi hiện tại
                        imageFiles = currentQuestion.imageFiles,
                        questions = currentQuestion.questions, // Câu hỏi hiện tại
                        selectedAnswer = selectedAnswer,
                        onAnswerSelected = {
                            viewModel.updateSelectedAnswerParts(7, currentQuestion.name, it)
                        },
                        onPreviousClicked = { viewModel.previousQuestion() },
                        onNextClicked = { viewModel.nextQuestion(size = questionsPart7.size - 1) },
                        modifier = Modifier.padding(contentPadding)
                    )
                } else if (currentQuestion != null) {
                    val currentSelectedAnswerPart = uiState.selectedAnswerParts[7] ?: emptyMap()
                    val selectedAnswer = currentSelectedAnswerPart[currentQuestion.name] ?: emptyList()
                    Part7_Result(
                        timeLeft = timeLeft,
                        currentQuestion = currentQuestion.name, // Truyền câu hỏi hiện tại
                        imageFiles = currentQuestion.imageFiles,
                        questions = currentQuestion.questions, // Câu hỏi hiện tại
                        selectedAnswer = selectedAnswer,
                        onPreviousClicked = { viewModel.previousQuestion() },
                        onNextClicked = { viewModel.nextQuestion(size = questionsPart7.size - 1) },
                        modifier = Modifier.padding(contentPadding)
                    )
                } else {
                    // Xử lý trường hợp không có câu hỏi nào
                    Text("Không có câu hỏi nào!")
                }
            }
            ExamScreenState.Result -> {
                ExamResult(
                    testName = title,
                    score = uiState.score.toString(),
                    correctInParts = uiState.correctInParts,
                    onDetailsButton = { viewModel.setDone() },
                    onRetakeButton = {
                        viewModel.clear(context)
                        viewModel.resetCounting()
                    },
                    modifier = Modifier.padding(contentPadding)
                )
            }
            else -> {

            }
        }
    }

}