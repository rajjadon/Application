package com.example.application.di

import com.example.application.data.networkWapper.networkMapper.NetworkMapper
import com.example.application.data.networkWapper.networkMapper.NetworkMapperImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class NetworkMapperBinder {

    @Binds
    abstract fun bindNetworkHelper(networkMapperImpl: NetworkMapperImpl): NetworkMapper
}