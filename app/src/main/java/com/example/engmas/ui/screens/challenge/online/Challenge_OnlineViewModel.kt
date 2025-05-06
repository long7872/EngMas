package com.example.engmas.ui.screens.challenge.online

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.engmas.coroutine.AppCoroutineScope
import com.example.engmas.data.model.Challenge
import com.example.engmas.data.model.UserStatus
import com.example.engmas.data.repository.ChallengeRepository
import com.example.engmas.data.repository.NetworkUserRepository
import com.example.engmas.data.repository.NetworkUserScoreRepository
import com.example.engmas.data.repository.NetworkWordRepository
import com.example.engmas.network.RetrofitClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlin.random.Random

class Challenge_OnlineViewModel: ViewModel() {
    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()
    private val userRepository = NetworkUserRepository(RetrofitClient.api)
    private val wordRepository = NetworkWordRepository(RetrofitClient.api)
    private val challengeRepository = ChallengeRepository()
    private val userScoreRepository = NetworkUserScoreRepository()

    private val _uiState = MutableStateFlow(Challenge_OnlineUiState())
    val uiState: StateFlow<Challenge_OnlineUiState> = _uiState.asStateFlow()

    init {
        enterMatching()
    }

    private fun enterMatching() {
        val userId = auth.currentUser?.uid ?: return
        _uiState.update {
            it.copy(
                matchingState = MatchingState.Matching
            )
        }

        viewModelScope.launch {
            try {
                userRepository.updateUserStatus(userId, UserStatus.Matching.name)
                val thisUser = userRepository.getUser(userId)
                _uiState.update {
                    it.copy(
                        thisUser = thisUser
                    )
                }
                delay(500)
                startMatching(userId)
            } catch (e: Exception) {
                Log.e(null , "enterMatching: ${e.message}")
            }
            Log.e("Game" , "enterMatching")
        }
    }

    fun startMatching(userId: String) {
        Log.e("Game" , "startMatching")
        viewModelScope.launch {
            while (true) {
                try {
                    val users = userRepository.getAllUsers()

                    var matchedUser = users.firstOrNull {
                        it.userId != userId && it.status == UserStatus.Matching.name
                    }

                    if (matchedUser != null) {
                        userRepository.updateUserStatus(matchedUser.userId, userId)
                        var thisUserStatus = userRepository.getUser(userId).status

                        if (thisUserStatus != UserStatus.Matching.name) {
                            matchedUser = userRepository.getUser(thisUserStatus)
                            userRepository.updateUserStatus(matchedUser.userId, userId)
                        }

                        thisUserStatus = userRepository.getUser(userId).status
                        val otherUserStatus = userRepository.getUser(matchedUser.userId).status
                        if (thisUserStatus == matchedUser.userId
                            && otherUserStatus == userId
                        ) {

                            _uiState.update {
                                it.copy(matchedUser = matchedUser)
                            }
                            delay(2000)
                            val success1 = userRepository.updateUserStatus(userId, UserStatus.Matched.name)
                            val success2 = userRepository.updateUserStatus(
                                matchedUser.userId,
                                UserStatus.Matched.name
                            )
                            Log.e("Game" , "Match opponent")
                            if (success1 && success2) {
                                generateGame(userId, matchedUser.userId)
                                enterMatched()
                                break
                            }
                        }
                    }

                } catch (e: Exception) {
                    Log.e("Game", e.message.toString())
                }

                delay(1000)
            }
        }
    }

    private fun enterMatched() {
        _uiState.update {
            it.copy(matchingState = MatchingState.Matched)
        }

        val userId = auth.currentUser?.uid ?: return
        viewModelScope.launch {
            delay(2000)
            try {
                // 1. Update trạng thái người chơi hiện tại thành Played
                userRepository.updateUserStatus(userId, UserStatus.Play.name)

                // 2. Lặp cho đến khi đối thủ cũng là Played
                while (true) {
                    val matchedUserId = _uiState.value.matchedUser.userId
                    val allUsers = userRepository.getAllUsers()
                    val matchedUser = allUsers.firstOrNull { it.userId == matchedUserId }

                    if (matchedUser?.status == UserStatus.Play.name) {
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
        Log.e("Game" , "generate")
        viewModelScope.launch {
            createChallenge(userId, matchedUserId)
            observeChallenge(userId, matchedUserId)
            if (userId < matchedUserId) {
                Log.d("Game", "Host")
                repeat(10) {
                    getUnscrambleWord(seed)
                    delay(100) // delay nhỏ để tránh API bị overload, nếu cần
                    Log.d("Unscramble", "OriginalList: ${_uiState.value.originalList}," +
                            " ScrambledList: ${_uiState.value.unscrambleList}")
                }
            } else {
                Log.d("Game", "Not Host")
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
                challengeRepository.uploadWordList(seed, updatedOriginalList, updatedUnscrambleList)
                it.copy(
                    originalList = updatedOriginalList,
                    unscrambleList = updatedUnscrambleList
                )
            }
        } catch (e: Exception) {
            Log.e("Unscramble", "Error: ${e.message}")
        }
    }

    fun nextQuestion(answer: String = "") {
        val currentProgress = _uiState.value.thisUserCurrentQuestion
        val nextProgress = currentProgress + 1
        _uiState.update {
            it.copy(thisUserCurrentQuestion = nextProgress)
        }

        if (nextProgress < 10) {
            val myId = auth.currentUser?.uid ?: return
            val otherId = _uiState.value.matchedUser.userId
            val challengeId = generateSeed(myId, otherId)

            challengeRepository.updateProgress(challengeId, myId, nextProgress)

            if (answer != "") {
                viewModelScope.launch {
//                    val isTrue = _uiState.value.originalList[currentProgress] == answer
                    val isTrue = true
                    if (isTrue) {
                        val currentScore = _uiState.value.thisUserScore + 1
                        _uiState.update {
                            it.copy(thisUserScore = currentScore)
                        }

                        challengeRepository.updateScore(challengeId, myId, currentScore)
                    }
                }
            }
        }
    }

    fun doneChallenge() {
        val myId = auth.currentUser?.uid ?: return
        val otherId = _uiState.value.matchedUser.userId
        val challengeId = generateSeed(myId, otherId)
        challengeRepository.deleteChallenge(challengeId)
    }

    // create new challenge while 2 player matched
    private suspend fun createChallenge(userId1: String, userId2: String) {
        val challengeId = generateSeed(userId1, userId2)  // Challenge ID

        val challenge = Challenge(
            challengeId = challengeId,
            player1Id = userId1,
            player2Id = userId2,
            player1Progress = 0,
            player2Progress = 0,
            player1Score = 0,
            player2Score = 0,
            player1Status = ResultState.None.name,
            player2Status = ResultState.None.name
        )

        // Firestore auto create challenges
        firestore.collection("challenges")
            .document(challengeId)  // Unique Key
            .set(challenge)      // Add Data
            .addOnSuccessListener {
                Log.d("Firestore", "Challenge created successfully!")
            }
            .addOnFailureListener { e ->
                Log.e("Firestore", "Error creating challenge: ${e.message}")
            }
            .await()
    }

    private fun observeChallenge(userId: String, otherId: String) {
        val challengeId = generateSeed(userId, otherId)
        Log.d("OnlineViewModel", "State ${_uiState.value}")
        firestore
            .collection("challenges")
            .document(challengeId)
            .addSnapshotListener { snapshot, _ ->
                Log.d("OnlineViewModel", "State ${_uiState.value}")
                if (snapshot != null && snapshot.exists()) {
                    val challenge = snapshot.toObject(Challenge::class.java)
                    val myId = auth.currentUser?.uid ?: return@addSnapshotListener

                    var myProgress= 0
                    var opponentProgress= 0
                    var myScore = 0
                    var opponentScore = 0
                    var wordList = listOf("")
                    var scrambleWordList = listOf("")
                    if (challenge != null) {
                        wordList = challenge.wordList
                        scrambleWordList = challenge.scrambleWordList
                        if (challenge.player1Id == myId) {
                            myProgress = challenge.player1Progress
                            myScore = challenge.player1Score
                            opponentProgress = challenge.player2Progress
                            opponentScore = challenge.player2Score
                        } else if (challenge.player2Id == myId) {
                            myProgress = challenge.player2Progress
                            myScore = challenge.player2Score
                            opponentProgress = challenge.player1Progress
                            opponentScore = challenge.player1Score
                        } else {
                            Log.e("observeChallenge", "User ID not found in challenge!")
                        }
                    }

                    _uiState.update {
                        it.copy(
                            thisUserCurrentQuestion = myProgress,
                            otherUserCurrentQuestion = opponentProgress,
                            thisUserScore = myScore,
                            otherUserScore = opponentScore
                        )
                    }

                    if (userId > otherId) {
                        _uiState.update {
                            it.copy(
                                originalList = wordList,
                                unscrambleList = scrambleWordList,
                            )
                        }
                    }

//                    Log.d("OnlineViewModel", "State ${_uiState.value}")

                    val thisCurrentProgress = _uiState.value.thisUserCurrentQuestion
                    val otherCurrentProgress = _uiState.value.otherUserCurrentQuestion
                    if (thisCurrentProgress == 9 && otherCurrentProgress == 9) {
                        val thisScore = _uiState.value.thisUserScore
                        val otherScore = _uiState.value.otherUserScore
                        _uiState.update {
                            it.copy(
                                resultState = if (thisScore < otherScore) {
                                    ResultState.Lose
                                }
                                else {
                                    ResultState.Win
                                }
                            )
                        }
                        if (_uiState.value.resultState == ResultState.Win) {
                            AppCoroutineScope.scope.launch {
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
                        Log.e("Game", "${_uiState.value.resultState}")
                    }
                }
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