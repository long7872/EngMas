package com.example.engmas.data.repository

import android.util.Log
import com.example.engmas.data.model.Vocab
import com.example.engmas.network.UserApiService

interface WordRepository {
    suspend fun getRandomVocab(): Vocab
    suspend fun searchVocabs(query: String): List<Vocab>
}

class NetworkWordRepository(private val api: UserApiService): WordRepository {

    override suspend fun getRandomVocab(): Vocab {
        val response = api.getRandomVocab()
        if (response.isSuccessful) {
            return response.body() ?: throw Exception("Empty word")
        } else {
            throw Exception("Error fetching word: ${response.code()}")
        }
    }

    override suspend fun searchVocabs(query: String): List<Vocab> {
        if (query.isBlank()) return emptyList()

        val response = api.searchVocabs(query)
        Log.d("RETROFIT", "Response code: ${response.code()}")

        if (response.isSuccessful) {
            val body = response.body()
            Log.d("RETROFIT", "Body: $body")
            return body ?: emptyList()
        } else {
            val error = response.errorBody()?.string()
            Log.e("RETROFIT", "Error: ${response.code()} - $error")
            throw Exception("Error fetching words: ${response.code()} - $error")
        }
    }

}