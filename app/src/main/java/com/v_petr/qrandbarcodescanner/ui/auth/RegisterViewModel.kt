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
class RegisterViewModel @Inject constructor(
    val repository: AuthRepository
) : ViewModel() {
    private val _register = MutableLiveData<UiState<String>>()
    val register: LiveData<UiState<String>>
        get() = _register

    fun register(email: String, password: String, user: User) {
        _register.value = UiState.Loading
        val result: ((UiState<String>) -> Unit) = { _register.value = it }
        repository.registerUser(
            email, password, user, result
        )
    }


}