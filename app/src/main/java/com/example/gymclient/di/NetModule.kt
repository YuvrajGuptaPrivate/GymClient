package com.example.gymclient.di

import com.example.gymclient.BuildConfig
import com.example.gymclient.data.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class NetModule {



    @Singleton
    @Provides
    fun providesRetrofitInstance() : Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BuildConfig.BASE_URL)
            .build()
    }


    @Singleton
    @Provides
    fun providesAPIServise(retrofit: Retrofit): ApiService{
        return retrofit.create(ApiService::class.java)
    }

}