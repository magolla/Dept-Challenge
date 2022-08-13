package com.facundocetraro.deptchallenge.data.source

import com.facundocetraro.deptchallenge.data.ImageDate
import kotlinx.coroutines.flow.Flow

interface ImageDateRepository {
    suspend fun fetchImageDate()
    fun getAllImageDates(): Flow<List<ImageDate>>
}
