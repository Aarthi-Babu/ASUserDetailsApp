package com.example.asuserdetailsapp.service

import com.example.asuserdetailsapp.model.LoginRequestModel
import com.example.asuserdetailsapp.model.LoginResponse
import com.example.asuserdetailsapp.model.UserDetailsResponse
import com.example.asuserdetailsapp.utils.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserDetailRepository @Inject constructor(
    private val listingService: UserDetailService,
    private val dispatchers: CoroutineDispatcher = Dispatchers.IO
) {

    suspend fun getToken(req:LoginRequestModel): Resource<LoginResponse> {
        return withContext(dispatchers) {
            try {
                Resource.success(listingService.getToken(req))
            } catch (e: Throwable) {
                Resource.error(e)
            }
        }
    }

    suspend fun getDetails(token : String): Resource<UserDetailsResponse> {
        return withContext(dispatchers) {
            try {
                Resource.success(listingService.getUserDetails(token))
            } catch (e: Throwable) {
                Resource.error(e)
            }
        }

    }


}