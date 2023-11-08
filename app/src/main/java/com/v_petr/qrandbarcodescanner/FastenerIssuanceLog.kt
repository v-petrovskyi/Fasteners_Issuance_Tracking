package com.v_petr.qrandbarcodescanner

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable

class FastenerIssuanceLog : BaseObservable {
    private var partBarcode: String = ""
    private var materialCode: String = ""
    private var qty: Int = 0
    private lateinit var dateTime: String


    constructor()

    constructor(partBarcode: String, materialCode: String, qty: Int) {
        this.partBarcode = partBarcode
        this.materialCode = materialCode
        this.qty = qty
    }
    @Bindable
    fun getPartBarcode(): String {
        return partBarcode
    }

    fun setPartBarcode(partBarcode: String) {
        this.partBarcode = partBarcode
        notifyPropertyChanged(BR.logRecord)
    }

    @Bindable
    fun getMaterialCode(): String {
        return materialCode
    }

    fun setMaterialCode(materialCode: String) {
        this.materialCode = materialCode
        notifyPropertyChanged(BR.logRecord)

    }

    @Bindable
    fun getQty(): Int {
        return qty
    }

    fun setQty(qty: Int) {
        this.qty = qty
        notifyPropertyChanged(BR.logRecord)

    }

    override fun toString(): String {
        return "FastenerIssuanceLog(partBarcode='$partBarcode', materialCode='$materialCode', qty=$qty)"
    }


}
