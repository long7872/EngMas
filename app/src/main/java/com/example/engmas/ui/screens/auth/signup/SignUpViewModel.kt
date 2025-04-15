package com.example.engmas.ui.screens.auth.signup

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.userProfileChangeRequest
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

enum class SignUpState {
    Idle,
    Success,
    Error
}

class SignUpViewModel : ViewModel() {
    private val auth = FirebaseAuth.getInstance()
    var signUpState = mutableStateOf(SignUpState.Idle)
        private set

    var errorMessage by mutableStateOf("")
        private set

    fun signUp(name: String, email: String, password: String) {
        viewModelScope.launch {
            try {
                val result = auth.createUserWithEmailAndPassword(email, password).await()
                result.user?.updateProfile(
                    userProfileChangeRequest { displayName = name }
                )?.await()
                signUpState.value = SignUpState.Success
            } catch (e: Exception) {
                signUpState.value = SignUpState.Error
                errorMessage = e.message ?: "Unknown error"
            }
        }
    }
}