package com.example.engmas.ui.screens.exam

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.engmas.ui.screens.exam.data.Question
import com.example.engmas.ui.screens.exam.data.ToeicQuestion
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import net.lingala.zip4j.ZipFile
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import java.io.File
import java.io.IOException

class ExamViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(ExamUiState())
    val uiState: StateFlow<ExamUiState> = _uiState.asStateFlow()

    private val url =
        "https://www.dropbox.com/scl/fi/k1ugbo4q76pfi1s4uf1rv/Test-1-ETS-2024.zip?rlkey=0vwo199t6sg3glpurkembx0qr&st=v55vjqdi&dl=1"

    fun getListNameToeic(context: Context) {
        // Lấy tất cả thư mục con trong destinationFolder
        val root = File(context.dataDir, "toeic")
        val subfolders = root.listFiles { file -> file.isDirectory }
        val listName = mutableListOf<String>()

        // Kiểm tra nếu có thư mục con và in tên của chúng
        subfolders?.forEach { folder ->
            listName.add(folder.name)
        }

        _uiState.update {
            it.copy(
                listName= listName,
                rootFolder = root
            )
        }
        Log.d("Exam View Model", "function getListNameToeic: listName: ${_uiState.value.listName}")
    }

    fun setSelectedExam(exam: String) {
        _uiState.update { it.copy(selectedExam = exam) }
    }

    fun loadExams(context: Context) {
        viewModelScope.launch {
            changeScreenState(ExamScreenState.Loading)
            val destinationFile = File(context.dataDir, "toeic.zip")
//            downloadFileFromDrive(url, destinationFile)
            val destinationFolder = File(context.dataDir, "toeic")
//            unzipFile(destinationFile, destinationFolder)

            getListNameToeic(context)

            changeScreenState(ExamScreenState.Main)
        }
    }

    private fun loadPart(part: Int) {
        // /toeic
        val root = _uiState.value.rootFolder ?: throw Exception("view model still not contains root folder")
        // /toeic/ETS
        val selectedExam = _uiState.value.selectedExam
        val selectedExamFolder = File(root, selectedExam)
        // /toeic/ETS/Part
        val selectedPart = File(selectedExamFolder, "Part $part")

        val allQuestion = selectedPart.listFiles { file -> file.isDirectory }
        val listQuestion = mutableListOf<ToeicQuestion>()
        // /toeic/ETS/Part/1
        allQuestion?.forEach { file ->
            val imageFiles = file.listFiles { _, name -> name.endsWith(".jpg", ignoreCase = true) }
                ?.map { File(file, it.name) } ?: emptyList()
            val audioFile = file.listFiles { _, name -> name.endsWith(".mp3", ignoreCase = true) }
                ?.firstOrNull()
            val jsonFile = file.listFiles { _, name -> name.endsWith(".json", ignoreCase = true) }
                ?.firstOrNull()
            val questions = jsonFile?.let {
                val jsonString = it.readText()
                Json.decodeFromString<List<Question>>(jsonString)
            } ?: emptyList()
            val toeicQuestion = ToeicQuestion(
                name = file.name,
                audioFile = audioFile,
                imageFiles = imageFiles,
                questions = questions
            )
            listQuestion.add(toeicQuestion)
        } ?: throw Exception("view model cannot load all question")

        val questionParts = _uiState.value.questionParts
        val sortedList = listQuestion.sortedBy {
            // Chuyển đổi chuỗi có dạng "start-end" thành một dãy số (Range)
            val parts = it.name.split("-")
            if (parts.size == 1) {
                // Nếu chỉ có một số, trả về số đó
                parts[0].toInt()
            } else {
                // Nếu là dãy số, lấy số đầu tiên trong dãy (start)
                parts[0].toInt()
            }
        }
        val questionPart = mapOf(
            part to sortedList
        )
        Log.d("Exam View Model", "questions in part: $questionPart")
        val newQuestionParts = questionParts + questionPart
        _uiState.update { it.copy(questionParts = newQuestionParts) }
    }

    // Hàm tải file từ Google Drive (sử dụng Coroutine để chạy trong background thread)
    private suspend fun downloadFileFromDrive(url: String, destinationFile: File) {
        Log.d("Exam View Model", "starting download from: $url, to: $destinationFile")
        withContext(Dispatchers.IO) {
            val client = OkHttpClient()
            val request = Request.Builder().url(url).build()

            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) throw IOException("Unexpected code $response")

                // Ghi dữ liệu vào file
                destinationFile.outputStream().use { fileOutput ->
                    response.body?.byteStream()?.copyTo(fileOutput)
                }
            }
            Log.d("Exam View Model", "done download file: $url, to: $destinationFile")
        }
    }

    // Giải nén file ZIP
    private fun unzipFile(zipFile: File, destinationFolder: File) {
        Log.d("Exam View Model", "function unzip: unzipping ...")
        val zip = ZipFile(zipFile)
        Log.d("Exam View Model", "function unzip: create zip instance")
        zip.extractAll(destinationFolder.absolutePath)
        Log.d("Exam View Model", "function unzip: extract done to: ${destinationFolder.absolutePath}")
    }

    fun changeScreenState(screenState: ExamScreenState) {
        _uiState.update { it.copy(screenState = screenState) }
    }

    private fun nextPart() {
        val currentPart = _uiState.value.selectedParts
        var nextPart = currentPart + 1

        // Tìm phần đã được check gần nhất
        while (nextPart < _uiState.value.checkList.size && !_uiState.value.checkList[nextPart]) {
            nextPart++  // Bỏ qua phần chưa được check
        }

        if (nextPart < _uiState.value.checkList.size) {  // Kiểm tra nếu tìm thấy phần tử true
            when (nextPart) {
                0 -> {
                    changeScreenState(ExamScreenState.Part1)
                    loadPart(1)
                }
                1 -> {
                    changeScreenState(ExamScreenState.Part2)
                    loadPart(2)
                }
                2 -> {
                    changeScreenState(ExamScreenState.Part3)
                    loadPart(3)
                }
                3 -> {
                    changeScreenState(ExamScreenState.Part4)
                    loadPart(4)
                }
                4 -> {
                    changeScreenState(ExamScreenState.Part5)
                    loadPart(5)
                }
                5 -> {
                    changeScreenState(ExamScreenState.Part6)
                    loadPart(6)
                }
                6 -> {
                    changeScreenState(ExamScreenState.Part7)
                    loadPart(7)
                }
            }
            updateSelectedParts(nextPart)
        } else {
            changeScreenState(ExamScreenState.Result)
            calculateScore()
        }
    }

    private fun previousPart() {
        val currentPart = _uiState.value.selectedParts
        var previousPart = currentPart - 1

        // Tìm phần đã được check gần nhất
        while (previousPart >= 0 && !_uiState.value.checkList[previousPart]) {
            previousPart--  // Bỏ qua phần chưa được check
        }

        if (previousPart >= 0) {  // Kiểm tra nếu tìm thấy phần tử true
            when (previousPart) {
                0 -> {
                    changeScreenState(ExamScreenState.Part1)
                    loadPart(1)
                }
                1 -> {
                    changeScreenState(ExamScreenState.Part2)
                    loadPart(2)
                }
                2 -> {
                    changeScreenState(ExamScreenState.Part3)
                    loadPart(3)
                }
                3 -> {
                    changeScreenState(ExamScreenState.Part4)
                    loadPart(4)
                }
                4 -> {
                    changeScreenState(ExamScreenState.Part5)
                    loadPart(5)
                }
                5 -> {
                    changeScreenState(ExamScreenState.Part6)
                    loadPart(6)
                }
                6 -> {
                    changeScreenState(ExamScreenState.Part7)
                    loadPart(7)
                }
            }
            updateSelectedParts(previousPart)
        } else {
            changeScreenState(ExamScreenState.Result)
        }
    }

    fun nextQuestion(size: Int) {
        // Cập nhật chỉ số câu hỏi hiện tại khi người dùng chọn câu trả lời
        if (_uiState.value.questionIndexInPart < size) {
            // Chuyển sang câu hỏi tiếp theo nếu còn câu hỏi
            _uiState.update { it.copy(questionIndexInPart = it.questionIndexInPart + 1) }
        } else {
            nextPart()
            _uiState.update { it.copy(questionIndexInPart = 0) }
        }
    }

    fun previousQuestion() {
        if (_uiState.value.questionIndexInPart > 0) {
            _uiState.update { it.copy(questionIndexInPart = it.questionIndexInPart - 1) }
        } else {
            if (_uiState.value.isDone) {
                val currentPart = _uiState.value.selectedParts
                var previousPart = currentPart - 1

                // Tìm phần đã được check gần nhất
                while (previousPart >= 0 && !_uiState.value.checkList[previousPart]) {
                    previousPart--  // Bỏ qua phần chưa được check
                }
                val questionsPart: List<ToeicQuestion> = _uiState.value.questionParts[previousPart+1]?: emptyList()
                previousPart()
                _uiState.update { it.copy(questionIndexInPart = questionsPart.size - 1) }
            }
        }
    }

    fun calculateScore() {
        val questionParts = _uiState.value.questionParts
        val answerParts = _uiState.value.selectedAnswerParts
        val checkList = _uiState.value.checkList

        // Khởi tạo correctInParts với tất cả phần tử bằng 0
        val correctInParts = List(7) { 0 }.toMutableList()

        // Duyệt qua các phần trong checkList (từ part 1 đến part 7)
        for (part in checkList.indices) {
            // Kiểm tra nếu phần này được phép làm (checkList[part] == true)
            if (checkList[part]) {
                val partNumber = part + 1 // Phần bắt đầu từ 1 thay vì 0
                if (questionParts.containsKey(partNumber) && answerParts.containsKey(partNumber)) { // Kiểm tra xem part có tồn tại trong questionParts không
                    val questionPart = questionParts[partNumber] ?: emptyList()
                    val answerPart = answerParts[partNumber] ?: emptyMap()

                    var correctCount = 0
                    for (toeicQuestion in questionPart) {
                        val selectedAnswers = answerPart[toeicQuestion.name] ?: emptyList()
                        val correctAnswers = toeicQuestion.questions.map { it.correctAnswer }

                        for (selectedAnswer in selectedAnswers) {
                            if (correctAnswers.contains(selectedAnswer)) {
                                correctCount++
                            }
                        }
                    }

                    // Cập nhật số câu đúng cho partNumber tương ứng
                    correctInParts[partNumber - 1] = correctCount
                } else {
                    Log.d("Exam View Model", "Part $partNumber not found in questionParts.")
                }
            }
        }

        _uiState.update { it.copy(correctInParts = correctInParts) }

        Log.d("Exam View Model", "function cal score: list score: $correctInParts")
        val totalListeningCorrect = correctInParts.take(4).sum()
        val totalReadingCorrect = correctInParts.drop(4).sum()
        Log.d("Exam View Model", "function cal score: totalListeningCorrect: $totalListeningCorrect")
        Log.d("Exam View Model", "function cal score: totalReadingCorrect: $totalReadingCorrect")
        val listeningScore = calculateListeningScore(totalListeningCorrect)
        val readingScore = calculateReadingScore(totalReadingCorrect)

        val score = listeningScore + readingScore
        Log.d("Exam View Model", "function cal score: score: $score, " +
                "listening: $listeningScore, reading: $readingScore")
        _uiState.update { it.copy(score = score) }
    }

    fun updateSelectedAnswerParts(part: Int, name: String, answers: List<String>) {
        // Lấy danh sách các câu trả lời hiện tại
        val current = _uiState.value.selectedAnswerParts

        // Nếu phần đã tồn tại trong selectedAnswerParts, chúng ta chỉ cần thêm câu trả lời cho câu hỏi này
        val partAnswers = current[part] ?: emptyMap() // Lấy câu trả lời hiện tại cho part, nếu không có thì trả về emptyMap()

        // Lấy danh sách câu trả lời hiện tại của câu hỏi name (nếu có), hoặc tạo danh sách rỗng
        val existingAnswers = partAnswers[name] ?: emptyList()

        // Thêm câu trả lời mới vào danh sách hiện tại
        val updatedAnswers = answers

        // Cập nhật lại câu trả lời cho câu hỏi này trong phần hiện tại
        val updatedPartAnswers = partAnswers + (name to updatedAnswers)

        // Cập nhật lại selectedAnswerParts với phần đã được thay đổi
        val newSelectedAnswerParts = current + (part to updatedPartAnswers)

        Log.d("Exam View Model", "selected answer questions in part: $newSelectedAnswerParts")

        // Cập nhật trạng thái với selectedAnswerParts mới
        _uiState.update { it.copy(selectedAnswerParts = newSelectedAnswerParts) }
    }

    fun setDone() {
        _uiState.update { it.copy(isDone = true) }
        val currentPart = _uiState.value.selectedParts

        if (currentPart < _uiState.value.checkList.size && currentPart >= 0) {  // Kiểm tra nếu tìm thấy phần tử true
            when (currentPart) {
                0 -> {
                    changeScreenState(ExamScreenState.Part1)
                    loadPart(1)
                }
                1 -> {
                    changeScreenState(ExamScreenState.Part2)
                    loadPart(2)
                }
                2 -> {
                    changeScreenState(ExamScreenState.Part3)
                    loadPart(3)
                }
                3 -> {
                    changeScreenState(ExamScreenState.Part4)
                    loadPart(4)
                }
                4 -> {
                    changeScreenState(ExamScreenState.Part5)
                    loadPart(5)
                }
                5 -> {
                    changeScreenState(ExamScreenState.Part6)
                    loadPart(6)
                }
                6 -> {
                    changeScreenState(ExamScreenState.Part7)
                    loadPart(7)
                }
            }
        }
    }
    fun clear(context: Context) {
        _uiState.value = ExamUiState()
        getListNameToeic(context)
        changeScreenState(ExamScreenState.Select)
    }

    private fun updateSelectedParts(index: Int) {
        _uiState.update { it.copy(selectedParts = index) }
    }

    fun updateCheckList(list: List<Boolean>) {
        _uiState.update { it.copy(checkList = list) }
        Log.d("Exam View Model", "function updateCheckList: checkList: ${_uiState.value.checkList}")

        Log.d("Exam View Model", "function updateCheckList: screen state before: ${_uiState.value.screenState}")
        val value = findFirstTrueIndex(list)
        if (value != -1) {  // Kiểm tra nếu tìm thấy phần tử true
            when (value) {
                0 -> {
                    changeScreenState(ExamScreenState.Part1)
                    loadPart(1)
                }
                1 -> {
                    changeScreenState(ExamScreenState.Part2)
                    loadPart(2)
                }
                2 -> {
                    changeScreenState(ExamScreenState.Part3)
                    loadPart(3)
                }
                3 -> {
                    changeScreenState(ExamScreenState.Part4)
                    loadPart(4)
                }
                4 -> {
                    changeScreenState(ExamScreenState.Part5)
                    loadPart(5)
                }
                5 -> {
                    changeScreenState(ExamScreenState.Part6)
                    loadPart(6)
                }
                6 -> {
                    changeScreenState(ExamScreenState.Part7)
                    loadPart(7)
                }
            }
            updateSelectedParts(value)
        } else {
            // Nếu không có phần nào được chọn, quay lại màn hình chọn
            changeScreenState(ExamScreenState.Select)
        }
        Log.d("Exam View Model", "function updateCheckList: screen state after: ${_uiState.value.screenState}")
    }

    private fun findFirstTrueIndex(list: List<Boolean>): Int {
        val value = list.indexOfFirst { it }
        // Nếu tìm thấy giá trị true, thì cập nhật giá trị ở chỉ mục đó thành false
//        if (value != -1) {
//            // Tạo bản sao của checkList với giá trị tại chỉ số "value" được thay thành false
//            val updatedCheckList = _uiState.value.checkList.toMutableList().apply {
//                this[value] = false
//            }
//
//            // Cập nhật lại _uiState với checkList đã được thay đổi
//            _uiState.update { it.copy(checkList = updatedCheckList) }
//            Log.d("Exam View Model", "function findFirstTrueIndex: new checklist: $updatedCheckList")
//        }
        Log.d("Exam View Model", "function findFirstTrueIndex: return: $value")
        return value
    }

    private fun calculateListeningScore(questionCorrect: Int): Int {
        return when (questionCorrect) {
            in 1..16 -> 5
            17 -> 10
            18 -> 15
            19 -> 20
            20 -> 25
            21 -> 30
            22 -> 35
            23 -> 40
            24 -> 45
            25 -> 50
            26 -> 55
            27 -> 60
            28 -> 70
            29 -> 80
            30 -> 85
            31 -> 90
            32 -> 95
            33 -> 100
            34 -> 105
            35 -> 115
            36 -> 125
            37 -> 135
            38 -> 140
            39 -> 150
            40 -> 160
            41 -> 170
            42 -> 175
            43 -> 180
            44 -> 190
            45 -> 200
            46 -> 205
            47 -> 215
            48 -> 220
            49 -> 225
            50 -> 230
            51 -> 235
            52 -> 245
            53 -> 255
            54 -> 260
            55 -> 265
            56 -> 275
            57 -> 285
            58 -> 290
            59 -> 295
            60 -> 300
            61 -> 310
            62 -> 320
            63 -> 325
            64 -> 330
            65 -> 335
            66 -> 340
            67 -> 345
            68 -> 350
            69 -> 355
            70 -> 360
            71 -> 365
            72 -> 370
            73 -> 375
            74 -> 385
            75 -> 395
            76 -> 400
            77 -> 405
            78 -> 415
            79 -> 420
            80 -> 425
            81 -> 430
            82 -> 435
            83 -> 440
            84 -> 445
            85 -> 450
            86 -> 455
            87 -> 460
            88 -> 465
            89 -> 475
            90 -> 480
            91 -> 485
            92 -> 490
            in 93..100 -> 495
            else -> 0
        }
    }
    private fun calculateReadingScore(questionCorrect: Int): Int {
        return when (questionCorrect) {
            in 1..20 -> 5
            21 -> 10
            22 -> 15
            23 -> 20
            24 -> 25
            25 -> 30
            26 -> 35
            27 -> 40
            28 -> 45
            29 -> 55
            30 -> 60
            31 -> 65
            32 -> 70
            33 -> 75
            34 -> 80
            35 -> 85
            36 -> 90
            37 -> 95
            38 -> 105
            39 -> 115
            40 -> 120
            41 -> 125
            42 -> 130
            43 -> 135
            44 -> 140
            45 -> 145
            46 -> 150
            47 -> 160
            48 -> 170
            49 -> 175
            50 -> 185
            51 -> 195
            52 -> 205
            53 -> 210
            54 -> 215
            55 -> 220
            56 -> 230
            57 -> 240
            58 -> 245
            59 -> 250
            60 -> 255
            61 -> 260
            62 -> 270
            63 -> 275
            64 -> 280
            65 -> 285
            66 -> 290
            67 -> 295
            68 -> 295
            69 -> 300
            70 -> 310
            71 -> 315
            72 -> 320
            73 -> 325
            74 -> 330
            75 -> 335
            76 -> 340
            77 -> 345
            78 -> 355
            79 -> 360
            80 -> 370
            81 -> 375
            82 -> 385
            83 -> 390
            84 -> 395
            85 -> 400
            86 -> 405
            87 -> 415
            88 -> 420
            89 -> 425
            90 -> 435
            91 -> 440
            92 -> 450
            93 -> 455
            94 -> 460
            95 -> 470
            96 -> 475
            97 -> 485
            98 -> 485
            99 -> 490
            100 -> 495
            else -> 0
        }
    }
}

