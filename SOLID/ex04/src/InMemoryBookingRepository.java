public class InMemoryBookingRepository implements BookingRepository {

    public void save(BookingRequest request) {
        System.out.println("Saved booking: " + request.bookingId);
    }
}