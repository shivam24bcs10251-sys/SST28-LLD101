# 🎬 Movie Ticket Booking System — Low Level Design

A complete **BookMyShow-lite** implementation in Java covering all 10 design requirements.

---

## 📁 Directory Structure

```
MTBD_Code/
├── enums/
│   ├── SeatType.java           # SILVER | GOLD | PLATINUM (with price multipliers)
│   ├── SeatStatus.java         # AVAILABLE | LOCKED | BOOKED
│   ├── BookingStatus.java      # PENDING | CONFIRMED | CANCELLED
│   └── PaymentStatus.java      # PENDING | SUCCESS | FAILED
├── models/
│   ├── User.java               # Registered user
│   ├── Movie.java              # Movie entity (title, genre, duration, language)
│   ├── City.java               # City for geographic filtering
│   ├── Theatre.java            # Theatre with multiple screens
│   ├── Screen.java             # Screen with seats and shows
│   ├── Seat.java               # Physical seat (row, number, SeatType)
│   ├── Show.java               # Scheduled screening (movie + screen + time)
│   ├── ShowSeat.java           # Per-show seat state (status, lock, expiry)
│   ├── Booking.java            # Booking record for a user's selected seats
│   └── Payment.java            # Payment transaction record
├── pricing/
│   ├── PricingStrategy.java        # Interface: calculatePrice(ShowSeat, Show)
│   ├── BasePricingStrategy.java    # Base price × SeatType multiplier
│   ├── PeakHourPricingStrategy.java # Decorator: +surge for 6 PM – 10 PM
│   ├── WeekendPricingStrategy.java  # Decorator: +demand for Sat/Sun
│   └── PricingContext.java          # Holds and delegates to strategy chain
├── payment/
│   ├── PaymentGateway.java      # Interface: processPayment(Payment)
│   ├── RazorpayGateway.java     # Concrete: simulated Razorpay
│   ├── StripeGateway.java       # Concrete: simulated Stripe
│   └── PaymentService.java      # Orchestrates payment + gateway selection
├── services/
│   ├── SearchService.java       # Search by movie title or city
│   ├── ShowService.java         # Query shows by theatre / movie ID
│   └── BookingService.java      # Facade: lock → price → pay → confirm
├── concurrency/
│   └── SeatLockManager.java    # ReentrantLock per show + 8-min TTL scheduler
├── Main.java                   # End-to-end demo (all 10 requirements)
└── movie_ticket_booking_uml.xml  # draw.io UML Class Diagram
```

---

## ✅ Requirements Coverage

| # | Requirement | Implementation |
|---|-------------|----------------|
| 1 | Search by **movie** or **city** | `SearchService.searchByMovie()` / `searchByCity()` |
| 2 | **Listing** of shows with movie name, timings, screen | `SearchService.printShows()`, `Show.printListing()` |
| 3 | One theatre can have **multiple screens** | `Theatre` → `List<Screen>` (Composition) |
| 4 | Each screen: **different movies at different times** | `Screen` → `List<Show>` (different movies per show) |
| 5 | Each screen has **different seats** | `Screen` → `List<Seat>` with custom layout |
| 6 | **Different prices** for different seat types | `SeatType.priceMultiplier` × `basePrice` |
| 7 | **Dynamic pricing strategies**: peak hour & weekend | Decorator chain: `BasePricing` → `PeakHour` → `Weekend` |
| 8 | **Payment gateway interface** | `PaymentGateway` interface → `RazorpayGateway`, `StripeGateway` |
| 9 | **Simultaneous booking** handled safely | Per-show `ReentrantLock` in `SeatLockManager` |
| 10 | Seat locked for **8 minutes** during payment | `ShowSeat.lockExpiry = now + 8 min`; scheduler auto-releases |

---

## 🏗️ Design Patterns Used

| Pattern | Where |
|---------|-------|
| **Strategy** | `PricingStrategy` interface, `PaymentGateway` interface |
| **Decorator** | `PeakHourPricingStrategy` and `WeekendPricingStrategy` wrap inner strategies |
| **Facade** | `BookingService` simplifies: lock → price → pay → confirm |
| **Composition** | Theatre → Screen → Seat / Show hierarchy |
| **Factory (light)** | `Main.populateSeats()` creates seats for a screen |

---

## ⚙️ Concurrency Design (Requirements 9 & 10)

```
User A Thread                     User B Thread
     |                                 |
     |──lockSeats(show, [A1,A2])──>    |
     |      Acquire ReentrantLock      |
     |      Check A1: AVAILABLE ✓      |
     |      Check A2: AVAILABLE ✓      |
     |      Lock A1, A2 (8 min TTL)    |
     |      Release ReentrantLock      |
     |                                 |──lockSeats(show, [A1,A2])──>
     |                                 |      Acquire ReentrantLock
     |                                 |      Check A1: LOCKED ✗
     |                                 |      → FAIL (no partial lock)
     |                                 |      Release ReentrantLock
     |──pay()──────────────────────>   |
     |      Seats → BOOKED             |      Seats never locked → user notified
```

**Key properties:**
- `ConcurrentHashMap<showId, ReentrantLock>` — one lock per show (fairness enabled)
- Check + lock is **atomic** inside the critical section (prevents TOCTOU race)
- `ScheduledExecutorService` sweeps every 60 s to reclaim expired locks
- If user cancels or payment fails → seats released back to `AVAILABLE`

---

## 💰 Pricing Example

| Seat | Type | Base | Peak (+25%) | Weekend (+20%) | **Final** |
|------|------|------|-------------|----------------|-----------|
| A1   | SILVER   | ₹200 | ₹250 | ₹300 | **₹300** |
| B1   | GOLD     | ₹300 | ₹375 | ₹450 | **₹450** |
| C1   | PLATINUM | ₹400 | ₹500 | ₹600 | **₹600** |

*(Saturday 7 PM show — both decorators active)*

---

## 🚀 How to Run

```bash
# From inside the MTBD_Code directory:

# 1. Compile all source files
mkdir -p bin
javac -d bin enums/*.java models/*.java pricing/*.java payment/*.java concurrency/*.java services/*.java Main.java

# 2. Run the demo
java -cp bin Main
```

### Expected Output Highlights

```
▶  [Req 1a] Searching shows for movie: 'Inception'
  ShowID Movie                  Theatre            Start        End          Screen
  ──────────────────────────────────────────────────────────────────────────────────
  SH1    Inception               PVR Juhu           19:00        21:28        Audi 1
  SH4    Inception               INOX Garuda        15:00        17:28        Screen 1

▶  [Req 6 & 7] Seat prices for Show SH1 (Sat 7 PM — Peak + Weekend):
  Seat A1   (SILVER)   → ₹300.00
  Seat B1   (GOLD)     → ₹450.00
  Seat C1   (PLATINUM) → ₹600.00

[Razorpay] Payment SUCCESS. TxnRef: RZP-XXXXXXXX
Booking CONFIRMED! Seats permanently reserved.

[Thread-Charlie] Charlie's booking FAILED — seats taken by Bob!

[BookingService] Booking BK-XXXXXXXX CANCELLED.
Seats B1 & B2 are now AVAILABLE again for others.
```

---

## 📊 UML Diagram

Open `movie_ticket_booking_uml.xml` in **[draw.io](https://app.diagrams.net)**:
`File → Open From → This Device → movie_ticket_booking_uml.xml`

The diagram covers all packages: Enums, Models, Pricing, Payment, Services, and Concurrency with full relationship arrows (composition, association, implements).

---

## 🔮 Possible Extensions

- **Cancellation Policy**: partial refund logic based on time before show
- **Waitlist**: queue users when seats are LOCKED, notify on release
- **Seat Map UI**: 2D seat grid renderer
- **Notification Service**: email/SMS on booking confirmation
- **Admin Panel**: add movies, screens, shows via CRUD APIs
- **Discount / Coupon Strategy**: another decorator in the pricing chain
