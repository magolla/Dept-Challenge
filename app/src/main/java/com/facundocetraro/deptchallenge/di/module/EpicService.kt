package com.facundocetraro.deptchallenge.di.module

import com.facundocetraro.deptchallenge.data.model.ImageDate
import com.facundocetraro.deptchallenge.data.model.Photo
import retrofit2.http.GET
import retrofit2.http.Path

interface EpicService {
    @GET("enhanced/all")
    suspend fun getAllDates(): List<ImageDate>

    @GET("enhanced/date/{imageDate}")
    suspend fun fetchImagesFromDate(@Path("imageDate") imageDate: String): List<Photo>
}
