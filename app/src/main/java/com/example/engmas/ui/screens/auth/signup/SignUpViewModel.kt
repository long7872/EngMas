package com.example.engmas.ui.screens.auth.signup

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.engmas.data.model.Privilege
import com.example.engmas.data.model.User
import com.example.engmas.data.model.UserStatus
import com.example.engmas.data.repository.NetworkUserRepository
import com.example.engmas.data.repository.UserRepository
import com.example.engmas.network.RetrofitClient
import com.example.engmas.network.UserApiService
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.userProfileChangeRequest
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import retrofit2.Retrofit

enum class SignUpState {
    Idle,
    Success,
    Error
}

class SignUpViewModel : ViewModel() {
    private val auth = FirebaseAuth.getInstance()
    private val repository = NetworkUserRepository(RetrofitClient.userApi)

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

                val userId = result.user?.uid.toString()
                val photoUrl = result.user?.photoUrl?.toString() ?: ""
                val userData = User(
                    userId = userId,
                    username = name,
                    email = email,
                    photoUrl = photoUrl,
                    facebook = "",
                    privilege = Privilege.User,
                    status = UserStatus.Online.name
                )

                val response = repository.createUser(userData)
                if (response.isSuccess) {
                    signUpState.value = SignUpState.Success
                } else {
                    signUpState.value = SignUpState.Error
                    errorMessage = response.exceptionOrNull()?.message ?: "Unknown error"
                }

            } catch (e: Exception) {
                signUpState.value = SignUpState.Error
                errorMessage = e.message ?: "Unknown error"
            }
            Log.e(null, errorMessage)
        }
    }
}