package com.deepak.pokequest.data.di

import android.content.Context
import com.deepak.pokequest.data.db.PokeQuestDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun providePokeQuestDao(pokeQuestDB: PokeQuestDB) = pokeQuestDB.pokeQuestDao()

    @Provides
    @Singleton
    fun providePokeQuestDB(@ApplicationContext context: Context) = PokeQuestDB.getInstance(context)
}