package com.example.e_commerce.models.auth.register

import com.google.gson.annotations.SerializedName

data class RegisterRequest(
    @SerializedName("name")
    val name: String,
    @SerializedName("phone")
    val phone: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("image")
    val image: String
)
