package com.facundocetraro.deptchallenge.viewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.facundocetraro.deptchallenge.data.model.DateWithPhotos
import com.facundocetraro.deptchallenge.data.model.DateWithSavedStatus
import com.facundocetraro.deptchallenge.data.source.imageDate.ImageDateRepository
import com.facundocetraro.deptchallenge.util.ConnectivityUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DateListViewModel @Inject constructor(
    private val imageDateRepository: ImageDateRepository,
    @ApplicationContext val context: Context
) :
    ViewModel() {

    private val _downloadStatus: MutableLiveData<DownloadStatus> = MutableLiveData()
    val downloadStatus: LiveData<DownloadStatus> = _downloadStatus

    fun getImageDatesFlow(): Flow<List<DateWithSavedStatus>> = imageDateRepository.getAllImageDates()

    fun fetchDateList() {
        viewModelScope.launch {
            try {
                val dateList = imageDateRepository.fetchImageDate()
                _downloadStatus.value =
                    if (dateList.isNotEmpty()) DownloadStatus.CONTAIN_ELEMENTS else DownloadStatus.EMPTY_ELEMENTS
            } catch (ex: Exception) {
                ex.printStackTrace()
                if (ConnectivityUtil.hasInternetConnection(context)) {
                    _downloadStatus.value = DownloadStatus.GENERIC_ERROR
                }
            }
        }
    }

    enum class DownloadStatus {
        CONTAIN_ELEMENTS,
        EMPTY_ELEMENTS,
        GENERIC_ERROR
    }
}
