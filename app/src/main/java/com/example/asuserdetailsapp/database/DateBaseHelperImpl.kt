package com.example.asuserdetailsapp.database

class DateBaseHelperImpl(private val appDatabase: UserDataBase) : DataBaseHelper {

    override suspend fun getUsers(): List<User> = appDatabase.userDao().getUsers()
    override suspend fun updateUser(user: User) = appDatabase.userDao().updateUser()
    override suspend fun insertAll(users: List<User>) = appDatabase.userDao().insertAll(users)

}