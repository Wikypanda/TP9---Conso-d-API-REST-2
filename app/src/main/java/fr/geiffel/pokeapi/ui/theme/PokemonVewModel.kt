package fr.geiffel.pokeapiclient.ui

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import fr.geiffel.pokeapiclient.data.GenerationBasic
import fr.geiffel.pokeapiclient.data.Pokemon
import fr.geiffel.pokeapiclient.data.PokemonRepository

class PokemonViewModel : ViewModel() {

    private val repository = PokemonRepository()

    // États observables
    val pokemons = mutableStateOf<List<Pokemon>>(emptyList())
    val generations = mutableStateOf<List<GenerationBasic>>(emptyList())
    val error = mutableStateOf("")
    val isLoading = mutableStateOf(false)

    // État pour la pagination
    val currentPage = mutableStateOf(0)
    val selectedGenerationId = mutableStateOf<Int?>(null)

    private val itemsPerPage = 20

    // Charger tous les pokémons (pagination)
    fun loadAllPokemon(page: Int = 0) {
        isLoading.value = true
        currentPage.value = page
        selectedGenerationId.value = null

        val offset = page * itemsPerPage
        repository.getPokemonList(offset, itemsPerPage, object : PokemonRepository.DataCallback<List<Pokemon>> {
            override fun onSuccess(data: List<Pokemon>) {
                pokemons.value = data
                isLoading.value = false
            }

            override fun onError(message: String) {
                error.value = message
                isLoading.value = false
            }
        })
    }

    // Charger les générations
    fun loadGenerations() {
        repository.getGenerations(object : PokemonRepository.DataCallback<List<GenerationBasic>> {
            override fun onSuccess(data: List<GenerationBasic>) {
                generations.value = data
            }

            override fun onError(message: String) {
                error.value = message
            }
        })
    }

    // Charger les pokémons d'une génération
    fun loadPokemonByGeneration(generationId: Int, page: Int = 0) {
        isLoading.value = true
        currentPage.value = page
        selectedGenerationId.value = generationId

        val offset = page * itemsPerPage
        repository.getPokemonByGeneration(
            generationId,
            offset,
            itemsPerPage,
            object : PokemonRepository.DataCallback<List<Pokemon>> {
                override fun onSuccess(data: List<Pokemon>) {
                    pokemons.value = data
                    isLoading.value = false
                }

                override fun onError(message: String) {
                    error.value = message
                    isLoading.value = false
                }
            }
        )
    }

    // Navigation
    fun nextPage() {
        val nextPage = currentPage.value + 1
        if (selectedGenerationId.value != null) {
            loadPokemonByGeneration(selectedGenerationId.value!!, nextPage)
        } else {
            loadAllPokemon(nextPage)
        }
    }

    fun previousPage() {
        if (currentPage.value > 0) {
            val prevPage = currentPage.value - 1
            if (selectedGenerationId.value != null) {
                loadPokemonByGeneration(selectedGenerationId.value!!, prevPage)
            } else {
                loadAllPokemon(prevPage)
            }
        }
    }
}