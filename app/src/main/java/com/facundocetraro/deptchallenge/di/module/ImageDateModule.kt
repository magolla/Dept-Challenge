package com.facundocetraro.deptchallenge.di.module

import com.facundocetraro.deptchallenge.data.source.ImageDateLocalDataSource
import com.facundocetraro.deptchallenge.data.source.ImageDateRemoteDataSource
import com.facundocetraro.deptchallenge.data.source.ImageDateRepository
import com.facundocetraro.deptchallenge.data.source.ImageDateRepositoryImpl
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
    fun provideDateService(retrofit: Retrofit): ImageDateService {
        return retrofit.create(ImageDateService::class.java)
    }

    @Provides
    fun provideImageDateRemoteSource(imageDateService: ImageDateService): ImageDateRemoteDataSource =
        ImageDateRemoteDataSource(imageDateService)

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