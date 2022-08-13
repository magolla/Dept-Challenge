package com.facundocetraro.deptchallenge.data.source.dao

import androidx.room.*
import com.facundocetraro.deptchallenge.data.model.DateWithPhotos
import com.facundocetraro.deptchallenge.data.model.ImageDate
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged

@Dao
interface ImageDateDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(imageDates: List<ImageDate>)

    @Transaction
    @Query("SELECT * FROM imageDate")
    fun getUsersWithPlaylists(): Flow<List<DateWithPhotos>>

    fun getImageDatesDistinctUntilChanged() =
        getUsersWithPlaylists().distinctUntilChanged()


}