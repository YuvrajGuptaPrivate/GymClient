package com.example.gymclient.di

import com.example.gymclient.data.repository.RemoteDataSource
import com.example.gymclient.data.repository.RemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
abstract class RemoteDataSourceModule {


    @Binds
    abstract fun bindRemoteDataSource(
        RemoteDataSourceImpl: RemoteDataSourceImpl
    ): RemoteDataSource


}