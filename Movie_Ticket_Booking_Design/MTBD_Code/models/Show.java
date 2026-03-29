package models;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A Show is a scheduled screening of a Movie on a specific Screen at a given time.
 *
 * It holds the seat-level state map (Seat → ShowSeat) for this particular
 * show instance. Different shows on the same screen are independent inventories.
 *
 * Requirement:
 *  - Listing of multiple shows with movie name, timings, etc.
 *  - Each screen can have different movies at different times.
 */
public class Show {
    private final String                id;
    private final Movie                 movie;
    private final Screen                screen;
    private final LocalDateTime         startTime;
    private final LocalDateTime         endTime;
    private final Map<String, ShowSeat> showSeatMap;  // seatId → ShowSeat

    public Show(String id, Movie movie, Screen screen,
                LocalDateTime startTime, LocalDateTime endTime) {
        this.id          = id;
        this.movie       = movie;
        this.screen      = screen;
        this.startTime   = startTime;
        this.endTime     = endTime;
        this.showSeatMap = new HashMap<>();

        // Initialise ShowSeat for every seat in the screen
        for (Seat seat : screen.getSeats()) {
            showSeatMap.put(seat.getId(), new ShowSeat(seat, this));
        }
    }

    // ── Helper ──────────────────────────────────────────────────────────────
    public ShowSeat getShowSeat(String seatId) {
        return showSeatMap.get(seatId);
    }

    public Map<String, ShowSeat> getShowSeatMap() { return showSeatMap; }

    /** Human-readable listing (Requirement 2) */
    public void printListing() {
        System.out.printf("  [%s] %-20s | %-15s | %s - %s | Screen: %s%n",
                id, movie.getTitle(), screen.getTheatre().getName(),
                startTime.toLocalTime(), endTime.toLocalTime(), screen.getName());
    }

    // ── Getters ──────────────────────────────────────────────────────────────
    public String        getId()        { return id; }
    public Movie         getMovie()     { return movie; }
    public Screen        getScreen()    { return screen; }
    public LocalDateTime getStartTime() { return startTime; }
    public LocalDateTime getEndTime()   { return endTime; }

    @Override
    public String toString() {
        return "Show{movie='" + movie.getTitle()
                + "', start=" + startTime
                + ", screen=" + screen.getName() + "}";
    }
}
