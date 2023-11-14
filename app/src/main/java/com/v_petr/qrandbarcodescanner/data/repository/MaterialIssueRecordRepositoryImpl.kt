package com.v_petr.qrandbarcodescanner.data.repository

import com.google.firebase.database.FirebaseDatabase
import com.v_petr.qrandbarcodescanner.data.model.MaterialIssueRecord

class MaterialIssueRecordRepositoryImpl(
    val database: FirebaseDatabase,
) : MaterialIssueRecordRepository {
    override fun getAll(): List<MaterialIssueRecord> {
        return arrayListOf(
            MaterialIssueRecord("part1", "material1", 1),
            MaterialIssueRecord("part2", "material2", 2),
            MaterialIssueRecord("part3", "material3", 3),
            MaterialIssueRecord("part4", "material4", 4),
            MaterialIssueRecord("part5", "material5", 5),
        )
    }
}