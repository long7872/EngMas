package com.example.engmas.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit

object RetrofitClientGemini {
    private const val BASE_URL_GEMINI = "https://generativelanguage.googleapis.com/" // Đúng base URL của Gemini API

    private val json = Json {
        ignoreUnknownKeys = true
        encodeDefaults = true
        coerceInputValues = true
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val request: Request = chain.request().newBuilder()
                .addHeader("Authorization", "Bearer AIzaSyARZhj49ynYXFDRiChknpEzGXMWJSCubXs")
                .build()
            chain.proceed(request)
        }
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL_GEMINI)
        .client(okHttpClient)
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()

    val geminiApi: GeminiApiService by lazy {
        retrofit.create(GeminiApiService::class.java)
    }
}
