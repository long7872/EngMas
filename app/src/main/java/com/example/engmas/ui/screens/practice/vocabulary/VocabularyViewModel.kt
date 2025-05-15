package com.example.engmas.ui.screens.practice.vocabulary

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.engmas.data.model.UserLearning
import com.example.engmas.data.model.UserLearningStatus
import com.example.engmas.data.repository.NetworkTopicRepository
import com.example.engmas.data.repository.NetworkUserScoreRepository
import com.example.engmas.network.RetrofitClient
import com.example.engmas.ui.screens.practice.courses.CourseScreenState
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

class VocabularyViewModel: ViewModel() {
    private val auth = FirebaseAuth.getInstance()
    private val userId = auth.currentUser?.uid ?: ""
//    private val firestore = FirebaseFirestore.getInstance()
    private val topicRepository = NetworkTopicRepository(RetrofitClient.api)
    private val userScoreRepository = NetworkUserScoreRepository()

    private val _uiState = MutableStateFlow(VocabularyUiState())
    val uiState: StateFlow<VocabularyUiState> = _uiState.asStateFlow()

    init {
        getUserScore()
    }

    fun getTopic() {
        viewModelScope.launch {
            val list = topicRepository.getTopicLearning(userId)

            _uiState.update {
                it.copy(listTopics = list)
            }

            Log.d("Vocabulary View Model", "list response: $list")
        }
    }

    fun getVocabsInTopic(topicId: Int) {
        viewModelScope.launch {
            val list = topicRepository.getVocabsInTopic(topicId, userId)

            _uiState.update {
                it.copy(
                    selectedTopic = list.topic,
                    listVocabsInTopic = list.vocabs
                )
            }

            Log.d("Vocabulary View Model", "list vocab response: $list")
        }
    }

    private fun syncStatusToDb() {
        viewModelScope.launch {
            val request = UpdateStatusRequest(
                userId = userId,
                vocabQuestions = _uiState.value.listVocabsInTopic,
            )
            val result = topicRepository.syncStatus(request)
            Log.d("Vocabulary View Model", "sync status to database: $result")
        }
    }

    fun setupGame() {
        val correctAnswer = _uiState.value.selectedVocab.wordVi
        val otherAnswers = _uiState.value.listVocabsInTopic
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

    fun nextQuestion(hasGame: Boolean = true, hasDelay: Boolean = true) {
        viewModelScope.launch {
            if (hasDelay) delay(2000)

            sortByStatus()
            if (checkVocabHasLearningStatus() || checkVocabHasReviewStatus()) {
                setSelectedVocab(_uiState.value.listVocabsInTopic.first())
                changeScreenState(VocabularyScreenState.InTopic)
                delay(10)
                changeScreenState(VocabularyScreenState.Flashcard)
            } else {
                Log.d("Vocabulary View Model", "Done Vocab")
                changeScreenState(VocabularyScreenState.InTopic)
                _uiState.update { it.copy(isDone = true) }
            }
        }
    }

    fun nextFlashcard() {
        viewModelScope.launch {

            sortByStatus()
            Log.d("Vocabulary View Model", "Learning status: ${checkVocabHasLearningStatus()}")
            if (checkVocabHasLearningStatus() || checkVocabHasReviewStatus()) {
                setSelectedVocab(_uiState.value.listVocabsInTopic.first())
                delay(10)
            } else {
                Log.d("Vocabulary View Model", "Done Flashcard")
                _uiState.update { it.copy(isDone = true) }
            }
        }
    }

    private fun sortByStatus() {
        _uiState.update { currentState ->
            currentState.copy(
                listVocabsInTopic = _uiState.value.listVocabsInTopic.sortedBy {
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
        return _uiState.value.listVocabsInTopic.any { it.status == QuestionStatus.Learning }
    }
    private fun checkVocabHasReviewStatus(): Boolean {
        return _uiState.value.listVocabsInTopic.any { it.status == QuestionStatus.Review }
    }

    fun updateStatusForVocabItem(item: QuestionVocabItem, newStatus: QuestionStatus) {
        updateUserScore(newStatus)
        val updatedVocabQuestions = _uiState.value.listVocabsInTopic.map {
            if (it.id == item.id) {
                it.copy(status = newStatus)
            } else {
                it
            }
        }
        _uiState.update { it.copy(listVocabsInTopic = updatedVocabQuestions) }
        Log.d("Vocabulary View Model", "Update status: $item, $newStatus")
        sortByStatus()
        syncStatusToDb()
    }
    fun updateStatusForAllItems(newStatus: QuestionStatus) {
        val updatedVocabQuestions = _uiState.value.listVocabsInTopic.map {
            it.copy(status = newStatus)
        }
        _uiState.update {
            it.copy(
                listVocabsInTopic = updatedVocabQuestions,
            )
        }
        sortByStatus()
        syncStatusToDb()
    }

    fun setSelectedVocab(vocab: QuestionVocabItem) {
        _uiState.update { it.copy(selectedVocab = vocab) }
        Log.d("Vocabulary View Model", "set selected vocab: $vocab")
    }

    fun changeScreenState(screenState: VocabularyScreenState) {
        _uiState.update { it.copy(screenState = screenState) }
    }

    fun getUserScore() {
        viewModelScope.launch {
            val user = userScoreRepository.getUser(userId)
            user.onSuccess { userScore ->
                _uiState.update { it.copy(userScore = userScore) }
            }.onFailure { error ->
                Log.e("Home", "${error.message}")
            }
        }
    }
    fun updateUserScore(status: QuestionStatus) {
        viewModelScope.launch {
            if (status == QuestionStatus.Known) {
                val currentWeek = _uiState.value.userScore.currentWeekStats
                val wordsLearned = currentWeek.wordsLearned + 1
                val updated = currentWeek.copy(wordsLearned = wordsLearned)
                val updatedUser = _uiState.value.userScore.copy(currentWeekStats = updated)
                val updatedResult = userScoreRepository.updateUser(userId ,updatedUser)
                updatedResult.onSuccess {
                    Log.d("Vocabulary View Model", "update user score: $updatedResult")
                }.onFailure { error ->
                    Log.d("Vocabulary View Model", "update user score error: ${error.message}")
                }
                _uiState.update { it.copy(userScore = updatedUser) }
            }
            if (status == QuestionStatus.Review) {
                val currentWeek = _uiState.value.userScore.currentWeekStats
                val wordsToReview = currentWeek.wordsToReview + 1
                val updated = currentWeek.copy(wordsToReview = wordsToReview)
                val updatedUser = _uiState.value.userScore.copy(currentWeekStats = updated)
                val updatedResult = userScoreRepository.updateUser(userId ,updatedUser)
                updatedResult.onSuccess {
                    Log.d("Vocabulary View Model", "update user score: $updatedResult")
                }.onFailure { error ->
                    Log.d("Vocabulary View Model", "update user score error: ${error.message}")
                }
                _uiState.update { it.copy(userScore = updatedUser) }
            }
        }
    }
}