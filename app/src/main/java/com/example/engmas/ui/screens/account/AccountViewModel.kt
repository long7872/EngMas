package com.example.engmas.ui.screens.account

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.engmas.data.model.User
import com.example.engmas.data.model.UserFriend
import com.example.engmas.data.model.UserFriendStatus
import com.example.engmas.data.repository.NetworkUserRepository
import com.example.engmas.network.RetrofitClient
import com.example.engmas.ui.screens.account.data.Friend
import com.example.engmas.ui.screens.account.data.LearningBadge
import com.example.engmas.ui.screens.account.data.UserFriendResponse
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody

class AccountViewModel: ViewModel() {
    private val auth = FirebaseAuth.getInstance()
    private val userId = auth.currentUser?.uid ?: ""
    private val userRepository = NetworkUserRepository(RetrofitClient.api)

    private val _uiState = MutableStateFlow(AccountUiState())
    val uiState: StateFlow<AccountUiState> = _uiState.asStateFlow()

    fun getUserId(): String = userId

    fun getUser() {
        viewModelScope.launch {
            val result = userRepository.getUser(userId)
            if (result != User()) {
                _uiState.update { it.copy(user = result) }
                Log.d("Account View Model", "function getUser: get $result")
            } else {
                Log.d("Account View Model", "function getUser: Cannot get $result")
            }
        }
    }

    fun updateUser(user: User) {
        viewModelScope.launch {
            val updateUser = user.copy(userId = userId)
            val result = userRepository.updateUser(updateUser)
            Log.d("Account View Model", "update user to database: $result")
        }
    }

    fun uploadImage(context: Context, imageUri: Uri) {
        viewModelScope.launch {
            try {
                val imagePart = createMultipartFromUri(context, imageUri)
                val url = userRepository.uploadImage(userId, imagePart)
                val updateUser = _uiState.value.user.copy(photoUrl = url)
                _uiState.update { it.copy(user = updateUser) }
//                _uploadState.value = Result.success(url)
            } catch (e: Exception) {
//                _uploadState.value = Result.failure(e)
            }
        }
    }

    fun deleteUser() {
        viewModelScope.launch {
            val result = userRepository.deleteUser(userId)
            Log.d("Account View Model", "update user to database: $result")
        }
    }

    fun getUserBadges() {
        viewModelScope.launch {
            val result = userRepository.getUserBadges(userId)
            _uiState.update { it.copy(badges = result) }
        }
    }

    fun updateFavourite(learningBadge: LearningBadge, favourite: Int) {
        viewModelScope.launch {
            val result = userRepository.updateFavouriteStatus(learningBadge, favourite)
            Log.d("Account View Model", "update favourite badge to database: $result")
            val updatedBadges = _uiState.value.badges.map {
                if (it.badgeId == learningBadge.badgeId) {
                    it.copy(favourite = favourite) // Cập nhật giá trị favourite
                } else {
                    it  // Giữ nguyên các phần tử khác
                }
            }
            _uiState.update { it.copy(badges = updatedBadges) }
        }
    }

    fun loadFriend() {
        viewModelScope.launch {
            val result = userRepository.getAllFriendships(userId)
            Log.d("Account View Model", "get all friendships from database: $result")
            _uiState.update { it.copy(friends = result) }
        }
    }

    fun addFriendRequest(friend: Friend) {
        viewModelScope.launch {
            val updateFriend = friend.copy(status = UserFriendStatus.Pending)
            val result = userRepository.insertUserFriend(userId, updateFriend)
            Log.d("Account View Model", "update status friend to database: $result")
            val updatedSearch = _uiState.value.searchedFriends.map {
                if (it.friendId == friend.friendId) {
                    it.copy(status = updateFriend.status)
                } else {
                    it
                }
            }
            _uiState.update { it.copy(searchedFriends = updatedSearch) }
        }
    }

    fun acceptFriendRequest(friendId: String, status: UserFriendStatus) {
        viewModelScope.launch {
            val updateUserFriend = UserFriend(
                friendId = friendId,
                status = status
            )
            val result = userRepository.updateStatusFriend(userId, updateUserFriend)
            Log.d("Account View Model", "update status friend to database: $result")
            val updatedFriends = _uiState.value.friends.map {
                if (it.friendId == friendId) {
                    it.copy(status = status) // Cập nhật giá trị favourite
                } else {
                    it  // Giữ nguyên các phần tử khác
                }
            }
            _uiState.update { it.copy(friends = updatedFriends) }
        }
    }

    fun deleteFriendRequest(friendId: String) {
        viewModelScope.launch {
            val result = userRepository.deleteFriendRequest(userId, friendId)
            Log.d("Account View Model", "delete friend request from database: $result")
            val updatedFriends = _uiState.value.friends.filter { it.friendId != friendId }
            val updatedSearch = _uiState.value.searchedFriends.map {
                if (it.friendId == friendId) {
                    it.copy(status = UserFriendStatus.None)
                } else {
                    it
                }
            }
            _uiState.update {
                it.copy(
                    friends = updatedFriends,
                    searchedFriends = updatedSearch
                )
            }
        }
    }

    fun searchFriend(query: String) {
        viewModelScope.launch {
            val result = userRepository.searchFriends(userId, query)
            Log.d("Account View Model", "search friend: $result")
            _uiState.update { it.copy(searchedFriends = result) }
        }
    }

    fun changeSearchScreen(isSearch: Boolean) {
        _uiState.update { it.copy(isSearchScreen = isSearch) }
    }

    private fun createMultipartFromUri(context: Context, uri: Uri): MultipartBody.Part {
        val contentResolver = context.contentResolver
        val inputStream = contentResolver.openInputStream(uri)
        val bytes = inputStream?.readBytes() ?: ByteArray(0)
        val requestFile = bytes.toRequestBody("image/*".toMediaTypeOrNull())
        return MultipartBody.Part.createFormData("image", "upload.jpg", requestFile)
    }
}