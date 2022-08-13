package com.facundocetraro.deptchallenge.di

import androidx.room.Database
import androidx.room.RoomDatabase
import com.facundocetraro.deptchallenge.data.model.ImageDate
import com.facundocetraro.deptchallenge.data.model.Photo
import com.facundocetraro.deptchallenge.data.source.dao.ImageDateDao
import com.facundocetraro.deptchallenge.data.source.dao.PhotoDao

@Database(entities = [ImageDate::class, Photo::class], version = 1)
abstract class PlanetDatabase : RoomDatabase() {
    abstract fun imageDateDao(): ImageDateDao
    abstract fun photoDao(): PhotoDao
}

