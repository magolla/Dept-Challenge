package com.facundocetraro.deptchallenge.di

import androidx.room.Database
import androidx.room.RoomDatabase
import com.facundocetraro.deptchallenge.data.ImageDate
import com.facundocetraro.deptchallenge.data.source.dao.ImageDateDao

@Database(entities = [ImageDate::class], version = 1)
abstract class PlanetDatabase : RoomDatabase() {
    abstract fun imageDateDao(): ImageDateDao
}

