package com.v_petr.qrandbarcodescanner.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.v_petr.qrandbarcodescanner.models.FastenerIssuanceLog

class ScannerViewModel : ViewModel() {

    private lateinit var fastenerIssuanceLog: FastenerIssuanceLog
    private val fastenerIssuanceLogLiveData = MutableLiveData<FastenerIssuanceLog>()
    // TODO: Implement the ViewModel

    fun createNewFastenerIssuanceLog() {
        fastenerIssuanceLog = FastenerIssuanceLog()
    }

    fun getCurrentFastenerIssuanceLog(): MutableLiveData<FastenerIssuanceLog> {
        fastenerIssuanceLogLiveData.value = fastenerIssuanceLog
        return fastenerIssuanceLogLiveData
    }

    fun increaseQty() {
        fastenerIssuanceLog.setQty(fastenerIssuanceLog.getQty() + 1)
        fastenerIssuanceLogLiveData.value = fastenerIssuanceLog
    }

    fun decreaseQty() {
        fastenerIssuanceLog.setQty(fastenerIssuanceLog.getQty() - 1)
        fastenerIssuanceLogLiveData.value = fastenerIssuanceLog
    }


}