package com.facundocetraro.deptchallenge.data.source.photo

import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.facundocetraro.deptchallenge.data.model.Photo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PhotoRepositoryImpl @Inject constructor(
    private val photoRemoteDataSource: PhotoRemoteDataSource,
    private val photoLocalDataSource: PhotoLocalDataSource,
    private val workManager: WorkManager
) :
    PhotoRepository {

    override suspend fun fetchPhotosFromDateAndStoreThem(imageDate: String) {
        val photoList = photoRemoteDataSource.fetchImagesFromDate(imageDate)
        photoList.map { it.dateId = imageDate }
        photoLocalDataSource.updateAll(photoList)
    }

    override fun getAllPhotosFromDate(photoDate: String): Flow<List<Photo>> {
        return photoLocalDataSource.getAllPhotosFromDate(photoDate)
    }

    override suspend fun startDownloadingPendingImages(imageDate: String) {
        val workRequest = OneTimeWorkRequestBuilder<PhotoDownloadAndStoreWorker>()
            .setInputData(workDataOf(PhotoDownloadAndStoreWorker.imageDateId to imageDate))
            .build()
        workManager.enqueue(workRequest)
    }

    override suspend fun getPhotoById(photoId: String): Photo {
        return photoLocalDataSource.getPhotoById(photoId)
    }

    override suspend fun savePathInDatabase(photo: Photo) {
        photoLocalDataSource.savePathInDatabase(photo)
    }
}
