package com.v_petr.qrandbarcodescanner

import java.util.Calendar

class FastenerIssuanceLog {
    lateinit var partBarcode: String
    lateinit var materialCode: String
    var qty: Int = 0
    lateinit var dateTime: String


    constructor()

    constructor(
        partBarcode: String,
        materialCode: String,
        qty: Int,
    ) {
        this.partBarcode = partBarcode
        this.materialCode = materialCode
        this.qty = qty
        this.dateTime = Calendar.getInstance().time.toString()
    }

    override fun toString(): String {
        return "FastenerIssuanceLog(partBarcode='$partBarcode', materialCode='$materialCode', qty=$qty)"
    }


}
