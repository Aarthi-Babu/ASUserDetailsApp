package com.example.asuserdetailsapp.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User (
    @PrimaryKey var id: Int = 0,
    @ColumnInfo(name = "first_name") var firstName: String?,
    @ColumnInfo(name = "last_name") var lastName: String?,
    @ColumnInfo(name = "image") var image: String?,
    @ColumnInfo(name = "mobile") var mobile: String?,
    @ColumnInfo(name = "bio") var bio: String?,
    @ColumnInfo(name = "youTubeUrl") var youTubeUrl: String?,
    @ColumnInfo(name = "instaTubeUrl") var instaTubeUrl: String?
)