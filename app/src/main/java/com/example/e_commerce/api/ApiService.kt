package com.example.e_commerce.api

import com.example.e_commerce.models.MainResponse
import com.example.e_commerce.models.auth.login.LoginRequest
import com.example.e_commerce.models.auth.login.LoginResponse
import com.example.e_commerce.models.auth.profile.ProfileResponse
import com.example.e_commerce.models.auth.register.RegisterRequest
import com.example.e_commerce.models.auth.register.RegisterResponse
import com.example.e_commerce.models.categories.CategoriesResponse
import com.example.e_commerce.models.favorite.Favorites
import com.example.e_commerce.models.favorite.addToFavorites.AddToFavorites
import com.example.e_commerce.models.home.HomeResponse
import com.example.e_commerce.models.productDetails.ProductDetailsResponse
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
    ): Response<HomeResponse>

    @Headers("lang: ar")
    @GET("products/{ProductId}")
    suspend fun getProductDetails(
        @Header("Authorization") token: String?,
        @Path("ProductId") productId : Int
    ): Response<ProductDetailsResponse>

    @Headers("lang: ar")
    @GET("favorites")
    suspend fun getFavorites(
        @Header("Authorization") token: String
    ): Response<Favorites>

    @Headers("lang: ar")
    @GET("categories")
    suspend fun getCategories(
    ): Response<CategoriesResponse>

    @Headers("lang: ar")
    @GET("categories/{category_id}")
    suspend fun getCategoryDetails(
        @Path("category_id") category_id: Int
    ): Response<MainResponse>
//
//
    @Headers("lang: ar")
    @POST("products/search")
    suspend fun search(
        @Header("Authorization") token: String,
        @Query("text") searchText: String
    ): Response<MainResponse>



    @Headers("lang: ar")
    @POST("favorites")
    suspend fun addToFavorites(
        @Header("Authorization") token: String,
        @Query("product_id") productId: Int
    ): Response<AddToFavorites>


//    @FormUrlEncoded
//    @POST("/login")
//    suspend fun login(
//            @Field("email") email : String,
//            @Field("password") password : String
//    ) : Response<Login>
}