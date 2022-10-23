package com.inhaproject.karaoke3.retrofit

import java.io.Serializable

data class RegisterModel(
    var id: String,
    var pw: String
)


data class RegisterResult(
    var message: Boolean
)

data class LoginModel(
    var id: String,
    var pw: String
)

data class LoginResult(
    var Status: String
)

data class User(
    val UID: Int,
    val id: String,
    val password: String
): Serializable