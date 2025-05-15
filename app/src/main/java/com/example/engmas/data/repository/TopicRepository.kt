package com.example.engmas.data.repository

import android.util.Log
import com.example.engmas.data.model.UserLearning
import com.example.engmas.data.model.Vocab
import com.example.engmas.network.EngMasApiService
import com.example.engmas.ui.screens.practice.courses.data.UpdateStatusRequest
import com.example.engmas.ui.screens.practice.vocabulary.model.TopicProgress
import com.example.engmas.ui.screens.practice.vocabulary.model.VocabsInTopic

interface TopicRepository {
    suspend fun insertUserLearning(userLearning: UserLearning): Result<Unit>
    suspend fun updateUserLearning(userLearning: UserLearning): Result<Unit>
    suspend fun syncStatus(request: UpdateStatusRequest): String
    suspend fun getTopicLearning(userId: String): List<TopicProgress>
    suspend fun getVocabsInTopic(topicId: Int, userId: String): VocabsInTopic
}

class NetworkTopicRepository(private val api: EngMasApiService): TopicRepository {
    override suspend fun insertUserLearning(userLearning: UserLearning): Result<Unit> {
        return try {
            val response = api.insertUserLearning(userLearning)
            if (response.isSuccessful) {
                Result.success(Unit)  // Thành công
            } else {
                Result.failure(Exception("Error inserting user learning: ${response.code()}"))  // Lỗi
            }
        } catch (e: Exception) {
            Result.failure(e)  // Xử lý exception
        }
    }

    override suspend fun updateUserLearning(userLearning: UserLearning): Result<Unit> {
        return try {
            val response = api.updateUserLearning(userLearning)
            if (response.isSuccessful) {
                Result.success(Unit)  // Thành công
            } else {
                Result.failure(Exception("Error updating user learning: ${response.code()}"))  // Lỗi
            }
        } catch (e: Exception) {
            Result.failure(e)  // Xử lý exception
        }
    }

    override suspend fun getTopicLearning(userId: String): List<TopicProgress> {
        val response = api.getTopicLearning(userId)
        if (response.isSuccessful) {
            return response.body() ?: throw Exception("Empty Topic Learning")
        } else {
            throw Exception("Error fetching word: ${response.code()}")
        }
    }

    override suspend fun syncStatus(request: UpdateStatusRequest): String {
        Log.d("Course Repository", "function syncStatus: Parameters: $request")
        val response = api.updateAllLearningStatus(request)
        if (response.isSuccessful) {
            val info = response.body()?.string()
            if (info != null) return info
            else throw Exception("sync status failed")
        } else {
            throw Exception("Error: ${response.code()} ${response.message()}")
        }
    }

    override suspend fun getVocabsInTopic(topicId: Int, userId: String): VocabsInTopic {
        val response = api.getVocabsTopicLearning(topicId, userId)
        if (response.isSuccessful) {
            return response.body() ?: throw Exception("Empty Topic Learning")
        } else {
            throw Exception("Error fetching word: ${response.code()}")
        }
    }
}