import concurrency.SeatLockManager;
import enums.SeatType;
import models.*;
import payment.PaymentService;
import payment.RazorpayGateway;
import payment.StripeGateway;
import pricing.*;
import services.BookingService;
import services.SearchService;
import services.ShowService;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * ═══════════════════════════════════════════════════════════════════════
 *  Movie Ticket Booking System — End-to-End Demo
 * ═══════════════════════════════════════════════════════════════════════
 *
 * Demonstrates all 10 requirements:
 *  [1]  Search by movie title & by city
 *  [2]  Listing of shows with movie name, timings, theatre, screen
 *  [3]  One theatre can have multiple screens
 *  [4]  Each screen can have different movies at different times
 *  [5]  Each screen can have different seats
 *  [6]  Different prices for different seat types (SILVER/GOLD/PLATINUM)
 *  [7]  Dynamic pricing: PeakHour + Weekend decorator strategies
 *  [8]  Payment gateway interface (Razorpay & Stripe)
 *  [9]  Simultaneous seat booking handled via ReentrantLock
 *  [10] Seat locked for 8 minutes while user pays
 */
public class Main {

    public static void main(String[] args) throws InterruptedException {

        System.out.println("══════════════════════════════════════════════════════════");
        System.out.println("   Movie Ticket Booking System — LLD Demo");
        System.out.println("══════════════════════════════════════════════════════════\n");

        // ──────────────────────────────────────────────────────────────────
        // 1. SETUP: Cities, Movies, Theatres, Screens, Seats
        // ──────────────────────────────────────────────────────────────────

        // Cities
        City mumbai    = new City("C1", "Mumbai");
        City bangalore = new City("C2", "Bangalore");

        // Movies
        Movie inception  = new Movie("M1", "Inception",       "Sci-Fi",  148, "English");
        Movie kgf        = new Movie("M2", "KGF Chapter 3",   "Action",  173, "Kannada");
        Movie dunki      = new Movie("M3", "Dunki",           "Drama",   161, "Hindi");

        // ── Theatre 1: Mumbai (Req 3 — multiple screens) ──────────────────
        Theatre pvr_mumbai = new Theatre("T1", "PVR Juhu", mumbai, "Juhu Beach Road");

        Screen sc1 = new Screen("S1", "Audi 1", pvr_mumbai);
        Screen sc2 = new Screen("S2", "Audi 2", pvr_mumbai);

        populateSeats(sc1, 3, 5);   // 3 rows × 5 cols
        populateSeats(sc2, 2, 4);   // 2 rows × 4 cols

        pvr_mumbai.addScreen(sc1);
        pvr_mumbai.addScreen(sc2);

        // ── Theatre 2: Bangalore ──────────────────────────────────────────
        Theatre inox_blr = new Theatre("T2", "INOX Garuda", bangalore, "Garuda Mall, MG Road");

        Screen sc3 = new Screen("S3", "Screen 1", inox_blr);
        Screen sc4 = new Screen("S4", "Screen 2", inox_blr);

        populateSeats(sc3, 2, 5);
        populateSeats(sc4, 2, 4);

        inox_blr.addScreen(sc3);
        inox_blr.addScreen(sc4);

        // ── Shows (Req 4 — different movies at different times on same screen)
        // Saturday 19:00 → peak hour + weekend pricing
        LocalDateTime satPeak  = LocalDateTime.of(2026, 4, 4, 19, 0);  // Sat 7 PM
        LocalDateTime satLate  = LocalDateTime.of(2026, 4, 4, 22, 0);  // Sat 10 PM
        LocalDateTime sunMorn  = LocalDateTime.of(2026, 4, 5,  9, 0);  // Sun 9 AM
        LocalDateTime wedAfter = LocalDateTime.of(2026, 4, 8, 15, 0);  // Wed 3 PM (no surge)

        // Audi 1: Inception at 7 PM Sat | KGF at 10 PM Sat
        Show show1 = createShow("SH1", inception, sc1, satPeak,  satPeak.plusMinutes(148));
        Show show2 = createShow("SH2", kgf,       sc1, satLate,  satLate.plusMinutes(173));

        // Audi 2: Dunki Sunday morning
        Show show3 = createShow("SH3", dunki,     sc2, sunMorn,  sunMorn.plusMinutes(161));

        // Bangalore Screen 1: Inception Wed afternoon (no surge)
        Show show4 = createShow("SH4", inception, sc3, wedAfter, wedAfter.plusMinutes(148));

        // Bangalore Screen 2: KGF Saturday peak
        Show show5 = createShow("SH5", kgf,       sc4, satPeak,  satPeak.plusMinutes(173));

        List<Theatre> allTheatres = List.of(pvr_mumbai, inox_blr);

        // ──────────────────────────────────────────────────────────────────
        // 2. SERVICES SETUP
        // ──────────────────────────────────────────────────────────────────

        // Pricing chain: Base → PeakHour (x1.25) → Weekend (x1.20) [Req 6, 7]
        PricingStrategy chain = new WeekendPricingStrategy(
                                    new PeakHourPricingStrategy(
                                        new BasePricingStrategy(200.0), 1.25),
                                    1.20);
        PricingContext pricingCtx = new PricingContext(chain);

        SeatLockManager lockManager    = new SeatLockManager();
        PaymentService  paymentService = new PaymentService(new RazorpayGateway()); // Req 8

        SearchService  searchService  = new SearchService(allTheatres);
        ShowService    showService    = new ShowService(allTheatres);
        BookingService bookingService = new BookingService(lockManager, pricingCtx, paymentService);

        // ──────────────────────────────────────────────────────────────────
        // 3. REQ 1 & 2: SEARCH DEMO
        // ──────────────────────────────────────────────────────────────────

        System.out.println("▶  [Req 1a] Searching shows for movie: 'Inception'");
        List<Show> inceptionShows = searchService.searchByMovie("Inception");
        SearchService.printShows(inceptionShows);

        System.out.println("\n▶  [Req 1b] Searching shows in city: 'Bangalore'");
        List<Show> blrShows = searchService.searchByCity("Bangalore");
        SearchService.printShows(blrShows);

        // ──────────────────────────────────────────────────────────────────
        // 4. REQ 6 & 7: PRICING DEMO (show seat prices for show1)
        // ──────────────────────────────────────────────────────────────────

        System.out.println("\n▶  [Req 6 & 7] Seat prices for Show SH1 (Sat 7 PM — Peak + Weekend):");
        sc1.getSeats().forEach(seat -> {
            ShowSeat ss = show1.getShowSeat(seat.getId());
            double p = pricingCtx.getPrice(ss, show1);
            System.out.printf("  Seat %-4s (%s) → ₹%.2f%n", seat.getLabel(), seat.getType(), p);
        });

        // ──────────────────────────────────────────────────────────────────
        // 5. REQ 8, 9, 10: BOOKING FLOW — HAPPY PATH
        // ──────────────────────────────────────────────────────────────────

        System.out.println("\n══════════════════════════════════════════════════════════");
        System.out.println("   BOOKING FLOW — Happy Path (User Alice books 2 seats)");
        System.out.println("══════════════════════════════════════════════════════════");

        User alice = new User("U1", "Alice", "alice@example.com", "9999900001");

        // Alice books seats A1 & A2 in show1 (SH1: Inception, Sat 7 PM)
        List<String> aliceSeats = Arrays.asList("S1-A1", "S1-A2");
        Booking aliceBooking = bookingService.initiateBooking(alice, show1, aliceSeats);

        if (aliceBooking != null) {
            boolean paid = bookingService.processPayment(aliceBooking);
            System.out.println("Result: " + aliceBooking);
        }

        // ──────────────────────────────────────────────────────────────────
        // 6. REQ 9: CONCURRENT BOOKING — Two users try same seat
        // ──────────────────────────────────────────────────────────────────

        System.out.println("\n══════════════════════════════════════════════════════════");
        System.out.println("   CONCURRENT BOOKING — Bob & Charlie try same seats");
        System.out.println("══════════════════════════════════════════════════════════");

        User bob     = new User("U2", "Bob",     "bob@example.com",     "9999900002");
        User charlie = new User("U3", "Charlie", "charlie@example.com", "9999900003");

        // Both try to book A3 & A4 in show1 simultaneously
        List<String> contestedSeats = Arrays.asList("S1-A3", "S1-A4");

        Thread bobThread = new Thread(() -> {
            System.out.println("[Thread-Bob] Bob is selecting seats A3 & A4 in Inception...");
            Booking b = bookingService.initiateBooking(bob, show1, contestedSeats);
            if (b != null) {
                try { Thread.sleep(100); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
                bookingService.processPayment(b);
                System.out.println("[Thread-Bob] " + b);
            }
        }, "Thread-Bob");

        Thread charlieThread = new Thread(() -> {
            System.out.println("[Thread-Charlie] Charlie is also selecting seats A3 & A4...");
            Booking b = bookingService.initiateBooking(charlie, show1, contestedSeats);
            if (b != null) {
                bookingService.processPayment(b);
                System.out.println("[Thread-Charlie] " + b);
            } else {
                System.out.println("[Thread-Charlie] Charlie's booking FAILED — seats taken by Bob!");
            }
        }, "Thread-Charlie");

        bobThread.start();
        Thread.sleep(20);  // 20 ms delay so Bob slightly ahead (realistic race)
        charlieThread.start();

        bobThread.join();
        charlieThread.join();

        // ──────────────────────────────────────────────────────────────────
        // 7. REQ 10: LOCK EXPIRY DEMO (demonstrates the 8-min hold concept)
        // ──────────────────────────────────────────────────────────────────

        System.out.println("\n══════════════════════════════════════════════════════════");
        System.out.println("   SEAT LOCK EXPIRY — Dave locks but doesn't pay (cancel)");
        System.out.println("══════════════════════════════════════════════════════════");

        User dave = new User("U4", "Dave", "dave@example.com", "9999900004");
        List<String> daveSeats = Arrays.asList("S1-B1", "S1-B2");

        Booking daveBooking = bookingService.initiateBooking(dave, show1, daveSeats);
        System.out.println("Dave's booking state: " + (daveBooking != null ? daveBooking.getStatus() : "null"));
        System.out.println("Dave's seats locked for 8 minutes. Dave abandons the booking...");

        // Simulate Dave cancelling (in production, the scheduler would auto-release)
        bookingService.cancelBooking(daveBooking);
        System.out.println("Seats B1 & B2 are now AVAILABLE again for others.");

        // ──────────────────────────────────────────────────────────────────
        // 8. REQ 8: STRIPE GATEWAY DEMO
        // ──────────────────────────────────────────────────────────────────

        System.out.println("\n══════════════════════════════════════════════════════════");
        System.out.println("   PAYMENT GATEWAY SWAP — Eve pays via Stripe");
        System.out.println("══════════════════════════════════════════════════════════");

        paymentService.setGateway(new StripeGateway());
        User   eve       = new User("U5", "Eve", "eve@example.com", "9999900005");
        Booking eveBooking = bookingService.initiateBooking(eve, show3,
                                 Arrays.asList("S2-A1", "S2-A2"));
        if (eveBooking != null) {
            bookingService.processPayment(eveBooking);
            System.out.println("Result: " + eveBooking);
        }

        // ──────────────────────────────────────────────────────────────────
        System.out.println("\n══════════════════════════════════════════════════════════");
        System.out.println("   DEMO COMPLETE");
        System.out.println("══════════════════════════════════════════════════════════");

        lockManager.shutdown();
    }

    // ── Helper: create a Show and register it with the Screen ──────────────

    private static Show createShow(String id, Movie movie, Screen screen,
                                   LocalDateTime start, LocalDateTime end) {
        Show show = new Show(id, movie, screen, start, end);
        screen.addShow(show);
        return show;
    }

    /**
     * Helper: populate a screen with rows × cols seats.
     *
     * Seat naming convention: "<screenId>-<row><col>"  e.g. "S1-A3"
     * Seat types distributed as:
     *   Row A      → SILVER
     *   Row B      → GOLD
     *   Row C+     → PLATINUM
     *
     * Requirement 5: Each screen can have different seats.
     * Requirement 6: Prices differ per seat type.
     */
    private static void populateSeats(Screen screen, int rows, int cols) {
        char[] rowLabels = {'A', 'B', 'C', 'D', 'E', 'F'};
        SeatType[] seatTypes = {SeatType.SILVER, SeatType.GOLD, SeatType.PLATINUM};

        for (int r = 0; r < rows; r++) {
            char rowLabel = rowLabels[r];
            SeatType type = seatTypes[Math.min(r, seatTypes.length - 1)];

            for (int c = 1; c <= cols; c++) {
                String seatId = screen.getId() + "-" + rowLabel + c;
                Seat seat = new Seat(seatId, rowLabel, c, type);
                screen.addSeat(seat);
            }
        }
    }
}
