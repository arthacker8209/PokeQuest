package com.deepak.pokequest.data.repository

import com.deepak.pokequest.data.db.dao.PokeQuestDao
import com.deepak.pokequest.data.mapper.PokemonMapper.toDomain
import com.deepak.pokequest.data.mapper.PokemonMapper.toEntity
import com.deepak.pokequest.data.remote.client.PokeQuestApi
import com.deepak.pokequest.data.remote.utils.ErrorType
import com.deepak.pokequest.data.remote.utils.Result
import com.deepak.pokequest.data.remote.utils.getResult
import com.deepak.pokequest.domain.model.Pokemon
import javax.inject.Inject


class PokeQuestRepository @Inject constructor(
    private val pokeQuestApi: PokeQuestApi,
    private val pokeQuestDao: PokeQuestDao
): PokeQuestContract.Repository {

    override suspend fun getPokemonList(
        page: Int,
        pageSize: Int
    ): Result<List<Pokemon>> {

        val result = getResult { pokeQuestApi.getPokemon(page, pageSize) }
        if (result is Result.Success){
            val pokemonEntityList = result.data.data?.map { pokemon ->
               pokemon.toEntity()
            }

            val pokemonList = pokemonEntityList?.map { pokemonEntity ->
                pokemonEntity.toDomain()
            }
            pokeQuestDao.deleteAllPokemon()
            if (pokemonEntityList != null) {
                pokeQuestDao.insertAllPokemon(pokemonEntityList)
            }
            return Result.Success(pokemonList!!)
        }
        else if (result is Result.Error && result.type == ErrorType.NetworkException) {
            val pokemon = pokeQuestDao.getAllPokemon()
            if (pokemon.isNotEmpty()) {
                val pokemonList = pokemon.map { pokemonEntity ->
                    pokemonEntity.toDomain()
                }
                return Result.Success(pokemonList)
            }
        }
        return Result.Error("Something went wrong", ErrorType.InvalidData)
    }

    override suspend fun getPokemonById(id: String): Result<Pokemon> {
        return try{
            val pokemon = pokeQuestDao.getPokemonById(id).toDomain()
            Result.Success(pokemon)
        }catch (e: Exception){
            Result.Error("Something went wrong.", ErrorType.InvalidData)
        }
    }

}