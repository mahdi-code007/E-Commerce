package com.example.e_commerce.api

import com.example.e_commerce.models.auth.login.LoginRequest
import com.example.e_commerce.models.auth.login.LoginResponse
import com.example.e_commerce.models.auth.profile.ProfileResponse
import com.example.e_commerce.models.auth.register.RegisterRequest
import com.example.e_commerce.models.auth.register.RegisterResponse
import com.example.e_commerce.models.banners.BannersResponse
import com.example.e_commerce.models.categories.CategoriesDetails
import com.example.e_commerce.models.categories.CategoriesResponse
import com.example.e_commerce.models.home.homeData.HomeDataResponse
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
    ): Response<ProfileResponse>

    @Headers("lang: ar")
    @GET("home")
    suspend fun getHomeData(
        @Header("Authorization") token: String?
    ): Response<HomeDataResponse>

    @Headers("lang: ar")
    @GET("banners")
    suspend fun getBanners(
    ): Response<BannersResponse>

    @Headers("lang: ar")
    @GET("categories")
    suspend fun getCategories(
    ): Response<CategoriesResponse>

    @Headers("lang: ar")
    @GET("categories/{category_id}")
    suspend fun getCategoryDetails(
        @Path("category_id") category_id: Int
    ): Response<CategoriesDetails>


//    @FormUrlEncoded
//    @POST("/login")
//    suspend fun login(
//            @Field("email") email : String,
//            @Field("password") password : String
//    ) : Response<Login>
}