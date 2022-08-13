package com.facundocetraro.deptchallenge.data.model

import androidx.room.Embedded
import androidx.room.Relation


data class DateWithPhotos(
    @Embedded val imageDate: ImageDate,
    @Relation(
        parentColumn = "date",
        entityColumn = "dateId"
    )
    val photoLists: List<Photo>
)