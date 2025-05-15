package com.example.engmas.ui.screens.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.engmas.data.repository.NetworkVocabRepository
import com.example.engmas.network.RetrofitClient
import com.example.engmas.ui.screens.home.data.VocabInfo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SearchViewModel: ViewModel() {
    private val vocabRepository = NetworkVocabRepository(RetrofitClient.api)

    private val _vocabState = MutableStateFlow(VocabInfo())
    val vocabState: StateFlow<VocabInfo> = _vocabState.asStateFlow()

    fun collectVocabInfo(vocabId: Int) {
        viewModelScope.launch {
            val result = vocabRepository.collectVocabInfo(vocabId)
            Log.d("Home View Model", "collect vocabulary data: $result")
            _vocabState.value = result
        }
    }

}