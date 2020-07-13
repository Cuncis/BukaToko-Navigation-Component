package com.cuncis.bukatoko.ui.cart

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR

object Item: BaseObservable() {

    @Bindable
    private var selectedItemPosition = 0

    @JvmStatic
    fun getSelectedItemPosition(): Int {
        return selectedItemPosition
    }

    fun setSelectedItemPosition(selectedItemPosition: Int) {
        this.selectedItemPosition = selectedItemPosition
        notifyPropertyChanged(BR.cart)
    }

}