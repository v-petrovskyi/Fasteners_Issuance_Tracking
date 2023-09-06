package com.v_petr.qrandbarcodescanner

class BarCode {
    var owner = ""
    var order = ""
    var barCode: Long = 0

    constructor()

    constructor(owner: String, order: String, barCode: Long) {
        this.owner = owner
        this.order = order
        this.barCode = barCode
    }

    override fun toString(): String {
        return "BarCode(owner='$owner', order='$order', barCode=$barCode)"
    }

}