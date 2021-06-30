package com.example.sampleprojectsetup.data.remote.callback

import android.net.Uri

/**
 * Created by zeeshan irfan on 03/10/2019.
 */


interface IGenericCallBack {
    fun success(apiName: String, response: Any?)

    fun failure(apiName: String,message: String?)

}

interface ICallBackUri {
    fun imageUri(result: Uri?)
}

interface ICallBackUriMultiple {
    fun imageUri(result: ArrayList<Uri>)
}