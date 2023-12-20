package com.v_petr.qrandbarcodescanner.data.repository

import com.v_petr.qrandbarcodescanner.data.model.PartCode
import com.v_petr.qrandbarcodescanner.utils.UiState

interface PartCodeRepository {
    fun add(partCode: PartCode, result: (UiState<String>) -> Unit)
    fun get(code: String, result: (UiState<PartCode>) -> Unit)
}