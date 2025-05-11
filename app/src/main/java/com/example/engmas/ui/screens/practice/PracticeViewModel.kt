package com.example.engmas.ui.screens.practice

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.engmas.data.repository.NetworkCourseRepository
import com.example.engmas.network.RetrofitClient
import com.example.engmas.ui.screens.practice.vocabulary.VocabularyUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PracticeViewModel: ViewModel() {
    private val courseRepository = NetworkCourseRepository(RetrofitClient.api)

    private val _uiState = MutableStateFlow(PracticeUiState())
    val uiState: StateFlow<PracticeUiState> = _uiState.asStateFlow()

    fun getCourses() {
        viewModelScope.launch {
            val list = courseRepository.getAllCourses()
            _uiState.update { it.copy(courseList = list) }
            Log.d("Practice View Model", "course list response: $list")
        }
    }

}