package com.facundocetraro.deptchallenge.data.source

import com.facundocetraro.deptchallenge.data.ImageDate
import com.facundocetraro.deptchallenge.data.source.dao.ImageDateDao
import com.facundocetraro.deptchallenge.di.PlanetDatabase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ImageDateLocalDataSource @Inject constructor(private val planetDatabase: PlanetDatabase) {
    suspend fun updateAll(imageDate: List<ImageDate>) {
        planetDatabase.imageDateDao().insertAll(imageDate)
    }
    fun getAllImageDates(): Flow<List<ImageDate>> {
        return planetDatabase.imageDateDao().getImageDatesDistinctUntilChanged()
    }

}
