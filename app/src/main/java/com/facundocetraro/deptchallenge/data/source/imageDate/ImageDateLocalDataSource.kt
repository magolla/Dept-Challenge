package com.facundocetraro.deptchallenge.data.source.imageDate

import com.facundocetraro.deptchallenge.data.model.ImageDate
import com.facundocetraro.deptchallenge.data.model.Photo
import com.facundocetraro.deptchallenge.di.PlanetDatabase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ImageDateLocalDataSource @Inject constructor(private val planetDatabase: PlanetDatabase) {
    suspend fun updateAll(imageDate: List<ImageDate>) {
        planetDatabase.imageDateDao().insertAll(imageDate)
    }
    fun getAllImageDates(): Flow<List<ImageDate>> {
        return planetDatabase.imageDateDao().getImageDatesDistinctUntilChanged()
    }

    fun getAllPhotosFromDate(photoDate: String): Flow<List<Photo>> {
        return planetDatabase.photoDao().getPhotoDistinctUntilChanged(photoDate)
    }

}
