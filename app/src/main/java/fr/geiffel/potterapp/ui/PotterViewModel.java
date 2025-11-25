package fr.geiffel.potterapp.ui;

import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.State;
import androidx.lifecycle.ViewModel;

import java.util.List;

import fr.geiffel.potterapp.data.PotterApiRepository;
import fr.geiffel.potterapp.domain.Book;
import fr.geiffel.potterapp.domain.House;
import fr.geiffel.potterapp.domain.Movie;
import fr.geiffel.potterapp.domain.PotterCharacter;
import fr.geiffel.potterapp.domain.Spell;

public class PotterViewModel extends ViewModel {

    private final MutableState<List<House>> _houses = SnapshotStateKt.mutableStateOf(List.of(), SnapshotStateKt.structuralEqualityPolicy());
    private final MutableState<List<Spell>> _spells = SnapshotStateKt.mutableStateOf(List.of(), SnapshotStateKt.structuralEqualityPolicy());
    private final MutableState<List<Book>> _books = SnapshotStateKt.mutableStateOf(List.of(), SnapshotStateKt.structuralEqualityPolicy());
    private final MutableState<List<Movie>> _movies = SnapshotStateKt.mutableStateOf(List.of(), SnapshotStateKt.structuralEqualityPolicy());
    private final MutableState<List<PotterCharacter>> _characters =SnapshotStateKt.mutableStateOf(List.of(), SnapshotStateKt.structuralEqualityPolicy());
    private final MutableState<List<PotterCharacter>> _staff = SnapshotStateKt.mutableStateOf(List.of(), SnapshotStateKt.structuralEqualityPolicy());
    private final MutableState<String> _error = SnapshotStateKt.mutableStateOf("", SnapshotStateKt.structuralEqualityPolicy());
    private final PotterApiRepository potterApiRepository = new PotterApiRepository();

    public State<List<House>> houses() {
        return _houses;
    }

    public State<List<Spell>> spells() {
        return _spells;
    }

    public State<List<Book>> books() {
        return _books;
    }

    public State<List<Movie>> movies() {
        return _movies;
    }

    public State<List<PotterCharacter>> characters() {
        return _characters;
    }

    public State<List<PotterCharacter>> staff() {
        return _staff;
    }

    public State<String> error() {
        return _error;
    }

    public void loadHouses() {
        potterApiRepository.getHouses(new PotterApiRepository.DataCallback<List<House>>() {
            @Override
            public void onSuccess(List<House> data) {
                _houses.setValue(data);
            }

            @Override
            public void onError(String message) {
                _error.setValue(message);
            }
        });
    }

    public void loadBooks() {
        potterApiRepository.getBooks(new PotterApiRepository.DataCallback<List<Book>>() {
            @Override
            public void onSuccess(List<Book> data) {
                _books.setValue(data);
            }

            @Override
            public void onError(String message) {
                _error.setValue(message);
            }
        });
    }

    public void loadMovies() {
        potterApiRepository.getMovies(new PotterApiRepository.DataCallback<List<Movie>>() {
            @Override
            public void onSuccess(List<Movie> data) {
                _movies.setValue(data);
            }

            @Override
            public void onError(String message) {
                _error.setValue(message);
            }
        });
    }

    public void loadSpells() {
        potterApiRepository.getAllSpells(new PotterApiRepository.DataCallback<List<Spell>>() {
            @Override
            public void onSuccess(List<Spell> data) {
                _spells.setValue(data);
            }

            @Override
            public void onError(String message) {
                _error.setValue(message);
            }
        });
    }

    public void loadCharactersByHouse(String house) {
        potterApiRepository.getCharactersByHouse(house, new PotterApiRepository.DataCallback<List<PotterCharacter>>() {
            @Override
            public void onSuccess(List<PotterCharacter> data) {
                _characters.setValue(data);
            }

            @Override
            public void onError(String message) {
                _error.setValue(message);
            }
        });
    }

    public void loadStaff() {
        potterApiRepository.getStaff(new PotterApiRepository.DataCallback<List<PotterCharacter>>() {
            @Override
            public void onSuccess(List<PotterCharacter> data) {
                _staff.setValue(data);
            }

            @Override
            public void onError(String message) {
                _error.setValue(message);
            }
        });
    }
}
