package fr.geiffel.pokeapiclient.data

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PokemonRepository {

    private val service = PokeApiClient.service

    // Récupérer les pokémons avec pagination
    fun getPokemonList(
        offset: Int = 0,
        limit: Int = 20,
        callback: DataCallback<List<Pokemon>>
    ) {
        service.getPokemonList(limit, offset).enqueue(object : Callback<PokemonListResponse> {
            override fun onResponse(
                call: Call<PokemonListResponse>,
                response: Response<PokemonListResponse>
            ) {
                if (response.isSuccessful) {
                    val pokemons = response.body()?.results?.map {
                        Pokemon.fromBasic(it)
                    } ?: emptyList()
                    callback.onSuccess(pokemons)
                } else {
                    callback.onError("Erreur: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<PokemonListResponse>, t: Throwable) {
                callback.onError(t.message ?: "Erreur inconnue")
            }
        })
    }

    // Récupérer les générations
    fun getGenerations(callback: DataCallback<List<GenerationBasic>>) {
        service.getGenerations().enqueue(object : Callback<GenerationListResponse> {
            override fun onResponse(
                call: Call<GenerationListResponse>,
                response: Response<GenerationListResponse>
            ) {
                if (response.isSuccessful) {
                    callback.onSuccess(response.body()?.results ?: emptyList())
                } else {
                    callback.onError("Erreur: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<GenerationListResponse>, t: Throwable) {
                callback.onError(t.message ?: "Erreur inconnue")
            }
        })
    }

    // Récupérer les pokémons d'une génération
    fun getPokemonByGeneration(
        generationId: Int,
        offset: Int = 0,
        limit: Int = 20,
        callback: DataCallback<List<Pokemon>>
    ) {
        service.getGenerationDetail(generationId).enqueue(object : Callback<GenerationDetail> {
            override fun onResponse(
                call: Call<GenerationDetail>,
                response: Response<GenerationDetail>
            ) {
                if (response.isSuccessful) {
                    val allSpecies = response.body()?.pokemon_species ?: emptyList()
                    // Pagination manuelle
                    val paginatedSpecies = allSpecies
                        .drop(offset)
                        .take(limit)

                    val pokemons = paginatedSpecies.mapIndexed { index, species ->
                        val id = species.url.trimEnd('/').split("/").last().toInt()
                        Pokemon(
                            id = id,
                            name = species.name.capitalize(),
                            imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$id.png"
                        )
                    }
                    callback.onSuccess(pokemons)
                } else {
                    callback.onError("Erreur: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<GenerationDetail>, t: Throwable) {
                callback.onError(t.message ?: "Erreur inconnue")
            }
        })
    }

    interface DataCallback<T> {
        fun onSuccess(data: T)
        fun onError(message: String)
    }
}