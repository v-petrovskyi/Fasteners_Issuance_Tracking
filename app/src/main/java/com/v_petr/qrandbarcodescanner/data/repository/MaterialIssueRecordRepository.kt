package com.v_petr.qrandbarcodescanner.data.repository

import com.v_petr.qrandbarcodescanner.data.model.MaterialIssueRecord
import com.v_petr.qrandbarcodescanner.utils.UiState

interface MaterialIssueRecordRepository {
    fun getAll(): UiState<List<MaterialIssueRecord>>
}