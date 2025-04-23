package com.example.engmas.ui.screens.challenge.scoreboard
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.example.engmas.data.repository.UserScoreRepository
import com.example.engmas.data.model.UserScore

class ScoreBoardViewModel : ViewModel() {

    private val userScoreRepository = UserScoreRepository()

    // StateFlow lưu danh sách người chơi
    private val _players = MutableStateFlow<List<UserScore>>(emptyList())
    val players: StateFlow<List<UserScore>> get() = _players
    private val updatedPlayers = mutableListOf<UserScore>()

    init {
        // Lấy dữ liệu khi ViewModel được tạo
        getAllPlayers()
//        getTopPlayers()
    }

    // Hàm lấy dữ liệu người chơi từ Firebase
    private fun getAllPlayers() {
        viewModelScope.launch {
            val playerList = userScoreRepository.getPlayersWithRank()

            // Cập nhật _players để UI nhận giá trị mới
            _players.value = playerList
        }
    }

    // Hàm lấy dữ liệu người chơi từ Firebase
    fun getTopPlayers(): List<UserScore> {
        val playerList = _players.value

        val topPlayer = playerList.firstOrNull()
        val remainingPlayers = playerList.drop(1).take(9)

        topPlayer?.let { updatedPlayers.add(it) }
        updatedPlayers.addAll(remainingPlayers)

        return updatedPlayers
    }


    fun getUserRank(userId: String): Int? {
        val list = _players.value
        return list.indexOfFirst { it.id == userId }.takeIf { it >= 0 }?.plus(1)
    }

    fun getUserByName(userName: String): UserScore? {
        return _players.value.find { it.name == userName }
    }


}
