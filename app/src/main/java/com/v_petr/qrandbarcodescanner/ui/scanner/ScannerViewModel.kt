package com.v_petr.qrandbarcodescanner.ui.scanner

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.v_petr.qrandbarcodescanner.data.repository.MaterialIssueRecordRepository
import com.v_petr.qrandbarcodescanner.utils.Event
import com.v_petr.qrandbarcodescanner.utils.MaterialIssueRecordDTO
import com.v_petr.qrandbarcodescanner.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ScannerViewModel @Inject constructor(
    private val repository: MaterialIssueRecordRepository,
) : ViewModel() {
    private val _addRecord: MutableLiveData<Event<UiState<String>>> by lazy {
        MutableLiveData<Event<UiState<String>>>()
    }
    val addRecord: LiveData<Event<UiState<String>>>
        get() = _addRecord

    val currentRecord = MaterialIssueRecordDTO

    fun addMaterialIssueRecord() {
        _addRecord.value = Event(UiState.Loading)
        val result: ((UiState<String>) -> Unit) = { _addRecord.value = Event(it) }
        repository.add(currentRecord.toMaterialIssueRecord(), result)
    }

    fun increaseQty() {
        currentRecord.qty++
    }

    fun decreaseQty() {
        if (currentRecord.qty > 0) currentRecord.qty-- else currentRecord.qty = 0
    }

    fun clearFieldsCurrentRecord() {
        currentRecord.material = ""
        currentRecord.part = ""
        currentRecord.qty = 0
    }

}