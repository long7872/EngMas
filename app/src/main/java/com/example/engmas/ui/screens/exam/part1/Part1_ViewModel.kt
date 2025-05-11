package com.example.engmas.ui.screens.exam.part1

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.engmas.ui.screens.exam.ExamUiState
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.File
import java.io.IOException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import net.lingala.zip4j.ZipFile
import org.json.JSONObject

class Part1ViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(ExamUiState())
    val uiState = _uiState.asStateFlow()

//    // Tải dữ liệu câu hỏi từ JSON hoặc từ Google Drive
//    fun loadQuestionsFromResources(context: Context, url: String) {
//        viewModelScope.launch {
//            // Tải file ZIP từ Google Drive
//            Log.d("Part1ViewModel", "Starting to download from: $url")
//            val destinationFile = File(context.dataDir, "part1.zip")
//            downloadFileFromDrive(url, destinationFile)
//            Log.d("Part1ViewModel", "Finished downloading file to: ${destinationFile.absolutePath}")
//
//            // Giải nén file ZIP vào thư mục cache
//            val destinationFolder = File(context.dataDir, "part1")
//            Log.d("Part1ViewModel", "Starting to unzip file: ${destinationFile.absolutePath}")
//            unzipFile(destinationFile, destinationFolder)
//            Log.d("Part1ViewModel", "Finished unzipping to: ${destinationFolder.absolutePath}")
//
//            // Tải dữ liệu từ thư mục giải nén
//            loadQuestionsFromDirectory(destinationFolder)
//        }
//    }

//    // Hàm tải file từ Google Drive (sử dụng Coroutine để chạy trong background thread)
//    private suspend fun downloadFileFromDrive(url: String, destinationFile: File) {
//        Log.d("Part1ViewModel", "starting download: $url $destinationFile")
//        withContext(Dispatchers.IO) {
//            val client = OkHttpClient()
//            val request = Request.Builder().url(url).build()
//
//            client.newCall(request).execute().use { response ->
//                if (!response.isSuccessful) throw IOException("Unexpected code $response")
//
//                // Ghi dữ liệu vào file
//                destinationFile.outputStream().use { fileOutput ->
//                    response.body?.byteStream()?.copyTo(fileOutput)
//                }
//            }
//        }
//    }

//    // Giải nén file ZIP
//    private fun unzipFile(zipFile: File, destinationFolder: File) {
//        Log.d("Part1ViewModel", "unzipping to: ${destinationFolder.absolutePath}")
//        val zip = ZipFile(zipFile)
//        Log.d("Part1ViewModel", "unzippgggggggggggggggingdfdddg to: ${destinationFolder.absolutePath}")
//        zip.extractAll(destinationFolder.absolutePath)
//        Log.d("Part1ViewModel", "unzippingdfdddg to: ${destinationFolder.absolutePath}")
//    }

    // Tải câu hỏi từ thư mục giải nén
//    private fun loadQuestionsFromDirectory(directory: File) {
//        val partDataList = mutableListOf<QuestionData>()
//
//        val subEtsLink = directory.listFiles()?.joinToString(", ") { it.name } ?: ""
//        // Kiểm tra thư mục
//        Log.d("Part1ViewModel", "Directory contents: $subEtsLink")
//        val etsFolder = File(directory, subEtsLink)
//
//        val subParts = etsFolder.listFiles()?.joinToString(", ") { it.name } ?: ""
//        Log.d("Part1ViewModel", "Directory ETS contents: $subParts")
//
//        val partFolder = File(etsFolder, subParts)
//        for (i in 1..6) {
//            val questionFolder = File(partFolder, "$i")
//
//            // Kiểm tra nếu thư mục câu hỏi tồn tại
//            if (questionFolder.exists()) {
//                val jsonFile = File(questionFolder, "question$i.json")
//                if (jsonFile.exists()) {
//                    val jsonContent = jsonFile.readText()
//
//                    // Kiểm tra nội dung JSON
//                    Log.d("Part1ViewModel", "JSON content for question $i: $jsonContent")
//
//                    try {
//                        val jsonObject = JSONObject(jsonContent)
//                        val question = jsonObject.getString("question")
//                        val options = jsonObject.getJSONArray("options")
//                        val correctAnswer = jsonObject.getString("correct_answer")
//
//                        val audioFile = File(questionFolder, "audio_question$i.mp3")
//                        val imageFile = File(questionFolder, "image_question$i.jpg")
//
//                        partDataList.add(
//                            QuestionData(
//                                question = question,
//                                options = (0 until options.length()).map { options.getString(it) },
//                                correctAnswer = correctAnswer,
//                                audioFile = audioFile,
//                                imageFile = imageFile
//                            )
//
//                        )
//                    } catch (e: Exception) {
//                        Log.e("Part1ViewModel", "Error reading JSON for question $i", e)
//                    }
//                } else {
//                    Log.e("Part1ViewModel", "JSON file for question $i not found.")
//                }
//            }
//        }
//
//        Log.d("Part1ViewModel", "Loaded questions: ${partDataList.size} $partDataList")
//
//        _uiState.update {
//            it.copy(
//                questionsPart1 = partDataList,
//                selectedAnswers = MutableList(partDataList.size) { "" } // Khởi tạo với số phần tử tương ứng
//            )
//        }
//    }
//
//
//    fun selectAnswer(answer: String) {
//        // Save the selected answer
//        val currentQuestionIndex = _uiState.value.currentQuestionIndex
//        _uiState.update {
//            val updatedAnswers = it.selectedAnswers.toMutableList()
//            updatedAnswers[currentQuestionIndex] = answer // Save the answer for the current question
//            it.copy(currentAnswer = answer, selectedAnswers = updatedAnswers)
//        }
//    }
//
//    fun nextQuestion() {
//        // Lấy số lượng câu hỏi trong danh sách
//        val totalQuestions = _uiState.value.questionsPart1.size
//
//        // Kiểm tra nếu câu hỏi hiện tại chưa phải là câu hỏi cuối cùng
//        if (_uiState.value.currentQuestionIndex < totalQuestions - 1) {
//            // Tăng currentQuestionIndex để chuyển sang câu hỏi tiếp theo
//            _uiState.update {
//                it.copy(
//                    currentQuestionIndex = it.currentQuestionIndex + 1,
//                    currentAnswer = null // Reset câu trả lời đã chọn
//                )
//            }
//        } else {
//            Log.d("Part1ViewModel", "No more questions available.")
//        }
//    }

}

