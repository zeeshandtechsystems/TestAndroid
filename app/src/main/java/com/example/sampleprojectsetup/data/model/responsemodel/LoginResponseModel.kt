package com.example.sampleprojectsetup.data.model.responsemodel

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


class LoginResponseModel {
    @SerializedName("isError")
    @Expose
    var isError: Boolean = false

    @SerializedName("messages")
    @Expose
    val messages: String? = null

    @SerializedName("data")
    @Expose
    val data: User? = null

    class User {
        @SerializedName("userId")
        @Expose
        val userId: String = ""

        @SerializedName("accessToken")
        @Expose
        val accessToken: String = ""

        @SerializedName("isGuest")
        @Expose
        val isGuest: Boolean = false

        @SerializedName("syncContactCount")
        @Expose
        val syncContactCount: Int = 0
    }
}
