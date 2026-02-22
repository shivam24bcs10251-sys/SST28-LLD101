import java.util.List;

public class BookingRequest {

    public String bookingId;
    public String roomType;
    public List<String> addOns;

    public BookingRequest(String bookingId,
                          String roomType,
                          List<String> addOns) {

        this.bookingId = bookingId;
        this.roomType = roomType;
        this.addOns = addOns;
    }
}