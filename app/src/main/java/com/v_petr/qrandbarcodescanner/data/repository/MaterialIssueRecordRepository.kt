package com.v_petr.qrandbarcodescanner.data.repository

import com.v_petr.qrandbarcodescanner.data.model.MaterialIssueRecord

interface MaterialIssueRecordRepository {
    fun getAll(): List<MaterialIssueRecord>
}