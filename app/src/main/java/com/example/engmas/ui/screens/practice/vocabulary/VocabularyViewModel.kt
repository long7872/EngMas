package com.example.engmas.ui.screens.practice.vocabulary

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.engmas.data.model.UserLearning
import com.example.engmas.data.model.UserLearningStatus
import com.example.engmas.data.repository.NetworkTopicRepository
import com.example.engmas.network.RetrofitClient
import com.example.engmas.ui.screens.practice.vocabulary.model.VocabLearningInTopic
import com.example.engmas.ui.screens.practice.vocabulary.model.VocabLearningStatus
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
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

    private val _uiState = MutableStateFlow(VocabularyUiState())
    val uiState: StateFlow<VocabularyUiState> = _uiState.asStateFlow()

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

    fun deleteVocab(vocab: VocabLearningInTopic) {
        val list = _uiState.value.listVocabsInTopic
        val newList = list.filter { it.id != vocab.id }
        _uiState.update { it.copy( listVocabsInTopic = newList ) }
    }

    fun insertVocabLearning(vocabId: Int, status: UserLearningStatus = UserLearningStatus.Learning) {
        viewModelScope.launch {
            val userLearning = UserLearning(
                userId = userId,
                apiId = vocabId,
                status = status,
            )
            val result = topicRepository.insertUserLearning(userLearning)

            result.onSuccess {
                Log.d("Vocabulary View Model", "insert vocabulary user_learning table success")
            }.onFailure {
                Log.e("Vocabulary View Model", "insert vocabulary user_learning table failed: ${it.message}")
            }
        }
    }

    fun updateVocabLearning(vocabId: Int, status: UserLearningStatus) {
        viewModelScope.launch {
            val userLearning = UserLearning(
                userId = userId,
                apiId = vocabId,
                status = status,
            )
            val result = topicRepository.updateUserLearning(userLearning)

            result.onSuccess {
                Log.d("Vocabulary View Model", "update vocabulary user_learning table success")
            }.onFailure {
                Log.e("Vocabulary View Model", "update vocabulary user_learning table failed: ${it.message}")
            }
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

    fun changeScreenState(screenState: VocabularyScreenState) {
        _uiState.update { it.copy(screenState = screenState) }
    }

    fun setSelectedVocab(vocab: VocabLearningInTopic) {
        _uiState.update { it.copy(selectedVocab = vocab) }
        Log.d("Vocabulary View Model", "set selected vocab: $vocab")
    }

}