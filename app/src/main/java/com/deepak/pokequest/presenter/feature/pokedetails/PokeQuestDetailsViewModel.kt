package com.deepak.pokequest.presenter.feature.pokedetails

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deepak.pokequest.common.dispatchers.CoroutineDispatchersProvider
import com.deepak.pokequest.data.remote.utils.Result
import com.deepak.pokequest.domain.model.Pokemon
import com.deepak.pokequest.domain.usecase.GetPokemonById
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class PokeQuestDetailsViewModel @Inject constructor(
    private val getPokemonById: GetPokemonById,
    private val dispatchersProvider: CoroutineDispatchersProvider
): ViewModel() {

    private val _pokemon = mutableStateOf<Pokemon?>(null)
    val pokemon: State<Pokemon?> get() = _pokemon

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> get() = _isLoading

    private val _pokemonStats = mutableStateOf<PokemonStats?>(null)
    val pokemonStats: State<PokemonStats?> = _pokemonStats

    fun getPokemonDetails(id: String){
        _isLoading.value = true
        viewModelScope.launch(dispatchersProvider.io) {
            when(val result = getPokemonById(id)){
                is Result.Error -> {
                    _isLoading.value = false
                }
                is Result.Success -> {
                    withContext(dispatchersProvider.main){
                        _isLoading.value = false
                        _pokemon.value = result.data
                        preparePokemonStats(result.data)
                    }
                }
            }
        }
    }

    private fun preparePokemonStats(pokemon: Pokemon){
        val level = pokemon.level.filter { it.isDigit() }.toIntOrNull() ?: 0
        val hp = pokemon.hp.filter { it.isDigit() }.toIntOrNull() ?: 0
        val attacks = pokemon.attacks.map {
            it.name to (it.damage.filter { char -> char.isDigit() }.toIntOrNull() ?: 0)
        }

        _pokemonStats.value = PokemonStats(level, hp, attacks)
    }

    data class PokemonStats(
        val level: Int,
        val hp: Int,
        val attacks: List<Pair<String, Int>>
    )
}