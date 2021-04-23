package com.example.e_commerce.models.auth.login

data class LoginData(
    val credit: Int,
    val email: String,
    val id: Int,
    val image: String,
    val name: String,
    val phone: String,
    val points: Int,
    val token: String?
)