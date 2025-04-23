package com.example.engmas.ui.screens.home

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.engmas.data.Streak
import com.example.engmas.data.Today
import com.example.engmas.data.model.UserStatus
import com.example.engmas.data.repository.NetworkUserRepository
import com.example.engmas.data.repository.NetworkWordRepository
import com.example.engmas.network.RetrofitClient
import com.example.engmas.ui.screens.challenge.online.Challenge_OnlineUiState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.GenericTypeIndicator
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private val auth = FirebaseAuth.getInstance()
    private val userRepository = NetworkUserRepository(RetrofitClient.userApi)
    private val wordRepository = NetworkWordRepository(RetrofitClient.userApi)

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

//    private val _searchHistory = mutableStateListOf<String>()
//    val searchHistory: List<String> get() = _searchHistory

    // Q:
    private val _streak = MutableStateFlow<Streak?>(null)
    val streak: StateFlow<Streak?> get() = _streak

    private val dbRef: DatabaseReference = FirebaseDatabase.getInstance().getReference("users")

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


    // Hàm lấy UID của bản ghi đầu tiên và sau đó tải streak
    fun fetchFirstUserIdAndLoadStreak() {

        dbRef.limitToFirst(1).get().addOnSuccessListener { snapshot ->
            if (snapshot.exists()) {
                val userId = snapshot.children.firstOrNull()?.key
                if (userId != null) {
                    Log.d("FirebaseDebug", "Found first userId: $userId")
                    loadStreak(userId)
                } else {
                    Log.e("FirebaseError", "❌ Không tìm thấy người dùng nào trong database")
                }
            } else {
                Log.e("FirebaseError", "❌ Database trống")
            }
        }.addOnFailureListener { error ->

            Log.e("FirebaseError", "❌ Lỗi khi lấy danh sách người dùng: ${error.message}")
        }
    }

    // Hàm tải dữ liệu streak từ Firebase khi biết UID của người dùng
    fun loadStreak(uid: String) {
        Log.d("FirebaseDebug", "🔍 Kiểm tra UID: $uid") // Log kiểm tra giá trị UID
        val streakRef = dbRef.child(uid).child("streak")

        streakRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    // Lấy dữ liệu streak từ snapshot
                    val completedDays = snapshot.child("completedDays").children.map { it.getValue(String::class.java) ?: "" }
                    val todayDay = snapshot.child("today").child("day").getValue(String::class.java) ?: ""
                    val isCompletedToday = snapshot.child("today").child("isCompleted").getValue(Boolean::class.java) ?: false
                    val highestStreak = snapshot.child("highestStreak").getValue(Int::class.java) ?: 0
                    val lastUpdated = snapshot.child("lastUpdated").getValue(String::class.java) ?: ""

                    // Tạo đối tượng Streak từ dữ liệu Firebase
                    val streak = Streak(
                        completedDays = completedDays,
                        today = Today(day = todayDay, isCompleted = isCompletedToday),
                        highestStreak = highestStreak,
                        lastUpdated = lastUpdated
                    )

                    // Log hoặc xử lý dữ liệu streak đã tải
                    _streak.value = streak
                    Log.d("FirebaseDebug", "✅ Đã tải dữ liệu streak thành công: $streak")
                } else {
                    // Trường hợp không tìm thấy dữ liệu streak cho UID này
                    Log.e("FirebaseError", "❌ Không tìm thấy dữ liệu streak cho UID: $uid")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Xử lý khi có lỗi trong việc tải dữ liệu
                Log.e("FirebaseError", "❌ Lỗi khi tải dữ liệu streak: ${error.message}")
            }
        })
    }

}