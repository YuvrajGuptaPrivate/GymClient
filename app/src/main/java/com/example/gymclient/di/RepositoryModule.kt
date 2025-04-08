package com.example.gymclient.di

import com.example.gymclient.data.repository.RepositoryImpl
import com.example.gymclient.domain.Repository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {



    @Binds
    abstract fun Reposiotry(
       RepositoryImpl: RepositoryImpl
    ): Repository


}