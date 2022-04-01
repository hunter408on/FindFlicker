package com.cvs.find_flicker.data.repository.local

interface PhotosLocalDataSource {
    suspend fun fetchRecentQuery(): List<String>
    suspend fun updateRecentQuery(queryList: List<String>)
}