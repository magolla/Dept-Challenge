package com.facundocetraro.deptchallenge.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "imageDate")
data class ImageDate(
    @PrimaryKey val date: String
)
