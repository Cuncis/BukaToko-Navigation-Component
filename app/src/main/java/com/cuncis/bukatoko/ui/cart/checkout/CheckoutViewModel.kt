package com.cuncis.bukatoko.ui.cart.checkout

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cuncis.bukatoko.data.model.City
import com.cuncis.bukatoko.data.model.Cost
import com.cuncis.bukatoko.data.model.Resource
import com.cuncis.bukatoko.data.model.TransactionData
import com.cuncis.bukatoko.data.repository.ApiRepoProduct
import com.cuncis.bukatoko.data.repository.ApiRepoRajaOngkir
import kotlinx.coroutines.launch

class CheckoutViewModel(private val apiRepoRajaOngkir: ApiRepoRajaOngkir, private val apiRepoProduct: ApiRepoProduct)
    : ViewModel() {

    private val _cities = MutableLiveData<Resource<City.Response>>()
    val cities: MutableLiveData<Resource<City.Response>>
        get() = _cities

    private val _cost = MutableLiveData<Resource<Cost.Response>>()
    val cost: MutableLiveData<Resource<Cost.Response>>
        get() = _cost

    private val _transaction = MutableLiveData<Resource<TransactionData.Data>>()
    val transaction: MutableLiveData<Resource<TransactionData.Data>>
        get() = _transaction


    fun getCities() {
        _cities.postValue(Resource.loading(null))
        viewModelScope.launch {
            try {
                val response = apiRepoRajaOngkir.getCities()
                _cities.postValue(Resource.success(response))
            } catch (t: Throwable) {
                _cities.postValue(Resource.error(t.message.toString(), null, t))
            }
        }
    }

    fun postCost(origin: String, destination: String, weight: String, courier: String) {
        _cost.postValue(Resource.loading(null))
        viewModelScope.launch {
            try {
                val response = apiRepoRajaOngkir.postCost(origin, destination, weight, courier)
                _cost.postValue(Resource.success(response))
            } catch (t: Throwable) {
                _cost.postValue(Resource.error(t.message.toString(), null, t))
            }
        }
    }

    fun postTransaction(transactionData: TransactionData.Data) {
        _transaction.postValue(Resource.loading(null))
        viewModelScope.launch {
            try {
                val response = apiRepoProduct.postTransaction(transactionData)
                _transaction.postValue(Resource.success(response))
            } catch (t: Throwable) {
                _transaction.postValue(Resource.error(t.message.toString(), null, t))
            }
        }
    }

}