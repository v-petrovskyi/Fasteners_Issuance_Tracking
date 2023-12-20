package com.v_petr.qrandbarcodescanner.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import com.v_petr.qrandbarcodescanner.data.model.PartCode
import com.v_petr.qrandbarcodescanner.data.repository.PartCodeRepository
import com.v_petr.qrandbarcodescanner.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UploadingNewPartCodesViewModel @Inject constructor(
    private val repository: PartCodeRepository,
) : ViewModel() {

    private fun add(partCode: PartCode) {
        repository.add(partCode) { uiState ->
            when (uiState) {
                is UiState.Loading -> {}
                is UiState.Success -> {
//                    Log.d(TAG, "add: Success ${uiState.data}")
                }
                is UiState.Failure -> {
                    Log.e(TAG, "add: Failure ${uiState.error}")
                }
            }
        }
    }

    fun upload(list: List<PartCode>) {
        list.forEach {
            add(it)
        }
    }


    companion object {
        const val TAG = "UploadingNewPartCodesViewModel"
    }
}