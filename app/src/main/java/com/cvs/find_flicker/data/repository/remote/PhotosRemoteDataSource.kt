package com.cvs.find_flicker.data.repository.remote

import com.cvs.find_flicker.data.Resource
import com.cvs.find_flicker.data.models.PhotoList

interface PhotosRemoteDataSource {
    suspend fun requestPhotos(tags: String): Resource<PhotoList>
}