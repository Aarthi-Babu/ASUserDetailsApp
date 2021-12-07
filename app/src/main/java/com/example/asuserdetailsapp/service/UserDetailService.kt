package com.example.asuserdetailsapp.service

import com.example.asuserdetailsapp.model.LoginRequestModel
import com.example.asuserdetailsapp.model.LoginResponse
import com.example.asuserdetailsapp.model.UserDetailsResponse
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface UserDetailService {

    @POST("login")
    suspend fun getToken(
        @Body requestParams: LoginRequestModel
    ): LoginResponse

    @POST("get_all_user_details")
    suspend fun getUserDetails(
        @Header("authorization") auth: String
    ): UserDetailsResponse

}