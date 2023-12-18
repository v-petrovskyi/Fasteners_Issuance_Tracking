package com.v_petr.qrandbarcodescanner.data.model

import com.google.firebase.firestore.ServerTimestamp
import java.util.Date

data class MaterialIssueRecord(
    val part: String = "",
    val material: String = "",
    val qty: Int = 0,
    var id: String = "",
    @ServerTimestamp
    var timestamp: Date = Date(),
    val userId: String = "",
)
