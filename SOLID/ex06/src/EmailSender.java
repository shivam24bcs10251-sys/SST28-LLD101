public class EmailSender extends NotificationSender {

    protected SendResult doSend(Notification n) {

        String output =
            "EMAIL -> to=" + n.to +
            " subject=" + n.subject +
            " body=" + n.body;

        System.out.println(output);

        return SendResult.ok(output);
    }
}