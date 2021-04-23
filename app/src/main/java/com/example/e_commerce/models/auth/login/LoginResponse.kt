package com.example.e_commerce.models.auth.login

data class LoginResponse(
    val loginData: LoginData,
    val message: String,
    val status: Boolean
)