package com.facundocetraro.deptchallenge.data.source.imageDate

import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.facundocetraro.deptchallenge.data.model.DateWithSavedStatus
import com.facundocetraro.deptchallenge.data.model.DownloadState
import com.facundocetraro.deptchallenge.data.model.ImageDate
import com.facundocetraro.deptchallenge.data.source.photo.PhotoDownloadAndStoreWorker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ImageDateRepositoryImpl @Inject constructor(
    private val imageDateRemoteDataSource: ImageDateRemoteDataSource,
    private val imageDateLocalDataSource: ImageDateLocalDataSource,
    private val workManager: WorkManager
) :
    ImageDateRepository {
    override suspend fun fetchImageDate() = withContext(Dispatchers.IO) {
        val imageDate = imageDateRemoteDataSource.getAllImageDates()
        imageDateLocalDataSource.updateAll(imageDate)
        imageDate
    }

    override fun getAllImageDates(): Flow<List<DateWithSavedStatus>> {
        return imageDateLocalDataSource.getAllImageDates().map {
            it.onEach { dateWithPhotos ->
                if (dateWithPhotos.calculateDownloadStatus() == DownloadState.DOWNLOADING) {
                    val workRequest = OneTimeWorkRequestBuilder<PhotoDownloadAndStoreWorker>()
                        .setInputData(workDataOf(PhotoDownloadAndStoreWorker.imageDateId to dateWithPhotos.imageDate.date))
                        .build()
                    workManager.enqueue(workRequest)
                }
            }.map { dateWithPhotos ->
                DateWithSavedStatus(
                    dateWithPhotos.imageDate.date,
                    dateWithPhotos.calculateDownloadStatus()
                )
            }
        }
    }
}
