package com.v_petr.qrandbarcodescanner.data.repository

import com.v_petr.qrandbarcodescanner.data.model.User
import com.v_petr.qrandbarcodescanner.utils.UiState

interface AuthRepository {
    fun loginUser(user: User, result: (UiState<String>) -> Unit)
    fun registerUser(email: String, password: String, user: User, result: (UiState<String>) -> Unit)
    fun updateUserInfo(user: User, result: (UiState<String>) -> Unit)
    fun forgotPassword(user: User, result: (UiState<String>) -> Unit)
}