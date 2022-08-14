package com.facundocetraro.deptchallenge.data.source.photo

import com.facundocetraro.deptchallenge.data.model.Photo
import com.facundocetraro.deptchallenge.di.PlanetDatabase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PhotoLocalDataSource @Inject constructor(private val planetDatabase: PlanetDatabase) {
    suspend fun updateAll(photos: List<Photo>) {
        planetDatabase.photoDao().insertAll(photos)
    }

    fun getAllPhotosFromDate(photoDate: String): Flow<List<Photo>> {
        return planetDatabase.photoDao().getPhotoDistinctUntilChanged(photoDate)
    }

    suspend fun savePathInDatabase(photo: Photo) {
        planetDatabase.photoDao().update(photo)
    }

    suspend fun getPhotoById(photoId: String): Photo {
        return planetDatabase.photoDao().getPhotoById(photoId)
    }

}
