package com.example.engmas.ui.screens.auth.signup

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.userProfileChangeRequest
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class SignUpViewModel : ViewModel() {
    private val auth = FirebaseAuth.getInstance()
    var signUpState = mutableStateOf("")
        private set
    var isLoading = mutableStateOf(false)
        private set

    fun signUp(name: String, email: String, password: String) {
        isLoading.value = true
        viewModelScope.launch {
            try {
                val result = auth.createUserWithEmailAndPassword(email, password).await()
                result.user?.updateProfile(
                    userProfileChangeRequest { displayName = name }
                )?.await()
                signUpState.value = "Success"
            } catch (e: Exception) {
                signUpState.value = "Error: ${e.message}"
            } finally {
                isLoading.value = false
            }
        }
    }
}