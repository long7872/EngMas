package com.example.engmas.ui.screens.home.chatbot_deepseek

import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface DeepSeekApi {

    @Headers(
        "Content-Type: application/json"
    )
    @POST("api/v1/chat/completions")  // Đường dẫn endpoint
    fun sendMessage(
        @Header("Authorization") apiKey: String,  // Thêm Authorization header
        @Body requestBody: RequestBody             // Dữ liệu yêu cầu
    ): Call<ChatbotResponse>
}
