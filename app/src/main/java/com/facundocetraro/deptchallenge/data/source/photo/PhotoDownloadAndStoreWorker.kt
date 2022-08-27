package com.facundocetraro.deptchallenge.data.source.photo

import android.content.Context
import android.graphics.Bitmap
import androidx.core.graphics.drawable.toBitmap
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import coil.ImageLoader
import coil.request.ImageRequest
import com.facundocetraro.deptchallenge.data.model.Photo
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.first
import java.io.File
import java.io.FileOutputStream

@HiltWorker
class PhotoDownloadAndStoreWorker @AssistedInject constructor(
    @Assisted private val appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val photoRepository: PhotoRepository
) :
    CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        val imageUriInput =
            inputData.getString(imageDateId) ?: return Result.failure()
        val allPhotosFromDate = photoRepository.getAllPhotosFromDate(imageUriInput)
        downloadImagesFromList(allPhotosFromDate.first())

        return Result.success()
    }

    companion object {
        const val imageDateId = "imageDateId"
    }

    private suspend fun downloadImagesFromList(photoList: List<Photo>): MutableList<com.autodesk.coroutineworker.CoroutineWorker> {
        val imageLoader = ImageLoader(appContext)
        val workerList = mutableListOf<com.autodesk.coroutineworker.CoroutineWorker>()
        photoList.filter { it.localUri == null }.forEach { photo ->
            val worker = com.autodesk.coroutineworker.CoroutineWorker.execute {
                try {
                    val pathSaved = storeInDirectory(imageLoader, photo)
                    photo.localUri = pathSaved
                    photoRepository.savePathInDatabase(photo)
                } catch (ex: Exception) {
                    ex.printStackTrace()
                }
            }
            workerList.add(worker)
        }
        return workerList
    }

    private suspend fun storeInDirectory(imageLoader: ImageLoader, photo: Photo): String {
        val request = ImageRequest.Builder(appContext)
            .data(photo.getPhotoJpgUrl())
            .build()
        val drawable = imageLoader.execute(request).drawable

        val extStorageDirectory = "${appContext.filesDir}"
        val bm = drawable?.toBitmap()

        var file = File(extStorageDirectory, photo.dateId)

        if (!file.exists()) {
            file.mkdirs()
        }
        file = File(file.toString(), photo.image + ".jpg")
        val outStream = FileOutputStream(file)
        bm?.compress(Bitmap.CompressFormat.PNG, 100, outStream)

        outStream.flush()
        outStream.close()

        return file.toString()
    }


}
