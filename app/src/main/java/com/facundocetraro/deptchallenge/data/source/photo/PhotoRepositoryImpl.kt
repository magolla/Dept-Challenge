package com.facundocetraro.deptchallenge.data.source.photo

import android.content.Context
import android.graphics.Bitmap
import androidx.core.graphics.drawable.toBitmap
import coil.ImageLoader
import coil.request.ImageRequest
import com.autodesk.coroutineworker.CoroutineWorker
import com.facundocetraro.deptchallenge.data.model.Photo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

class PhotoRepositoryImpl @Inject constructor(
    private val photoRemoteDataSource: PhotoRemoteDataSource,
    private val photoLocalDataSource: PhotoLocalDataSource,
    private val appContext: Context
) :
    PhotoRepository {

    override suspend fun fetchPhotosFromDateAndStoreThem(imageDate: String) {
        val photoList = photoRemoteDataSource.fetchImagesFromDate(imageDate)
        photoList.forEach { it.dateId = imageDate }
        photoLocalDataSource.updateAll(photoList)
    }

    override fun getAllPhotosFromDate(photoDate: String): Flow<List<Photo>> {
        return photoLocalDataSource.getAllPhotosFromDate(photoDate)
    }

    override suspend fun startDownloadingPendingImages(imageDate: String): MutableList<CoroutineWorker> {
        val localPhotos = photoLocalDataSource.getAllPhotosFromDate(imageDate)
        return downloadImagesFromList(localPhotos.first())
    }

    override suspend fun getPhotoById(photoId: String): Photo {
        return photoLocalDataSource.getPhotoById(photoId)
    }

    private suspend fun downloadImagesFromList(photoList: List<Photo>): MutableList<CoroutineWorker> {
        val imageLoader = ImageLoader(appContext)
        val workerList = mutableListOf<CoroutineWorker>()
        photoList.filter { it.localUri == null }.forEach { photo ->
           val worker = CoroutineWorker.execute {
                try {
                    val pathSaved = storeInDirectory(imageLoader, photo)
                    photo.localUri = pathSaved
                    photoLocalDataSource.savePathInDatabase(photo)
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
        val bm = drawable!!.toBitmap()

        var file = File(extStorageDirectory, photo.dateId)

        if (!file.exists()) {
            file.mkdirs()
        }
        file = File(file.toString(), photo.image + ".jpg")
        val outStream = FileOutputStream(file)
        bm.compress(Bitmap.CompressFormat.PNG, 100, outStream)

        outStream.flush()
        outStream.close()

        return file.toString()
    }
}
