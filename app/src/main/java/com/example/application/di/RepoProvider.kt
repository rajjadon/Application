package com.example.application.di

import com.example.application.data.repo.detailsRepo.DetailsApiImpl
import com.example.application.data.repo.detailsRepo.DetailsApiRepo
import com.example.application.data.repo.searchRepo.SearchApiImpl
import com.example.application.data.repo.searchRepo.SearchApiRepo
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepoProvider {

    @Binds
    abstract fun provideSearchApiRepo(searchApiImpl: SearchApiImpl): SearchApiRepo

    @Binds
    abstract fun provideDetailsRepo(detailsApiImpl: DetailsApiImpl): DetailsApiRepo
}