package com.cvs.find_flicker

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.cvs.find_flicker.data.repository.PhotosRepository
import com.cvs.find_flicker.viewmodel.PhotosViewModel
import com.util.InstantExecutorExtension
import com.util.MainCoroutineRule
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
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
        sut = PhotosViewModel(repository)
    }

    @Test
    fun `call fetch recentQuery upon init`() {
        coVerify { repository.fetchRecentQuery() }
    }

    @Test
    fun `call requestPhoto recentQuery with empty upon init`() {
        coVerify { repository.requestPhotos("") }
    }

    @Test
    fun `fetch recentQuery upon init`() {
        val queryList = listOf("1", "2", "3")
        coEvery { repository.fetchRecentQuery() } returns flow {
            emit(queryList)
        }
        sut.getQueryList()
        sut.queriesLiveData.observeForever { }
        assertEquals(queryList, sut.queriesLiveData.value)
    }
}
