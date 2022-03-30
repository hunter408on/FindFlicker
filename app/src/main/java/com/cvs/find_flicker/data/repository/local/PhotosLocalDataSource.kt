package com.cvs.find_flicker.data.repository.local

import com.cvs.find_flicker.data.Resource
import com.cvs.find_flicker.data.models.PhotoList
import kotlinx.coroutines.flow.Flow

interface PhotosLocalDataSource {
    suspend fun fetchRecentQuery(): List<String>
    suspend fun updateRecentQuery(queryList: List<String>)
}