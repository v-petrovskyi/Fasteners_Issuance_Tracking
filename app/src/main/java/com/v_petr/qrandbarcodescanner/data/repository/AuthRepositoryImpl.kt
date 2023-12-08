package com.v_petr.qrandbarcodescanner.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.firestore.FirebaseFirestore
import com.v_petr.qrandbarcodescanner.data.model.User
import com.v_petr.qrandbarcodescanner.utils.UiState

class AuthRepositoryImpl(
    val database: FirebaseFirestore,
    val auth: FirebaseAuth,
) : AuthRepository {
    override fun registerUser(
        email: String,
        password: String,
        user: User, result: (UiState<String>) -> Unit
    ) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    user.id = auth.currentUser?.uid ?: ""
                    updateUserInfo(user) { state ->
                        when (state) {
                            is UiState.Success -> result.invoke(UiState.Success("User register successfully"))
                            is UiState.Failure -> result.invoke(UiState.Failure(state.error))
                            else -> {}
                        }
                    }
                } else {
                    try {
                        throw it.exception ?: Exception("Invalid authentication")
                    } catch (e: FirebaseAuthWeakPasswordException) {
                        result.invoke(UiState.Failure("Authentication failed, Password should be at least 6 characters"))
                    } catch (e: FirebaseAuthInvalidCredentialsException) {
                        result.invoke(UiState.Failure("Authentication failed, Invalid email entered"))
                    } catch (e: FirebaseAuthUserCollisionException) {
                        result.invoke(UiState.Failure("Authentication failed, Email already registered."))
                    } catch (e: Exception) {
                        result.invoke(UiState.Failure(e.message))
                    }
                }
            }.addOnFailureListener {
                result.invoke(UiState.Failure(it.localizedMessage))
            }
    }

    override fun loginUser(email: String, password: String, result: (UiState<String>) -> Unit) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    result.invoke(UiState.Success("Login successfully"))
                }
            }
            .addOnFailureListener {
                result.invoke(UiState.Failure("Authentication failed"))
            }
    }

    override fun updateUserInfo(user: User, result: (UiState<String>) -> Unit) {
        val document = database.collection("users").document(user.id)
        document
            .set(user)
            .addOnCompleteListener {
                result.invoke(UiState.Success("User has been update successfully"))
            }
            .addOnFailureListener {
                result.invoke(UiState.Failure(it.localizedMessage))
            }
    }

    override fun forgotPassword(user: User, result: (UiState<String>) -> Unit) {
        TODO("Not yet implemented")
    }
}