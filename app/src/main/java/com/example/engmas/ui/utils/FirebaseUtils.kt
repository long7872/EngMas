package com.example.engmas.ui.utils

import com.google.firebase.auth.FirebaseAuth

fun getCurrentUserId(): String? {
    return FirebaseAuth.getInstance().currentUser?.uid
}
