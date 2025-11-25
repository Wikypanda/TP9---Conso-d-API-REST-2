package fr.geiffel.potterapp.domain;

public class Movie {
    private Attributes attributes;

    public Attributes getAttributes() {
        return attributes;
    }

    public static class Attributes {
        private String title;

        public String getTitle() {
            return title;
        }
    }
}
