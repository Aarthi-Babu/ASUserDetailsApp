package com.example.asuserdetailsapp.model

data class LoginResponse(
    val block_status: Int,
    val country_code: String,
    val created_at: String,
    val device_id: String,
    val device_token: String,
    val device_type: String,
    val email: String,
    val email_verified_at: Any,
    val first_name: String,
    val id: Int,
    val image: Any,
    val instagram_link: Any,
    val last_name: String,
    val mobile: String,
    val token: String,
    val updated_at: String,
    val user_bio: Any,
    val website_link: Any,
    val youtube_link: Any
)