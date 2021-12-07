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
        token: String
    ): MutableLiveData<Resource<UserDetailsResponse>> {
        val result = MutableLiveData<Resource<UserDetailsResponse>>()
        result.value = Resource.loading()
        viewModelScope.launch {

            result.postValue(repo.getDetails(token))

        }
        return result
    }

    fun fetchDataFromDb(dbHelper: DateBaseHelperImpl, userResponse: UserDetailsResponse) {
        viewModelScope.launch {
            val users = mutableListOf<User>()
            userResponse.forEach {
                val user = User(
                    it.id,
                    it.first_name,
                    it.last_name,
                    it.image,
                    it.mobile,
                    it.user_bio,
                    it.youtube_link,
                    it.instagram_link
                )
                users.add(user)
            }
            dbHelper.deleteData()
            dbHelper.insertAll(users)
            userDetailsFromDb.postValue(dbHelper.getUsers() as ArrayList<User>?)
        }
    }


    fun updateDb(dbHelper: DateBaseHelperImpl, user: User) {
        viewModelScope.launch {
            dbHelper.updateUser(user)
        }
    }


}