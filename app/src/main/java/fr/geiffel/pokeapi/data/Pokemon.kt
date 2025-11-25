package fr.geiffel.pokeapiclient.data

// Réponse de l'API pour la liste des Pokémons
data class PokemonListResponse(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<PokemonBasic>
)

// Informations de base d'un Pokémon
data class PokemonBasic(
    val name: String,
    val url: String
)

// Modèle métier Pokemon
data class Pokemon(
    val id: Int,
    val name: String,
    val imageUrl: String
) {
    companion object {
        fun fromBasic(basic: PokemonBasic): Pokemon {
            // Extraire l'ID de l'URL (ex: https://pokeapi.co/api/v2/pokemon/1/)
            val id = basic.url.trimEnd('/').split("/").last().toInt()
            return Pokemon(
                id = id,
                name = basic.name.capitalize(),
                imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$id.png"
            )
        }
    }
}

// Réponse pour les générations
data class GenerationListResponse(
    val count: Int,
    val results: List<GenerationBasic>
)

data class GenerationBasic(
    val name: String,
    val url: String
)

// Détails d'une génération
data class GenerationDetail(
    val id: Int,
    val name: String,
    val pokemon_species: List<PokemonSpeciesRef>
)

data class PokemonSpeciesRef(
    val name: String,
    val url: String
)