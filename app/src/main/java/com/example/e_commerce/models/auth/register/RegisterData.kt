package com.example.e_commerce.models.auth.register

data class RegisterData(
    val email: String,
    val id: Int,
    val image: String,
    val name: String,
    val phone: String,
    val token: String
)