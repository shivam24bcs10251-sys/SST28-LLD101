package models;

import java.util.ArrayList;
import java.util.List;

/**
 * A Theatre belongs to a City and can contain multiple Screens.
 * Requirement: One theatre can have multiple screens.
 */
public class Theatre {
    private final String id;
    private String name;
    private City   city;
    private String address;
    private List<Screen> screens;

    public Theatre(String id, String name, City city, String address) {
        this.id      = id;
        this.name    = name;
        this.city    = city;
        this.address = address;
        this.screens = new ArrayList<>();
    }

    /** Add a screen to this theatre. */
    public void addScreen(Screen screen) {
        screens.add(screen);
    }

    // ── Getters ──────────────────────────────────────────────────────────────
    public String       getId()      { return id; }
    public String       getName()    { return name; }
    public City         getCity()    { return city; }
    public String       getAddress() { return address; }
    public List<Screen> getScreens() { return screens; }

    @Override
    public String toString() {
        return "Theatre{name='" + name + "', city=" + city.getName()
                + ", screens=" + screens.size() + "}";
    }
}
