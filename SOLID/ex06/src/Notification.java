public class Notification {

    public String to;
    public String subject;
    public String body;

    public Notification(String to,
                        String subject,
                        String body) {

        this.to = to;
        this.subject = subject;
        this.body = body;
    }
}