package com.v_petr.qrandbarcodescanner.data.repository

import android.util.Log
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ServerValue
import com.google.firebase.database.ktx.getValue
import com.v_petr.qrandbarcodescanner.data.model.MaterialIssueRecord
import com.v_petr.qrandbarcodescanner.utils.FirebaseTables
import com.v_petr.qrandbarcodescanner.utils.UiState

class MaterialIssueRecordRepositoryImpl(
    private val database: FirebaseDatabase,
) : MaterialIssueRecordRepository {

    override fun getAll(result: (UiState<List<MaterialIssueRecord>>) -> Unit) {
        val data = listOf<MaterialIssueRecord>()
        database.getReference("refer").child(FirebaseTables.MATERIAL_ISSUE_RECORD)
            .get()
            .addOnSuccessListener { dataSnapshot ->
                val list = arrayListOf<MaterialIssueRecord>()
                val map = dataSnapshot.getValue<HashMap<String, MaterialIssueRecord>>()
                Log.d(TAG, "getAll: $dataSnapshot")
                map?.values?.toList()?.sortedByDescending { it.timestamp.toString().toLong() }
                    ?.let { list.addAll(it) }
                result.invoke(
                    UiState.Success(list)
                )
            }.addOnFailureListener {
                result.invoke(UiState.Failure(it.localizedMessage))
            }
    }

    override fun add(materialIssueRecord: MaterialIssueRecord, result: (UiState<String>) -> Unit) {
        val reference = database.getReference("refer").child(FirebaseTables.MATERIAL_ISSUE_RECORD)
            .push()
        materialIssueRecord.key = reference.key.toString()
        materialIssueRecord.timestamp = ServerValue.TIMESTAMP

        reference
            .setValue(materialIssueRecord)
            .addOnSuccessListener {
                result.invoke(UiState.Success(materialIssueRecord.key))
            }.addOnFailureListener {
                result.invoke(UiState.Failure(it.localizedMessage))
            }

    }

    companion object {
        const val TAG = "MaterialIssueRecordRepositoryImpl"
    }
}