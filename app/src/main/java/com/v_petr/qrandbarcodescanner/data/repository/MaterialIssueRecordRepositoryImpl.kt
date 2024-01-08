package com.v_petr.qrandbarcodescanner.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.v_petr.qrandbarcodescanner.data.model.MaterialIssueRecord
import com.v_petr.qrandbarcodescanner.utils.FirebaseTables
import com.v_petr.qrandbarcodescanner.utils.UiState

class MaterialIssueRecordRepositoryImpl(
    private val database: FirebaseFirestore,
) : MaterialIssueRecordRepository {

    override fun getAll(result: (UiState<List<MaterialIssueRecord>>) -> Unit) {
        database.collection(FirebaseTables.MATERIAL_ISSUE_RECORD)
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener {
                val list = arrayListOf<MaterialIssueRecord>()
                for (document in it) {
                    list.add(document.toObject(MaterialIssueRecord::class.java))
                }
                result.invoke(UiState.Success(list))
            }
            .addOnFailureListener {
                result.invoke(UiState.Failure(it.localizedMessage))
            }
    }

    override fun add(materialIssueRecord: MaterialIssueRecord, result: (UiState<String>) -> Unit) {
        val document = database.collection(FirebaseTables.MATERIAL_ISSUE_RECORD).document()
        materialIssueRecord.id = document.id
        document
            .set(materialIssueRecord)
            .addOnSuccessListener {
                result.invoke(UiState.Success(materialIssueRecord.id))
            }
            .addOnFailureListener {
                result.invoke(UiState.Failure(it.localizedMessage))
            }
    }

    override fun delete(
        materialIssueRecord: MaterialIssueRecord,
        result: (UiState<String>) -> Unit
    ) {
        database.collection(FirebaseTables.MATERIAL_ISSUE_RECORD).document(materialIssueRecord.id)
            .delete().addOnSuccessListener {
                result.invoke(UiState.Success("Deleted successfully"))
            }
            .addOnFailureListener {
                result.invoke(UiState.Failure(it.localizedMessage))
            }
    }

    companion object {
        const val TAG = "MaterialIssueRecordRepositoryImpl"
    }
}