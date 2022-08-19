package com.facundocetraro.deptchallenge.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.autodesk.coroutineworker.CoroutineWorker
import com.facundocetraro.deptchallenge.data.model.Photo
import com.facundocetraro.deptchallenge.data.source.photo.PhotoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PhotoListViewModel @Inject constructor(private val photoRepository: PhotoRepository) :
    ViewModel() {

    private var parallelWorker: MutableList<CoroutineWorker?> = mutableListOf()

    fun getPhotosFlow(photoDate: String): Flow<List<Photo>> =
        photoRepository.getAllPhotosFromDate(photoDate)

    fun fetchImagesFromDate(imageDate: String) {
        viewModelScope.launch {
            try {
                photoRepository.fetchPhotosFromDateAndStoreThem(imageDate)
                parallelWorker.addAll(photoRepository.startDownloadingPendingImages(imageDate))
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }
    }

    override fun onCleared() {
        parallelWorker.forEach {
            it?.cancel()
        }
        super.onCleared()
    }
}
