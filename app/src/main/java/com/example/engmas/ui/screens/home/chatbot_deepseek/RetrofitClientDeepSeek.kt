package com.example.engmas.ui.screens.home.chatbot_deepseek

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClientDeepSeek {
    private const val BASE_URL = "https://openrouter.ai/" // Thay thế bằng URL của DeepSeek

    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val api: DeepSeekApi = retrofit.create(DeepSeekApi::class.java)
}
