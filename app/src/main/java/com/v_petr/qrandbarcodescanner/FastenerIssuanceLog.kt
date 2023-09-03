package com.v_petr.qrandbarcodescanner

import java.time.LocalDateTime

class FastenerIssuanceLog {
    var partBarcode: String
    var materialCode: String
    var qty: Int
    var dateTime: String

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
