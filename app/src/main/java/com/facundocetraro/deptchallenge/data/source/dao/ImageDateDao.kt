package com.facundocetraro.deptchallenge.data.source.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.facundocetraro.deptchallenge.data.model.ImageDate
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged

@Dao
interface ImageDateDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(imageDates: List<ImageDate>)

    @Query("SELECT * FROM imageDate")
    fun getImageDates(): Flow<List<ImageDate>>

    fun getImageDatesDistinctUntilChanged() =
        getImageDates().distinctUntilChanged()

}