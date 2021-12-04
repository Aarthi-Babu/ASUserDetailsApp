package com.example.asuserdetailsapp.model

class UserDetailsResponse : ArrayList<UserDetailsResponseItem>()

data class UserDetailsResponseItem(
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
    val image: String,
    val instagram_link: String,
    val last_name: String,
    val mobile: String,
    val safetydetails: Safetydetails,
    val updated_at: String,
    val user_bio: String,
    val userfollower_count: Int,
    val userfollowing_count: Int,
    val userlikes_count: Int,
    val uservideo: List<Any>,
    val uservideo_count: Int,
    val website_link: Any,
    val youtube_link: String
)

data class Safetydetails(
    val account_type: Int,
    val comment_status: Int,
    val id: Int,
    val like_status: Int,
    val qrcode: String,
    val react_status: Int,
    val user_id: Int
)