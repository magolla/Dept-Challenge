package com.facundocetraro.deptchallenge.data.source.photo

import com.facundocetraro.deptchallenge.data.model.Photo
import com.facundocetraro.deptchallenge.di.module.EpicService
import javax.inject.Inject

class PhotoRemoteDataSource @Inject constructor(private val epicService: EpicService) {
    suspend fun fetchImagesFromDate(imageDate: String): List<Photo> {
        return epicService.fetchImagesFromDate(imageDate)
    }
}
