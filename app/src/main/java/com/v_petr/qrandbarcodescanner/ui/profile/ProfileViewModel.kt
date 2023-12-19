package com.v_petr.qrandbarcodescanner.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.v_petr.qrandbarcodescanner.data.model.User
import com.v_petr.qrandbarcodescanner.data.repository.AuthRepository
import com.v_petr.qrandbarcodescanner.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

    private val _currentUser: MutableLiveData<UiState<User>> by lazy { MutableLiveData<UiState<User>>() }
    val currentUser: LiveData<UiState<User>>
        get() = _currentUser

    private val _updateUser: MutableLiveData<UiState<String>> by lazy {
        MutableLiveData<UiState<String>>()
    }
    val updateUser: LiveData<UiState<String>>
        get() = _updateUser

    private lateinit var user: User

    fun getUser() {
        _currentUser.value = UiState.Loading
        repository.getUser {
            if (it != null) {
                user = it
                _currentUser.value = UiState.Success(it)
            } else {
                _currentUser.value = UiState.Failure("Fail")
            }
        }
    }

    fun updateUser(firstName: String, lastName: String) {
        _updateUser.value = UiState.Loading
        user.firstName = firstName
        user.lastName = lastName
        repository.updateUserInfo(user) {
            _updateUser.value = it
        }
    }

}