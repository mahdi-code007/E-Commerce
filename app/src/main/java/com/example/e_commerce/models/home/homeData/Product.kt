package com.example.e_commerce.models.home.homeData

data class Product(
    val description: String,
    val discount: Double,
    val id: Int,
    val image: String,
    val images: ArrayList<String>,
    val in_cart: Boolean,
    val in_favorites: Boolean,
    val name: String,
    val old_price: Double,
    val price: Double
)