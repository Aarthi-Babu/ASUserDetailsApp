package com.example.asuserdetailsapp.service

import android.provider.ContactsContract
import com.example.asuserdetailsapp.model.LoginRequestModel
import com.example.asuserdetailsapp.model.LoginResponse
import com.example.asuserdetailsapp.model.UserDetailsResponse
import dagger.Subcomponent
import retrofit2.http.*

interface UserDetailService {

    @POST("login")
    suspend fun getToken(
        @Body requestParams: LoginRequestModel
    ): LoginResponse

    @POST("get_all_user_details")
    suspend fun getUserDetails(
        @Header("authorization")  auth:String
    ): UserDetailsResponse

}