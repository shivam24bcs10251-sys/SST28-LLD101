package models;

import enums.BookingStatus;

import java.util.List;
import java.util.UUID;

/**
 * Represents a Booking made by a User for specific seats in a Show.
 *
 * Lifecycle:
 *   PENDING → (payment success) → CONFIRMED
 *   PENDING → (payment failed / lock expired) → CANCELLED
 */
public class Booking {
    private final String        id;
    private final User          user;
    private final Show          show;
    private final List<ShowSeat> bookedSeats;
    private double              totalAmount;
    private BookingStatus       status;

    public Booking(User user, Show show, List<ShowSeat> bookedSeats, double totalAmount) {
        this.id          = "BK-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        this.user        = user;
        this.show        = show;
        this.bookedSeats = bookedSeats;
        this.totalAmount = totalAmount;
        this.status      = BookingStatus.PENDING;
    }

    public void confirm()  { this.status = BookingStatus.CONFIRMED; }
    public void cancel()   { this.status = BookingStatus.CANCELLED; }

    // ── Getters ──────────────────────────────────────────────────────────────
    public String          getId()          { return id; }
    public User            getUser()        { return user; }
    public Show            getShow()        { return show; }
    public List<ShowSeat>  getBookedSeats() { return bookedSeats; }
    public double          getTotalAmount() { return totalAmount; }
    public BookingStatus   getStatus()      { return status; }

    @Override
    public String toString() {
        return "Booking{id='" + id + "', user='" + user.getName()
                + "', movie='" + show.getMovie().getTitle()
                + "', seats=" + bookedSeats.size()
                + ", amount=₹" + totalAmount
                + "', status=" + status + "}";
    }
}
