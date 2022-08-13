package com.facundocetraro.deptchallenge.data.source

import com.facundocetraro.deptchallenge.data.ImageDate
import com.facundocetraro.deptchallenge.di.module.ImageDateService
import javax.inject.Inject

class ImageDateRemoteDataSource @Inject constructor(private val imageDateService: ImageDateService) {
    suspend fun getAllImageDates(): List<ImageDate> {
        return imageDateService.getAllDates()
    }

}
