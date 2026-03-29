package services;

import models.Screen;
import models.Show;
import models.Theatre;

import java.util.ArrayList;
import java.util.List;

/**
 * ShowService provides read-only queries on shows.
 *
 * Requirement 2: Listing of multiple shows (movie name, timings).
 * Complements SearchService for more fine-grained queries.
 */
public class ShowService {

    private final List<Theatre> allTheatres;

    public ShowService(List<Theatre> allTheatres) {
        this.allTheatres = allTheatres;
    }

    /** Get all shows for a specific theatre. */
    public List<Show> getShowsByTheatre(String theatreId) {
        List<Show> results = new ArrayList<>();
        for (Theatre t : allTheatres) {
            if (t.getId().equals(theatreId)) {
                for (Screen s : t.getScreens()) {
                    results.addAll(s.getShows());
                }
            }
        }
        return results;
    }

    /** Get all shows across all theatres for a given movie ID. */
    public List<Show> getShowsByMovieId(String movieId) {
        List<Show> results = new ArrayList<>();
        for (Theatre t : allTheatres) {
            for (Screen s : t.getScreens()) {
                for (Show show : s.getShows()) {
                    if (show.getMovie().getId().equals(movieId)) {
                        results.add(show);
                    }
                }
            }
        }
        return results;
    }

    /** Get a specific show by its ID. */
    public Show getShowById(String showId) {
        for (Theatre t : allTheatres) {
            for (Screen s : t.getScreens()) {
                for (Show show : s.getShows()) {
                    if (show.getId().equals(showId)) {
                        return show;
                    }
                }
            }
        }
        return null;
    }
}
