package com.example.engmas.ui.screens.challenge.online

import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class Challenge_OnlineViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(Challenge_OnlineUiState())
    val uiState: StateFlow<Challenge_OnlineUiState> = _uiState.asStateFlow()

    init {
        enterMatching()
    }

    fun enterMatching() {
        _uiState.update {
            it.copy(
                matchingState = MatchingState.Matching
            )
        }
    }
    fun enterMatched() {
        _uiState.update {
            it.copy(
                matchingState = MatchingState.Matched
            )
        }
    }

    fun updateWinState() {
        _uiState.update {
            it.copy(
                matchingState = MatchingState.Matching,
                resultState = ResultState.Win
            )
        }
    }
    fun updateLoseState() {
        _uiState.update {
            it.copy(
                matchingState = MatchingState.Matching,
                resultState = ResultState.Lose
            )
        }
    }
    fun resetResultState() {
        _uiState.update {
            it.copy(
                resultState = ResultState.None
            )
        }
    }
}