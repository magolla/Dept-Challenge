package com.facundocetraro.deptchallenge.viewModel

import androidx.lifecycle.ViewModel
import com.facundocetraro.deptchallenge.data.model.Photo
import com.facundocetraro.deptchallenge.data.source.photo.PhotoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class PlayerScreenViewModel @Inject constructor(private val photoRepository: PhotoRepository) :
    ViewModel() {

    fun getPhotosFlow(photoDate: String): Flow<List<Photo>> =
        photoRepository.getAllPhotosFromDate(photoDate)

}
