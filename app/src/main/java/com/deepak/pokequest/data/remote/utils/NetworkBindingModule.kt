package com.deepak.pokequest.data.remote.utils

import com.deepak.pokequest.data.remote.interceptors.NetworkStateChecker
import com.deepak.pokequest.data.remote.interceptors.NetworkStateCheckerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface NetworkBindingModule {

    @Binds
    fun bindNetworkStateChecker(networkStateCheckerImpl: NetworkStateCheckerImpl): NetworkStateChecker
}