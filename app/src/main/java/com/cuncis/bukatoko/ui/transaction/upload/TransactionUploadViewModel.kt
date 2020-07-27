package com.cuncis.bukatoko.ui.transaction.upload


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cuncis.bukatoko.data.model.Resource
import com.cuncis.bukatoko.data.model.Upload
import com.cuncis.bukatoko.data.repository.ApiRepoProduct
import com.cuncis.bukatoko.util.ErrorUtils
import kotlinx.coroutines.launch
import okhttp3.MultipartBody

class TransactionUploadViewModel(private val apiRepoProduct: ApiRepoProduct): ViewModel() {

    private val _uploadImage = MutableLiveData<Resource<Upload.Response>>()
    val uploadImage: MutableLiveData<Resource<Upload.Response>>
        get() = _uploadImage

    fun uploadImageFile(code: String, imageFile: MultipartBody.Part) {
        _uploadImage.postValue(Resource.loading(null))
        viewModelScope.launch {
            try {
                val response = apiRepoProduct.postUploadImage(code, imageFile)
                _uploadImage.postValue(Resource.success(response))
            } catch (t: Throwable) {
                _uploadImage.postValue(Resource.error(ErrorUtils.getErrorThrowableMessage(t), null, t))
            }
        }
    }

}