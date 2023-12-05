package com.v_petr.qrandbarcodescanner.utils

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import com.v_petr.qrandbarcodescanner.data.model.MaterialIssueRecord

object MaterialIssueRecordDTO : BaseObservable() {
    var part: String = ""
        @Bindable get() = field
        @Bindable set(value) {
            field = value
            notifyPropertyChanged(BR.part)

        }
    var material: String = ""
        @Bindable get() = field
        @Bindable set(value) {
            field = value
            notifyPropertyChanged(BR.material)
        }
    var qty: Int = 0
        @Bindable get() = field
        @Bindable set(value) {
            field = value
            notifyPropertyChanged(BR.qty)
        }
    fun toMaterialIssueRecord(): MaterialIssueRecord {
        return MaterialIssueRecord(part, material, qty)
    }
}
