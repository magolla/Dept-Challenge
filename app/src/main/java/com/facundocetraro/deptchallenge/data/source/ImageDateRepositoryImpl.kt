package com.facundocetraro.deptchallenge.data.source

import com.facundocetraro.deptchallenge.data.ImageDate
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ImageDateRepositoryImpl @Inject constructor(
    private val imageDateRemoteDataSource: ImageDateRemoteDataSource,
    private val imageDateLocalDataSource: ImageDateLocalDataSource
) :
    ImageDateRepository {
    override suspend fun fetchImageDate() {
        val imageDate = imageDateRemoteDataSource.getAllImageDates()
        imageDateLocalDataSource.updateAll(imageDate)
    }

    override fun getAllImageDates(): Flow<List<ImageDate>> {
        return imageDateLocalDataSource.getAllImageDates()
    }
}
