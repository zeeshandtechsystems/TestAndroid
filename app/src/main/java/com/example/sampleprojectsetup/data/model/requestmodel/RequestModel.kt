package com.example.sampleprojectsetup.data.model.requestmodel

data class LoginRequestModel(var isGuest: Boolean, var countryCode: String, var phoneNumber: String, var password: String, var deviceToken: String, var deviceModel: String, var os: String, var version: String, var deviceType: Int)

