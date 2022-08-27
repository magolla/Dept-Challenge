package com.facundocetraro.deptchallenge.data.source.imageDate

import com.facundocetraro.deptchallenge.data.model.DateWithSavedStatus
import com.facundocetraro.deptchallenge.data.model.ImageDate
import kotlinx.coroutines.flow.Flow

interface ImageDateRepository {
    suspend fun fetchImageDate(): List<ImageDate>
    fun getAllImageDates(): Flow<List<DateWithSavedStatus>>
}
