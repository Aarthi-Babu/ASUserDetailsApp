package com.example.asuserdetailsapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [User::class], version = 1)
abstract class UserDataBase : RoomDatabase() {
    abstract fun userDao(): UserDao

    object DatabaseBuilder {

        private var INSTANCE: UserDataBase? = null

        fun getInstance(context: Context): UserDataBase {
            if (INSTANCE == null) {
                synchronized(UserDataBase::class) {
                    INSTANCE = buildRoomDB(context)
                }
            }
            return INSTANCE!!
        }

        private fun buildRoomDB(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                UserDataBase::class.java,
                "user_details_db"
            ).build()

    }
}