package com.example.e_commerce.repository

import com.example.e_commerce.api.RetrofitInstance
import com.example.e_commerce.models.auth.login.LoginRequest
import com.example.e_commerce.models.auth.register.RegisterRequest

class AuthRepository {

    suspend fun login(loginRequest: LoginRequest) =
            RetrofitInstance.api.login(loginRequest)

    suspend fun register(registerRequest: RegisterRequest) =
        RetrofitInstance.api.register(registerRequest)

    suspend fun getProfileData(token : String) =
            RetrofitInstance.api.getProfileData(token)

    suspend fun getHomeData(token: String?) =
            RetrofitInstance.api.getHomeData(token)

}