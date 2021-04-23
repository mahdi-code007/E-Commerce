package com.example.e_commerce.models.auth.login

import com.google.gson.annotations.SerializedName

data class LoginRequest(
        @SerializedName("email")
        val email : String,
        @SerializedName("password")
        val password : String
)
