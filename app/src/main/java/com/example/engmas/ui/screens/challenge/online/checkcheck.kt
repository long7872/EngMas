//package com.example.engmas.ui.screens.challenge.online
//
//import android.util.Log
//import androidx.compose.runtime.collectAsState
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import com.example.engmas.data.model.Challenge
//import com.example.engmas.data.model.User
//import com.example.engmas.data.model.UserStatus
//import com.example.engmas.data.repository.ChallengeRepository
//import com.example.engmas.data.repository.NetworkUserRepository
//import com.example.engmas.data.repository.NetworkWordRepository
//import com.example.engmas.network.RetrofitClient
//import com.google.firebase.auth.FirebaseAuth
//import com.google.firebase.firestore.FirebaseFirestore
//import kotlinx.coroutines.delay
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.StateFlow
//import kotlinx.coroutines.flow.asStateFlow
//import kotlinx.coroutines.flow.update
//import kotlinx.coroutines.launch
//import kotlinx.coroutines.tasks.await
//import kotlin.random.Random
//import androidx.compose.foundation.Image
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.layout.ContentScale
//import coil.compose.AsyncImage
//import coil.compose.rememberAsyncImagePainter
//import coil.compose.rememberImagePainter
//
//@Composable
//fun DisplayImageFromGoogleDrive() {
//    val imageUrl = "https://drive.google.com/uc?export=view&id=1GUPPO1kPiGfecoIBePcCU0tbs4hKtkT2"
//
//    Image(
//        painter = rememberAsyncImagePainter(imageUrl),
//        contentDescription = "Image from Google Drive",
//        contentScale = ContentScale.Crop
//    )
//    AsyncImage(
//        model = imageUrl, null
//    )
//}
//
//@Preview
//@Composable
//fun PreviewImage() {
//    DisplayImageFromGoogleDrive()
//}
//
//
//class Check: ViewModel() {
//    private val auth = FirebaseAuth.getInstance()
//    private val firestore = FirebaseFirestore.getInstance()
//    private val userRepository = NetworkUserRepository(RetrofitClient.userApi)
//    private val wordRepository = NetworkWordRepository(RetrofitClient.userApi)
//    private val challengeRepository = ChallengeRepository()
//
//    private val _uiState = MutableStateFlow(Challenge_OnlineUiState())
//    val uiState: StateFlow<Challenge_OnlineUiState> = _uiState.asStateFlow()
//
//    init {
//        enterMatching()
//    }
//
//    private fun enterMatching() {
//        _uiState.update {
//            it.copy(
//                matchingState = MatchingState.Matching
//            )
//        }
//        val userId = auth.currentUser?.uid ?: return
//
//        viewModelScope.launch {
//            try {
//                userRepository.updateUserStatus(userId, UserStatus.Matching.name)
//            } catch (e: Exception) {
//                Log.e(null , "enterMatching: ${e.message}")
//            }
//        }
//    }
//
//    fun startMatching(userId: String) {
//        viewModelScope.launch {
//            while (true) {
//                try {
//                    val users = userRepository.getAllUsers()
//
//                    var matchedUser = users.firstOrNull {
//                        it.userId != userId && it.status == UserStatus.Matching.name
//                    }
//
//                    if (matchedUser != null) {
//                        userRepository.updateUserStatus(matchedUser.userId, userId)
//                        var thisUserStatus = userRepository.getUser(userId).status
//
//                        if (thisUserStatus != UserStatus.Matching.name) {
//                            matchedUser = userRepository.getUser(thisUserStatus)
//                            userRepository.updateUserStatus(matchedUser.userId, userId)
//                        }
//
//                        thisUserStatus = userRepository.getUser(userId).status
//                        val otherUserStatus = userRepository.getUser(matchedUser.userId).status
//                        if (thisUserStatus == matchedUser.userId
//                            && otherUserStatus == userId) {
//
//                            _uiState.update {
//                                it.copy(matchedUser = matchedUser)
//                            }
//                            userRepository.updateUserStatus(userId, UserStatus.Matched.name)
//                            userRepository.updateUserStatus(matchedUser.userId, UserStatus.Matched.name)
////                            generateGame(userId, matchedUser.userId)
//                            enterMatched()
//                            break
//                        }
//
////                        if (success1 && success2) {
////                            delay(2000)
////                            generateGame(userId, matchedUser.userId)
////                            enterMatched()
////                            break
////                        }
//                    }
//
//                } catch (e: Exception) {
//                    // Xử lý lỗi nếu cần
//                }
//
//                delay(3000)
//            }
//        }
//    }
//
//    private fun generateGame(userId: String, matchedUserId: String) {
//        Log.d("Game", "generate")
//        val seed = generateSeed(userId, matchedUserId)
//
//        viewModelScope.launch {
//            createChallenge(userId, matchedUserId)
//            observeChallenge(seed)
//            val wordList = challengeRepository.fetchWordList(seed)
//            val scrambledList = wordList.map { shuffleWithSeed(it, seed) }
//            _uiState.update {
//                it.copy(
//                    originalList = wordList,
//                    unscrambleList = scrambledList
//                )
//            }
//        }
//
//        Log.d("Unscramble", "OriginalList: ${_uiState.value.originalList}," +
//                " ScrambledList: ${_uiState.value.unscrambleList}")
//    }
//
//    private fun enterMatched() {
//        _uiState.update {
//            it.copy(matchingState = MatchingState.Matched)
//        }
//
//        val userId = auth.currentUser?.uid ?: return
//        viewModelScope.launch {
//            try {
//                while (true) {
//                    val thisUser = userRepository.getUser(userId)
//                    val matchedUserId = _uiState.value.matchedUser.userId
//                    val matchedUser = userRepository.getUser(matchedUserId)
//
//                    // Nếu cả 2 đã matched
//                    if (thisUser.status == UserStatus.Matched.name
//                        && matchedUser.status == UserStatus.Matched.name) {
//                        if (userId < matchedUserId) { // máy có ID nhỏ hơn làm chủ động
//                            userRepository.updateUserStatus(userId, UserStatus.Play.name)
//                            userRepository.updateUserStatus(matchedUserId, UserStatus.Play.name)
//                        }
//                    }
//
//                    // Kiểm tra khi cả hai đã là Played
//                    if (thisUser.status == UserStatus.Play.name && matchedUser.status == UserStatus.Play.name) {
//                        _uiState.update {
//                            it.copy(matchingState = MatchingState.Play)
//                        }
//
//                        generateGame(userId, matchedUser.userId)
//                        break
//                    }
//
//                    delay(2000)
//                }
//            } catch (e: Exception) {
//                Log.e("enterMatched", "Lỗi khi chuyển sang trạng thái Played: ${e.message}")
//            }
//        }
//    }
//
//    fun nextQuestion(answer: String = "") {
//        val currentProgress = _uiState.value.thisUserCurrentQuestion
//        val nextProgress = currentProgress + 1
//        _uiState.update {
//            it.copy(thisUserCurrentQuestion = nextProgress)
//        }
//
//        val myId = auth.currentUser?.uid ?: return
//        val otherId = _uiState.value.matchedUser.userId
//        val challengeId = generateSeed(myId, otherId)
//
//        challengeRepository.updateProgress(challengeId, myId, nextProgress)
//
//        if (answer != "") {
//            viewModelScope.launch {
//                val isTrue = _uiState.value.originalList[currentProgress] == answer
//                if (isTrue) {
//                    val currentScore = _uiState.value.thisUserScore + 1
//                    _uiState.update {
//                        it.copy(thisUserScore = currentScore)
//                    }
//
//                    challengeRepository.updateScore(challengeId, myId, currentScore)
//                }
//            }
//        }
//    }
//
//    fun updateWinState() {
//        _uiState.update {
//            it.copy(
//                matchingState = MatchingState.Matching,
//                resultState = ResultState.Win
//            )
//        }
//    }
//    fun updateLoseState() {
//        _uiState.update {
//            it.copy(
//                matchingState = MatchingState.Matching,
//                resultState = ResultState.Lose
//            )
//        }
//    }
//    fun resetResultState() {
//        _uiState.update {
//            it.copy(
//                resultState = ResultState.None
//            )
//        }
//    }
//
//    // create new challenge while 2 player matched
//    private suspend fun createChallenge(userId1: String, userId2: String) {
//        val challengeId = generateSeed(userId1, userId2)  // Challenge ID
//
//        val challenge = Challenge(
//            challengeId = challengeId,
//            player1Id = userId1,
//            player2Id = userId2,
//            player1Progress = 0,
//            player2Progress = 0,
//            player1Score = 0,
//            player2Score = 0,
//            player1Status = ResultState.None.name,
//            player2Status = ResultState.None.name
//        )
//
//        // Firestore auto create challenges
//        firestore.collection("challenges")
//            .document(challengeId)  // Unique Key
//            .set(challenge)      // Add Data
//            .addOnSuccessListener {
//                Log.d("Firestore", "Challenge created successfully!")
//            }
//            .addOnFailureListener { e ->
//                Log.e("Firestore", "Error creating challenge: ${e.message}")
//            }
//            .await()
//
//        val isHost = userId1 < userId2
//        val wordList = mutableListOf<String>()
//        var wordsFetch: List<String>
//
//        if (isHost) {
//            // Host create list
//            repeat(10) {
//                val word = wordRepository.getRandomVocab().word
//                wordList.add(word)
//            }
//            Log.d("in Host", "word list: $wordList")
//            challengeRepository.uploadWordList(challengeId, wordList)
//        } else {
//            // Joiner wait and get list
//            do {
//                delay(500)
//                wordsFetch = challengeRepository.fetchWordList(challengeId)
//                Log.d("in Host", "word fetcg: $wordsFetch")
//            } while (wordsFetch.isEmpty())
//        }
//    }
//
//    private fun observeChallenge(challengeId: String) {
//        firestore
//            .collection("challenges")
//            .document(challengeId)
//            .addSnapshotListener { snapshot, _ ->
//                if (snapshot != null && snapshot.exists()) {
//                    val challenge = snapshot.toObject(Challenge::class.java)
//                    val myId = auth.currentUser?.uid ?: return@addSnapshotListener
//
//                    var myProgress= 0
//                    var opponentProgress= 0
//                    if (challenge != null) {
//                        if (challenge.player1Id == myId) {
//                            myProgress = challenge.player1Progress
//                            opponentProgress = challenge.player2Progress
//                        } else if (challenge.player2Id == myId) {
//                            myProgress = challenge.player2Progress
//                            opponentProgress = challenge.player1Progress
//                        } else {
//                            Log.e("observeChallenge", "User ID not found in challenge!")
//                        }
//                    }
//
//                    _uiState.update {
//                        it.copy(
//                            thisUserCurrentQuestion = myProgress,
//                            otherUserCurrentQuestion = opponentProgress,
//                        )
//                    }
//                }
//            }
//    }
//
//    private fun generateSeed(userId1: String, userId2: String): String {
//        return listOf(userId1, userId2).sorted().joinToString(" ")
//    }
//
//    private fun shuffleWithSeed(word: String, seedString: String): String {
//        // Chuyển đổi String thành Long seed bằng cách sử dụng hashCode
//        val seed = seedString.hashCode().toLong()
//
//        val chars = word.toCharArray().toMutableList()
//        chars.shuffle(Random(seed))
//        return chars.joinToString("")
//    }
//
//}