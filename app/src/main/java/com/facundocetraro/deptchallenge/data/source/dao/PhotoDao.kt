package com.facundocetraro.deptchallenge.data.source.dao

import androidx.room.*
import com.facundocetraro.deptchallenge.data.model.Photo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged


@Dao
interface PhotoDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(photos: List<Photo>)

    @Query("SELECT * FROM photo where photo.dateId == :photoId")
    fun getPhotos(photoId: String): Flow<List<Photo>>

    fun getPhotoDistinctUntilChanged(photoDate: String) =
        getPhotos(photoDate).distinctUntilChanged()

    @Update
    suspend fun update(photo: Photo)

    @Query("SELECT * FROM photo WHERE identifier=:photoId ")
    suspend fun getPhotoById(photoId: String): Photo

}