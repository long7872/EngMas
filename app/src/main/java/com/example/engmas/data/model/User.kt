package com.example.engmas.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class User (
    @SerialName(value = "user_id")
    val userId: String = "",
    val privilege: Privilege = Privilege.User,
    val username: String = "",
    val email: String = "",
    val facebook: String = "",
    @SerialName(value = "photo_url")
    val photoUrl: String = "",
    /*
     *  Use UserStatus enum class
     */
    val status: String = UserStatus.Online.name
)

@Serializable
enum class Privilege {
    User,
    Admin
}

enum class UserStatus {
    Offline,
    Online,
    Matching,
    Matched,
    Play
}