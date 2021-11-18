package com.example.e_commerce.models

import com.google.gson.annotations.SerializedName

data class MainResponse(
    @SerializedName("data")
    val parentData: ParentData,
    @SerializedName("message")
    val message: Any, // null
    @SerializedName("status")
    val status: Boolean // true
)