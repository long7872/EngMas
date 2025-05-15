package com.example.engmas.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

object RetrofitClient {
//    private const val BASE_URL = "https://engmasserver-production.up.railway.app/"
    private const val BASE_URL = "http://10.0.2.2:3000/"

    private val json = Json {
        ignoreUnknownKeys = true
        encodeDefaults = true
        coerceInputValues = true
    }

    private val logging = okhttp3.logging.HttpLoggingInterceptor().apply {
        level = okhttp3.logging.HttpLoggingInterceptor.Level.BODY
    }

    private val client = okhttp3.OkHttpClient.Builder()
        .addInterceptor(logging)
        .build()

    private val retrofit = Retrofit.Builder()
        .client(client)
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(BASE_URL)
        .build()

    val api: EngMasApiService by lazy {
        retrofit.create(EngMasApiService::class.java)
    }
}