package com.facundocetraro.deptchallenge.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "imageData")
data class ImageDate(
    @PrimaryKey val date: String
)
