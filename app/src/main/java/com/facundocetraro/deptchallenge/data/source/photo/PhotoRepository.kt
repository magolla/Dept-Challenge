package com.facundocetraro.deptchallenge.data.source.photo

import com.facundocetraro.deptchallenge.data.model.Photo
import kotlinx.coroutines.flow.Flow

interface PhotoRepository {
    suspend fun fetchPhotosFromDateAndStoreThem(imageDate: String)
    fun getAllPhotosFromDate(photoDate: String): Flow<List<Photo>>
    suspend fun startDownloadingPendingImages(imageDate: String)
}
