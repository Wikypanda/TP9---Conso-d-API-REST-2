package fr.geiffel.potterapp.data;

import java.util.List;

import fr.geiffel.potterapp.domain.Book;
import fr.geiffel.potterapp.domain.House;
import fr.geiffel.potterapp.domain.Movie;
import fr.geiffel.potterapp.domain.PotterCharacter;
import fr.geiffel.potterapp.domain.Spell;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PotterApiRepository {
    private final HPApiService hpApiService;
    private final PotterApiService potterApiService;
    private final PotterDbApiService potterDbApiService;

    public PotterApiRepository() {
        hpApiService = HPApiClient.getClient().create(HPApiService.class);
        potterApiService = PotterApiClient.getClient().create(PotterApiService.class);
        potterDbApiService = PotterDbApiClient.getClient().create(PotterDbApiService.class);
    }

    public void getHouses(final DataCallback<List<House>> callback) {
        potterApiService.getHouses().enqueue(new Callback<List<House>>() {
            @Override
            public void onResponse(Call<List<House>> call, Response<List<House>> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("Erreur : " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<House>> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }

    public void getBooks(final DataCallback<List<Book>> callback) {
        potterApiService.getBooks().enqueue(new Callback<List<Book>>() {
            @Override
            public void onResponse(Call<List<Book>> call, Response<List<Book>> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("Erreur : " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Book>> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }

    public void getMovies(final DataCallback<List<Movie>> callback) {
        potterDbApiService.getMovies().enqueue(new Callback<PotterDbResponse<Movie>>() {
            @Override
            public void onResponse(Call<PotterDbResponse<Movie>> call, Response<PotterDbResponse<Movie>> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body().getData());
                } else {
                    callback.onError("Erreur : " + response.code());
                }
            }

            @Override
            public void onFailure(Call<PotterDbResponse<Movie>> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }

    public void getAllSpells(final DataCallback<List<Spell>> callback) {
        hpApiService.getAllSpells().enqueue(new Callback<List<Spell>>() {
            @Override
            public void onResponse(Call<List<Spell>> call, Response<List<Spell>> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("Erreur : " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Spell>> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }

    public void getCharactersByHouse(String house, final DataCallback<List<PotterCharacter>> callback) {
        hpApiService.getCharactersByHouse(house).enqueue(new Callback<List<PotterCharacter>>() {
            @Override
            public void onResponse(Call<List<PotterCharacter>> call, Response<List<PotterCharacter>> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("Erreur : " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<PotterCharacter>> call, Throwable throwable) {
                callback.onError(throwable.getMessage());
            }
        });
    }

    public void getStaff(final DataCallback<List<PotterCharacter>> callback) {
        hpApiService.getStaff().enqueue(new Callback<List<PotterCharacter>>() {
            @Override
            public void onResponse(Call<List<PotterCharacter>> call, Response<List<PotterCharacter>> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("Erreur : " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<PotterCharacter>> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }

    public interface DataCallback<T> {
        void onSuccess(T data);
        void onError(String message);
    }
}
