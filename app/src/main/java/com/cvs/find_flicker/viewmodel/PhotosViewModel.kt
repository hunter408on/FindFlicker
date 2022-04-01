package com.cvs.find_flicker.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cvs.find_flicker.data.Resource
import com.cvs.find_flicker.data.models.PhotoList
import com.cvs.find_flicker.data.repository.PhotosRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PhotosViewModel @Inject constructor(private val photosRepository: PhotosRepository) : ViewModel() {
    private val maxRecentQueryCount = 5
    private val photosMutableLiveData = MutableLiveData<Resource<PhotoList>>()
    private val queriesMutableLiveData = MutableLiveData<List<String>>()
    val photosLiveData: LiveData<Resource<PhotoList>> get() = photosMutableLiveData
    val queriesLiveData: LiveData<List<String>> get() = queriesMutableLiveData
    private var currentQuery: String = ""

    init {
        getQueryList()
        requestPhotos("")
    }
    private fun requestPhotos(tags: String) {
        addQuery(tags)
        viewModelScope.launch {
            photosRepository.requestPhotos(tags).collect {
                photosMutableLiveData.postValue(it)
            }
        }
    }

    fun tryFetchWith(query: String) {
        if (currentQuery != query ) {
            currentQuery = query
            requestPhotos(query)
        }
    }

    fun getQueryList() {
        viewModelScope.launch {
            photosRepository.fetchRecentQuery().collect {
                queriesMutableLiveData.postValue(it)
            }
        }
    }

    private fun addQuery(query: String) {
        val queryList = queriesMutableLiveData.value?.toMutableList() ?: mutableListOf()
        val isValidQuery = query.isNotEmpty() && queryList.find { it == query } == null
        if (!isValidQuery) return

        queryList.add(query)
        if (queryList.size == maxRecentQueryCount + 1) {
            queryList.removeAt(0)
        }
        queriesMutableLiveData.value = queryList
        viewModelScope.launch {
            photosRepository.updateRecentQuery(queryList.toList())
        }
    }
}