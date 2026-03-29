package models;

import java.util.ArrayList;
import java.util.List;

/**
 * A Screen belongs to a Theatre and can host multiple Shows at different times.
 * Each Screen also owns a fixed layout of Seats.
 *
 * Requirement:
 *  - Each screen can have different movies at different times (via Shows).
 *  - Each screen can have different seats.
 */
public class Screen {
    private final String    id;
    private String          name;           // e.g. "Screen 1", "Audi 3"
    private Theatre         theatre;
    private List<Seat>      seats;          // physical seats in this screen
    private List<Show>      shows;          // scheduled shows on this screen

    public Screen(String id, String name, Theatre theatre) {
        this.id      = id;
        this.name    = name;
        this.theatre = theatre;
        this.seats   = new ArrayList<>();
        this.shows   = new ArrayList<>();
    }

    public void addSeat(Seat seat) { seats.add(seat); }
    public void addShow(Show show) { shows.add(show); }

    // ── Getters ──────────────────────────────────────────────────────────────
    public String     getId()      { return id; }
    public String     getName()    { return name; }
    public Theatre    getTheatre() { return theatre; }
    public List<Seat> getSeats()   { return seats; }
    public List<Show> getShows()   { return shows; }

    @Override
    public String toString() {
        return "Screen{name='" + name + "', seats=" + seats.size()
                + ", shows=" + shows.size() + "}";
    }
}
