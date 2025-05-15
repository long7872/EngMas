package com.example.engmas.ui.screens.challenge.offline

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.engmas.data.repository.NetworkUserRepository
import com.example.engmas.data.repository.NetworkVocabRepository
import com.example.engmas.network.RetrofitClient
import com.example.engmas.ui.screens.challenge.online.Challenge_OnlineUiState
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class Challenge_OfflineViewModel: ViewModel() {
    private val auth = FirebaseAuth.getInstance()
    private val userId = auth.currentUser?.uid ?: ""
    private val vocabRepository = NetworkVocabRepository(RetrofitClient.api)
    private val userRepository = NetworkUserRepository(RetrofitClient.api)

    private val _uiState = MutableStateFlow(Challenge_OfflineUiState())
    val uiState: StateFlow<Challenge_OfflineUiState> = _uiState.asStateFlow()

    init {
        getData()
        getUser()
    }

    fun getUser() {
        viewModelScope.launch {
            val result = userRepository.getUser(userId)
            Log.d("Challenge Offline View Model", "function getUser: $result")
            _uiState.update { it.copy(thisUser = result) }
        }
    }
    fun getData() {
        viewModelScope.launch {
            val result = vocabRepository.getRandomVocab()
            val scrambledList = result.map { word ->
                word.toCharArray().apply { shuffle() }.concatToString()
            }
            Log.d("Challenge Offline View Model", "function getData: $result, scrambled: $scrambledList")
            _uiState.update { it.copy(
                originalList = result,
                scrambledList = scrambledList
            ) }
        }
    }

    fun playAgain() {
        viewModelScope.launch {
            val user = _uiState.value.thisUser
            _uiState.value = Challenge_OfflineUiState()
            getData()
            delay(2000)
            _uiState.update { it.copy(thisUser = user) }
        }
    }

    fun nextQuestion(answer: String) {
        val currentIndex = _uiState.value.thisUserCurrentQuestion
        var currentScore = _uiState.value.thisUserScore
//        if (_uiState.value.originalList[currentIndex] == answer)
        if (answer != "")
            currentScore++
        _uiState.update { it.copy(
            thisUserCurrentQuestion = currentIndex + 1,
            thisUserScore = currentScore
        ) }
        checkDone()
    }
    private fun checkDone() {
        if (_uiState.value.thisUserCurrentQuestion == 10)
            _uiState.update { it.copy(screenState = OfflineScreenState.Result) }
    }
}