package fr.geiffel.potterapp

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import fr.geiffel.potterapp.ui.PotterViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.ContentScale
import coil.compose.rememberAsyncImagePainter
import fr.geiffel.potterapp.domain.Book
import fr.geiffel.potterapp.domain.House
import fr.geiffel.potterapp.domain.Movie
import fr.geiffel.potterapp.domain.PotterCharacter
import fr.geiffel.potterapp.domain.Spell

class MainActivity : ComponentActivity() {
    private val viewModel: PotterViewModel by viewModels()

    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PotterApp(viewModel)
        }
    }
}

@Composable
fun PotterApp(viewModel: PotterViewModel) {
    val potterCharacters: List<PotterCharacter> by viewModel.characters()
    val spells: List<Spell> by viewModel.spells()
    val books: List<Book> by viewModel.books()
    val movies: List<Movie> by viewModel.movies()
    val houses: List<House> by viewModel.houses()
    val staff: List<PotterCharacter> by viewModel.staff()
    viewModel.loadHouses()
    var displayedContent: String by remember { mutableStateOf("houses") }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Choisis une catégorie :", style = MaterialTheme.typography.headlineMedium)
        FlowRow(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            houses.forEach { house ->
                Button(onClick = { viewModel.loadCharactersByHouse(house.house.lowercase()) ; displayedContent = "houses" }) {
                    Text(house.house)
                }
            }
            Button(onClick = {viewModel.loadSpells() ; displayedContent = "spells"}) {
                Text("Spells")
            }
            Button(onClick = {viewModel.loadStaff() ; displayedContent = "staff"}) {
                Text("Staff")
            }
            Button(onClick = {viewModel.loadBooks() ; displayedContent = "books"}) {
                Text("Books")
            }
            Button(onClick = {viewModel.loadMovies() ; displayedContent = "movies"}) {
                Text("Movies")
            }
        }

        Spacer(Modifier.height(16.dp))

        LazyColumn {
            when (displayedContent) {
                "houses" -> {
                    items(potterCharacters.size) { index ->
                        val c = potterCharacters[index]
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { /* TODO: Afficher détails */ }
                                .padding(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = rememberAsyncImagePainter(c.getImage()),
                                contentDescription = null,
                                modifier = Modifier.size(64.dp),
                                contentScale = ContentScale.Crop
                            )
                            Spacer(Modifier.width(16.dp))
                            Text(c.getName())
                            Text(c.getDateOfBirth() ?: "Null")
                        }
                    }
                }
                "spells" -> {
                    items(spells.size) { index ->
                        val s = spells[index]
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { /* TODO: Afficher détails */ }
                                .padding(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(s.getName())
                            Text(s.getDescription())
                        }
                    }
                }
                "staff" -> {
                    items(staff.size) { index ->
                        val c = staff[index]
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { /* TODO: Afficher détails */ }
                                .padding(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = rememberAsyncImagePainter(c.getImage()),
                                contentDescription = null,
                                modifier = Modifier.size(64.dp),
                                contentScale = ContentScale.Crop
                            )
                            Spacer(Modifier.width(16.dp))
                            Text(c.getName())
                            Text(c.getDateOfBirth() ?: "Null")
                        }
                    }
                }
                "books" -> {
                    items(books.size) { index ->
                        val b = books[index]
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { /* TODO: Afficher détails */ }
                                .padding(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(b.getTitle())
                        }
                    }
                }
                "movies" -> {
                    items(movies.size) { index ->
                        val m = movies[index]
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { /* TODO: Afficher détails */ }
                                .padding(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(m.getAttributes().getTitle())
                        }
                    }
                }
            }
        }
    }
}