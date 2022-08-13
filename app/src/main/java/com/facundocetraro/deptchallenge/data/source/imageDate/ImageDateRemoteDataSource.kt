package com.facundocetraro.deptchallenge.data.source.imageDate

import com.facundocetraro.deptchallenge.data.model.ImageDate
import com.facundocetraro.deptchallenge.data.model.Photo
import com.facundocetraro.deptchallenge.di.module.EpicService
import javax.inject.Inject

class ImageDateRemoteDataSource @Inject constructor(private val epicService: EpicService) {
    suspend fun getAllImageDates(): List<ImageDate> {
        return epicService.getAllDates()
    }

    suspend fun fetchImagesFromDate(imageDate: String): List<Photo> {
        return epicService.fetchImagesFromDate(imageDate)
    }
}
