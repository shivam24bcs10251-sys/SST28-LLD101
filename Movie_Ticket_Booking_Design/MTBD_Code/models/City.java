package models;

/**
 * Represents a City where one or more Theatres are located.
 * Used to filter shows by geography.
 */
public class City {
    private final String id;
    private String name;

    public City(String id, String name) {
        this.id   = id;
        this.name = name;
    }

    public String getId()   { return id; }
    public String getName() { return name; }

    @Override
    public String toString() { return "City{" + name + "}"; }
}
