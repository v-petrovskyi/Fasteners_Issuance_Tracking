package com.v_petr.qrandbarcodescanner.data.repository

import com.v_petr.qrandbarcodescanner.data.model.MaterialIssueRecord
import com.v_petr.qrandbarcodescanner.utils.UiState

interface MaterialIssueRecordRepository {
    fun getAll(result: (UiState<List<MaterialIssueRecord>>) -> Unit)
    fun add(materialIssueRecord: MaterialIssueRecord, result: (UiState<String>) -> Unit)
    fun delete(materialIssueRecord: MaterialIssueRecord, result: (UiState<String>) -> Unit)
}