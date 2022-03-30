package com.cvs.find_flicker.data.repository.remote

import com.cvs.find_flicker.data.models.PhotoList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PhotosService {
    @GET("/services/feeds/photos_public.gne?format=json&nojsoncallback=1")
    suspend fun getPhotoList(@Query("tags") tags: String): Response<PhotoList>
}