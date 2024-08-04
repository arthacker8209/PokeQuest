package com.deepak.pokequest.presenter.feature.pokelist

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.palette.graphics.Palette
import com.deepak.pokequest.common.Constants.PAGE_SIZE
import com.deepak.pokequest.common.dispatchers.CoroutineDispatchersProvider
import com.deepak.pokequest.data.remote.utils.Result
import com.deepak.pokequest.domain.model.Pokemon
import com.deepak.pokequest.domain.usecase.GetPokemonList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class PokemonViewModel @Inject constructor(
    private val getPokemonList: GetPokemonList,
    private val dispatchersProvider: CoroutineDispatchersProvider
): ViewModel() {

    private var curPage = 1
    var pokemonList = mutableStateOf<List<PokeQuestListEntity>>(listOf())
    var loadError = mutableStateOf("")
    var isLoading = mutableStateOf(false)
    var endReached = mutableStateOf(false)

    private var cachedPokemonList = listOf<PokeQuestListEntity>()
    private var isSearchStarting = true
    var isSearching = mutableStateOf(false)

    init {
        loadPokemonPaginated()
    }

    fun searchPokemonList(query: String) {
        val listToSearch = if(isSearchStarting) {
            pokemonList.value
        } else {
            cachedPokemonList
        }
        viewModelScope.launch(dispatchersProvider.default) {
            if(query.isEmpty()) {
                pokemonList.value = cachedPokemonList
                isSearching.value = false
                isSearchStarting = true
                return@launch
            }
            val results = listToSearch.filter {
                it.name?.contains(query.trim(), ignoreCase = true) == true ||
                        it.hp.toString() == query.trim()
            }
            if(isSearchStarting) {
                cachedPokemonList = pokemonList.value
                isSearchStarting = false
            }
            pokemonList.value = results
            isSearching.value = true
        }
    }

    fun loadPokemonPaginated() {
        if (isLoading.value || endReached.value) return

        isLoading.value = true
        viewModelScope.launch(dispatchersProvider.io) {

            when( val result = getPokemonList(curPage, PAGE_SIZE)) {
                is Result.Error -> {
                    withContext(dispatchersProvider.main){
                        loadError.value = result.message
                        isLoading.value = false
                    }
                }
                is Result.Success -> {
                    withContext(dispatchersProvider.main){
                        endReached.value = PAGE_SIZE >= result.data.size
                        val pokeQuestEntries = result.data.map { pokemon ->
                            mapToPokemonEntity(pokemon)
                        }

                        endReached.value = pokeQuestEntries.size < PAGE_SIZE
                        curPage++
                        loadError.value = ""
                        isLoading.value = false
                        pokemonList.value += pokeQuestEntries
                    }
                }
            }
        }
    }

    fun calcDominantColor(drawable: Drawable, onFinish: (Color) -> Unit) {
        val bmp = (drawable as BitmapDrawable).bitmap.copy(Bitmap.Config.ARGB_8888, true)
        Palette.from(bmp).generate { palette ->
            palette?.dominantSwatch?.rgb?.let { colorValue ->
                onFinish(Color(colorValue))
            }
        }
    }

    private fun mapToPokemonEntity(pokemon: Pokemon): PokeQuestListEntity {
        return PokeQuestListEntity(
            id = pokemon.id,
            name = pokemon.name,
            hp = pokemon.hp,
            smallImage = pokemon.smallImage,
            types = pokemon.types,
            level = pokemon.level
        )
    }

    fun onScrollReachedEnd() {
        if (!isSearching.value) {
            loadPokemonPaginated()
        }
    }

    fun sortPokemonList(sortOption: SortOption) {
        viewModelScope.launch(dispatchersProvider.default) {
            val sortedList = when (sortOption) {
                SortOption.HP -> pokemonList.value.sortedByDescending { it.hp?.toIntOrNull() }
                SortOption.LEVEL -> pokemonList.value.sortedByDescending { it.level?.toIntOrNull() }
            }
            pokemonList.value = sortedList
        }
    }
}