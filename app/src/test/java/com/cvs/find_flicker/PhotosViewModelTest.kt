package com.cvs.find_flicker

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.cvs.find_flicker.data.Resource
import com.cvs.find_flicker.data.models.PhotoList
import com.cvs.find_flicker.data.repository.PhotosRepository
import com.cvs.find_flicker.utils.UTUtils
import com.cvs.find_flicker.utils.UTUtils.anyListString
import com.cvs.find_flicker.utils.UTUtils.anyOtherString
import com.cvs.find_flicker.utils.UTUtils.anyString
import com.cvs.find_flicker.utils.UTUtils.getAnyPhotoList
import com.cvs.find_flicker.viewmodel.PhotosViewModel
import com.google.gson.Gson
import com.util.InstantExecutorExtension
import com.util.MainCoroutineRule
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExperimentalCoroutinesApi
@ExtendWith(InstantExecutorExtension::class)
class PhotosViewModelTest {
    private lateinit var sut: PhotosViewModel
    private val repository: PhotosRepository = mockk()

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()


    @Before
    fun setUp() {
        coEvery { repository.fetchRecentQuery() } returns flow {
            emit(anyListString())
        }
        coEvery { repository.requestPhotos("") } returns flow {
            emit(Resource.success(getAnyPhotoList()))
        }
        coEvery { repository.requestPhotos(anyString()) } returns flow {
            emit(Resource.success(getAnyPhotoList()))
        }
        coEvery { repository.requestPhotos(anyOtherString()) } returns flow {
            emit(Resource.success(getAnyPhotoList()))
        }
        sut = PhotosViewModel(repository)
    }

    @After
    fun endTesting() {

    }

    @Test
    fun `test fetchRecentQuery upon init`() {
        coVerify(atMost = 1) { repository.fetchRecentQuery() }
    }

    @Test
    fun `test requestPhotos with empty upon init`() {
        coVerify(atMost = 1) { repository.requestPhotos("") }
    }

    @Test
    fun `test fetchRecentQuery result upon init`() {
        sut.queriesLiveData.observeForever { }
        assertEquals(anyListString(), sut.queriesLiveData.value)
    }

    @Test
    fun `test requestPhotos result upon init`() {
        sut.photosLiveData.observeForever { }
        assertEquals(getAnyPhotoList(), sut.photosLiveData.value?.data)
    }

    @Test
    fun `test NO requestPhotos upon empty query after init`() {
        sut.tryFetchWith("")
        coVerify(atMost = 1) { repository.requestPhotos("") }
    }

    @Test
    fun `test requestPhotos TWICE upon non-empty query after init`() {
        sut.tryFetchWith(anyString())
        coVerify(atMost = 1) { repository.requestPhotos("") }
        coVerify(atMost = 1) { repository.requestPhotos(anyString()) }
    }

    @Test
    fun `test no side effect for requestPhotos upon repeated non-empty queries after init`() {
        sut.tryFetchWith(anyString())
        sut.tryFetchWith(anyString())
        coVerify(atMost = 1) { repository.requestPhotos("") }
        coVerify(atMost = 1) { repository.requestPhotos(anyString()) }
    }

    @Test
    fun `test no side effect for requestPhotos upon different non-empty queries after init`() {
        sut.tryFetchWith(anyString())
        sut.tryFetchWith(anyOtherString())
        coVerify(atMost = 1) { repository.requestPhotos("") }
        coVerify(atMost = 1) { repository.requestPhotos(anyString()) }
        coVerify(atMost = 1) { repository.requestPhotos(anyOtherString()) }
    }
}
