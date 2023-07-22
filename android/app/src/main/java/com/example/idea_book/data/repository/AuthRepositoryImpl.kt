package com.example.idea_book.data.repository

import android.util.Log
import com.example.idea_book.domain.repository.AuthRepository
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepositoryImpl: AuthRepository {
    private val auth = Firebase.auth

    override suspend fun signIn(email: String, password: String): Boolean {
        val result = auth.signInWithEmailAndPassword(email, password).await()
        return result.user != null
    }

    override suspend fun signUp(username: String, email: String, password: String): Boolean {
        val result = auth.createUserWithEmailAndPassword(email, password).await()
        val user = result.user
        Log.i("AuthRepositoryImpl", "signUp: $user")
        if (user != null) {
            val profileUpdates = UserProfileChangeRequest.Builder()
                .setDisplayName(username)
                .build()
            user.updateProfile(profileUpdates).await()
            return true
        }
        Log.i("AuthRepositoryImpl", getUser()?.displayName.toString())
        return false
    }

    override suspend fun signOut(): Boolean {
        auth.signOut()
        return true
    }

    override fun isAuth(): Boolean {
        return auth.currentUser != null
    }

    override fun getUser(): FirebaseUser? {
        return auth.currentUser
    }

    override fun onAuthChangeListener() {
        auth.addAuthStateListener {

        }
    }
}
