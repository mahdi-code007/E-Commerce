package com.example.e_commerce.models.auth.login

data class LoginResponse(
    val `data`: Data,
    val message: String,
    val status: Boolean
)