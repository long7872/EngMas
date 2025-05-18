package com.example.engmas.ui.screens.practice.voices

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.engmas.data.repository.NetworkVoiceRepository
import com.example.engmas.network.RetrofitClient
import com.example.engmas.ui.screens.practice.PracticeUiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch
import java.io.File

class VoiceViewModel: ViewModel() {
    private val voiceRepository = NetworkVoiceRepository(RetrofitClient.api)

    private val _uiState = MutableStateFlow(VoiceUiState())
    val uiState: StateFlow<VoiceUiState> = _uiState.asStateFlow()

    fun getSentence() {
        viewModelScope.launch {
            val result = voiceRepository.getSentence()
            Log.d("Voice View Model", "function get sentences from repo: $result")
            _uiState.update { it.copy(sentences = result) }
        }
    }

    fun setSentence(expectedText: String) {
        _uiState.update { it.copy(selectedSentence = expectedText) }
    }

    fun analyzeVoice(file: File, text: String, accent: String = "us") {
        Log.d("AUDIO_PATH", "Recorded file: ${file.absolutePath}")
        viewModelScope.launch {
            try {
                val result = voiceRepository.analyzeVoice(file, text, accent)
                Log.d("Voice View Model", "function analyze voice: Input: $text, accent: $accent")
                Log.d("Voice View Model", "function analyze voice: Output: $result")
                _uiState.update { it.copy(analyzedResult = result) }
                delay(1000)
                changeScreenState(VoiceScreenState.Analysis)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun nextSentence() {
        val current = _uiState.value.currentSentence
        if (current < _uiState.value.sentences.size - 1) {
            _uiState.update { it.copy(currentSentence = current + 1) }
        }
    }

    fun previousSentence() {
        val current = _uiState.value.currentSentence
        if (current > 0) {
            _uiState.update { it.copy(currentSentence = current - 1) }
        }
    }

    fun changeScreenState(screenState: VoiceScreenState) {
        _uiState.update { it.copy(screenState = screenState) }
    }
}