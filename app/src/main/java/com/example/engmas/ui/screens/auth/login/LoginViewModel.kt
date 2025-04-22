package com.example.engmas.ui.screens.auth.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

enum class LoginState {
    Idle,
    Success,
    Error
}

class LoginViewModel : ViewModel() {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    var loginState = mutableStateOf(LoginState.Idle)
        private set

    var errorMessage by mutableStateOf("")
        private set

    init {
        if (auth.currentUser != null) {
            loginState.value = LoginState.Success
        }
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            try {
                auth.signInWithEmailAndPassword(email, password).await()
                loginState.value = LoginState.Success
            } catch (e: Exception) {
                loginState.value = LoginState.Error
                errorMessage = e.message ?: "Unknown error"
            }
        }
    }

    fun logout() {
        auth.signOut()
        loginState.value = LoginState.Idle
    }
}