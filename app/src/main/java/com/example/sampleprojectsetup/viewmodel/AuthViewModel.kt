package com.example.sampleprojectsetup.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.sampleprojectsetup.data.model.responsemodel.LoginResponseModel
import com.example.sampleprojectsetup.data.repository.AuthRepository
import com.example.sampleprojectsetup.utilities.helper.SingleLiveData

/**
 * Develop By Messagemuse
 */
class AuthViewModel(application: Application) : AndroidViewModel(application) {

    private var repository = AuthRepository()
    var failureMessage: SingleLiveData<String> = SingleLiveData()
    var objResponse: SingleLiveData<LoginResponseModel> = SingleLiveData()


    init {
        failureMessage = repository.failureMessage
        objResponse = repository.objResponse

    }

    fun login(isGuest: Boolean, countryCode: String, phoneNumber: String, password: String, isLoaderShown: Boolean) {
        repository.loginUser(isGuest, countryCode, phoneNumber, password, isLoaderShown)
    }


}