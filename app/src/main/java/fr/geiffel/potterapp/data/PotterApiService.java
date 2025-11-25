package fr.geiffel.potterapp.data;

import java.util.List;

import fr.geiffel.potterapp.domain.Book;
import fr.geiffel.potterapp.domain.House;
import retrofit2.Call;
import retrofit2.http.GET;

public interface PotterApiService {
    @GET("houses")
    Call<List<House>> getHouses();

    @GET("books")
    Call<List<Book>> getBooks();
}
