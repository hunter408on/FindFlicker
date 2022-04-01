package com.cvs.find_flicker.di

import com.cvs.find_flicker.data.repository.PhotosRepository
import com.cvs.find_flicker.data.repository.PhotosRepositoryImpl
import com.cvs.find_flicker.data.repository.local.PhotosLocalDataSource
import com.cvs.find_flicker.data.repository.local.PhotosLocalDataSourceImpl
import com.cvs.find_flicker.data.repository.remote.PhotosRemoteDataSource
import com.cvs.find_flicker.data.repository.remote.PhotosRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {
    @Binds
    @Singleton
    abstract fun providePhotosRepository(photosRepository: PhotosRepositoryImpl): PhotosRepository

    @Binds
    @Singleton
    abstract fun providePhotosRemoteDataSourceRepository(photosRemoteDataSource: PhotosRemoteDataSourceImpl): PhotosRemoteDataSource

    @Binds
    @Singleton
    abstract fun providePhotosLocalDataSourceRepository(photosLocalDataSource: PhotosLocalDataSourceImpl): PhotosLocalDataSource

}
