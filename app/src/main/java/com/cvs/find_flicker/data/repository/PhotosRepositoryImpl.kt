package com.cvs.find_flicker.data.repository

import com.cvs.find_flicker.data.Resource
import com.cvs.find_flicker.data.models.PhotoList
import com.cvs.find_flicker.data.repository.local.PhotosLocalDataSource
import com.cvs.find_flicker.data.repository.remote.PhotosRemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class PhotosRepositoryImpl @Inject constructor(
    private val remoteDataSource: PhotosRemoteDataSource,
    private val localDataSource: PhotosLocalDataSource,
    private val ioDispatcher: CoroutineContext
): PhotosRepository {
    override suspend fun requestPhotos(tags: String): Flow<Resource<PhotoList>> {
        return flow {
            emit(Resource.loading())
            emit(remoteDataSource.requestPhotos(tags))
        }.flowOn(ioDispatcher)
    }

    override suspend fun fetchRecentQuery(): Flow<List<String>> {
        return flow {
            emit(localDataSource.fetchRecentQuery())
        }.flowOn(ioDispatcher)
    }

    override suspend fun updateRecentQuery(queryList: List<String>) {
        localDataSource.updateRecentQuery(queryList)
    }
}