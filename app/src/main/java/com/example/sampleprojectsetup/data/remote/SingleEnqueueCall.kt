package com.example.sampleprojectsetup.data.remote

import com.example.sampleprojectsetup.data.remote.callback.IGenericCallBack
import com.example.sampleprojectsetup.data.model.responsemodel.GeneralResponseModel
import com.example.sampleprojectsetup.utilities.Constants
import com.example.sampleprojectsetup.utilities.extensions.hideAppLoader
import com.example.sampleprojectsetup.utilities.extensions.onTokenExpiredLogout
import com.example.sampleprojectsetup.utilities.extensions.showAppLoader
import com.example.sampleprojectsetup.view.base.ActivityBase
import com.google.android.material.snackbar.Snackbar
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.net.UnknownHostException


object SingleEnqueueCall {
    var retryCount = 0
    var snackbar = Snackbar.make(ActivityBase.activity.findViewById(android.R.id.content), Constants.CONST_NO_INTERNET_CONNECTION, Snackbar.LENGTH_INDEFINITE)

    fun <T> callRetrofit(call: Call<T>, apiName: String, isLoaderShown: Boolean, apiListener: IGenericCallBack) {
        if (isLoaderShown)
            ActivityBase.activity.showAppLoader()
        snackbar.dismiss()
        call.enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                hideAppLoader()
                if (response.isSuccessful) {
                    retryCount = 0
                    apiListener.success(apiName, response.body())
                } else {
                    when {
                        response.code() == 401 -> {
                            onTokenExpiredLogout()
                            return
                        }
                        response.errorBody() != null -> try {
                            retryCount = 0
                            val gson = GsonBuilder().create()
                            try {
                                val errorModel: GeneralResponseModel = gson.fromJson(response.errorBody()!!.string(), GeneralResponseModel::class.java)
                                apiListener.failure(apiName, errorModel.messages)
                            } catch (ex: Exception) {
                                ex.printStackTrace()
                                apiListener.failure(apiName, Constants.CONST_SERVER_NOT_RESPONDING)
                            }
                        } catch (e: IOException) {
                            e.printStackTrace()
                            apiListener.failure(apiName, Constants.CONST_SERVER_NOT_RESPONDING)
                        }
                        else -> {
                            apiListener.failure(apiName, Constants.CONST_SERVER_NOT_RESPONDING)
                            return
                        }
                    }
                }
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                hideAppLoader()
                val callBack = this
                if (t.message != "Canceled") {
                    if (t is UnknownHostException || t is IOException) {
                        snackbar.setAction("Retry") {
                            snackbar.dismiss()
                            enqueueWithRetry(call, callBack, isLoaderShown)
                        }
                            snackbar.show()
                       // apiListener.failure(apiName, Constants.CONST_NO_INTERNET_CONNECTION)
                    } else {
                        retryCount = 0
                        apiListener.failure(apiName, t.toString())
                    }
                }else {
                    retryCount = 0
                }
            }
        })
    }

    fun <T> enqueueWithRetry(call: Call<T>, callback: Callback<T>, isLoaderShown: Boolean) {
        ActivityBase.activity.showAppLoader()
        call.clone().enqueue(callback)
    }
}
