package com.example.engmas.ui.screens.challenge.online

import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.engmas.data.model.User
import com.example.engmas.data.model.UserStatus
import com.example.engmas.data.repository.NetworkUserRepository
import com.example.engmas.data.repository.NetworkWordRepository
import com.example.engmas.network.RetrofitClient
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.random.Random

class Challenge_OnlineViewModel: ViewModel() {
    private val auth = FirebaseAuth.getInstance()
    private val userRepository = NetworkUserRepository(RetrofitClient.userApi)
    private val wordRepository = NetworkWordRepository(RetrofitClient.userApi)

    private val _uiState = MutableStateFlow(Challenge_OnlineUiState())
    val uiState: StateFlow<Challenge_OnlineUiState> = _uiState.asStateFlow()

    init {
        enterMatching()
    }

    fun enterMatching() {
        _uiState.update {
            it.copy(
                matchingState = MatchingState.Matching
            )
        }
        val userId = auth.currentUser?.uid ?: return

        viewModelScope.launch {
            try {
                userRepository.updateUserStatus(userId, UserStatus.Matching.name)
            } catch (e: Exception) {
                Log.e(null , "enterMatching: ${e.message}")
            }
        }
    }

    fun startMatching(userId: String) {
        viewModelScope.launch {
            while (true) {
                try {
                    val users = userRepository.getAllUsers()

                    val matchedUser = users.firstOrNull {
                        it.userId != userId && it.status == UserStatus.Matching.name
                    }

                    _uiState.update {
                        it.copy(matchedUser = matchedUser ?: User())
                    }

                    if (matchedUser != null) {
                        val success1 = userRepository.updateUserStatus(userId, UserStatus.Matched.name)
                        val success2 = userRepository.updateUserStatus(matchedUser.userId, UserStatus.Matched.name)

                        if (success1 && success2) {
                            generateGame(userId, matchedUser.userId)
                            enterMatched()
                            break
                        }
                    }

                } catch (e: Exception) {
                    // Xử lý lỗi nếu cần
                }

                delay(3000)
            }
        }
    }

    fun enterMatched() {
        _uiState.update {
            it.copy(matchingState = MatchingState.Matched)
        }

        val userId = auth.currentUser?.uid ?: return
        viewModelScope.launch {
            delay(2000)
            try {
                // 1. Update trạng thái người chơi hiện tại thành Played
                userRepository.updateUserStatus(userId, UserStatus.Played.name)

                // 2. Lặp cho đến khi đối thủ cũng là Played
                while (true) {
                    val matchedUserId = _uiState.value.matchedUser.userId
                    val allUsers = userRepository.getAllUsers()
                    val matchedUser = allUsers.firstOrNull { it.userId == matchedUserId }

                    if (matchedUser?.status == UserStatus.Played.name) {
                        // 3. Khi cả 2 là Played, bắt đầu game
                        _uiState.update {
                            it.copy(matchingState = MatchingState.Play)
                        }
                        break
                    }

                    delay(2000)
                }

            } catch (e: Exception) {
                Log.e("enterMatched", "Lỗi khi chuyển sang trạng thái Played: ${e.message}")
            }


            _uiState.update {
                it.copy(matchingState = MatchingState.Play)
            }
        }
    }

    private fun generateGame(userId: String, matchedUserId: String) {
        val seed = generateSeed(userId, matchedUserId)

        viewModelScope.launch {
            repeat(10) {
                getUnscrambleWord(seed)
                delay(100) // delay nhỏ để tránh API bị overload, nếu cần
                Log.d("Unscramble", "OriginalList: ${_uiState.value.originalList}," +
                        " ScrambledList: ${_uiState.value.unscrambleList}")
            }
        }
    }

    private suspend fun getUnscrambleWord(seed: String) {
        try {
            val vocab = wordRepository.getRandomVocab()
            val word = vocab.word
            val scrambled = shuffleWithSeed(word, seed)

            // Cập nhật state/UI nếu bạn dùng StateFlow/Livedata
            Log.d("Unscramble", "Original: $word, Scrambled: $scrambled")

            _uiState.update {
                val updatedOriginalList = it.originalList.toMutableList()
                val updatedUnscrambleList = it.unscrambleList.toMutableList()
                if (updatedOriginalList.size >= 10) updatedOriginalList.removeAt(10)
                if (updatedUnscrambleList.size >= 10) updatedUnscrambleList.removeAt(10)
                updatedOriginalList.add(word)
                updatedUnscrambleList.add(scrambled)
                it.copy(
                    originalList = updatedOriginalList,
                    unscrambleList = updatedUnscrambleList
                )
            }
        } catch (e: Exception) {
            Log.e("Unscramble", "Error: ${e.message}")
        }
    }

    fun nextQuestion() {
        _uiState.update {
            it.copy(thisUserCurrentQuestion = uiState.value.thisUserCurrentQuestion + 1)
        }
    }

    fun updateWinState() {
        _uiState.update {
            it.copy(
                matchingState = MatchingState.Matching,
                resultState = ResultState.Win
            )
        }
    }
    fun updateLoseState() {
        _uiState.update {
            it.copy(
                matchingState = MatchingState.Matching,
                resultState = ResultState.Lose
            )
        }
    }
    fun resetResultState() {
        _uiState.update {
            it.copy(
                resultState = ResultState.None
            )
        }
    }

    private fun generateSeed(userId1: String, userId2: String): String {
        return listOf(userId1, userId2).sorted().joinToString(" ")
    }

    private fun shuffleWithSeed(word: String, seedString: String): String {
        // Chuyển đổi String thành Long seed bằng cách sử dụng hashCode
        val seed = seedString.hashCode().toLong()

        val chars = word.toCharArray().toMutableList()
        chars.shuffle(Random(seed))
        return chars.joinToString("")
    }
}