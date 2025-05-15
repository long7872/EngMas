package com.example.engmas.data.repository

import android.util.Log
import com.example.engmas.data.model.Vocab
import com.example.engmas.network.EngMasApiService
import com.example.engmas.ui.screens.home.data.VocabInfo

interface VocabRepository {
    suspend fun getRandomVocab(): List<String>
    suspend fun searchVocabs(query: String): List<Vocab>
    suspend fun collectVocabInfo(vocabId: Int): VocabInfo
}

class NetworkVocabRepository(private val api: EngMasApiService): VocabRepository {

    override suspend fun getRandomVocab(): List<String> {
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

    override suspend fun collectVocabInfo(vocabId: Int): VocabInfo {
        val response = api.collectVocabInfo(vocabId)
        if (response.isSuccessful) {
            val info = response.body()
            if (info != null) return info
            else throw Exception("update favourite failed")
        } else {
            throw Exception("Error: ${response.code()} ${response.message()}")
        }
    }

}