package com.example.e_commerce.models.banners

data class BannersResponse(
    val `data`: List<Data>,
    val message: Any,
    val status: Boolean
)