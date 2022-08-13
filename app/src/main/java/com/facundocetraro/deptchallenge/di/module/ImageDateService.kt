package com.facundocetraro.deptchallenge.di.module

import com.facundocetraro.deptchallenge.data.ImageDate
import retrofit2.http.GET

interface ImageDateService {
    @GET("enhanced/all")
    suspend fun getAllDates(): List<ImageDate>
}
