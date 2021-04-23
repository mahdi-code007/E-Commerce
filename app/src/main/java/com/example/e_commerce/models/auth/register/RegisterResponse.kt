package com.example.e_commerce.models.auth.register

data class RegisterResponse(
    val registerData: RegisterData,
    val message: String,
    val status: Boolean
)