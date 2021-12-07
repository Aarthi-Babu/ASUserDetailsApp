package com.example.asuserdetailsapp.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    suspend fun getUsers(): List<User>

    @Update
    suspend fun updateUser(user: User)

    @Query("DELETE FROM user")
    suspend fun deleteData()

    @Insert
    suspend fun insertAll(users: List<User>)
}