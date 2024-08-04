package com.deepak.pokequest.data.repository

import com.deepak.pokequest.data.db.entity.PokemonEntity
import com.deepak.pokequest.data.remote.response.PokemonResponse
import com.deepak.pokequest.data.remote.utils.Result
import com.deepak.pokequest.domain.model.Pokemon

class PokeQuestContract {

    interface Repository{
        suspend fun getPokemonList(page: Int, pageSize: Int): Result<List<Pokemon>>
        suspend fun getPokemonById(id: String): Result<Pokemon>
    }
}