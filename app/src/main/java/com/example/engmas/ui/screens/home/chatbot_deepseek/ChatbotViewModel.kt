package com.example.engmas.ui.screens.home.chatbot_deepseek

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.engmas.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChatbotViewModel(application: Application) : AndroidViewModel(application) {

    val uiState = MutableLiveData<UIState<String>>()  // Dữ liệu UIState của chatbot
    val messages = MutableLiveData<List<Pair<String, Boolean>>>(emptyList())

    // Hàm lấy API key từ strings.xml
    fun getApiKey(): String {
        return getApplication<Application>().getString(R.string.api_key)
    }

    // Hàm gửi yêu cầu API
    fun sendMessage(message: String) {
        val apiKey = getApiKey()  // Lấy API key từ strings.xml
        val jsonBody = JSONObject().apply {
            put("model", "deepseek/deepseek-r1-distill-qwen-32b:free")
            put("messages", JSONArray().put(JSONObject().apply {
                put("role", "user")
                put("content", message)
            }))
        }

        // Chuyển JSONObject thành RequestBody
        val requestBody = RequestBody.create(
            "application/json".toMediaTypeOrNull(),
            jsonBody.toString()
        )

        // Gọi API trong Coroutine
        viewModelScope.launch(Dispatchers.IO) {
            uiState.postValue(UIState.Loading)  // Trạng thái loading

            // Thêm API Key vào header của yêu cầu HTTP
            val call = RetrofitClientDeepSeek.api.sendMessage(
                apiKey = "Bearer $apiKey",  // Gửi API key trong header
                requestBody = requestBody
            )
            call.enqueue(object : Callback<ChatbotResponse> {
                override fun onResponse(call: Call<ChatbotResponse>, response: Response<ChatbotResponse>) {
                    if (response.isSuccessful) {
                        uiState.postValue(UIState.Success(response.body()?.response ?: ""))
                        messages.postValue(messages.value?.plus(Pair(response.body()?.response ?: "", false)))
                    } else {
                        uiState.postValue(UIState.Error("Có lỗi xảy ra. Vui lòng thử lại!"))
                    }
                }

                override fun onFailure(call: Call<ChatbotResponse>, t: Throwable) {
                    uiState.postValue(UIState.Error("Không thể kết nối đến máy chủ."))
                }
            })
        }
    }
}
