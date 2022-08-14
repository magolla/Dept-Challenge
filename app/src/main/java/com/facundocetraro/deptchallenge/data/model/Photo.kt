package com.facundocetraro.deptchallenge.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.facundocetraro.deptchallenge.util.NetworkConstants

@Entity(tableName = "photo")
data class Photo(
    @PrimaryKey val identifier: String,
    var dateId: String,
    val image: String,
    var localUri: String? = null
) {
    fun getPhotoJpgUrl(): String {
        return "${getSplitedUrl()}/jpg/${image}.jpg"
    }
    fun getPhotoThumbUrl(): String {
        return "${getSplitedUrl()}/thumbs/${image}.jpg"
    }

    private fun getSplitedUrl(): String {
        val dateSpliced = dateId.split("-")
        return "${NetworkConstants.EPIC_IMAGE_ARCHIVE_URL}${dateSpliced[0]}/${dateSpliced[1]}/${dateSpliced[2]}"
    }

}