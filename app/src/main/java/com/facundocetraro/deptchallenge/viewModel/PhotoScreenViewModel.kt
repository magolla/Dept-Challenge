package com.facundocetraro.deptchallenge.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.facundocetraro.deptchallenge.data.model.Photo
import com.facundocetraro.deptchallenge.data.source.photo.PhotoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class PhotoScreenViewModel @Inject constructor(private val photoRepository: PhotoRepository) :
    ViewModel() {

    private val _photoLiveData: MutableLiveData<Photo> = MutableLiveData()
    val photoLiveData: LiveData<Photo> = _photoLiveData

    fun getPhotoById(photoId: String) {

        viewModelScope.launch {
            try {
                val photo = photoRepository.getPhotoById(photoId)
                _photoLiveData.value = photo
            } catch (ex: Exception) {

            }
        }

    }
}