package com.example.asuserdetailsapp.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User (
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "first_name") val firstName: String?,
    @ColumnInfo(name = "last_name") val lastName: String?,
    @ColumnInfo(name = "image") val image: String?,
    @ColumnInfo(name = "mobile") val mobile: String?,
    @ColumnInfo(name = "bio") val bio: String?,
    @ColumnInfo(name = "youTubeUrl") val youTubeUrl: String?,
    @ColumnInfo(name = "instaTubeUrl") val instaTubeUrl: String?
)