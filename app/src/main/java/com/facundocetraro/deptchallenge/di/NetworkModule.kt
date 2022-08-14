package com.facundocetraro.deptchallenge.di

import com.facundocetraro.deptchallenge.util.NetworkConstants
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    fun provideBaseUrl(): String {
        return NetworkConstants.BASE_URL
    }

    @Provides
    fun provideGsonConverterFactory(): GsonConverterFactory {
        val gsonBuilder = GsonBuilder().create()
        return GsonConverterFactory.create(gsonBuilder)
    }

    @Provides
    fun provideApiRetrofit(
        baseUrl: String,
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .build()
    }
}
