package com.cvs.find_flicker.data.repository

import com.cvs.find_flicker.data.Resource
import com.cvs.find_flicker.data.models.PhotoList
import kotlinx.coroutines.flow.Flow

interface PhotosRepository {
    suspend fun requestPhotos(tags: String): Flow<Resource<PhotoList>>
    suspend fun fetchRecentQuery(): Flow<List<String>>
    suspend fun updateRecentQuery(queryList: List<String>)
}