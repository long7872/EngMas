package com.example.engmas.network

import com.example.engmas.data.model.Course
import com.example.engmas.data.model.User
import com.example.engmas.data.model.UserCourse
import com.example.engmas.data.model.UserFriend
import com.example.engmas.data.model.UserLearning
import com.example.engmas.data.model.Vocab
import com.example.engmas.ui.screens.account.data.Friend
import com.example.engmas.ui.screens.account.data.LearningBadge
import com.example.engmas.ui.screens.account.data.UserFriendResponse
import com.example.engmas.ui.screens.home.data.CourseLearning
import com.example.engmas.ui.screens.home.data.VocabInfo
import com.example.engmas.ui.screens.practice.courses.data.QuestionInCourse
import com.example.engmas.ui.screens.practice.courses.data.UpdateStatusRequest
import com.example.engmas.ui.screens.practice.vocabulary.model.TopicProgress
import com.example.engmas.ui.screens.practice.vocabulary.model.VocabsInTopic
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface EngMasApiService {
    @POST("users")
    suspend fun createUser(@Body user: User): Response<Unit>
    @GET("users")
    suspend fun getAllUsers(): Response<List<User>>
    @GET("users/{user_id}")
    suspend fun getUser(@Path("user_id") userId: String): Response<User>
    @PUT("users/{user_id}")
    suspend fun updateUser(
        @Path("user_id") userId: String,
        @Body user: User
    ): Response<ResponseBody>
    @Multipart
    @POST("users/upload")
    suspend fun uploadUserImage(
        @Query("user_id") userId: String,
        @Part image: MultipartBody.Part
    ): Response<String>
    @DELETE("users/{user_id}")
    suspend fun deleteUser(@Path("user_id") userId: String): Response<ResponseBody>
    @PUT("users/{user_id}/status")
    suspend fun updateUserStatus(
        @Path("user_id") userId: String,
        @Body statusBody: Map<String, String>
    ): Response<Unit>
    @GET("users/{user_id}/badges")
    suspend fun getUserBadges(@Path("user_id") userId: String): Response<List<LearningBadge>>
    @PUT("users/{user_id}/favourite")
    suspend fun updateFavouriteStatus(
        @Path("user_id") userId: String,
        @Body favouriteStatus: LearningBadge
    ): Response<ResponseBody>
    @GET("users/{user_id}/friendships")
    suspend fun getFriendships(@Path("user_id") userId: String): Response<List<UserFriendResponse>>  // Trả về danh sách các bạn bè
    @POST("users/{user_id}/friendships")
    suspend fun insertUserFriend(
        @Path("user_id") userId: String,
        @Body friend: Friend
    ): Response<ResponseBody>
    @PUT("users/{user_id}/friendships")
    suspend fun updateStatusFriend(
        @Path("user_id") userId: String,
        @Body userFriend: UserFriend
    ): Response<ResponseBody>  // Trả về danh sách các bạn bè
    @DELETE("users/{user_id}/friendships/{friend_id}")
    suspend fun deleteFriendRequest(
        @Path("user_id") userId: String,
        @Path("friend_id") friendId: String
    ): Response<ResponseBody>
    @GET("users/{user_id}/friendships/search")
    suspend fun searchFriends(
        @Path("user_id") userId: String,
        @Query("q") query: String
    ): Response<List<Friend>>

    @GET("vocabs/random")
    suspend fun getRandomVocab(): Response<List<String>>
    @GET("vocabs/search/{query}")
    suspend fun searchVocabs(@Path("query", encoded = true) query: String): Response<List<Vocab>>
    @GET("vocabs/decode/{api_id}")
    suspend fun collectVocabInfo(@Path("api_id") vocabId: Int): Response<VocabInfo>

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
    @POST("topics/user/learning/update_status")
    suspend fun updateAllLearningStatus(@Body request: UpdateStatusRequest): Response<ResponseBody>


    @GET("courses")
    suspend fun getAllCourses(): Response<List<Course>>
    @GET("courses/learning/{course_id}")
    suspend fun getCourseLearning(
        @Path("course_id") courseId: Int,
        @Query("user_id") userId: String
    ): Response<QuestionInCourse>
    @POST("courses/learning/update_status")
    suspend fun updatesUserLearningStatus(@Body request: UpdateStatusRequest): Response<ResponseBody>
    @GET("courses/user_course/{user_id}")
    suspend fun getUserCourses(@Path("user_id") userId: String): Response<List<CourseLearning>>
}