package com.example.engmas.ui.screens.practice.courses

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.engmas.data.repository.NetworkCourseRepository
import com.example.engmas.network.RetrofitClient
import com.example.engmas.ui.screens.practice.courses.data.QuestionGrammarItem
import com.example.engmas.ui.screens.practice.courses.data.QuestionStatus
import com.example.engmas.ui.screens.practice.courses.data.QuestionVocabItem
import com.example.engmas.ui.screens.practice.courses.data.UpdateStatusRequest
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CourseViewModel: ViewModel() {
    private val auth = FirebaseAuth.getInstance()
    private val userId = auth.currentUser?.uid ?: ""
    private val courseRepository = NetworkCourseRepository(RetrofitClient.api)

    private val _uiState = MutableStateFlow(CourseUiState())
    val uiState: StateFlow<CourseUiState> = _uiState.asStateFlow()

    fun getQuestionInCourses(courseId: Int) {
        viewModelScope.launch {
            val result = courseRepository.getCourseLearning(courseId, userId)

            _uiState.update {
                it.copy(
                    selectedCourse = result.course,
                    vocabQuestions = result.vocabQuestions,
                    grammarQuestions = result.grammarQuestions
                )
            }
        }
    }

    private fun syncStatusToDb() {
        viewModelScope.launch {
            val request = UpdateStatusRequest(
                userId = userId,
                courseId = _uiState.value.selectedCourse.id,
                vocabQuestions = _uiState.value.vocabQuestions,
                grammarQuestions = _uiState.value.grammarQuestions
            )
            val result = courseRepository.syncStatus(request)
            Log.d("Course View Model", "sync status to database: $result")
        }
    }

    fun setupGame() {
        val correctAnswer = _uiState.value.selectedVocab.wordVi
        val otherAnswers = _uiState.value.vocabQuestions
            .filter { it != uiState.value.selectedVocab }  // Lọc bỏ selectedVocab
            .shuffled() // Trộn danh sách
            .take(3) // Lấy 3 từ ngẫu nhiên
        val answerList = mutableListOf<String>().apply {
            add(correctAnswer) // Thêm wordVi của selectedVocab vào đầu danh sách
            otherAnswers.forEach { add(it.wordVi) } // Thêm các từ ngẫu nhiên vào
        }
        answerList.shuffle()
        _uiState.update { currentState ->
            currentState.copy(answerList = answerList)
        }
    }

    fun nextQuestion() {
        viewModelScope.launch {
            delay(2000)

            sortByStatus()
            if (checkVocabHasLearningStatus() || checkVocabHasReviewStatus()) {
                setSelectedVocab(_uiState.value.vocabQuestions.first())
                changeScreenState(CourseScreenState.List)
                setupGame()
                delay(10)
                changeScreenState(CourseScreenState.Vocabulary)
            } else if (checkGrammarHasLearningStatus() || checkGrammarHasReviewStatus()) {
                setSelectedGrammar(_uiState.value.grammarQuestions.first())
                changeScreenState(CourseScreenState.List)
                delay(10)
                changeScreenState(CourseScreenState.Grammar)
            } else {
                Log.d("Course View Model", "Done Course")
                changeScreenState(CourseScreenState.List)
            }
        }
    }

    private fun sortByStatus() {
        _uiState.update { currentState ->
            currentState.copy(
                vocabQuestions = _uiState.value.vocabQuestions.sortedBy {
                    when (it.status) {
                        QuestionStatus.Learning -> 0
                        QuestionStatus.Review -> 1
                        QuestionStatus.Known -> 2
                    }
                },
                grammarQuestions = _uiState.value.grammarQuestions.sortedBy {
                    when (it.status) {
                        QuestionStatus.Learning -> 0
                        QuestionStatus.Review -> 1
                        QuestionStatus.Known -> 2
                    }
                }
            )
        }
    }

    fun checkVocabHasLearningStatus(): Boolean {
        return _uiState.value.vocabQuestions.any { it.status == QuestionStatus.Learning }
    }
    fun checkGrammarHasLearningStatus(): Boolean {
        return _uiState.value.grammarQuestions.any { it.status == QuestionStatus.Learning }
    }

    private fun checkVocabHasReviewStatus(): Boolean {
        return _uiState.value.vocabQuestions.any { it.status == QuestionStatus.Review }
    }
    private fun checkGrammarHasReviewStatus(): Boolean {
        return _uiState.value.grammarQuestions.any { it.status == QuestionStatus.Review }
    }

    fun updateStatusForVocabItem(item: QuestionVocabItem, newStatus: QuestionStatus) {
        val updatedVocabQuestions = _uiState.value.vocabQuestions.map {
            if (it.id == item.id) {
                it.copy(status = newStatus)
            } else {
                it
            }
        }
        _uiState.update { it.copy(vocabQuestions = updatedVocabQuestions) }
        sortByStatus()
        syncStatusToDb()
    }
    fun updateStatusForGrammarItem(item: QuestionGrammarItem, newStatus: QuestionStatus) {
        val updatedVocabQuestions = _uiState.value.grammarQuestions.map {
            if (it.id == item.id) {
                it.copy(status = newStatus)
            } else {
                it
            }
        }
        _uiState.update { it.copy(grammarQuestions = updatedVocabQuestions) }
        sortByStatus()
        syncStatusToDb()
    }
    fun updateStatusForAllItems(newStatus: QuestionStatus) {
        val updatedVocabQuestions = _uiState.value.vocabQuestions.map {
            it.copy(status = newStatus)
        }
        val updatedGrammarQuestions = _uiState.value.grammarQuestions.map {
            it.copy(status = newStatus)
        }
        _uiState.update {
            it.copy(
                vocabQuestions = updatedVocabQuestions,
                grammarQuestions = updatedGrammarQuestions
            )
        }
        sortByStatus()
        syncStatusToDb()
    }

    fun setSelectedVocab(vocab: QuestionVocabItem) {
        _uiState.update { it.copy(selectedVocab = vocab) }
        Log.d("Course View Model", "set selected vocab: $vocab")
    }
    fun setSelectedGrammar(grammar: QuestionGrammarItem) {
        _uiState.update { it.copy(selectedGrammar = grammar) }
        Log.d("Course View Model", "set selected grammar: $grammar")
    }

    fun changeScreenState(screenState: CourseScreenState) {
        _uiState.update { it.copy(screenState = screenState) }
    }

}