package com.v_petr.qrandbarcodescanner.ui.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.v_petr.qrandbarcodescanner.data.model.MaterialIssueRecord
import com.v_petr.qrandbarcodescanner.data.repository.MaterialIssueRecordRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    val repository: MaterialIssueRecordRepository,
) : ViewModel() {
    private val _records = MutableLiveData<List<MaterialIssueRecord>>()
    val record: LiveData<List<MaterialIssueRecord>>
        get() = _records

    fun getAllRecords() {
        _records.value = repository.getAll()
    }
}