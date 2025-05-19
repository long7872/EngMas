package com.example.engmas.ui.screens.chat.chatbot_deepseek

sealed class UIState<out T> {
    object Loading : UIState<Nothing>()  // Trạng thái khi đang tải dữ liệu
    data class Success<out T>(val data: T) : UIState<T>()  // Trạng thái thành công với dữ liệu trả về
    data class Error(val message: String) : UIState<Nothing>()  // Trạng thái lỗi
    object Idle : UIState<Nothing>() // Trạng thái mặc định, khi không có yêu cầu nào đang được xử lý
}
