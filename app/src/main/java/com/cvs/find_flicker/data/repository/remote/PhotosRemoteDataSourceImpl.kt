package com.cvs.find_flicker.data.repository.remote

import com.cvs.find_flicker.data.Resource
import com.cvs.find_flicker.data.error.NETWORK_ERROR
import com.cvs.find_flicker.data.error.NO_INTERNET_CONNECTION
import com.cvs.find_flicker.data.error.UNKNOWN
import com.cvs.find_flicker.data.models.PhotoList
import com.cvs.find_flicker.utils.NetworkConnectivity
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

class PhotosRemoteDataSourceImpl @Inject constructor(
    private val photosService: PhotosService,
    private val networkConnectivity: NetworkConnectivity
): PhotosRemoteDataSource {
    override suspend fun requestPhotos(tags: String): Resource<PhotoList> {
        return getResult {
            photosService.getPhotoList(tags)
        }
    }


    protected suspend fun <T> getResult(networkcall: suspend () -> Response<T>): Resource<T> {
        if (!networkConnectivity.isConnected()) {
            return Resource.error("no network", null, NO_INTERNET_CONNECTION)
        }
        try {
            val response = networkcall.invoke()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) return Resource.success(body)
            }
            return Resource.error(response.message(), null, response.code())
        } catch (e: Exception) {
            return Resource.error(e.message ?: e.toString(), null, NETWORK_ERROR)
        }
    }
}