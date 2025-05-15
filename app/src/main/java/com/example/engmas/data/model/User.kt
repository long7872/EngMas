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
    val status: String = UserStatus.Online.name,
    val name: String = "",
    @SerialName(value = "date_of_birth")
    val doB: String = "",
    @SerialName(value = "phone_number")
    val phoneNumber:String = ""
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