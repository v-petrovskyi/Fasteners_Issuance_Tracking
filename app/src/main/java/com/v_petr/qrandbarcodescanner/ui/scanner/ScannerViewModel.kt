package com.v_petr.qrandbarcodescanner.ui.scanner

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.v_petr.qrandbarcodescanner.data.model.MaterialIssueRecord
import com.v_petr.qrandbarcodescanner.data.repository.MaterialIssueRecordRepository
import com.v_petr.qrandbarcodescanner.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ScannerViewModel @Inject constructor(
    private val repository: MaterialIssueRecordRepository,
) : ViewModel() {
    private val _addRecord = MutableLiveData<UiState<String>>()
    val addRecord: LiveData<UiState<String>>
        get() = _addRecord

    fun addMaterialIssueRecord(materialIssueRecord: MaterialIssueRecord) {
        _addRecord.value = UiState.Loading
        val result: ((UiState<String>) -> Unit) = { _addRecord.value = it }
        repository.add(materialIssueRecord, result)
    }

}