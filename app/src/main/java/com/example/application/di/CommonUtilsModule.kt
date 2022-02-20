package com.example.application.di

import com.example.application.common.DataLoading
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CommonUtilsModule {
    @Singleton
    @Provides
    fun provideIsLoading(): DataLoading = DataLoading()
}