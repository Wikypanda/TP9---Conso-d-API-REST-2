package fr.geiffel.potterapp.data;

import java.util.List;

import fr.geiffel.potterapp.domain.PotterCharacter;
import fr.geiffel.potterapp.domain.Spell;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface HPApiService {
    @GET("characters/house/{house}")
    Call<List<PotterCharacter>> getCharactersByHouse(@Path("house") String house);

    @GET("character/{id}")
    Call<PotterCharacter> getCharacterById(@Path("id") String id);

    @GET("spells")
    Call<List<Spell>> getAllSpells();

    @GET("characters/staff")
    Call<List<PotterCharacter>> getStaff();
}
