package com.example.e_commerce.api

import com.example.e_commerce.models.auth.login.LoginResponse
import com.example.e_commerce.models.auth.login.LoginRequest
import com.example.e_commerce.models.auth.profile.Profile
import com.example.e_commerce.models.auth.register.RegisterRequest
import com.example.e_commerce.models.auth.register.RegisterResponse
import retrofit2.Response
import retrofit2.http.*

interface ApiService {


    @Headers("lang: ar")
    @POST("login")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ): Response<LoginResponse>


    @Headers("lang: ar")
    @POST("register")
    suspend fun register(
        @Body registerRequest: RegisterRequest
    ): Response<RegisterResponse>


    @Headers("lang: ar")
    @GET("profile")
    suspend fun getProfileData(
        @Header("Authorization") token: String
    ): Response<Profile>


//    @FormUrlEncoded
//    @POST("/login")
//    suspend fun login(
//            @Field("email") email : String,
//            @Field("password") password : String
//    ) : Response<Login>
}