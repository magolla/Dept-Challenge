package com.facundocetraro.deptchallenge.di.module

import com.facundocetraro.deptchallenge.data.source.imageDate.ImageDateLocalDataSource
import com.facundocetraro.deptchallenge.data.source.imageDate.ImageDateRemoteDataSource
import com.facundocetraro.deptchallenge.data.source.imageDate.ImageDateRepository
import com.facundocetraro.deptchallenge.data.source.imageDate.ImageDateRepositoryImpl
import com.facundocetraro.deptchallenge.di.PlanetDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
class ImageDateModule {
    @Provides
    fun provideEpicService(retrofit: Retrofit): EpicService {
        return retrofit.create(EpicService::class.java)
    }

    @Provides
    fun provideImageDateRemoteSource(epicService: EpicService): ImageDateRemoteDataSource =
        ImageDateRemoteDataSource(epicService)

    @Provides
    fun provideImageDateLocalSource(planetDatabase: PlanetDatabase): ImageDateLocalDataSource =
        ImageDateLocalDataSource(planetDatabase)

    @Provides
    fun provideImageDateRepository(
        imageDateRemoteDataSource: ImageDateRemoteDataSource,
        localDataSource: ImageDateLocalDataSource
    ): ImageDateRepository =
        ImageDateRepositoryImpl(imageDateRemoteDataSource, localDataSource)
}
