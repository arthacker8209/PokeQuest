package com.deepak.pokequest.domain.di

import com.deepak.pokequest.data.repository.PokeQuestRepository
import com.deepak.pokequest.domain.usecase.GetPokemonById
import com.deepak.pokequest.domain.usecase.GetPokemonList
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DomainModule {

    @Provides
    @Singleton
    fun provideGetPokemonList(repository: PokeQuestRepository): GetPokemonList{
        return GetPokemonList(repository)
    }

    @Provides
    @Singleton
    fun provideGetPokemonById(repository: PokeQuestRepository): GetPokemonById {
        return GetPokemonById(repository)
    }
}