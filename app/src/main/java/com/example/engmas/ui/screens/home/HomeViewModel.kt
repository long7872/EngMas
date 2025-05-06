package com.example.engmas.ui.screens.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.engmas.data.model.Stats
import com.example.engmas.data.model.Streak
import com.example.engmas.data.model.Today
import com.example.engmas.data.model.UserScore
import com.example.engmas.data.model.UserStatus
import com.example.engmas.data.repository.NetworkUserRepository
import com.example.engmas.data.repository.NetworkUserScoreRepository
import com.example.engmas.data.repository.NetworkWordRepository
import com.example.engmas.network.RetrofitClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Locale
import kotlin.math.abs

class HomeViewModel : ViewModel() {
    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()
    private val userRepository = NetworkUserRepository(RetrofitClient.api)
    private val wordRepository = NetworkWordRepository(RetrofitClient.api)
    private val userScoreRepository = NetworkUserScoreRepository()

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

//    private val _searchHistory = mutableStateListOf<String>()
//    val searchHistory: List<String> get() = _searchHistory


    init {
        viewModelScope.launch {
            val userId = auth.currentUser?.uid ?: ""
            try {
                userRepository.updateUserStatus(userId, UserStatus.Online.name)
                Log.e(null , "debug online status: $userId " +
                        UserStatus.Online.name
                )
            } catch (e: Exception) {
                Log.e(null , "enterOnline: ${e.message}")
            }
        }
        loadUserScore()
    }

    fun searchVocab(query: String) {
        _uiState.update { it.copy(query = query) }
        viewModelScope.launch {
            val result = wordRepository.searchVocabs(query)
            _uiState.update {
                it.copy(searchResults = result)
            }
        }
    }

    private fun loadUserScore() {
        val userId = auth.currentUser?.uid
        if (userId != null) {
            viewModelScope.launch {
                val result = userScoreRepository.getUser(userId)
                result.onSuccess { user ->
                    // Update userScore vào uiState

                    val today = Calendar.getInstance()
                    val (startOfCurrentWeek, endOfCurrentWeek) = getStartAndEndOfCurrentWeek(today)
                    val (startOfPreviousWeek, endOfPreviousWeek) = getPreviousWeek(today)
                    if (user.currentWeekStats.weekStartDate == startOfPreviousWeek
                        && user.currentWeekStats.weekEndDate == endOfPreviousWeek) {
                        val updatedUser = user.copy(
                            previousWeekStats = user.currentWeekStats,
                            currentWeekStats = Stats(
                                weekStartDate = startOfCurrentWeek,
                                weekEndDate = endOfCurrentWeek
                            )
                        )
                        val updatedResult = userScoreRepository.updateUser(userId ,updatedUser)
                        updatedResult.onSuccess {
                            Log.e("Home", updatedResult.toString())

                            val getUserAgain = userScoreRepository.getUser(userId)
                            getUserAgain.onSuccess { user ->
                                _uiState.update { it.copy(userScore = user) }
                            }.onFailure { error ->
                                Log.e("Home", "${error.message}")
                            }
                        }.onFailure { error ->
                            Log.e("Home", "${error.message}")
                        }
                    } else if (user.currentWeekStats.weekStartDate == startOfCurrentWeek
                        && user.currentWeekStats.weekEndDate == endOfCurrentWeek) {
                        _uiState.update { it.copy(userScore = user) }
                    } else {
                        val updatedUser = user.copy(
                            currentWeekStats = Stats(
                                weekStartDate = startOfCurrentWeek,
                                weekEndDate = endOfCurrentWeek
                            ),
                            previousWeekStats = Stats(
                                weekStartDate = startOfPreviousWeek,
                                weekEndDate = endOfPreviousWeek
                            )
                        )
                        val updatedResult = userScoreRepository.updateUser(userId ,updatedUser)
                        updatedResult.onSuccess {
                            Log.e("Home", updatedResult.toString())

                            val getUserAgain = userScoreRepository.getUser(userId)
                            getUserAgain.onSuccess { user ->
                                _uiState.update { it.copy(userScore = user) }
                            }.onFailure { error ->
                                Log.e("Home", "${error.message}")
                            }
                        }.onFailure { error ->
                            Log.e("Home", "${error.message}")
                        }
                    }

                    Log.e("Home", user.toString())
                }.onFailure {
                    // Nếu không tồn tại user, tạo mới
                    val thisUser = userRepository.getUser(userId)
                    val today = Calendar.getInstance()
                    val (startOfCurrentWeek, endOfCurrentWeek) = getStartAndEndOfCurrentWeek(today)
                    val (startOfPreviousWeek, endOfPreviousWeek) = getPreviousWeek(today)
                    val newUser = UserScore(
                        id = userId,
                        name = thisUser.username,
                        score = 0,
                        streak = Streak(),
                        currentWeekStats = Stats(
                            weekStartDate = startOfCurrentWeek,
                            weekEndDate = endOfCurrentWeek
                        ),
                        previousWeekStats = Stats(
                            weekStartDate = startOfPreviousWeek,
                            weekEndDate = endOfPreviousWeek
                        )
                    )
                    val createResult = userScoreRepository.createUser(newUser)
                    createResult.onSuccess { createdUser ->
                        _uiState.update { it.copy(userScore = createdUser) }
                        Log.e("Home", createdUser.toString())
                    }.onFailure { error ->
                        Log.e("Home", "${error.message}")
                        // Nếu create thất bại, log lỗi
                        // Bạn có thể handle thêm ở đây nếu cần
                    }
                }
            }
        }
    }

    fun checkAndUpdateStreak(streak: Streak) {
        Log.e("Check & Update", "$streak")
        val days = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")

        val completedDays = streak.completedDays.toMutableList()
        val today = streak.today
        val lastCompletedIndex = completedDays.lastOrNull()?.let { days.indexOf(it) } ?: -1
        val todayIndex = days.indexOf(today.day)

        var needUpdate = false
        var updatedStreak = streak

        Log.e("Check & Update", "$needUpdate $updatedStreak")
        if (today.day != getCurrentDayOfWeek()) {
            updatedStreak = updatedStreak.copy(
                today = Today(
                    day = getCurrentDayOfWeek(),
                    completed = false,
                )
            )
            needUpdate = true
            Log.e("Check & Update", "${today.day} ${getCurrentDayOfWeek()}")
        }

        // Nếu bỏ lỡ ngày => streak reset
        if (lastCompletedIndex != -1 && abs(todayIndex - lastCompletedIndex) > 1) {
            completedDays.clear() // Xóa tất cả completedDays nếu ngắt streak
            updatedStreak = updatedStreak.copy(
                completedDays = completedDays,
                nowStreak = 0 // Reset nowStreak về 0
            )
            needUpdate = true
        }

        // Nếu hôm nay đã hoàn thành nhưng chưa ghi nhận
        if (today.completed && !completedDays.contains(today.day)) {
            completedDays.add(today.day) // Thêm hôm nay vào completedDays
            updatedStreak = updatedStreak.copy(
                completedDays = completedDays,
                nowStreak = updatedStreak.nowStreak + 1 // Tăng nowStreak lên
            )
            needUpdate = true
        }

        // Cập nhật highestStreak nếu nowStreak lớn hơn
        if (updatedStreak.nowStreak > updatedStreak.highestStreak) {
            updatedStreak = updatedStreak.copy(
                highestStreak = updatedStreak.nowStreak
            )
            needUpdate = true
        }

        if (needUpdate) {
            // Cập nhật streak
            _uiState.update {
                it.copy(
                    userScore = it.userScore.copy(
                        streak = updatedStreak
                    )
                )
            }

            // Lấy userId
            val userId = auth.currentUser?.uid
            if (userId != null) {
                // Lấy userScore mới từ uiState
                val updatedUserScore = _uiState.value.userScore

                viewModelScope.launch {
                    val result = userScoreRepository.updateUser(userId, updatedUserScore)
                    result.onSuccess {
                        Log.d("HomeViewModel", "✅ Firestore đã cập nhật userScore mới thành công")
                    }.onFailure { e ->
                        Log.e("HomeViewModel", "❌ Lỗi khi cập nhật Firestore: ${e.message}")
                    }
                }
            }
            Log.d("HomeViewModel", "✅ Updated streak: $updatedStreak")
        }
    }

    fun markTodayCompleted() {
        val currentUserScore = _uiState.value.userScore
        val currentStreak = currentUserScore.streak
        val today = currentStreak.today

        // Nếu hôm nay đã hoàn thành rồi thì không cần làm gì nữa
        if (today.completed) {
            return
        }

        val updatedToday = today.copy(completed = true)
        val updatedStreak = currentStreak.copy(today = updatedToday)
        val updatedUserScore = currentUserScore.copy(streak = updatedStreak)

        // Cập nhật local uiState
        _uiState.update {
            it.copy(userScore = updatedUserScore)
        }

        // Gửi update lên Firestore
        val userId = auth.currentUser?.uid
        if (userId != null) {
            viewModelScope.launch {
                val result = userScoreRepository.updateUser(userId, updatedUserScore)
                result.onSuccess {
                    Log.d("HomeViewModel", "✅ markTodayCompleted: Cập nhật Firestore thành công")
                }.onFailure { e ->
                    Log.e("HomeViewModel", "❌ markTodayCompleted: Lỗi cập nhật Firestore: ${e.message}")
                }
            }
        }
    }

    fun getCurrentDayOfWeek(): String {
        val days = listOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat") // Thứ tự của Calendar
        val calendar = java.util.Calendar.getInstance()
        val dayIndex = calendar.get(java.util.Calendar.DAY_OF_WEEK) - 1 // Calendar: Sunday=1
        return days[dayIndex]
    }

    private fun getStartAndEndOfCurrentWeek(date: Calendar): Pair<String, String> {
        // Đặt ngày là ngày hiện tại
        val startOfWeek = date.clone() as Calendar
        startOfWeek.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY) // Thứ hai là ngày bắt đầu tuần

        val endOfWeek = startOfWeek.clone() as Calendar
        endOfWeek.add(Calendar.DAY_OF_YEAR, 6)  // Thứ bảy là ngày cuối tuần

        // Trả về dạng "dd/MM/yyyy"
        val startDate = formatDate(startOfWeek)
        val endDate = formatDate(endOfWeek)

        return Pair(startDate, endDate)
    }

    private fun getPreviousWeek(date: Calendar): Pair<String, String> {
        // Lùi về một tuần để lấy tuần trước
        date.add(Calendar.WEEK_OF_YEAR, -1)
        return getStartAndEndOfCurrentWeek(date)
    }

    private fun formatDate(calendar: Calendar): String {
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val month = calendar.get(Calendar.MONTH) + 1  // Lưu ý, tháng bắt đầu từ 0 (0 = January)
        val year = calendar.get(Calendar.YEAR)

        // Sử dụng Locale.getDefault() hoặc Locale("en", "US") để đảm bảo kết quả không bị ảnh hưởng bởi cấu hình khu vực
        return String.format(Locale.getDefault(), "%02d/%02d/%04d", day, month, year)  // Định dạng "dd/MM/yyyy"
    }
}