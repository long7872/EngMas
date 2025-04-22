package com.example.engmas.ui.screens.home

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.engmas.data.model.UserStatus
import com.example.engmas.data.repository.NetworkUserRepository
import com.example.engmas.data.repository.NetworkWordRepository
import com.example.engmas.network.RetrofitClient
import com.example.engmas.ui.screens.challenge.online.Challenge_OnlineUiState
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private val auth = FirebaseAuth.getInstance()
    private val userRepository = NetworkUserRepository(RetrofitClient.userApi)
    private val wordRepository = NetworkWordRepository(RetrofitClient.userApi)

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

//    private val _searchHistory = mutableStateListOf<String>()
//    val searchHistory: List<String> get() = _searchHistory


    init {
        viewModelScope.launch {
            val userId = auth.currentUser?.uid ?: ""
            try {
                userRepository.updateUserStatus(userId, UserStatus.Online.name)
                Log.e(null , "debug online status: $userId " +
                        UserStatus.Online.name
                )
            } catch (e: Exception) {
                Log.e(null , "enterOnline: ${e.message}")
            }
        }
    }

    fun searchVocab(query: String) {
        _uiState.update { it.copy(query = query) }
        viewModelScope.launch {
            val result = wordRepository.searchVocabs(query)
            _uiState.update {
                it.copy(searchResults = result)
            }
        }
    }
}