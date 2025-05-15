package com.example.engmas.ui.screens.challenge.scoreboard

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.engmas.data.model.UserScore
import com.example.engmas.data.repository.NetworkUserRepository
import com.example.engmas.data.repository.NetworkUserScoreRepository
import com.example.engmas.data.repository.UserScoreRepository
import com.example.engmas.network.RetrofitClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.Query
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ScoreBoardViewModel : ViewModel() {

    private val userScoreRepository: UserScoreRepository = NetworkUserScoreRepository()
    private val firestore = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()
    private val userRepository = NetworkUserRepository(RetrofitClient.api)

    private val _players = MutableStateFlow<List<UserScore>>(emptyList())
    val players: StateFlow<List<UserScore>> = _players.asStateFlow()

    private val _currentUser = MutableStateFlow(UserScore())
    val currentUser: StateFlow<UserScore> = _currentUser.asStateFlow()

    private val _image = MutableStateFlow("")
    val image: StateFlow<String> = _image.asStateFlow()

    private var topPlayersListener: ListenerRegistration? = null

    init {
        loadTopPlayers()
    }

    fun getImage(id: String) {
        viewModelScope.launch {
            val result = userRepository.getUser(id)
            Log.d("ScoreBoard View Model", "get Image user: $result")
            _image.value = result.photoUrl
        }
    }

    private fun loadTopPlayers() {
        topPlayersListener?.remove()

        topPlayersListener = firestore.collection("users")
            .orderBy("score", Query.Direction.DESCENDING)
            .limit(10)
            .addSnapshotListener { snapshots, error ->
                if (error != null) return@addSnapshotListener

                if (snapshots != null && !snapshots.isEmpty) {
                    val top10 = snapshots.documents.mapIndexed { idx, doc ->
                        doc.toObject(UserScore::class.java)!!.copy(
                            id = doc.id,
                            rank = idx + 1
                        )
                    }
                    _players.value = top10
                }
            }
    }

    fun fetchCurrentUser() {
        val userId = auth.currentUser?.uid ?: ""
        viewModelScope.launch {
            userScoreRepository.getUserScore(userId)
                .onSuccess { user ->
                    _currentUser.value = user
                }
                .onFailure {
                    _currentUser.value = UserScore()
                }
        }
    }

    fun getTopPlayers(): List<UserScore> {
        val list = _players.value
        if (list.isEmpty()) return emptyList()

        val topPlayer = list.firstOrNull()
        val remainingPlayers = list.drop(1).take(9)

        val updatedPlayers = mutableListOf<UserScore>()
        topPlayer?.let { updatedPlayers.add(it) }
        updatedPlayers.addAll(remainingPlayers)

        return updatedPlayers
    }

    override fun onCleared() {
        super.onCleared()
        topPlayersListener?.remove()
    }
}
