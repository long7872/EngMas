package com.example.engmas.ui.screens.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.engmas.network.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ChatViewModel : ViewModel() {

    private val _messages = MutableStateFlow<List<Pair<String, Boolean>>>(emptyList())
    val messages = _messages.asStateFlow()

    fun sendMessage(userInput: String) {
        // Thêm message người dùng
        _messages.value = _messages.value + (userInput to true)

        viewModelScope.launch {
            val request = GeminiRequest(
                contents = listOf(Content(parts = listOf(Part(text = userInput))))
            )

            try {
                val response = RetrofitClientGemini.geminiApi.generateChatResponse(request).execute()
                Log.d("chat viewmodel", "$response")
                val botReply = response.body()?.candidates?.firstOrNull()?.content?.parts?.firstOrNull()?.text
                    ?: "No response"

                _messages.value = _messages.value + (botReply to false)

            } catch (e: Exception) {
                _messages.value = _messages.value + ("Error: ${e.message}" to false)
            }
        }
    }
}
