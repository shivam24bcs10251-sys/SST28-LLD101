public class Main {

    public static void main(String[] args) {

        System.out.println("=== Notification Demo ===");

        Notification notification =
            new Notification(
                "riya@sst.edu",
                "Welcome",
                "Hello and welcome to SST!"
            );

        Notification smsNotification =
            new Notification(
                "9876543210",
                null,
                "Hello and welcome to SST!"
            );

        Notification waNotification =
            new Notification(
                "9876543210",
                null,
                "Hello and welcome to SST!"
            );

        AuditService audit = new AuditService();

        NotificationSender email = new EmailSender();
        NotificationSender sms = new SmsSender();
        NotificationSender wa = new WhatsAppSender();

        audit.record(email.send(notification));
        audit.record(sms.send(smsNotification));
        audit.record(wa.send(waNotification));

        audit.printSummary();
    }
}