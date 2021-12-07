package com.example.asuserdetailsapp.database

interface DataBaseHelper {
    suspend fun getUsers(): List<User>
    suspend fun updateUser(user: User)
    suspend fun deleteData()
    suspend fun insertAll(users: List<User>)
}