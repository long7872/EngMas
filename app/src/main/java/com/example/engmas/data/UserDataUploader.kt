package com.example.engmas.data

import android.util.Log
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlin.random.Random

// Định nghĩa một lớp User để lưu trữ tên và điểm người dùng
data class User(val name: String, val score: Int)

class UserDataUploader {

    // Lấy đối tượng DatabaseReference trỏ đến bảng "users" trong Firebase Realtime Database
    private val dbRef: DatabaseReference = FirebaseDatabase.getInstance().getReference("users")

    // Hàm upload dữ liệu người dùng vào Firebase
    fun uploadData() {
        for (i in 1..10) {  // Tạo 10 người dùng giả
            val userName = "UserGPT${Random.nextInt(100, 999)}"  // Tạo tên người dùng ngẫu nhiên
            val score = Random.nextInt(100, 999)  // Tạo điểm số ngẫu nhiên

            val user = User(name = userName, score = score)
            val userId = dbRef.push().key

            userId?.let {
                dbRef.child(it).setValue(user)
                    .addOnSuccessListener {
                        // Log thành công
                        Log.d("FirebaseDebug", "✅ Đã thêm $userName với điểm $score")
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
}
