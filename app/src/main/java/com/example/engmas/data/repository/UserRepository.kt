package com.example.engmas.data.repository

import com.example.engmas.data.model.User
import com.example.engmas.network.UserApiService

interface UserRepository {
    suspend fun createUser(user: User): Result<Unit>
    suspend fun getUser(userId: String): User
    suspend fun getAllUsers(): List<User>
    suspend fun updateUserStatus(userId: String, status: String): Boolean
}

class NetworkUserRepository(private val api: UserApiService): UserRepository {

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

    override suspend fun updateUserStatus(userId: String, status: String): Boolean {
        val response = api.updateUserStatus(userId, mapOf("status" to status))
        return response.isSuccessful
    }

}