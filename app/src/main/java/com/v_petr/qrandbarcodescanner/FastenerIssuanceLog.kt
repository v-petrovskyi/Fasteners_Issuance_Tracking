package com.v_petr.qrandbarcodescanner

class FastenerIssuanceLog {
    lateinit var partBarcode: String
    lateinit var materialCode: String
    var qty: Int = 0
    private lateinit var dateTime: String


    constructor()

    constructor(
        partBarcode: String,
        materialCode: String,
        qty: Int,
    ) {
        this.partBarcode = partBarcode
        this.materialCode = materialCode
        this.qty = qty
    }

    override fun toString(): String {
        return "FastenerIssuanceLog(partBarcode='$partBarcode', materialCode='$materialCode', qty=$qty)"
    }


}
