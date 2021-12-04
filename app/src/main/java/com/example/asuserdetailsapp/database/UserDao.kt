package com.example.asuserdetailsapp.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    suspend fun getUsers(): List<User>

    @Query("DELETE FROM user")
    suspend fun updateUser()

    @Insert
    suspend fun insertAll(users: List<User>)
}