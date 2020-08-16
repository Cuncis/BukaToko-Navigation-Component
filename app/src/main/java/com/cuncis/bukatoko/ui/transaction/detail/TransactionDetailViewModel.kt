package com.cuncis.bukatoko.ui.transaction.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cuncis.bukatoko.data.model.Resource
import com.cuncis.bukatoko.data.model.TransactionDetail
import com.cuncis.bukatoko.data.repository.ApiRepoProduct
import com.cuncis.bukatoko.util.ErrorUtils
import kotlinx.coroutines.launch

class TransactionDetailViewModel(private val apiRepoProduct: ApiRepoProduct) : ViewModel() {

    private val _transactionDetail = MutableLiveData<Resource<TransactionDetail.Response>>()
    val transactionDetail: MutableLiveData<Resource<TransactionDetail.Response>>
        get() = _transactionDetail

    fun getTransactionPaid(transactionCode: String) {
        _transactionDetail.postValue(Resource.loading(null))
        viewModelScope.launch {
            try {
                val response = apiRepoProduct.getTransactionDetail(transactionCode)
                _transactionDetail.postValue(Resource.success(response))
            } catch (t: Throwable) {
                _transactionDetail.postValue(Resource.error(ErrorUtils.getErrorThrowableMessage(t), null, t))
            }
        }
    }

}