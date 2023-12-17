package com.v_petr.qrandbarcodescanner.ui.profile

import androidx.lifecycle.ViewModel
import com.v_petr.qrandbarcodescanner.data.model.User
import com.v_petr.qrandbarcodescanner.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

    fun getUser(result: (User?) -> Unit) {
        repository.getSession(result)
    }
}