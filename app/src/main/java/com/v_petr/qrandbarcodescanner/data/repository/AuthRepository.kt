package com.v_petr.qrandbarcodescanner.data.repository

import com.v_petr.qrandbarcodescanner.data.model.User
import com.v_petr.qrandbarcodescanner.utils.UiState

interface AuthRepository {
    fun loginUser(email: String, password: String, result: (UiState<String>) -> Unit)
    fun registerUser(email: String, password: String, user: User, result: (UiState<String>) -> Unit)
    fun updateUserInfo(user: User, result: (UiState<String>) -> Unit)
    fun forgotPassword(email: String, result: (UiState<String>) -> Unit)
    fun logout(result: () -> Unit)
}