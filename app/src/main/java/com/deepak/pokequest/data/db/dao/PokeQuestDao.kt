package com.deepak.pokequest.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.deepak.pokequest.data.db.entity.PokemonEntity

@Dao
interface PokeQuestDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllPokemon (pokemon: List<PokemonEntity>)

    @Query("SELECT * FROM pokemon WHERE id = :id")
    suspend fun getPokemonById(id: String): PokemonEntity

    @Query("SELECT * FROM pokemon")
    suspend fun getAllPokemon(): List<PokemonEntity>

    @Query("DELETE FROM pokemon")
    suspend fun deleteAllPokemon()
}