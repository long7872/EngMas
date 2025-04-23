package com.example.engmas.data.repository

import com.example.engmas.data.model.UserScore
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.getValue
import kotlinx.coroutines.tasks.await

class UserScoreRepository {

    private val dbRef: DatabaseReference = FirebaseDatabase.getInstance().getReference("users")

    // Hàm lấy danh sách người chơi từ Firebase kèm theo xếp hạng
    suspend fun getPlayersWithRank(): List<UserScore> {
        return try {
            val dataSnapshot = dbRef.get().await()

            // Chuyển dữ liệu thành danh sách người chơi
            val players = dataSnapshot.children.mapNotNull { snapshot ->
                snapshot.getValue<UserScore>()?.let { userScore ->
                    userScore.copy(id = snapshot.key ?: "")
                }
            }

            // Sắp xếp danh sách người chơi theo điểm số (giảm dần)
            val sortedPlayers = players.sortedByDescending { it.score }

            // Thêm xếp hạng vào mỗi người chơi
            sortedPlayers.mapIndexed { index, userScore ->
                userScore.copy(rank = index + 1)
            }
        } catch (e: Exception) {
            emptyList<UserScore>()
        }
    }
}
