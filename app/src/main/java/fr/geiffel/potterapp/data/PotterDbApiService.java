package fr.geiffel.potterapp.data;

import fr.geiffel.potterapp.domain.Movie;
import retrofit2.Call;
import retrofit2.http.GET;

public interface PotterDbApiService {
    @GET("movies")
    Call<PotterDbResponse<Movie>> getMovies();
}
