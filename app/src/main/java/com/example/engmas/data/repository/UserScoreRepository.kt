package com.example.engmas.data.repository

import android.util.Log
import com.example.engmas.data.model.UserScore
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await

interface UserScoreRepository {
    suspend fun createUser(user: UserScore): Result<UserScore>
    suspend fun getUser(userId: String): Result<UserScore>
    suspend fun updateUser(userId: String, userScore: UserScore): Result<Unit>
    suspend fun deleteUser(userId: String): Result<Unit>

    suspend fun getTop10Players(): Result<List<UserScore>>
    suspend fun getUserScore(userId: String): Result<UserScore>
}

class NetworkUserScoreRepository : UserScoreRepository {
    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()

    override suspend fun createUser(user: UserScore): Result<UserScore> {
        return try {
            val userId = auth.currentUser?.uid ?: return Result.failure(Exception("User not logged in"))
            val userDocRef = firestore.collection("users").document(userId)
            userDocRef.set(user).await()
            Result.success(user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getUser(userId: String): Result<UserScore> {
        return try {
            val userDocRef = firestore.collection("users").document(userId)
            val snapshot = userDocRef.get().await()
            if (snapshot.exists()) {
                val user = snapshot.toObject(UserScore::class.java)
                Log.d("GetUser", user.toString())
                Result.success(user ?: UserScore())
            } else {
                Result.failure(Exception("User not found"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateUser(userId: String, userScore: UserScore): Result<Unit> {
        return try {
            firestore.collection("users").document(userId)
                .update("name", userScore.name,
                    "score", userScore.score,
                    "streak", userScore.streak,
                    "currentWeekStats", userScore.currentWeekStats,
                    "previousWeekStats", userScore.previousWeekStats)
                .await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteUser(userId: String): Result<Unit> {
        return try {
            firestore.collection("users").document(userId).delete().await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getTop10Players(): Result<List<UserScore>> {
        return try {
            val snapshots = firestore.collection("users")
                .orderBy("score", Query.Direction.DESCENDING)
                .limit(10)
                .get()
                .await()

            val top10 = snapshots.documents.mapIndexed { idx, doc ->
                doc.toObject(UserScore::class.java)!!.copy(
                    id = doc.id,
                    rank = idx + 1
                )
            }
            Result.success(top10)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getUserScore(userId: String): Result<UserScore> {
        return try {
            val snapshot = firestore.collection("users").document(userId).get().await()
            if (!snapshot.exists()) {
                return Result.failure(Exception("User not found"))
            }
            val user = snapshot.toObject(UserScore::class.java)!!.copy(id = snapshot.id)

            val higherCount = firestore.collection("users")
                .whereGreaterThan("score", user.score)
                .get()
                .await()
                .size()

            Result.success(user.copy(rank = higherCount + 1))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
