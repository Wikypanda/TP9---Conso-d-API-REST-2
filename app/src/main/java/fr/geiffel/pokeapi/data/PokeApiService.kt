package fr.geiffel.pokeapiclient.data

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokeApiService {

    // Itération 1: Récupérer les 20 premiers pokémons
    @GET("pokemon?limit=20")
    fun getFirst20Pokemon(): Call<PokemonListResponse>

    // Itération 2: Récupérer des pokémons avec pagination
    @GET("pokemon")
    fun getPokemonList(
        @Query("limit") limit: Int = 20,
        @Query("offset") offset: Int = 0
    ): Call<PokemonListResponse>

    // Itération 3: Récupérer toutes les générations
    @GET("generation")
    fun getGenerations(): Call<GenerationListResponse>

    // Itération 3: Récupérer les détails d'une génération
    @GET("generation/{id}")
    fun getGenerationDetail(@Path("id") id: Int): Call<GenerationDetail>
}