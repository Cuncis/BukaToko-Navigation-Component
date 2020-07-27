package com.cuncis.bukatoko.ui.transaction

import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cuncis.bukatoko.data.model.Resource
import com.cuncis.bukatoko.data.model.Transaction
import com.cuncis.bukatoko.data.repository.ApiRepoProduct
import com.cuncis.bukatoko.util.ErrorUtils
import kotlinx.coroutines.launch

class TransactionViewModel(private val apiRepoProduct: ApiRepoProduct) : ViewModel() {

    private val _transactionUnpaidList = MutableLiveData<Resource<Transaction.Response>>()
    val transactionUnpaidList: MutableLiveData<Resource<Transaction.Response>>
        get() = _transactionUnpaidList

    private val _transactionPaidList = MutableLiveData<Resource<Transaction.Response>>()
    val transactionPaidList: MutableLiveData<Resource<Transaction.Response>>
        get() = _transactionPaidList

    fun getTransactionUnpaid(userId: String) {
        _transactionUnpaidList.postValue(Resource.loading(null))
        viewModelScope.launch {
            try {
                val response = apiRepoProduct.getTransactionUnpaid(userId)
                _transactionUnpaidList.postValue(Resource.success(response))
            } catch (t: Throwable) {
                _transactionUnpaidList.postValue(Resource.error(ErrorUtils.getErrorThrowableMessage(t), null, t))
            }
        }
    }

    fun getTransactionPaid(userId: String) {
        _transactionPaidList.postValue(Resource.loading(null))
        viewModelScope.launch {
            try {
                val response = apiRepoProduct.getTransactionPaid(userId)
                _transactionPaidList.postValue(Resource.success(response))
            } catch (t: Throwable) {
                _transactionPaidList.postValue(Resource.error(ErrorUtils.getErrorThrowableMessage(t), null, t))
            }
        }
    }

}