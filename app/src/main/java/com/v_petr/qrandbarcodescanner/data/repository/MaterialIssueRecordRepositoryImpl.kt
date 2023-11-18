package com.v_petr.qrandbarcodescanner.data.repository

import android.util.Log
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.getValue
import com.v_petr.qrandbarcodescanner.data.model.MaterialIssueRecord
import com.v_petr.qrandbarcodescanner.utils.FirebaseTables
import com.v_petr.qrandbarcodescanner.utils.UiState

class MaterialIssueRecordRepositoryImpl(
    private val database: FirebaseDatabase,
) : MaterialIssueRecordRepository {
    override fun getAll(result: (UiState<List<MaterialIssueRecord>>) -> Unit) {
        val data = listOf<MaterialIssueRecord>()
        database.getReference("refer").child(FirebaseTables.MATERIAL_ISSUE_RECORD).get()
            .addOnSuccessListener {

                val materialIssueRecords = arrayListOf<MaterialIssueRecord>()
                val map = it.getValue<HashMap<String, MaterialIssueRecord>>()
                Log.d("TAG", "getAll: $it")
                map?.forEach { entry ->
                    materialIssueRecords.add(entry.value)
                }
                result.invoke(
                    UiState.Success(materialIssueRecords)
                )
            }.addOnFailureListener {
                result.invoke(UiState.Failure(it.localizedMessage))
            }
    }

    override fun add(materialIssueRecord: MaterialIssueRecord, result: (UiState<String>) -> Unit) {
        val reference = database.getReference("refer").child(FirebaseTables.MATERIAL_ISSUE_RECORD)
            .push()
        materialIssueRecord.key = reference.key.toString()

        reference
            .setValue(materialIssueRecord)
            .addOnSuccessListener {
                result.invoke(UiState.Success(materialIssueRecord.key))
            }.addOnFailureListener {
                result.invoke(UiState.Failure(it.localizedMessage))
            }

    }
}