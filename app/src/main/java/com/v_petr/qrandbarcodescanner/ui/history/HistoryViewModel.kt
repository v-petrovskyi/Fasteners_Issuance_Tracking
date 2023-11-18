package com.v_petr.qrandbarcodescanner.ui.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.v_petr.qrandbarcodescanner.data.model.MaterialIssueRecord
import com.v_petr.qrandbarcodescanner.data.repository.MaterialIssueRecordRepository
import com.v_petr.qrandbarcodescanner.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    val repository: MaterialIssueRecordRepository,
) : ViewModel() {
    private val _records = MutableLiveData<UiState<List<MaterialIssueRecord>>>()
    val records: LiveData<UiState<List<MaterialIssueRecord>>>
        get() = _records

    private val _addRecord = MutableLiveData<UiState<String>>()
    val addRecord: LiveData<UiState<String>>
        get() = _addRecord

    fun getAllRecords() {
        _records.value = UiState.Loading
        repository.getAll { _records.value = it }
    }

    fun add() {
        _records.value = UiState.Loading
        val materialIssueRecord = MaterialIssueRecord("test part", "test mater", 2)
        repository.add(materialIssueRecord) {
            _addRecord.value = it
        }
    }

    companion object {
        const val TAG = "HistoryViewModel"
    }
}