package com.example.engmas.data.repository

import android.util.Log
import com.example.engmas.data.model.Challenge
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class ChallengeRepository {
    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()

    fun updateProgress(challengeId: String, myId: String, newProgress: Int) {
        firestore.collection("challenges")
            .document(challengeId)
            .get()
            .addOnSuccessListener { document ->
                val challenge = document.toObject(Challenge::class.java)
                if (challenge != null) {
                    val isPlayer1 = challenge.player1Id == myId
                    val field = if (isPlayer1) "player1Progress" else "player2Progress"
                    firestore.collection("challenges")
                        .document(challengeId)
                        .update(field, newProgress)
                }
            }
    }

    fun updateScore(challengeId: String, myId: String, newScore: Int) {
        firestore.collection("challenges")
            .document(challengeId)
            .get()
            .addOnSuccessListener { document ->
                val challenge = document.toObject(Challenge::class.java)
                if (challenge != null) {
                    val isPlayer1 = challenge.player1Id == myId
                    val field = if (isPlayer1) "player1Score" else "player2Score"
                    firestore.collection("challenges")
                        .document(challengeId)
                        .update(field, newScore)
                }
            }
    }

    fun uploadWordList(challengeId: String, wordList: List<String>, scrambleWordList: List<String>) {
        val batch = firestore.batch()
        val challengeRef = firestore.collection("challenges").document(challengeId)

        batch.update(challengeRef, "wordList", wordList)
        batch.update(challengeRef, "scrambleWordList", scrambleWordList)

        batch.commit()
            .addOnSuccessListener {
                Log.d("Upload", "Both wordList and scrambleWordList updated successfully")
            }
            .addOnFailureListener { e ->
                Log.e("Upload", "Error updating word lists: ${e.message}")
            }
    }

    fun deleteChallenge(challengeId: String) {
        val db = FirebaseFirestore.getInstance()
        db.collection("challenges")
            .document(challengeId)
            .delete()
            .addOnSuccessListener {
                println("Challenge deleted successfully.")
            }
            .addOnFailureListener { e ->
                println("Error deleting challenge: ${e.message}")
            }
    }
}