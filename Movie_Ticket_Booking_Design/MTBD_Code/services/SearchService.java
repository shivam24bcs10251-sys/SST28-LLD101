package services;

import models.City;
import models.Movie;
import models.Screen;
import models.Show;
import models.Theatre;

import java.util.ArrayList;
import java.util.List;

/**
 * SearchService — handles Requirements 1 & 2.
 *
 * Allows users to search for available Shows either by:
 *   (a) Movie title (partial, case-insensitive match)
 *   (b) City name  (partial, case-insensitive match)
 *
 * Results include movie name, theatre, screen, and timings.
 */
public class SearchService {

    private final List<Theatre> allTheatres;

    public SearchService(List<Theatre> allTheatres) {
        this.allTheatres = allTheatres;
    }

    /**
     * Requirement 1(a): Search shows by movie title.
     *
     * @param movieTitle partial or full movie title (case-insensitive)
     * @return list of Shows matching the movie
     */
    public List<Show> searchByMovie(String movieTitle) {
        List<Show> results = new ArrayList<>();
        String query = movieTitle.toLowerCase();

        for (Theatre theatre : allTheatres) {
            for (Screen screen : theatre.getScreens()) {
                for (Show show : screen.getShows()) {
                    if (show.getMovie().getTitle().toLowerCase().contains(query)) {
                        results.add(show);
                    }
                }
            }
        }
        return results;
    }

    /**
     * Requirement 1(b): Search shows by city name.
     *
     * @param cityName partial or full city name (case-insensitive)
     * @return list of Shows available in that city
     */
    public List<Show> searchByCity(String cityName) {
        List<Show> results = new ArrayList<>();
        String query = cityName.toLowerCase();

        for (Theatre theatre : allTheatres) {
            if (theatre.getCity().getName().toLowerCase().contains(query)) {
                for (Screen screen : theatre.getScreens()) {
                    results.addAll(screen.getShows());
                }
            }
        }
        return results;
    }

    /**
     * Pretty-print a list of shows (Requirement 2).
     */
    public static void printShows(List<Show> shows) {
        if (shows.isEmpty()) {
            System.out.println("  No shows found.");
            return;
        }
        System.out.printf("  %-6s %-22s %-18s %-12s %-12s %-10s%n",
                "ShowID", "Movie", "Theatre", "Start", "End", "Screen");
        System.out.println("  " + "-".repeat(82));
        for (Show s : shows) {
            System.out.printf("  %-6s %-22s %-18s %-12s %-12s %-10s%n",
                    s.getId(),
                    s.getMovie().getTitle(),
                    s.getScreen().getTheatre().getName(),
                    s.getStartTime().toLocalTime(),
                    s.getEndTime().toLocalTime(),
                    s.getScreen().getName());
        }
    }
}
