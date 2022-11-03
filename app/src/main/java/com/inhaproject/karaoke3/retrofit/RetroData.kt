package com.inhaproject.karaoke3.retrofit

import java.io.Serializable

data class RegisterModel(
    var userid: String,
    var password: String
)

data class LoginModel(
    var userid: String,
    var password: String
)

data class LoginResult(
    var token: String
)

data class User(
    val UID: Int,
    val userid: String,
    val password: String
): Serializable