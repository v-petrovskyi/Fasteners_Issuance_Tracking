package com.v_petr.qrandbarcodescanner

import java.time.LocalDateTime
import kotlin.properties.Delegates

class FastenerIssuanceLog {
    private var partBarcode: String
    private var materialCode: String
    private var qty by Delegates.notNull<Int>()
    private var dateTime: String

    constructor(
        partBarcode: String,
        materialCode: String,
        qty: Int,
    ) {
        this.partBarcode = partBarcode
        this.materialCode = materialCode
        this.qty = qty
        this.dateTime = LocalDateTime.now().toString()
    }
}
