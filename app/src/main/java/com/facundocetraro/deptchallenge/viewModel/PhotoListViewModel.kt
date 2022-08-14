package com.facundocetraro.deptchallenge.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.facundocetraro.deptchallenge.data.model.Photo
import com.facundocetraro.deptchallenge.data.source.photo.PhotoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PhotoListViewModel @Inject constructor(private val photoRepository: PhotoRepository) :
    ViewModel() {


    fun getPhotosFlow(photoDate: String): Flow<List<Photo>> =
        photoRepository.getAllPhotosFromDate(photoDate)

    fun fetchImagesFromDate(imageDate: String) {
        viewModelScope.launch {
            try {
                photoRepository.fetchPhotosFromDateAndStoreThem(imageDate)
                photoRepository.startDownloadingPendingImages(imageDate)
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }
    }

}