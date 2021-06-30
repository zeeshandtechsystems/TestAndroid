package com.example.sampleprojectsetup.data.remote

import com.example.sampleprojectsetup.utilities.Constants
import com.example.sampleprojectsetup.data.model.requestmodel.*
import com.example.sampleprojectsetup.data.model.responsemodel.LoginResponseModel
import retrofit2.Call
import retrofit2.http.*


interface ApiInterface {

    //auth
    @POST(Constants.LOGIN_URL)
    fun login(@Body obj: LoginRequestModel): Call<LoginResponseModel>


}