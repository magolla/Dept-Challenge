package com.facundocetraro.deptchallenge.data.model

data class DateWithSavedStatus(
    val imageDate: String,
    val photoLists: DownloadState = DownloadState.NOT_STARTED
)

enum class DownloadState {
    NOT_STARTED,
    DOWNLOADING,
    DOWNLOADED
}
