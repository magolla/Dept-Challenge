package com.facundocetraro.deptchallenge.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.facundocetraro.deptchallenge.data.ImageDate
import com.facundocetraro.deptchallenge.data.source.ImageDateRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DateListViewModel @Inject constructor(private val imageDateRepository: ImageDateRepository):ViewModel() {

    fun getImageDatesFlow(): Flow<List<ImageDate>> = imageDateRepository.getAllImageDates()

    fun fetchDateList() {
        viewModelScope.launch {
            imageDateRepository.fetchImageDate()
        }
    }

}