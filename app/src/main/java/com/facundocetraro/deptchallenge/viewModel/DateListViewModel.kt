package com.facundocetraro.deptchallenge.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.facundocetraro.deptchallenge.data.model.DateWithPhotos
import com.facundocetraro.deptchallenge.data.model.ImageDate
import com.facundocetraro.deptchallenge.data.source.imageDate.ImageDateRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DateListViewModel @Inject constructor(private val imageDateRepository: ImageDateRepository) :
    ViewModel() {

    fun getImageDatesFlow(): Flow<List<DateWithPhotos>> = imageDateRepository.getAllImageDates()

    fun fetchDateList() {
        viewModelScope.launch {
            try {
                imageDateRepository.fetchImageDate()
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }
    }

}