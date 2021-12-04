package com.example.asuserdetailsapp.model

data class LoginRequestModel(
    val email: String? = "demo@gmail.com",
    val password: String? = "123456"
)