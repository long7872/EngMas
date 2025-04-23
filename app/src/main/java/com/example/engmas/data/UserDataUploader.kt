package com.example.engmas.data

import android.util.Log
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlin.random.Random

// Định nghĩa một lớp User để lưu trữ tên và điểm người dùng
data class User(val name: String, val score: Int)

// Định nghĩa các lớp Streak và Today
data class Streak(
    val completedDays: List<String>,
    val today: Today,
    val highestStreak: Int,
    val lastUpdated: String
)

data class Today(
    val day: String,
    val isCompleted: Boolean
)

class UserDataUploader {

    // Lấy đối tượng DatabaseReference trỏ đến bảng "users" trong Firebase Realtime Database
    private val dbRef: DatabaseReference = FirebaseDatabase.getInstance().getReference("users")

    // Hàm upload dữ liệu người dùng và streak vào Firebase
    fun uploadData() {
        for (i in 1..10) {  // Tạo 10 người dùng giả
            val userName = "UserGPT${Random.nextInt(100, 999)}"  // Tạo tên người dùng ngẫu nhiên
            val score = Random.nextInt(100, 999)  // Tạo điểm số ngẫu nhiên

            val user = User(name = userName, score = score)
            val userId = dbRef.push().key

            userId?.let { id ->
                // Thêm user vào Firebase
                dbRef.child(id).setValue(user)
                    .addOnSuccessListener {
                        // Log thành công
                        Log.d("FirebaseDebug", "✅ Đã thêm $userName với điểm $score")

                        // Tạo dữ liệu streak cho user này
                        val streak = createRandomStreak() // Tạo dữ liệu streak ngẫu nhiên
                        val streakRef = dbRef.child(id).child("streak")

                        val streakData = mapOf(
                            "completedDays" to streak.completedDays,
                            "today" to mapOf(
                                "day" to streak.today.day,
                                "isCompleted" to streak.today.isCompleted
                            ),
                            "highestStreak" to streak.highestStreak,
                            "lastUpdated" to streak.lastUpdated
                        )

                        // Thêm streak vào Firebase
                        streakRef.setValue(streakData)
                            .addOnSuccessListener {
                                Log.d("FirebaseDebug", "✅ Đã thêm dữ liệu streak cho $userName thành công")
                            }
                            .addOnFailureListener { error ->
                                Log.e("FirebaseError", "❌ Lỗi khi thêm streak cho $userName: ${error.message}")
                            }

                    }
                    .addOnFailureListener { error ->
                        // Log lỗi
                        Log.e("FirebaseError", "❌ Lỗi khi thêm $userName: ${error.message}")
                    }
            } ?: run {
                Log.e("FirebaseError", "❌ Không thể tạo userId cho $userName")
            }
        }
    }

    // Hàm tạo dữ liệu streak ngẫu nhiên cho user
    private fun createRandomStreak(): Streak {
        // Tạo một số ngày ngẫu nhiên mà người dùng đã hoàn thành
        val daysOfWeek = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")
        val completedDays = daysOfWeek.shuffled().take(Random.nextInt(1, 5)) // Chọn 1-4 ngày ngẫu nhiên

        val today = daysOfWeek.random() // Ngày hôm nay ngẫu nhiên
        val isCompletedToday = Random.nextBoolean() // Ngẫu nhiên là đã hoàn thành hay chưa

        return Streak(
            completedDays = completedDays,
            today = Today(day = today, isCompleted = isCompletedToday),
            highestStreak = Random.nextInt(10, 100), // Chuỗi streak ngẫu nhiên
            lastUpdated = "2024-04-${Random.nextInt(1, 30)}" // Ngày cập nhật ngẫu nhiên
        )
    }
}
