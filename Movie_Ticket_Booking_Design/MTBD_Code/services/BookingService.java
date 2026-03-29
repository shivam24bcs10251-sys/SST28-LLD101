package services;

import concurrency.SeatLockManager;
import enums.BookingStatus;
import enums.SeatStatus;
import models.Booking;
import models.Show;
import models.ShowSeat;
import models.User;
import payment.PaymentGateway;
import payment.PaymentService;
import pricing.PricingContext;

import java.util.ArrayList;
import java.util.List;

/**
 * BookingService orchestrates the full seat-booking lifecycle:
 *
 *   1. lockSeats()      → SeatLockManager reserves seats for 8 min (Req 9, 10)
 *   2. createBooking()  → calculates price via PricingContext, creates Booking (PENDING)
 *   3. pay()            → delegates to PaymentService → gateway (Req 8)
 *   4. cancelBooking()  → releases locks, marks booking CANCELLED
 *
 * Design Pattern: Facade — simplifies complex interactions between
 *   SeatLockManager, PricingContext, and PaymentService into one surface.
 */
public class BookingService {

    private final SeatLockManager  lockManager;
    private final PricingContext   pricingContext;
    private final PaymentService   paymentService;

    public BookingService(SeatLockManager lockManager,
                          PricingContext pricingContext,
                          PaymentService paymentService) {
        this.lockManager    = lockManager;
        this.pricingContext = pricingContext;
        this.paymentService = paymentService;
    }

    /**
     * Step 1 + 2: Lock seats and create a PENDING booking.
     *
     * @param user    the requesting user
     * @param show    the selected show
     * @param seatIds list of seat IDs the user wants to book
     * @return a PENDING Booking if seats were locked, null otherwise
     */
    public Booking initiateBooking(User user, Show show, List<String> seatIds) {
        System.out.println("\n[BookingService] User '" + user.getName()
                + "' attempting to book " + seatIds.size() + " seat(s) in show: "
                + show.getMovie().getTitle() + "...");

        // ── Lock seats (concurrent-safe) ──────────────────────────────────
        boolean locked = lockManager.lockSeats(show, seatIds, user.getId());
        if (!locked) {
            System.out.println("[BookingService] Could not lock all seats. Booking aborted.");
            return null;
        }

        // ── Collect ShowSeat objects and compute total price ──────────────
        List<ShowSeat> lockedSeats = new ArrayList<>();
        double totalAmount = 0.0;

        for (String seatId : seatIds) {
            ShowSeat ss = show.getShowSeat(seatId);
            lockedSeats.add(ss);
            double price = pricingContext.getPrice(ss, show);
            System.out.printf("  Seat %s (%s) → ₹%.2f%n",
                    ss.getSeat().getLabel(), ss.getSeat().getType(), price);
            totalAmount += price;
        }

        System.out.printf("[BookingService] Total amount: ₹%.2f%n", totalAmount);

        // ── Create PENDING booking ────────────────────────────────────────
        Booking booking = new Booking(user, show, lockedSeats, totalAmount);
        System.out.println("[BookingService] Booking created: " + booking.getId()
                + " [" + booking.getStatus() + "]");
        return booking;
    }

    /**
     * Step 3: Process payment for a PENDING booking.
     * On success → seats become BOOKED; booking → CONFIRMED.
     * On failure → seats released; booking → CANCELLED.
     */
    public boolean processPayment(Booking booking) {
        if (booking == null || booking.getStatus() != BookingStatus.PENDING) {
            System.out.println("[BookingService] Cannot pay: booking is not PENDING.");
            return false;
        }

        System.out.println("\n[BookingService] Processing payment for " + booking.getId() + "...");
        var payment = paymentService.pay(booking);

        if (booking.getStatus() == BookingStatus.CONFIRMED) {
            // Mark all seats as permanently BOOKED
            for (ShowSeat ss : booking.getBookedSeats()) {
                ss.book();
            }
            System.out.println("[BookingService] Booking CONFIRMED! Seats permanently reserved.");
        } else {
            // Release the locks on payment failure
            List<String> seatIds = new ArrayList<>();
            for (ShowSeat ss : booking.getBookedSeats()) {
                seatIds.add(ss.getSeat().getId());
            }
            lockManager.unlockSeats(booking.getShow(), seatIds, booking.getUser().getId());
            System.out.println("[BookingService] Payment failed. Seats released.");
        }

        return booking.getStatus() == BookingStatus.CONFIRMED;
    }

    /**
     * Step 4: Cancel a booking (removes lock, marks CANCELLED).
     */
    public void cancelBooking(Booking booking) {
        if (booking == null) return;

        System.out.println("\n[BookingService] Cancelling booking " + booking.getId() + "...");
        List<String> seatIds = new ArrayList<>();
        for (ShowSeat ss : booking.getBookedSeats()) {
            if (ss.getStatus() == SeatStatus.LOCKED) {
                seatIds.add(ss.getSeat().getId());
            }
        }
        if (!seatIds.isEmpty()) {
            lockManager.unlockSeats(booking.getShow(), seatIds, booking.getUser().getId());
        }
        booking.cancel();
        System.out.println("[BookingService] Booking " + booking.getId() + " CANCELLED.");
    }
}
