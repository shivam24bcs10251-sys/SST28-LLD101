package models;

/**
 * Represents a registered user of the Movie Ticket Booking platform.
 */
public class User {
    private final String id;
    private String name;
    private String email;
    private String phone;

    public User(String id, String name, String email, String phone) {
        this.id    = id;
        this.name  = name;
        this.email = email;
        this.phone = phone;
    }

    // ── Getters ──────────────────────────────────────────────────────────────
    public String getId()    { return id; }
    public String getName()  { return name; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }

    @Override
    public String toString() {
        return "User{id='" + id + "', name='" + name + "', email='" + email + "'}";
    }
}
