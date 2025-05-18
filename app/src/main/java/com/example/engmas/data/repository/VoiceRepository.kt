package com.example.engmas.data.repository

import android.util.Log
import com.example.engmas.network.EngMasApiService
import com.example.engmas.ui.screens.practice.voices.data.VoiceAnalysisResponse
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

interface VoiceRepository {
    suspend fun getSentence(): List<String>
    suspend fun analyzeVoice(file: File, text: String, accent: String): VoiceAnalysisResponse
}

class NetworkVoiceRepository(private val api: EngMasApiService): VoiceRepository {
    override suspend fun getSentence(): List<String> {
        val response = api.getSentences()
        if (response.isSuccessful) {
            val list = response.body()
            if (list != null) return list
            else throw Exception("Cannot fetch sentence list from server")
        } else {
            throw Exception("Error: ${response.code()} ${response.message()}")
        }
    }

    override suspend fun analyzeVoice(file: File, text: String, accent: String): VoiceAnalysisResponse {
        val audioPart = MultipartBody.Part.createFormData(
            "audio",
            file.name,
            file.asRequestBody("audio/wav".toMediaTypeOrNull())
        )
        val textPart = text.toRequestBody("text/plain".toMediaTypeOrNull())
        val accentPart = accent.toRequestBody("text/plain".toMediaTypeOrNull())
        Log.d("Voice Repository", "function analyze voice: Input: $textPart, accent: $accentPart")

        val response = api.analyzeVoice(audioPart, textPart, accentPart)
        if (response.isSuccessful) {
            val info = response.body()
            info?.metadata?.predictedText?.let { Log.d("RECOGNIZED", it) }
            info?.pronunciation?.expectedText?.let { Log.d("EXPECTED", it) }
            if (info != null) return info
            else throw Exception("Cannot get info about voice result from server")
        } else {
            throw Exception("Error: ${response.code()} ${response.message()}")
        }
    }
}