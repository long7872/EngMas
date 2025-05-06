package com.example.engmas.network

import com.example.engmas.data.model.User
import com.example.engmas.data.model.UserLearning
import com.example.engmas.data.model.Vocab
import com.example.engmas.ui.screens.practice.vocabulary.model.TopicProgress
import com.example.engmas.ui.screens.practice.vocabulary.model.VocabsInTopic
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface EngMasApiService {
    @POST("users")
    suspend fun createUser(@Body user: User): Response<Unit>
    @GET("users")
    suspend fun getAllUsers(): Response<List<User>>
    @GET("users/{user_id}")
    suspend fun getUser(@Path("user_id") userId: String): Response<User>
    @PUT("users/{user_id}/status")
    suspend fun updateUserStatus(
        @Path("user_id") userId: String,
        @Body statusBody: Map<String, String>
    ): Response<Unit>

    @GET("vocabs/random")
    suspend fun getRandomVocab(): Response<Vocab>
    @GET("vocabs/search/{query}")
    suspend fun searchVocabs(@Path("query", encoded = true) query: String): Response<List<Vocab>>

    // Insert new UserLearning record
    @POST("topics/user/learning")
    suspend fun insertUserLearning(@Body userLearningRequest: UserLearning): Response<Unit>
    // Update existing UserLearning record
    @PUT("topics/user/learning")
    suspend fun updateUserLearning(@Body userLearningRequest: UserLearning): Response<Unit>
    @GET("topics/learning/{user_id}")
    suspend fun getTopicLearning(@Path("user_id") userId: String): Response<List<TopicProgress>>
    @GET("topics/learning/vocabs/{topic_id}")
    suspend fun getVocabsTopicLearning(
        @Path("topic_id") topicId: Int,
        @Query("user_id") userId: String
    ): Response<VocabsInTopic>
}