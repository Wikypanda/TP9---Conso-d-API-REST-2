package fr.geiffel.potterapp.domain;

public class PotterCharacter {
    private String id;
    private String name;
    private String house;
    private String image;
    private String dateOfBirth;

    public PotterCharacter() {
    }

    public PotterCharacter(String id, String name, String house,
                           String image, String dateOfBirth) {
        this.id = id;
        this.name = name;
        this.house = house;
        this.image = image;
        this.dateOfBirth = dateOfBirth;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getHouse() { return house; }
    public String getImage() { return image; }
    public String getDateOfBirth() { return dateOfBirth; }
}
