package com.example.engmas.ui.screens.challenge.online

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.engmas.coroutine.AppCoroutineScope
import com.example.engmas.data.model.Challenge
import com.example.engmas.data.model.User
import com.example.engmas.data.model.UserStatus
import com.example.engmas.data.repository.ChallengeRepository
import com.example.engmas.data.repository.NetworkUserRepository
import com.example.engmas.data.repository.NetworkUserScoreRepository
import com.example.engmas.data.repository.NetworkVocabRepository
import com.example.engmas.network.RetrofitClient
import com.example.engmas.network.SocketManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import io.socket.emitter.Emitter
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.json.JSONObject
import kotlin.random.Random

class Challenge_OnlineViewModel: ViewModel() {
    private val auth = FirebaseAuth.getInstance()
    private val userId = auth.currentUser?.uid ?: ""
    private val userRepository = NetworkUserRepository(RetrofitClient.api)
    private val userScoreRepository = NetworkUserScoreRepository()

    private val _uiState = MutableStateFlow(Challenge_OnlineUiState())
    val uiState: StateFlow<Challenge_OnlineUiState> = _uiState.asStateFlow()

    suspend fun getUser(userId: String) {
        val result = userRepository.getUser(userId)
        Log.d("Challenge Online View Model", "function getUser: $result")
        _uiState.update { it.copy(thisUser = result) }
    }

    fun rematch() {
        _uiState.value = Challenge_OnlineUiState()
        SocketManager.disconnect()
        enterMatching()
    }

    fun enterMatching() {
        connectSocket()
        joinQueue()
        _uiState.update { it.copy(matchingState = MatchingState.Matching) }
    }

    fun enterPlay() {
        viewModelScope.launch {
            delay(2000)
            _uiState.update { it.copy(matchingState = MatchingState.Play) }
        }
    }

    fun sendNextQuestion(answer: String) {
        val currentIndex = _uiState.value.thisUserCurrentQuestion
        var currentScore = _uiState.value.thisUserScore
//        if (_uiState.value.originalList[currentIndex] == answer)
        if (answer != "")
            currentScore++
        val data = JSONObject().apply {
            put("current_question", currentIndex + 1)
            put("current_score", currentScore)
        }
        _uiState.update { it.copy(
            thisUserCurrentQuestion = currentIndex + 1,
            thisUserScore = currentScore
        ) }
        SocketManager.emit("next_question", data)
        checkDone()
    }

    private fun checkDone() {
        viewModelScope.launch {
            if (_uiState.value.thisUserCurrentQuestion == 9
                && _uiState.value.opponentUserCurrentQuestion == 9) {
                if (_uiState.value.thisUserScore <= _uiState.value.opponentUserScore) {
                    _uiState.update { it.copy(resultState = ResultState.Lose) }
                } else {
                    _uiState.update { it.copy(resultState = ResultState.Win) }
                    val result = userScoreRepository.getUser(userId)
                    result.onSuccess { user ->
                        val currentScore = user.score
                        val updatedScore = currentScore + 1
                        val updatedUser = user.copy(score = updatedScore)
                        val updateResult = userScoreRepository.updateUser(userId, updatedUser)
                        updateResult.onSuccess {
                            Log.d("OnlineViewModel", "✅ Firestore đã cập nhật userScore mới thành công")
                        }.onFailure { e ->
                            Log.e("OnlineViewModel", "❌ Lỗi khi cập nhật Firestore: ${e.message}")
                        }
                    }
                }
            }
        }
    }

    private fun connectSocket() {
        if (!SocketManager.isConnected()) {
            SocketManager.connect()
            setupListeners()
        }
    }

    private fun setupListeners() {
        SocketManager.on("game_start", Emitter.Listener { args ->
            val data = args[0] as JSONObject
            val message = data.getString("message")
            val opponent = data.getString("opponent")
            val opponentUser = Json.decodeFromString<User>(opponent)
            _uiState.update {
                it.copy(
                    opponentUser = opponentUser,
                    matchingState = MatchingState.Matched
                )
            }
            Log.d("Socket", "Đối thủ: $opponent $opponentUser")
        })

        SocketManager.on("game_data", Emitter.Listener { args ->
            val data = args[0] as JSONObject
            val originalJson = data.getJSONArray("original")
            val scrambledJson = data.getJSONArray("scrambled")

            val original = List(originalJson.length()) { i -> originalJson.getString(i) }
            val scrambled = List(scrambledJson.length()) { i -> scrambledJson.getString(i) }

            _uiState.update {
                it.copy(
                    originalList = original,
                    scrambledList = scrambled
                )
            }
            Log.d("Socket", "Nhận từ: $original, scrambled: $scrambled")
            enterPlay()
        })

        SocketManager.on("opponent_next_question", Emitter.Listener { args ->
            val data = args[0] as JSONObject
            val currentQuestion = data.getInt("current_question")
            val currentScore = data.getInt("current_score")

            Log.d("Socket", "Opponent đang ở câu: $currentQuestion, điểm: $currentScore")

            _uiState.update {
                it.copy(
                    opponentUserCurrentQuestion = currentQuestion,
                    opponentUserScore = currentScore
                )
            }
            checkDone()
        })
    }

    fun joinQueue() {
        viewModelScope.launch {
            delay(2000)
            getUser(userId)
            val user = _uiState.value.thisUser
            if (user != User()) {
                val json = Json.encodeToString(user)
                val jsonObject = JSONObject(json)
                SocketManager.emit("join_queue", jsonObject)
                Log.d("JoinQueue", "Đã gửi user lên socket")
            } else {
                Log.e("JoinQueue", "User null, không thể join queue")
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        SocketManager.disconnect()
        Log.d("Challenge Online View Model", "On Clear, disconnected")
    }
}