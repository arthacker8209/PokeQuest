package com.deepak.pokequest.domain.usecase

import com.deepak.pokequest.data.remote.utils.Result
import com.deepak.pokequest.data.repository.PokeQuestRepository
import com.deepak.pokequest.domain.model.Pokemon
import javax.inject.Inject

class GetPokemonById @Inject constructor(
    private val repository: PokeQuestRepository
) {
    suspend operator fun invoke(id: String): Result<Pokemon> {
        return repository.getPokemonById(id)
    }
}