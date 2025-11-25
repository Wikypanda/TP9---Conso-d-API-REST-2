package fr.geiffel.potterapp.data;

import java.util.List;

public class PotterDbResponse<T> {
    private List<T> data;

    public List<T> getData() {
        return data;
    }
}
