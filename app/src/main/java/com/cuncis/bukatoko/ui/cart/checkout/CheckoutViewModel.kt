package com.cuncis.bukatoko.ui.cart.checkout

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cuncis.bukatoko.data.model.City
import com.cuncis.bukatoko.data.model.Resource
import com.cuncis.bukatoko.data.repository.ApiRepoRajaOngkir
import kotlinx.coroutines.launch

class CheckoutViewModel(private val apiRepoRajaOngkir: ApiRepoRajaOngkir): ViewModel() {

    private val _cities = MutableLiveData<Resource<City.Response>>()
    val cities: MutableLiveData<Resource<City.Response>>
        get() = _cities

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

}