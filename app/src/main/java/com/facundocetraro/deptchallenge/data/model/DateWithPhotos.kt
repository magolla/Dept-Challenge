package com.facundocetraro.deptchallenge.data.model

import androidx.room.Embedded
import androidx.room.Relation

data class DateWithPhotos(
    @Embedded val imageDate: ImageDate,
    @Relation(
        parentColumn = "date",
        entityColumn = "dateId"
    )
    val photoLists: List<Photo>
) {
    fun calculateDownloadStatus(): DownloadState {
        val downloadedPhotos = photoLists.count { it.localUri != null }

        return when (downloadedPhotos) {
            0 -> {
                DownloadState.NOT_STARTED
            }
            photoLists.size -> {
                DownloadState.DOWNLOADED
            }
            else -> {
                DownloadState.DOWNLOADING
            }
        }
    }
}
