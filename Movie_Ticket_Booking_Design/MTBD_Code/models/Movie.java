package models;

/**
 * Represents a Movie entity in the system.
 * A movie can be shown in multiple theatres/screens across cities.
 */
public class Movie {
    private final String id;
    private String title;
    private String genre;
    private int    durationMinutes;  // e.g. 150
    private String language;         // e.g. "Hindi", "English"

    public Movie(String id, String title, String genre, int durationMinutes, String language) {
        this.id              = id;
        this.title           = title;
        this.genre           = genre;
        this.durationMinutes = durationMinutes;
        this.language        = language;
    }

    // ── Getters ──────────────────────────────────────────────────────────────
    public String getId()               { return id; }
    public String getTitle()            { return title; }
    public String getGenre()            { return genre; }
    public int    getDurationMinutes()  { return durationMinutes; }
    public String getLanguage()         { return language; }

    @Override
    public String toString() {
        return "Movie{title='" + title + "', genre='" + genre
                + "', duration=" + durationMinutes + " min, lang='" + language + "'}";
    }
}
