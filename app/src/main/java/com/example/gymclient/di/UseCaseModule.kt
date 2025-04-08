package com.example.gymclient.di

import com.example.gymclient.domain.Repository
import com.example.gymclient.domain.Usecase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {


    @Provides
    @Singleton
    fun provideUsecase(
        Repository: Repository
    ) : Usecase{
        return Usecase(Repository)
    }



}