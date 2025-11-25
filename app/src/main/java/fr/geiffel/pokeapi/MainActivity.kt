package fr.geiffel.pokeapiclient

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import fr.geiffel.pokeapiclient.data.Pokemon
import fr.geiffel.pokeapiclient.ui.PokemonViewModel

class MainActivity : ComponentActivity() {
    private val viewModel: PokemonViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                PokemonApp(viewModel)
            }
        }
    }
}

@Composable
fun PokemonApp(viewModel: PokemonViewModel) {
    val pokemons = viewModel.pokemons.value
    val generations = viewModel.generations.value
    val isLoading = viewModel.isLoading.value
    val currentPage = viewModel.currentPage.value
    val selectedGenerationId = viewModel.selectedGenerationId.value

    // Charger les données au démarrage
    LaunchedEffect(Unit) {
        viewModel.loadAllPokemon()
        viewModel.loadGenerations()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Titre
        Text(
            text = "PokéAPI Client",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Bouton "Tous les pokémons"
        Button(
            onClick = { viewModel.loadAllPokemon(0) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        ) {
            Text("Tous les pokémons")
        }

        // Boutons de générations
        if (generations.isNotEmpty()) {
            Text(
                text = "Générations:",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            FlowRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                generations.forEachIndexed { index, generation ->
                    Button(
                        onClick = { viewModel.loadPokemonByGeneration(index + 1, 0) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (selectedGenerationId == index + 1)
                                MaterialTheme.colorScheme.secondary
                            else
                                MaterialTheme.colorScheme.primary
                        )
                    ) {
                        Text("Gen ${index + 1}")
                    }
                }
            }
        }

        Divider(modifier = Modifier.padding(vertical = 8.dp))

        // Navigation (flèches)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = { viewModel.previousPage() },
                enabled = currentPage > 0 && !isLoading
            ) {
                Text("← Précédent")
            }

            Text(
                text = "Page ${currentPage + 1}",
                style = MaterialTheme.typography.titleMedium
            )

            Button(
                onClick = { viewModel.nextPage() },
                enabled = !isLoading && pokemons.isNotEmpty()
            ) {
                Text("Suivant →")
            }
        }

        Divider(modifier = Modifier.padding(vertical = 8.dp))

        // Indicateur de chargement
        if (isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            // Liste des pokémons
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(pokemons) { pokemon ->
                    PokemonItem(pokemon)
                }
            }
        }
    }
}

@Composable
fun PokemonItem(pokemon: Pokemon) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Image du pokémon
            Image(
                painter = rememberAsyncImagePainter(pokemon.imageUrl),
                contentDescription = pokemon.name,
                modifier = Modifier.size(80.dp),
                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.width(16.dp))

            // Informations du pokémon
            Column {
                Text(
                    text = "#${pokemon.id}",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.secondary
                )
                Text(
                    text = pokemon.name,
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }
    }
}

@Composable
fun FlowRow(
    modifier: Modifier = Modifier,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    content: @Composable () -> Unit
) {
    androidx.compose.foundation.layout.FlowRow(
        modifier = modifier,
        horizontalArrangement = horizontalArrangement,
        verticalArrangement = verticalArrangement
    ) {
        content()
    }
}