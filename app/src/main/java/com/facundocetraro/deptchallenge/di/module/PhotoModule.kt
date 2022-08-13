package com.facundocetraro.deptchallenge.di.module

import android.content.Context
import com.facundocetraro.deptchallenge.data.source.photo.PhotoLocalDataSource
import com.facundocetraro.deptchallenge.data.source.photo.PhotoRemoteDataSource
import com.facundocetraro.deptchallenge.data.source.photo.PhotoRepository
import com.facundocetraro.deptchallenge.data.source.photo.PhotoRepositoryImpl
import com.facundocetraro.deptchallenge.di.PlanetDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class PhotoModule {
    @Provides
    fun providePhotoRemoteSource(epicService: EpicService): PhotoRemoteDataSource =
        PhotoRemoteDataSource(epicService)

    @Provides
    fun providePhotoLocalSource(planetDatabase: PlanetDatabase): PhotoLocalDataSource =
        PhotoLocalDataSource(planetDatabase)

    @Provides
    fun providePhotoRepository(
        photoRemoteDataSource: PhotoRemoteDataSource,
        localDataSource: PhotoLocalDataSource,
        @ApplicationContext appContext: Context
    ): PhotoRepository =
        PhotoRepositoryImpl(photoRemoteDataSource, localDataSource, appContext)
}