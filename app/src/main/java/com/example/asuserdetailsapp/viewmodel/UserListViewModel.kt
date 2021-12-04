package com.example.asuserdetailsapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.asuserdetailsapp.database.DateBaseHelperImpl
import com.example.asuserdetailsapp.database.User
import com.example.asuserdetailsapp.model.LoginRequestModel
import com.example.asuserdetailsapp.model.LoginResponse
import com.example.asuserdetailsapp.model.UserDetailsResponse
import com.example.asuserdetailsapp.service.UserDetailRepository
import com.example.asuserdetailsapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserListViewModel @Inject constructor(private val repo: UserDetailRepository) : ViewModel() {
    val userDetailsResponse = MutableLiveData<ArrayList<User>>()
    val userDetailsFromDb = MutableLiveData<ArrayList<User>>()
    var authToken: String? = null
    fun getLoginDetails(
        email: String,
        password: String
    ): MutableLiveData<Resource<LoginResponse>> {
        val result = MutableLiveData<Resource<LoginResponse>>()
        val loginRequest = LoginRequestModel(email, password)
        result.value = Resource.loading()
        viewModelScope.launch {
            result.postValue(repo.getToken(loginRequest))
        }
        return result
    }

    fun getUserDetails(
        token: String,
        dbHelper: DateBaseHelperImpl
    ): MutableLiveData<Resource<UserDetailsResponse>> {
        var userResponse: UserDetailsResponse?
        val result = MutableLiveData<Resource<UserDetailsResponse>>()
        result.value = Resource.loading()
        viewModelScope.launch {
            userResponse = repo.getDetails(token)?.data
            val users = mutableListOf<User>()
            val len = userResponse?.size
            if (len != null)
                for (i in 0 until len) {
                    val user = userResponse?.get(i)?.let {
                        User(
                            it.id, it.first_name,
                            it.last_name,
                            it.image,
                            it.mobile,
                            it.user_bio,
                            it.youtube_link,
                            it.instagram_link
                        )
                    }
                    if (user != null) {
                        users.add(user)
                    }
                }
            dbHelper.insertAll(users)

                userDetailsResponse.postValue(users as ArrayList<User>)
            fetchDataFromDb(dbHelper)
            result.postValue(repo.getDetails(token))

        }
        return result
    }


    private fun fetchDataFromDb(dbHelper: DateBaseHelperImpl) {
        viewModelScope.launch {
            userDetailsFromDb.postValue(dbHelper.getUsers() as ArrayList<User>?)
        }
    }


}