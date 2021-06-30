package com.example.sampleprojectsetup.data.repository

import android.os.Build
import com.example.sampleprojectsetup.SampleApp
import com.example.sampleprojectsetup.data.model.responsemodel.LoginResponseModel
import com.example.sampleprojectsetup.data.remote.SingleEnqueueCall
import com.example.sampleprojectsetup.data.remote.callback.IGenericCallBack
import com.example.sampleprojectsetup.utilities.Constants
import com.example.sampleprojectsetup.utilities.extensions.currentOS
import com.example.sampleprojectsetup.utilities.helper.SingleLiveData
import com.example.sampleprojectsetup.data.model.requestmodel.*

class AuthRepository : IGenericCallBack {

    var failureMessage: SingleLiveData<String> = SingleLiveData()
    var objResponse: SingleLiveData<LoginResponseModel> = SingleLiveData()


    fun loginUser(isGuest: Boolean, countryCode: String, phoneNumber: String, password: String, isLoaderShown: Boolean) {
        val deviceType = 1
        val deviceModel: String = Build.MANUFACTURER + " " + Build.MODEL
        val os: String = currentOS()
        val version: String = Build.VERSION.RELEASE
        //EasyPeasyApp.db.getString(Constants.DEVICE_ID)!!
        val call = SampleApp.apiService.login(LoginRequestModel(isGuest, countryCode, phoneNumber, password, SampleApp.db.getString(
            Constants.DEVICE_ID, "")!!, deviceModel, os, version, deviceType))
        SingleEnqueueCall.callRetrofit(call, Constants.LOGIN_URL, isLoaderShown, this)
    }



    override fun success(apiName: String, response: Any?) {
        when (apiName) {
            Constants.LOGIN_URL -> {
                val responseModel = response as LoginResponseModel
                if (response.isError) {
                    failureMessage.postValue(responseModel.messages)
                } else
                    objResponse.postValue(responseModel)
            }

        }

    }

    override fun failure(apiName: String, message: String?) {
     failureMessage.postValue(message)

    }


}