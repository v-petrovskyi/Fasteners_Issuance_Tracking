package com.v_petr.qrandbarcodescanner.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.v_petr.qrandbarcodescanner.data.model.User
import com.v_petr.qrandbarcodescanner.data.repository.AuthRepository
import com.v_petr.qrandbarcodescanner.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    val repository: AuthRepository
) : ViewModel() {
    private val _register = MutableLiveData<UiState<String>>()
    val register: LiveData<UiState<String>>
        get() = _register

    private val _forgotPassword = MutableLiveData<UiState<String>>()
    val forgotPassword: LiveData<UiState<String>>
        get() = _forgotPassword

    private val _login = MutableLiveData<UiState<String>>()
    val login: LiveData<UiState<String>>
        get() = _login

    fun register(email: String, password: String, user: User) {
        _register.value = UiState.Loading
        val result: ((UiState<String>) -> Unit) = { _register.value = it }
        repository.registerUser(
            email, password, user, result
        )
    }

    fun login(email: String, password: String) {
        _login.value = UiState.Loading
        repository.loginUser(email, password) { _login.value = it }
    }

    fun forgotPassword(email: String) {
        _forgotPassword.value = UiState.Loading
        repository.forgotPassword(email) { _forgotPassword.value = it }
    }

    fun logout(result: () -> Unit){
        repository.logout(result)
    }

}