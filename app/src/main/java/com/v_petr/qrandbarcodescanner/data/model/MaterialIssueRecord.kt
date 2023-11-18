package com.v_petr.qrandbarcodescanner.data.model

data class MaterialIssueRecord(
    val part: String = "",
    val material: String = "",
    val qty: Int = 0,
    var key: String = "",
    var timestamp: Any = 0,
)
