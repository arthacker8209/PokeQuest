package com.deepak.pokequest.data.remote.client

import com.deepak.pokequest.data.remote.response.PokemonResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PokeQuestApi {

    @GET("v2/cards")
    suspend fun getPokemon(
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int
    ): Response<PokemonResponse.Pokemon>

}