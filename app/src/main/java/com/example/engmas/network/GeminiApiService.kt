package com.example.engmas.network

import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.Call
import retrofit2.Response

interface GeminiApiService {
    @POST("v1beta/models/gemini-pro:generateContent")
    fun generateChatResponse(@Body request: GeminiRequest): Call<GeminiResponse>
}
