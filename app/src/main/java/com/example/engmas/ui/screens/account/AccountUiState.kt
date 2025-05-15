package com.example.engmas.ui.screens.account

import com.example.engmas.data.model.User
import com.example.engmas.ui.screens.account.data.Friend
import com.example.engmas.ui.screens.account.data.LearningBadge
import com.example.engmas.ui.screens.account.data.UserFriendResponse

data class AccountUiState(
    val user: User = User(),
    val badges: List<LearningBadge> = emptyList(),
    val friends: List<UserFriendResponse> = emptyList(),
    val searchedFriends: List<Friend> = emptyList(),
    val isSearchScreen: Boolean = false
)
