package com.example.e_commerce.repository

import com.example.e_commerce.api.ApiService
import com.example.e_commerce.models.auth.login.LoginRequest
import com.example.e_commerce.models.auth.register.RegisterRequest

class MainRepository constructor(private val apiService: ApiService) {

    suspend fun login(loginRequest: LoginRequest) =
        apiService.login(loginRequest)

    suspend fun register(registerRequest: RegisterRequest) =
        apiService.register(registerRequest)

    suspend fun getProfileData(token: String) =
        apiService.getProfileData(token)




}