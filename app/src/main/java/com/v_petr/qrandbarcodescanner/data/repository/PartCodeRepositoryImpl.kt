package com.v_petr.qrandbarcodescanner.data.repository

import android.util.Log
import com.google.firebase.database.FirebaseDatabase
import com.v_petr.qrandbarcodescanner.data.model.PartCode
import com.v_petr.qrandbarcodescanner.utils.FirebaseTables
import com.v_petr.qrandbarcodescanner.utils.UiState

class PartCodeRepositoryImpl(
    private val database: FirebaseDatabase,
) : PartCodeRepository {
    override fun add(partCode: PartCode, result: (UiState<String>) -> Unit) {
        val childId = partCode.barCode.toString()
        database.getReference(FirebaseTables.PART_CODES)
            .child(childId)
            .setValue(partCode)
            .addOnSuccessListener {
                result.invoke(UiState.Success(partCode.barCode.toString()))
            }
            .addOnFailureListener {
                result.invoke(UiState.Failure(it.localizedMessage))
            }
    }

    override fun get(code: String, result: (UiState<PartCode>) -> Unit) {
        Log.d(TAG, "get: code = $code")
        database.getReference(FirebaseTables.PART_CODES)
            .child(code)
            .get()
            .addOnSuccessListener {
                Log.d(TAG, "get: ${it.exists()}")
                val data: PartCode? = it.getValue(PartCode::class.java)
                if (data != null) {
                    Log.d(TAG, "get: $data")
                    result.invoke(UiState.Success(data))
                }else{
                    result.invoke(UiState.Failure("not found"))
                }
            }
            .addOnFailureListener {
                result.invoke(UiState.Failure(it.localizedMessage))
            }
    }

    companion object {
        const val TAG = "PartCodeRepositoryImpl"
    }
}