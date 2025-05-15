package com.example.engmas.data.repository

import com.example.engmas.data.model.User
import com.example.engmas.data.model.UserFriend
import com.example.engmas.network.EngMasApiService
import com.example.engmas.ui.screens.account.data.Friend
import com.example.engmas.ui.screens.account.data.LearningBadge
import com.example.engmas.ui.screens.account.data.UserFriendResponse
import okhttp3.MultipartBody

interface UserRepository {
    suspend fun createUser(user: User): Result<Unit>
    suspend fun getUser(userId: String): User
    suspend fun updateUser(user: User): String
    suspend fun uploadImage(userId: String, imagePart: MultipartBody.Part): String
    suspend fun deleteUser(userId: String): String
    suspend fun getAllUsers(): List<User>
    suspend fun getUserBadges(userId: String): List<LearningBadge>
    suspend fun updateFavouriteStatus(learningBadge: LearningBadge, favourite: Int): String
    suspend fun getAllFriendships(userId: String): List<UserFriendResponse>
    suspend fun insertUserFriend(userId: String, friend: Friend): String
    suspend fun updateStatusFriend(userId: String, userFriend: UserFriend): String
    suspend fun deleteFriendRequest(userId: String, friendId: String): String
    suspend fun searchFriends(userId: String, query: String): List<Friend>
}

class NetworkUserRepository(private val api: EngMasApiService): UserRepository {

    override suspend fun createUser(user: User): Result<Unit> {
        return try {
            val response = api.createUser(user)
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(Exception("Server error: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getUser(userId: String): User {
        val response = api.getUser(userId)
        if (response.isSuccessful) {
            val user = response.body()
            if (user != null) {
                return user
            } else {
                throw Exception("Response body is null")
            }
        } else {
            throw Exception("Server error: ${response.code()}")
        }
    }

    override suspend fun updateUser(user: User): String {
        val userId = user.userId
        val response = api.updateUser(userId, user)
        if (response.isSuccessful) {
            val info = response.body()?.string()
            if (info != null) return info
            else throw Exception("update status failed")
        } else {
            throw Exception("Error: ${response.code()} ${response.message()}")
        }
    }

    override suspend fun uploadImage(userId: String, imagePart: MultipartBody.Part): String {
        val response = api.uploadUserImage(userId, imagePart)
        if (response.isSuccessful) {
            val info = response.body()
            if (info != null) return info
            else throw Exception("update status failed")
        } else {
            throw Exception("Error: ${response.code()} ${response.message()}")
        }
    }

    override suspend fun deleteUser(userId: String): String {
        val response = api.deleteUser(userId)
        if (response.isSuccessful) {
            val info = response.body()?.string()
            if (info != null) return info
            else throw Exception("update status failed")
        } else {
            throw Exception("Error: ${response.code()} ${response.message()}")
        }
    }

    override suspend fun getAllUsers(): List<User> {
        val response = api.getAllUsers()
        if (response.isSuccessful) {
            val users = response.body()
            if (users != null) return users
            else throw Exception("Empty user list")
        } else {
            throw Exception("Error: ${response.code()}")
        }
    }

    suspend fun updateUserStatus(userId: String, status: String): Boolean {
        val response = api.updateUserStatus(userId, mapOf("status" to status))
        return response.isSuccessful
    }

    override suspend fun getUserBadges(userId: String): List<LearningBadge> {
        val response = api.getUserBadges(userId)
        if (response.isSuccessful) {
            val badges = response.body()
            if (badges != null) return badges
            else throw Exception("Empty user badge list")
        } else {
            throw Exception("Error: ${response.code()}")
        }
    }

    override suspend fun updateFavouriteStatus(learningBadge: LearningBadge, favourite: Int): String {
        val updateLearningBadge = learningBadge.copy(favourite = favourite)
        val response = api.updateFavouriteStatus(
            updateLearningBadge.userId,
            updateLearningBadge
        )
        if (response.isSuccessful) {
            val info = response.body()?.string()
            if (info != null) return info
            else throw Exception("update favourite failed")
        } else {
            throw Exception("Error: ${response.code()} ${response.message()}")
        }
    }

    override suspend fun getAllFriendships(userId: String): List<UserFriendResponse> {
        val response = api.getFriendships(userId)
        if (response.isSuccessful) {
            val friendships = response.body()
            if (friendships != null) return friendships
            else throw Exception("Empty user friendship list")
        } else if (response.code() == 404) {
            return emptyList()
        } else {
            throw Exception("Error: ${response.code()}")
        }
    }

    override suspend fun insertUserFriend(userId: String, friend: Friend): String {
        val response = api.insertUserFriend(userId, friend)
        if (response.isSuccessful) {
            val info = response.body()?.string()
            if (info != null) return info
            else throw Exception("insert friendship failed")
        } else {
            throw Exception("Error: ${response.code()} ${response.message()}")
        }
    }

    override suspend fun updateStatusFriend(userId: String, userFriend: UserFriend): String {
        val response = api.updateStatusFriend(userId, userFriend)
        if (response.isSuccessful) {
            val info = response.body()?.string()
            if (info != null) return info
            else throw Exception("update favourite failed")
        } else {
            throw Exception("Error: ${response.code()} ${response.message()}")
        }
    }

    override suspend fun deleteFriendRequest(userId: String, friendId: String): String {
        val response = api.deleteFriendRequest(userId, friendId)
        if (response.isSuccessful) {
            val info = response.body()?.string()
            if (info != null) return info
            else throw Exception("update favourite failed")
        } else {
            throw Exception("Error: ${response.code()} ${response.message()}")
        }
    }

    override suspend fun searchFriends(userId: String, query: String): List<Friend> {
        val response = api.searchFriends(userId, query)
        if (response.isSuccessful) {
            val users = response.body()
            if (users != null) return users
            else throw Exception("Empty user list")
        } else {
            throw Exception("Error: ${response.code()}")
        }
    }

}